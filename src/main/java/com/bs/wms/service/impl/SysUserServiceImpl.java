package com.bs.wms.service.impl;

import com.bs.wms.common.entity.Page;
import com.bs.wms.common.entity.R;
import com.bs.wms.dao.SysUserDao;
import com.bs.wms.entity.SysUser;
import com.bs.wms.query.SysUserQuery;
import com.bs.wms.service.SysUserService;
import com.bs.wms.util.MD5Util;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    SysUserDao sysUserDao;

    @Override
    public Page<SysUser> listSysUser(SysUserQuery sysUserQuery) {
        Page<SysUser> page = new Page<>();
        PageHelper.startPage(sysUserQuery.getPageNum(), sysUserQuery.getPageSize());//分页
        List<SysUser> sysUserList = sysUserDao.listSysUser(sysUserQuery);// 查询用户列表
        page.setRows(sysUserList);
        return page;
    }

    @Override
    public R saveSysUser(SysUser sysUser) {
        try {
            // 新增用户
            sysUser.setPassword(MD5Util.MD5Encode(sysUser.getPassword()));
            sysUser.setCreateTime(new Date());
            sysUser.setModifyTime(new Date());
            sysUserDao.insertSelective(sysUser);
        } catch (Exception e) {
            return R.error();
        }
        return R.ok();
    }

    @Override
    public R getSysUser(Long id) {
        return R.setData(sysUserDao.selectByPrimaryKey(id));
    }

    @Override
    public R updateSysUser(SysUser sysUser) {
        try {
            // 更新用户信息
            sysUser.setPassword(MD5Util.MD5Encode(sysUser.getPassword()));
            sysUser.setModifyTime(new Date());
            sysUserDao.updateByPrimaryKeySelective(sysUser);
        } catch (Exception e) {
            R.error("更新异常");
        }
        return R.ok();
    }

    @Override
    public R deleteSysUser(Long id) {
        try {
            // 删除用户
            sysUserDao.deleteByPrimaryKey(id);
        } catch (Exception e) {
            return R.error();
        }
        return R.ok();
    }

    @Override
    public SysUser getUserByName(String username) {
        return sysUserDao.getUserByName(username);
    }
}
