package com.bs.wms.common;

/**
 * 通用接口
 *
 * @param <T>
 */
public interface BaseDao<T> {

    int deleteByPrimaryKey(Long id);

    int insert(T record);

    int insertSelective(T record);

    T selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(T record);

    int updateByPrimaryKey(T record);
}
