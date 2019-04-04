package com.bs.wms.service;

import com.bs.wms.common.entity.Page;
import com.bs.wms.common.entity.R;
import com.bs.wms.entity.ItemSpec;
import com.bs.wms.query.ItemSpecQuery;

public interface ItemSpecService {
    Page<ItemSpec> listItemSpec(ItemSpecQuery itemSpecQuery);

    R saveItemSpec(ItemSpec itemSpec);

    R getItemSpec(Long id);

    R updateItemSpec(ItemSpec itemSpec);

    R deleteItemSpec(Long id);
}
