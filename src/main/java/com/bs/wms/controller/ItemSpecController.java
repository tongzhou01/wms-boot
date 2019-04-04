package com.bs.wms.controller;

import com.bs.wms.common.entity.Page;
import com.bs.wms.common.entity.R;
import com.bs.wms.entity.ItemSpec;
import com.bs.wms.query.ItemSpecQuery;
import com.bs.wms.service.ItemSpecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/spec")
public class ItemSpecController {

    @Autowired
    ItemSpecService itemSpecService;

    /**
     * 获取产品规格列表
     *
     * @param itemSpecQuery
     * @return
     */
    @GetMapping
    public Page<ItemSpec> getItemSpecList(ItemSpecQuery itemSpecQuery) {
        return itemSpecService.listItemSpec(itemSpecQuery);
    }

    /**
     * 创建产品规格
     *
     * @param itemSpec
     * @return
     */
    @PostMapping(value = "/")
    @ResponseStatus(HttpStatus.CREATED)
    public R saveItemSpec(@RequestBody ItemSpec itemSpec) {
        return itemSpecService.saveItemSpec(itemSpec);
    }

    /**
     * 获取产品规格详情
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R getItemSpec(@PathVariable Long id) {
        return itemSpecService.getItemSpec(id);
    }

    /**
     * 更新产品规格信息
     *
     * @param itemSpec
     * @return
     */
    @PutMapping
    public R updateItemSpec(@RequestBody ItemSpec itemSpec) {
        return itemSpecService.updateItemSpec(itemSpec);
    }

    /**
     * 删除产品规格
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public R deleteItemSpec(@PathVariable Long id) {
        return itemSpecService.deleteItemSpec(id);
    }
}
