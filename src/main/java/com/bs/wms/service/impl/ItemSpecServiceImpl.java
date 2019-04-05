package com.bs.wms.service.impl;

import com.bs.wms.common.entity.Page;
import com.bs.wms.common.entity.R;
import com.bs.wms.dao.ItemSpecDao;
import com.bs.wms.entity.ItemSpec;
import com.bs.wms.query.ItemSpecQuery;
import com.bs.wms.service.ItemSpecService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ItemSpecServiceImpl implements ItemSpecService {

    @Autowired
    ItemSpecDao itemSpecDao;

    @Override
    public Page<ItemSpec> listItemSpec(ItemSpecQuery itemSpecQuery) {
        Page<ItemSpec> page = new Page<>();
        PageHelper.startPage(itemSpecQuery.getPageNum(), itemSpecQuery.getPageSize());//分页
        List<ItemSpec> itemSpecList = itemSpecDao.listItemSpec(itemSpecQuery);// 查询商品规格列表
        page.setRows(itemSpecList);
        return page;
    }

    @Override
    public R saveItemSpec(ItemSpec itemSpec) {
        try {
            // 新增产品规格信息
            itemSpec.setCreateTime(new Date());
            itemSpec.setModifyTime(new Date());
            itemSpecDao.insertSelective(itemSpec);
        } catch (Exception e) {
            return R.error();
        }
        return R.ok();
    }

    @Override
    public R getItemSpec(Long id) {
        return R.setData(itemSpecDao.selectByPrimaryKey(id));
    }

    @Override
    public R updateItemSpec(ItemSpec itemSpec) {
        try {
            // 更新产品规格信息
            itemSpec.setModifyTime(new Date());
            itemSpecDao.updateByPrimaryKeySelective(itemSpec);
        } catch (Exception e) {
            R.error();
        }
        return R.ok();
    }

    @Override
    public R deleteItemSpec(Long id) {
        try {
            // 删除产品规格
            itemSpecDao.deleteByPrimaryKey(id);
        } catch (Exception e) {
            return R.error();
        }
        return R.ok();
    }
}
