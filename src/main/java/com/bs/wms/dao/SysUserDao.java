package com.bs.wms.dao;

import com.bs.wms.common.base.BaseDao;
import com.bs.wms.entity.SysUser;
import com.bs.wms.query.SysUserQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysUserDao extends BaseDao<SysUser> {
    List<SysUser> listSysUser(SysUserQuery sysUserQuery);

    SysUser getUserByName(String username);
}