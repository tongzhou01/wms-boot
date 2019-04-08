package com.bs.wms.dao;

import com.bs.wms.common.base.BaseDao;
import com.bs.wms.entity.OrderInfo;
import com.bs.wms.query.OrderInfoQuery;
import com.bs.wms.vo.CountVO;
import com.bs.wms.vo.OrderInfoVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderInfoDao extends BaseDao<OrderInfo> {

    List<OrderInfoVO> listOrder(OrderInfoQuery orderInfoQuery);

    OrderInfoVO getOrder(Long orderId);

    Integer selectMaxId();

    List<CountVO> getCount();
}