package com.bs.wms.service.impl;

import com.bs.wms.dao.SysUserDao;
import com.bs.wms.entity.SysUser;
import com.bs.wms.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    SysUserDao sysUserDao;

    @Override
    public SysUser getUserById(Long id) {
        return sysUserDao.selectByPrimaryKey(id);
    }
}
