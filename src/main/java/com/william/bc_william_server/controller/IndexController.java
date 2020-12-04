package com.william.bc_william_server.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.william.bc_william_server.constants.Constant;
import com.william.bc_william_server.service.IWilliamMenuService;
import com.william.pojo.WilliamMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author xinchuang
 * @version v1.0
 * @date 2019/11/14 16:33
 * @since Copyright(c) 爱睿智健康科技
 */
@Controller
@RequestMapping(value = "/index")
public class IndexController {

    @Autowired
    private IWilliamMenuService iWilliamMenuService;

    // 部门管理
    @RequestMapping(value = "/toDept")
    public String toDept(){
        return Constant.TO_SYSTEM + "/dept/deptManager";
    }

    @RequestMapping(value = "/toDeptLeft")
    public String toDeptLeft(){return Constant.TO_SYSTEM + "/dept/deptLeft";}

    @RequestMapping(value = "/toDeptRight")
    public String toDeptRight(){return Constant.TO_SYSTEM + "/dept/deptRight";}


    // 用户
    @RequestMapping(value = "/toUserManager")
    public String toUserManager(){
        return Constant.TO_SYSTEM + "user/userManager";
    }

    // 权限
    @RequestMapping(value = "/toPermissionLeft")
    public String toPermissionLeft(){
        return Constant.TO_SYSTEM + "permission/permissionLeft";
    }

    @RequestMapping(value = "/toPermissionRight")
    public String toPermissionRight(){
        return Constant.TO_SYSTEM + "permission/PermissionRight";
    }

    @RequestMapping(value = "/toPermissionManager")
    public String toPermissionManager(){
        return Constant.TO_SYSTEM + "permission/toPermissionManager";
    }


    // 配置角色
    @RequestMapping(value = "/toRole")
    public String toRole(){
        return Constant.TO_SYSTEM + "role/roleManager";
    }

    // 登录
    @RequestMapping(value = "/toLogin")
    public String toLogin(){ return Constant.TO_SYSTEM + "index/login"; }

    @RequestMapping(value = "/toMain")
    public String toMain(){
        return Constant.TO_SYSTEM + "index/main";
    }

    @RequestMapping(value = "/toIndex")
    public String toIndex(){
        return Constant.TO_SYSTEM + "index/index";
    }



    // 菜单
    @RequestMapping(value = "/toIcon")
    public String toIcon(String id,Model model){
        model.addAttribute("id",id);
        return Constant.TO_SYSTEM + "menu/icon";
    }


    @RequestMapping(value = "/toMenuEditor")
    public String toMenuEditor(@RequestParam(value = "pid",required = false) String pid,Model model){
        QueryWrapper<WilliamMenu> williamMenuQueryWrapper = new QueryWrapper<>();
        williamMenuQueryWrapper.eq("root_id","/");
        List<WilliamMenu> list = iWilliamMenuService.list(williamMenuQueryWrapper);
        model.addAttribute("menuList",list);
        return Constant.TO_SYSTEM + "menu/menuEditor";
    }


    @RequestMapping(value = "/toMenuList")
    public String toMenuList(Model model){
        List<WilliamMenu> menuList = iWilliamMenuService.list();
        model.addAttribute("menuList",menuList);
        return Constant.TO_SYSTEM + "menu/menuList";}


    // 菜单 two

    /**
     * 跳转到菜单管理
     *
     */
    @RequestMapping("/toMenuManager")
    public String toMenuManager() {
        return Constant.TO_SYSTEM + "menu/menuManager";
    }

    /**
     * 跳转到菜单管理-left
     *
     */
    @RequestMapping("/toMenuLeft")
    public String toMenuLeft() {
        return Constant.TO_SYSTEM + "menu/menuLeft";
    }


    /**
     * 跳转到菜单管理--right
     *
     */
    @RequestMapping("/toMenuRight")
    public String toMenuRight() {
        return Constant.TO_SYSTEM + "menu/menuRight";
    }



    @RequestMapping(value = "/toUserInfo")
    public String toUserInfo(){
        return Constant.TO_PAGE + "/user/userInfo";
    }


    @RequestMapping(value = "/toAllUsers")
    public String toAllUsers(){
        return Constant.TO_PAGE + "/user/allUsers";
    }


    @RequestMapping(value = "/toAddUser")
    public String toAddUser(){
        return Constant.TO_PAGE + "/user/addUser";
    }


    @RequestMapping(value = "/toSystemParameter")
    public String toSystemParameter(){
        return Constant.TO_PAGE + "/systemParameter/systemParameter";
    }


    @RequestMapping(value = "/toNewsList")
    public String toNewsList(){
        return Constant.TO_PAGE + "/news/newsList";
    }


    @RequestMapping(value = "/toNewsAdd")
    public String toNewsAdd(){
        return Constant.TO_PAGE + "/news/newsAdd";
    }


    @RequestMapping(value = "/toLinksList")
    public String toLinkList(){
        return Constant.TO_PAGE + "/links/linksList";
    }

    @RequestMapping(value = "/toLinksAdd")
    public String toLinksAdd(){
        return Constant.TO_PAGE + "/links/linksAdd";
    }

    @RequestMapping(value = "/toMessageReply")
    public String toMessageReply(){
        return Constant.TO_PAGE + "/message/messageReply";
    }

    @RequestMapping(value = "/toMessage")
    public String toMessage(){
        return Constant.TO_PAGE + "/message/message";
    }

    @RequestMapping(value = "/toImages")
    public String toImages(){
        return Constant.TO_PAGE + "/img/images";
    }




    @RequestMapping(value = "/toChangePwd")
    public String toChangePwd(){
        return Constant.TO_PAGE + "/user/changePwd";
    }

    @RequestMapping(value = "/to404")
    public String to404(){
        return Constant.TO_SYSTEM + "/index/404";
    }



}
