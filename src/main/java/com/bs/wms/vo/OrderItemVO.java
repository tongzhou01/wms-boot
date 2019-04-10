package com.bs.wms.vo;

import com.bs.wms.entity.ItemSpec;
import com.bs.wms.entity.OrderItem;

public class OrderItemVO extends OrderItem {

    private ItemSpec itemSpec;

    public ItemSpec getItemSpec() {
        return itemSpec;
    }

    public void setItemSpec(ItemSpec itemSpec) {
        this.itemSpec = itemSpec;
    }
}
