package com.william.bc_william_server.controller;


import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.william.bc_william_server.redis.RedisService;
import com.william.bc_william_server.service.IWilliamMenuService;
import com.william.bc_william_server.service.IWilliamRoleService;
import com.william.bc_william_server.utils.ZTreeUtils;
import com.william.constant.Constant;
import com.william.constant.RespCodeAndMsg;
import com.william.pojo.*;
import com.william.pojo.vo.MenuVo;
import com.william.utils.WebUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * <p>
 * 菜单 前端控制器
 * </p>
 *
 * @author mybatisPlusGenerator
 * @since 2019-11-20
 */
@RestController
@RequestMapping("/william-menu")
public class WilliamMenuController {

    @Autowired
    private IWilliamMenuService menuService;

    @Autowired
    private IWilliamRoleService roleService;

    @Autowired
    private RedisService redisService;

    @RequestMapping("/getIndexLeftMenuJson")
    public DataGridView  getIndexLeftMenuJson(WilliamMenu williamMenu) {
        //查询所有菜单
        QueryWrapper<WilliamMenu> queryWrapper=new QueryWrapper<>();
        //设置只能查询菜单
        queryWrapper.eq("type",Constant.TYPE_MNEU);
        queryWrapper.eq("status", Constant.AVAILABLE_TRUE);
        String acUser = redisService.getStr("user");
        ActiverUser activerUser = JSONUtil.toBean(acUser, ActiverUser.class);
        WilliamUser user = activerUser.getWilliamUser();
        List<WilliamMenu> list;
        if(Objects.equals(user.getUserType(),Constant.USER_TYPE_SUPER)) {
            list = menuService.list(queryWrapper);
        }else {
            //根据用户ID+角色+权限去查询
            String userId = user.getId();
            //根据用户ID查询角色
            List<Integer> currentUserRoleIds = roleService.getUserRoleIdsByUid(userId);
            //根据角色ID取到权限和菜单ID
            Set<Integer> pids=new HashSet<>();
            for (Integer rid : currentUserRoleIds) {
                List<Integer> permissionIds = roleService.getRoleMenuAndPIdsByRid(rid);
                pids.addAll(permissionIds);
            }

            //根据角色ID查询权限
            if(pids.size()>0) {
                queryWrapper.in("id", pids);
                list = menuService.list(queryWrapper);
            }else {
                list =new ArrayList<>();
            }
        }
        List<TreeNode> treeNodes = new ArrayList<>();
        for (WilliamMenu p : list) {
            Integer id=p.getId();
            Integer pid=p.getPid();
            String title=p.getTitle();
            String icon=p.getIcon();
            String href=p.getHref();
            Boolean spread = Objects.equals(p.getSpread(),Constant.OPEN_TRUE);
            treeNodes.add(new TreeNode(id, pid, title, icon, href, spread));
        }
        //构造层级关系
        List<TreeNode> listResult = TreeNodeBuilder.build(treeNodes, 1);
        return new DataGridView(listResult);
    }


    /****************菜单管理开始****************/

    /**
     * 加载菜单管理左边的菜单树的json
     */
    @RequestMapping("/getMenuManagerLeftTreeJson")
    public DataGridView getMenuManagerLeftTreeJson(WilliamMenu williamMenu) {
        QueryWrapper<WilliamMenu> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("type", Constant.TYPE_MNEU);
        List<WilliamMenu> list = this.menuService.list(queryWrapper);
        List<TreeNode> treeNodes=new ArrayList<>();
        for (WilliamMenu menu : list) {
            Boolean spread = Objects.equals(menu.getSpread(),Constant.OPEN_TRUE);
            treeNodes.add(new TreeNode(menu.getId(), menu.getPid(), menu.getTitle(), spread));
        }
        return new DataGridView(treeNodes);
    }

    /**
     * 查询菜单
     * @author     xinchuang
     * @param menuVo :
     * @return : com.william.pojo.DataGridView
     */
    @RequestMapping("/getAllMenu")
    public DataGridView getAllMenu(MenuVo menuVo) {
        IPage<WilliamMenu> page=new Page<>(menuVo.getPage(), menuVo.getLimit());
        QueryWrapper<WilliamMenu> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq(menuVo.getId()!=null, "id", menuVo.getId()).or().eq(menuVo.getId()!=null,"pid", menuVo.getId());
        //只查询菜单
        queryWrapper.eq("type", Constant.TYPE_MNEU);
        queryWrapper.like(StringUtils.isNotBlank(menuVo.getTitle()), "title", menuVo.getTitle());
        queryWrapper.orderByAsc("seq");
        this.menuService.page(page, queryWrapper);
        return new DataGridView(page.getTotal(), page.getRecords());
    }

    /**
     * 加载最大的排序码
     * @param permissionVo
     * @return
     */
    @RequestMapping("getMenuMaxOrderNum")
    public Map<String,Object> getMenuMaxOrderNum(){
        Map<String, Object> map=new HashMap<String, Object>();
        QueryWrapper<WilliamMenu> queryWrapper=new QueryWrapper<>();
        queryWrapper.orderByDesc("ordernum");
        IPage<WilliamMenu> page=new Page<>(1, 1);
        List<WilliamMenu> list = this.menuService.page(page, queryWrapper).getRecords();
        if(list.size()>0) {
            map.put("value", list.get(0).getSeq() + 1);
        }else {
            map.put("value", 1);
        }
        return map;
    }


    /**
     * 添加菜单
     * @author     xinchuang
     * @param williamMenu :
     * @return : com.william.pojo.Result
     */
    @RequestMapping("addMenu")
    public Result addMenu(WilliamMenu williamMenu) {
        //设置添加类型
        williamMenu.setType(Constant.TYPE_MNEU);
        williamMenu.setEntDate(new Date());
        this.menuService.save(williamMenu);
        return Result.getResult(RespCodeAndMsg.ADD_SUCCESS);
    }


    /**
     * 修改菜单
     * @author     xinchuang
     * @param williamMenu :
     * @return : com.william.pojo.Result
     */
    @RequestMapping("updateMenu")
    public Result updateMenu(WilliamMenu williamMenu) {
        this.menuService.updateById(williamMenu);
        return Result.getResult(RespCodeAndMsg.UPDATE_SUCCESS);
    }


    /**
     * 查询当前的ID的菜单有没有子菜单
     */
    @RequestMapping("getMenuHasChildrenNode")
    public Map<String,Object> getMenuHasChildrenNode(WilliamMenu williamMenu){
        Map<String, Object> map=new HashMap<String, Object>();

        QueryWrapper<WilliamMenu> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("pid", williamMenu.getId());
        List<WilliamMenu> list = this.menuService.list(queryWrapper);
        if(list.size()>0) {
            map.put("value", true);
        }else {
            map.put("value", false);
        }
        return map;
    }

    /**
     * 删除菜单
     * @author     xinchuang
     * @param permissionVo :
     * @return : com.william.pojo.Result
     */
    @RequestMapping("deleteMenu")
    public Result deleteMenu(WilliamMenu permissionVo) {
        this.menuService.removeById(permissionVo.getId());
        return Result.getResult(RespCodeAndMsg.UPDATE_SUCCESS);
    }

    /****************菜单管理结束****************/

}
