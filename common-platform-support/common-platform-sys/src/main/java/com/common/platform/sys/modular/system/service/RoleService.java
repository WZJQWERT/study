package com.common.platform.sys.modular.system.service;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.common.platform.base.config.sys.Const;
import com.common.platform.base.exception.BizExceptionEnum;
import com.common.platform.base.exception.ServiceException;
import com.common.platform.base.page.factory.LayuiPageFactory;
import com.common.platform.base.tree.node.ZTreeNode;
import com.common.platform.base.utils.CoreUtil;
import com.common.platform.sys.exception.RequestEmptyException;
import com.common.platform.sys.factory.ConstantFactory;
import com.common.platform.sys.log.LogObjectHolder;
import com.common.platform.sys.modular.system.entity.Relation;
import com.common.platform.sys.modular.system.entity.Role;
import com.common.platform.sys.modular.system.mapper.RelationMapper;
import com.common.platform.sys.modular.system.mapper.RoleMapper;
import com.common.platform.sys.modular.system.model.RoleDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色表 服务类
 * </p>
 */
@Service
public class RoleService extends ServiceImpl<RoleMapper,Role> {

    @Resource
    private RelationMapper relationMapper;

    /**
     * 添加角色
     */
    @Transactional(rollbackFor = Exception.class)
    public void addRole(Role role){
        if(CoreUtil.isOneEmpty(role,role.getName(),role.getPid(),role.getDescription())){
            throw new RequestEmptyException();
        }
        this.save(role);
    }

    /**
     * 编辑角色
     */
    @Transactional(rollbackFor = Exception.class)
    public void editRole(RoleDto roleDto){
        if(CoreUtil.isOneEmpty(roleDto,roleDto.getName(),roleDto.getPid(),roleDto.getDescription())){
            throw new RequestEmptyException();
        }
        Role oldRole = this.getById(roleDto.getRoleId());
        CoreUtil.copyProperties(roleDto,oldRole);
        this.updateById(oldRole);
    }

    /**
     * 设置某个角色的权限
     */
    @Transactional(rollbackFor = Exception.class)
    public void setAuthority(Long roleId,String ids){
        this.baseMapper.deleteRolesById(roleId);
        for(Long id : Convert.toLongArray(ids.split(","))){
            Relation relation = new Relation();
            relation.setMenuId(id);
            relation.setRoleId(roleId);
            this.relationMapper.insert(relation);
        }
    }

    /**
     * 删除角色
     */
    @Transactional(rollbackFor = Exception.class)
    public void delRoleById(Long roleId){
        if(CoreUtil.isEmpty(roleId)){
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        if(roleId.equals(Const.ADMIN_ROLE_ID)){
            throw new ServiceException(BizExceptionEnum.CANT_DELETE_ADMIN);
        }
        LogObjectHolder.me().set(ConstantFactory.me().getSingleRoleName(roleId));
        this.baseMapper.deleteById(roleId);
        this.baseMapper.deleteRolesById(roleId);
    }

    /**
     * 根据查询条件查询角色列表
     */
    public Page<Map<String,Object>> selectRoles(String condition){
        Page page = LayuiPageFactory.defaultPage();
        return this.baseMapper.selectRoles(page,condition);
    }

    /**
     * 删除某个角色的所有权限
     */
    public int deleteRolesById(Long roleId){
        return this.baseMapper.deleteRolesById(roleId);
    }

    /**
     * 获取角色树形
     */
    public List<ZTreeNode> roleTreeList(){
        return this.baseMapper.roleTreeList();
    }

    /**
     * 获取角色树形
     */
    public List<ZTreeNode> roleTreeListByRoleId(Long[] roleId){
        return this.baseMapper.roleTreeListByRoleId(roleId);
    }

    /**
     * 获取角色列表
     */
    public IPage listRole(String name){
        Page pageContext = LayuiPageFactory.defaultPage();
        return this.baseMapper.listRole(pageContext,name);
    }
}
