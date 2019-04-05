package com.bs.wms.service;

import com.bs.wms.common.entity.Page;
import com.bs.wms.common.entity.R;
import com.bs.wms.entity.SysUser;
import com.bs.wms.query.SysUserQuery;

public interface SysUserService {
    Page<SysUser> listSysUser(SysUserQuery itemSpecQuery);

    R saveSysUser(SysUser itemSpec);

    R getSysUser(Long id);

    R updateSysUser(SysUser itemSpec);

    R deleteSysUser(Long id);

    SysUser getUserByName(String username);
}
