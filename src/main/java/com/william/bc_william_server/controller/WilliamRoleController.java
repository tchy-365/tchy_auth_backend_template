package com.william.bc_william_server.controller;


import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.william.bc_william_server.service.IWilliamMenuService;
import com.william.bc_william_server.service.IWilliamRoleService;
import com.william.constant.Constant;
import com.william.constant.RespCodeAndMsg;
import com.william.pojo.*;
import com.william.pojo.vo.RoleVo;
import com.william.utils.ObjectId;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author mybatisPlusGenerator
 * @since 2019-11-20
 */
@RestController
@RequestMapping("/william-role")
public class WilliamRoleController {

    @Autowired
    private IWilliamRoleService iWilliamRoleService;

    @Autowired
    private IWilliamMenuService iWilliamMenuService;

    /**
     * 获取角色菜单
     * @author     xinchuang
     * @param roleVo :
     * @return : com.william.pojo.DataGridView
     */
    @GetMapping(value = "/getRolesList")
    public DataGridView getRolesList(RoleVo roleVo){
        IPage<WilliamRole> page=new Page<>(roleVo.getPage(), roleVo.getLimit());
        QueryWrapper<WilliamRole> queryWrapper=new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(roleVo.getName()), "name", roleVo.getName());
        queryWrapper.like(StringUtils.isNotBlank(roleVo.getDescription()), "description", roleVo.getDescription());
        queryWrapper.eq(roleVo.getStatus()!=null, "status", roleVo.getStatus());
        queryWrapper.orderByDesc("ent_time");
        this.iWilliamRoleService.page(page, queryWrapper);
        return new DataGridView(page.getTotal(), page.getRecords());
    }

    /**
     * 添加
     */
    @PostMapping("/addRole")
    public Result addRole(RoleVo roleVo) {
        // 创建人
//        roleVo.setEntName(loginName);
        roleVo.setEntTime(new Date());
        this.iWilliamRoleService.save(roleVo);
        return Result.getResult(RespCodeAndMsg.ADD_SUCCESS);
    }


    /**
     * 修改
     */
    @RequestMapping("/updateRole")
    public Result updateRole(RoleVo roleVo) {
        this.iWilliamRoleService.updateById(roleVo);
        return Result.getResult(RespCodeAndMsg.UPDATE_SUCCESS);
    }

    /**
     * 删除
     */
    @RequestMapping("/deleteRole")
    public Result deleteRole(Integer id) {
        this.iWilliamRoleService.removeById(id);
        return Result.getResult(RespCodeAndMsg.DISPATCH_SUCCESS);
    }


    /**
     * 根据角色ID加载菜单和权限的树的json串
     */
    @RequestMapping("/initPermissionByRoleId")
    public DataGridView initPermissionByRoleId(String roleId) {
        //查询所有可用的菜单和权限
        QueryWrapper<WilliamMenu> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("status", Constant.AVAILABLE_TRUE);
        List<WilliamMenu> allPermissions = iWilliamMenuService.list(queryWrapper);
        /**
         * 1,根据角色ID查询当前角色拥有的所有的权限或菜单ID
         * 2,根据查询出来的菜单ID查询权限和菜单数据
         */
        List<Integer> currentRolePermissions=this.iWilliamMenuService.queryRolePermissionIdsByRid(roleId);
        List<WilliamMenu> carrentPermissions=null;
        if(currentRolePermissions.size()>0) { //如果有ID就去查询
            queryWrapper.in("id", currentRolePermissions);
            carrentPermissions = iWilliamMenuService.list(queryWrapper);
        }else {
            carrentPermissions=new ArrayList<>();
        }
        //构造 List<TreeNode>
        List<TreeNode> nodes=new ArrayList<>();
        for (WilliamMenu p1 : allPermissions) {
            String checkArr="0";
            for (WilliamMenu p2 : carrentPermissions) {
                if(Objects.equals(p1.getId(),p2.getId())) {
                    checkArr="1";
                    break;
                }
            }
            Boolean spread=Objects.equals(p1.getSpread(),1);
            nodes.add(new TreeNode(p1.getId(), p1.getPid(), p1.getTitle(), spread, checkArr));
        }
        return new DataGridView(nodes);
    }

    /**
     * 保存角色和菜单权限之间的关系
     */
    @RequestMapping("saveRolePermission")
    public Result saveRolePermission(Integer rid,Integer[] ids) {
        this.iWilliamRoleService.saveRolePermission(rid,ids);
        return Result.getResult(RespCodeAndMsg.DISPATCH_SUCCESS);
    }


}
