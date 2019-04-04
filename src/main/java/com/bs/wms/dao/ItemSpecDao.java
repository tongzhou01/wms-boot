package com.bs.wms.dao;

import com.bs.wms.common.base.BaseDao;
import com.bs.wms.entity.ItemSpec;
import com.bs.wms.query.ItemSpecQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemSpecDao extends BaseDao<ItemSpec> {
    List<ItemSpec> listItemSpec(ItemSpecQuery itemSpecQuery);
}