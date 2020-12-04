package com.william.bc_william_server.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.william.bc_william_server.service.impl.WilliamMenuServiceImpl;
import com.william.pojo.WilliamMenu;
import com.william.pojo.ZTreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author xinchuang
 * @version v1.0
 * @date 2019/11/17 11:50
 * @since Copyright(c) 爱睿智健康科技
 */
@Component
public class ZTreeUtils {

    @Autowired
    private WilliamMenuServiceImpl williamMenuService;

    public static ZTreeNode toZTreeNode(WilliamMenu menu){
        ZTreeNode node = new ZTreeNode();
        node.setId(menu.getId());
        node.setTitle(menu.getTitle());
        node.setIcon(menu.getIcon());
        node.setHref(menu.getHref());
        node.setSpread(menu.getSpread() == 1?true:false);
        node.setTarget(menu.getTarget());
        node.setPid(menu.getPid());
        node.setPermission(menu.getPermission());
        return node;
    }


    /**
     * 构建树
     * @author     xinchuang
     * @param menuList :
     * @return : java.util.List<com.william.bc_william_server.pojo.tree.ZTreeNode>
     */
    public List<ZTreeNode> toZTreeNode(List<WilliamMenu> menuList){
        List<ZTreeNode> list = new ArrayList<ZTreeNode>();
        for(WilliamMenu menu : menuList){
            ZTreeNode node = toZTreeNode(menu);
            QueryWrapper<WilliamMenu> objectQueryWrapper = new QueryWrapper<>();
            objectQueryWrapper.eq("pid",menu.getId());
            List<WilliamMenu> williamMenus = williamMenuService.list(objectQueryWrapper);
            if(Objects.nonNull(williamMenus) && williamMenus.size()>0){
                node.setChildren(toZTreeNode(williamMenus));
            }
            list.add(node);
        }
        return list;
    }

    /***
     * 递归
     * 查询孩子
     * @param id
     * @return
     */
    public List<WilliamMenu> getChildrenAll(Integer id){
        QueryWrapper<WilliamMenu> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.eq("pid",id);
        //根据id查询孩子
        List<WilliamMenu> menuList =  williamMenuService.list(objectQueryWrapper);
        for(WilliamMenu m : menuList){
            System.out.println(m.getTitle());
            m.setChildren(getChildrenAll(m.getId()));
        }
        return menuList;
    }
}
