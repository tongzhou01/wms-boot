package com.bs.wms.service.impl;

import com.alibaba.fastjson.JSON;
import com.bs.wms.common.entity.Page;
import com.bs.wms.common.entity.R;
import com.bs.wms.constant.BaseConstant;
import com.bs.wms.dao.OrderInfoDao;
import com.bs.wms.dao.OrderItemDao;
import com.bs.wms.dto.SaveOrderDTO;
import com.bs.wms.dto.SendEmailDTO;
import com.bs.wms.entity.ItemSpec;
import com.bs.wms.entity.OrderInfo;
import com.bs.wms.entity.OrderItem;
import com.bs.wms.query.OrderInfoQuery;
import com.bs.wms.service.OrderInfoService;
import com.bs.wms.util.DateUtil;
import com.bs.wms.util.MailUtil;
import com.bs.wms.vo.CountDataVO;
import com.bs.wms.vo.CountVO;
import com.bs.wms.vo.OrderInfoVO;
import com.bs.wms.vo.OrderItemVO;
import com.github.pagehelper.PageHelper;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderInfoServiceImpl implements OrderInfoService {

    @Autowired
    OrderInfoDao orderInfoDao;

    @Autowired
    OrderItemDao orderItemDao;

    @Override
    public Page<OrderInfoVO> listOrder(OrderInfoQuery orderInfoQuery) {
        Page<OrderInfoVO> page = new Page<>();
        PageHelper.startPage(orderInfoQuery.getPageNum(), orderInfoQuery.getPageSize());//分页查询
        List<OrderInfoVO> orderInfoVOList = orderInfoDao.listOrder(orderInfoQuery);
        page.setRows(orderInfoVOList);
        return page;
    }

    @Override
    public R saveOrder(SaveOrderDTO saveOrderDTO) {
        List<OrderItem> orderItems = saveOrderDTO.getOrderItems();
        OrderInfo orderInfo = saveOrderDTO.getOrderInfo();
        try {
            // 新增订单信息
            Integer maxId = orderInfoDao.selectMaxId();
            if (maxId == null) {
                maxId = 1;
            }
            String orderNo = DateUtil.getCurrentTimeByDay().replace("-", "").concat(Integer.toString(maxId + 10001));// 生成订单号
            orderInfo.setOrderNo(orderNo);
            orderInfo.setCreateTime(new Date());
            orderInfo.setModifyTime(new Date());
            orderInfo.setSignStatus(orderInfo.getSignPerson() == null ? (byte) 0 : (byte) 1);
            // 计算商品总价
            BigDecimal totalAmount = orderItems.stream().map(OrderItem::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
            orderInfo.setTotalAmount(totalAmount);
            orderInfoDao.insertSelective(orderInfo);
            Long orderId = orderInfo.getId();
            if (orderId != null && orderItems != null && orderItems.size() > 0) {
                // 新增商品信息
                orderItems.forEach(orderItem -> orderItem.setOrderId(orderId));
                orderItemDao.batchSave(orderItems);
            }
        } catch (Exception e) {
            return R.error();
        }
        return R.ok();
    }

    @Override
    public R getOrder(Long id) {
        return R.setData(orderInfoDao.getOrder(id));
    }

    @Override
    public R updateOrder(SaveOrderDTO saveOrderDTO) {
        OrderInfo orderInfo = saveOrderDTO.getOrderInfo();
        List<OrderItem> orderItems = saveOrderDTO.getOrderItems();
        try {
            // 更新订单信息
            orderInfo.setModifyTime(new Date());
            orderInfoDao.updateByPrimaryKeySelective(orderInfo);
            // 更新商品信息
            orderItems.forEach(orderItem -> {
                orderItem.setModifyTime(new Date());
                orderItemDao.updateByPrimaryKeySelective(orderItem);
            });
        } catch (Exception e) {
            R.error();
        }
        return R.ok();
    }

    @Override
    public R deleteOrder(Long id) {
        try {
            // 删除订单
            int i = orderInfoDao.deleteByPrimaryKey(id);
            if (i > 0) {
                // 删除商品
                orderItemDao.deleteByOrderId(id);
            }
        } catch (Exception e) {
            return R.error();
        }
        return R.ok();
    }

    @Override
    public R sendMail(SendEmailDTO sendEmailDTO) {
        try {
            R r = exportExcel(sendEmailDTO.getOrderId());
            if (r.get("code").toString().equals("0"))
            MailUtil.sendMail(sendEmailDTO.getReceiverAddress(), "订单附件", "<p>订单附件，请查收。<p>", new File(r.get("filePath").toString()));
        } catch (Exception e) {
            return R.error();
        }
        return R.ok("发送成功");
    }

    @Override
    public R getCount(Integer year, Integer month) {
        String yearDay = year + "-" + (month < 10 ? "0" + month : month);// 月份<10 前面补0
        List<CountVO> countVOList = orderInfoDao.getCount(yearDay);// 查询统计信息
        Map<String, List<CountVO>> companyNameGroup = countVOList.stream().collect(Collectors.groupingBy(CountVO::getCompanyName));// 按公司名称分组
        List list = new ArrayList();// 接收处理完的数据
        int days = DateUtil.getDaysByYearMonth(year, month);
        companyNameGroup.forEach((key, countVOS) -> {// 遍历每个公司
            CountDataVO countDataVO = new CountDataVO();
            BigDecimal[] countArr = initArray(days);// 每天的交易金额初始化为0
            countVOS.forEach(countVO -> {// 处理每个公司的统计数据，无数据为0，有数据填充
                countArr[countVO.getDay() - 1] = countVO.getTotalAmount();// 数据填充
            });
            countDataVO.setName(key);
            countDataVO.setType(BaseConstant.LINE_TYPE);// 线型图
            countDataVO.setData(countArr);
            list.add(countDataVO);
        });

        // 统计求和
        CountDataVO countDataVO = new CountDataVO();
        BigDecimal[] totalArr = initArray(days);
        Map<Integer, List<CountVO>> dayGroup = countVOList.stream().collect(Collectors.groupingBy(CountVO::getDay));// 按天分组
        dayGroup.forEach((day, countVOS) -> {// 遍历每一天
            BigDecimal totalAmountOfDay = countVOS.stream().map(CountVO::getTotalAmount).reduce(BigDecimal.ZERO, BigDecimal::add);// 一天总金额
            totalArr[day - 1] = totalAmountOfDay;
        });
        countDataVO.setType(BaseConstant.LINE_TYPE);
        countDataVO.setName("总计");
        countDataVO.setData(totalArr);
        list.add(countDataVO);

        // X轴坐标值
        int[] categories = new int[days];
        for (int i = 0; i < categories.length; i++) {
            categories[i] = i + 1;
        }
        return R.setData(list).put("categories", categories).put("legend", companyNameGroup.keySet());
    }

    /**
     * 初始化数组
     *
     * @param size
     * @return
     */
    private BigDecimal[] initArray(int size) {
        BigDecimal[] decimalArr = new BigDecimal[size];
        for (int i = 0; i < decimalArr.length; i++) {
            decimalArr[i] = BigDecimal.ZERO;
        }
        return decimalArr;
    }

    @Override
    public R exportExcel(Long id) throws IOException {
        OrderInfoVO order = orderInfoDao.getOrder(id);
        String fileName = UUID.randomUUID().toString().concat(".xls");
        String path = BaseConstant.FILE_OUTPUT_EXCEL_PATH.concat(fileName);

        File fileDir = new File(BaseConstant.FILE_OUTPUT_EXCEL_PATH);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }

        //创建EXCEL sheet
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("sheet1");
        sheet.setDefaultColumnWidth(10);

        CellStyle titleStyle = workbook.createCellStyle();
        //边框
        titleStyle.setBorderBottom(BorderStyle.NONE);
        titleStyle.setBorderLeft(BorderStyle.NONE);
        titleStyle.setBorderTop(BorderStyle.NONE);
        titleStyle.setBorderRight(BorderStyle.NONE);
        titleStyle.setAlignment(HorizontalAlignment.CENTER); // 居中
        Font titleFont = workbook.createFont();
        titleFont.setFontHeightInPoints((short) 16);
        titleFont.setBold(true);
        titleStyle.setFont(titleFont);
        //创建EXCEL 标题
        int rows = 0;// 全局行数
        Row row = sheet.createRow(rows++);// 第一行
        Cell cell = row.createCell(0);
        cell.setCellStyle(titleStyle);
        cell.setCellValue(order.getOrderName());// 订单名称
        //合并单元格
        CellRangeAddress titleCellRangeAddress = new CellRangeAddress(0, 0, 0, 6);
        sheet.addMergedRegion(titleCellRangeAddress);
        setBorderStyle(titleCellRangeAddress, sheet);

        //二级标题
        CellStyle style = getCellStyle(workbook);
        sheet.setDefaultColumnWidth(12);
        Font font = workbook.createFont();
        font.setBold(false);
        //自动换行
        style.setWrapText(true);
        style.setFont(font);


        String[] firstRowValue = new String[4];// 内容
        int[] firstRowIndex = new int[4];
        firstRowIndex[0] = 0;
        firstRowIndex[1] = 1;
        firstRowIndex[2] = 5;
        firstRowIndex[3] = 6;
        firstRowValue[0] = "客户名称：";
        firstRowValue[1] = order.getCustomerName();
        firstRowValue[2] = "订单号：";
        firstRowValue[3] = order.getOrderNo();
        Row row1 = sheet.createRow(rows++);// 第二行
        for (int i = 0; i < firstRowValue.length; i++) {
            cell = row1.createCell(firstRowIndex[i]);
            cell.setCellStyle(style);
            cell.setCellValue(firstRowValue[i]);
        }
        String[] secondRowValue = new String[4];// 内容
        secondRowValue[0] = "送货地址：";
        secondRowValue[1] = order.getDeliveryAddress();
        secondRowValue[2] = "送货日期：";
        secondRowValue[3] = order.getDeliveryDate() == null ? "" : DateUtil.dateFormat(order.getDeliveryDate());
        Row row2 = sheet.createRow(rows++);// 第三行
        for (int i = 0; i < secondRowValue.length; i++) {
            cell = row2.createCell(firstRowIndex[i]);
            cell.setCellStyle(style);
            cell.setCellValue(secondRowValue[i]);
        }

        CellStyle itemCellStyle = getItemCellStyle(workbook);
        //创建标题
        Row row3 = sheet.createRow(rows++);// 第四行
        for (int i = 0; i < BaseConstant.ITEM_TITLE.length; i++) {
            cell = row3.createCell(i);
            cell.setCellValue(BaseConstant.ITEM_TITLE[i]);
            cell.setCellStyle(itemCellStyle);
        }

        //创建内容
        Row rowItem;// 从第五行开始
        List<OrderItemVO> orderItems = order.getOrderItems();
        for (int i = 0; i < orderItems.size(); i++) {
            rowItem = sheet.createRow(rows++);
            OrderItemVO orderItemVO = orderItems.get(i);
            ItemSpec itemSpec = orderItemVO.getItemSpec();
            rowItem.createCell(0).setCellValue(i + 1);
            rowItem.createCell(1).setCellValue(itemSpec == null ? "" : itemSpec.getName());
            rowItem.createCell(2).setCellValue(orderItemVO.getReserveNumber() + "|" + itemSpec == null ? "" : itemSpec.getUnit());
            rowItem.createCell(3).setCellValue(orderItemVO.getDeliveryNumber() + "|" + itemSpec ==null ? "" : itemSpec.getUnit());
            rowItem.createCell(4).setCellValue(orderItemVO.getUnitPrice() == null ? "" : orderItemVO.getUnitPrice().toString());
            rowItem.createCell(5).setCellValue(orderItemVO.getAmount() == null ? "" : orderItemVO.getAmount().toString());
            rowItem.createCell(6).setCellValue(orderItemVO.getRemark());
            Iterator<Cell> cellIterator = rowItem.cellIterator();
            while (cellIterator.hasNext()) {
                cellIterator.next().setCellStyle(itemCellStyle);
            }
        }
        // 合计行
        Row rowItemLast = sheet.createRow(rows++);
        rowItemLast.createCell(0).setCellValue("合计金额（元）：");
        rowItemLast.createCell(5).setCellValue(order.getTotalAmount() == null ? "" : order.getTotalAmount().toString());
        rowItemLast.createCell(6).setCellStyle(itemCellStyle);
        rowItemLast.getCell(5).setCellStyle(itemCellStyle);
        //合并单元格
        CellRangeAddress titleCellRangeAddress1 = new CellRangeAddress(rows-1, rows-1, 0, 4);
        sheet.addMergedRegion(titleCellRangeAddress1);
        setItemBorderStyle(titleCellRangeAddress1, sheet);

        String[] lastRowValue = new String[4];// 内容
        lastRowValue[0] = "订货电话：";
        lastRowValue[1] = order.getOrderPhone();
        lastRowValue[2] = "地址：";
        lastRowValue[3] = order.getOrderAddress();
        int[] lastRowIndex = new int[6];
        lastRowIndex[0] = 0;
        lastRowIndex[1] = 1;
        lastRowIndex[2] = 3;
        lastRowIndex[3] = 4;
        lastRowIndex[4] = 5;
        lastRowIndex[5] = 6;
        Row row5 = sheet.createRow(rows++);// 倒数第二行
        for (int i = 0; i < firstRowValue.length; i++) {
            cell = row5.createCell(lastRowIndex[i]);
            cell.setCellStyle(style);
            cell.setCellValue(firstRowValue[i]);
        }
        String[] lastRowValue1 = new String[6];// 内容
        lastRowValue1[0] = "客户签收：";
        lastRowValue1[1] = order.getSignPerson();
        lastRowValue1[2] = "送货人：";
        lastRowValue1[3] = order.getDeliveryPerson();
        lastRowValue1[4] = "开票人：";
        lastRowValue1[5] = order.getInvoicePerson();
        Row row6 = sheet.createRow(rows++);// 倒数第一行
        for (int i = 0; i < lastRowValue1.length; i++) {
            cell = row6.createCell(lastRowIndex[i]);
            cell.setCellStyle(style);
            cell.setCellValue(lastRowValue1[i]);
        }

        OutputStream os = null;
        try {
            //创建文件
            File file = new File(path);
            if (!file.exists()) {
                file.createNewFile();
            }
            os = new FileOutputStream(file);
            workbook.write(os);
        } catch (Exception e) {
            return R.error();
        } finally {
            if (os != null) {
                os.flush();
                os.close();
            }
        }
        return R.ok().put("filePath", path).put("fileName", fileName);
    }

    private void setBorderStyle(CellRangeAddress cra, Sheet sheet) {
        RegionUtil.setBorderBottom(BorderStyle.NONE, cra, sheet);
        RegionUtil.setBorderLeft(BorderStyle.NONE, cra, sheet);
        RegionUtil.setBorderRight(BorderStyle.NONE, cra, sheet);
        RegionUtil.setBorderTop(BorderStyle.NONE, cra, sheet);
    }
    private void setItemBorderStyle(CellRangeAddress cra, Sheet sheet) {
        RegionUtil.setBorderBottom(BorderStyle.THIN, cra, sheet);
        RegionUtil.setBorderLeft(BorderStyle.THIN, cra, sheet);
        RegionUtil.setBorderRight(BorderStyle.THIN, cra, sheet);
        RegionUtil.setBorderTop(BorderStyle.THIN, cra, sheet);
    }

    private CellStyle getCellStyle(Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        //边框
        cellStyle.setBorderBottom(BorderStyle.NONE);
        cellStyle.setBorderLeft(BorderStyle.NONE);
        cellStyle.setBorderTop(BorderStyle.NONE);
        cellStyle.setBorderRight(BorderStyle.NONE);
        //左右居中
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        return cellStyle;
    }

    private CellStyle getItemCellStyle(Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        //边框
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        //左右居中
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        return cellStyle;
    }

    /**
     * 测试
     *
     * @param args
     */
    public static void main(String[] args) {
        CountVO countVO1 = new CountVO("小米", 2019, 4, 1, BigDecimal.ONE);
        CountVO countVO2 = new CountVO("小米", 2019, 4, 2, BigDecimal.ONE);
        CountVO countVO3 = new CountVO("小米", 2019, 4, 2, BigDecimal.ONE);
        CountVO countVO4 = new CountVO("大米", 2019, 4, 4, BigDecimal.ONE);
        CountVO countVO5 = new CountVO("大米", 2019, 4, 5, BigDecimal.ONE);
        List<CountVO> countVOList = new ArrayList<>();
        countVOList.add(countVO1);
        countVOList.add(countVO2);
        countVOList.add(countVO3);
        countVOList.add(countVO4);
        countVOList.add(countVO5);
        int days = DateUtil.getDaysByYearMonth(2019, 4);

        Map<String, List<CountVO>> companyNameGroup = countVOList.stream().collect(Collectors.groupingBy(CountVO::getCompanyName));

        Map<Integer, List<CountVO>> dayGroup = countVOList.stream().collect(Collectors.groupingBy(CountVO::getDay));// 按天分组
        BigDecimal[] totalArr = new OrderInfoServiceImpl().initArray(days);
        dayGroup.forEach((day, countVOS) -> {// 遍历每一天
            BigDecimal totalAmountOfDay = countVOS.stream().map(CountVO::getTotalAmount).reduce(BigDecimal.ZERO, BigDecimal::add);// 一天总金额
            totalArr[day - 1] = totalAmountOfDay;
        });

        System.out.println(JSON.toJSONString(companyNameGroup));
        System.out.println(JSON.toJSONString(dayGroup));
        System.out.println("totalArr=" + JSON.toJSONString(totalArr));
        List list = new ArrayList();
        System.out.println(days);
        companyNameGroup.forEach((key, countVOS) -> {
            CountDataVO countDataVO = new CountDataVO();
            BigDecimal[] countArr = new OrderInfoServiceImpl().initArray(days);
            countVOS.forEach(countVO -> {
                countArr[countVO.getDay() - 1] = countVO.getTotalAmount();
            });
            countDataVO.setName(key);
            countDataVO.setData(countArr);
            list.add(countDataVO);
        });
        System.out.println("list = " + JSON.toJSONString(list));
    }
}
