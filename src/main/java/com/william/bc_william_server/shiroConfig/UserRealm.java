package com.william.bc_william_server.shiroConfig;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.william.bc_william_server.service.IWilliamMenuService;
import com.william.bc_william_server.service.IWilliamRoleService;
import com.william.bc_william_server.service.IWilliamUserService;
import com.william.constant.Constant;
import com.william.pojo.ActiverUser;
import com.william.pojo.WilliamMenu;
import com.william.pojo.WilliamUser;
import com.william.utils.Md5Util;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.*;

@Configuration
public class UserRealm extends AuthorizingRealm{

    @Autowired
    @Lazy
    private IWilliamUserService userService;

    @Autowired
    @Lazy
    private IWilliamMenuService menuService;

    @Autowired
    @Lazy
    private IWilliamRoleService roleService;

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    /**
     * 授权
     * @author     xinchuang
     * @param principals :
     * @return : org.apache.shiro.authz.AuthorizationInfo
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo=new SimpleAuthorizationInfo();
        ActiverUser activerUser=(ActiverUser) principals.getPrimaryPrincipal();
        WilliamUser user = activerUser.getWilliamUser();
        List<String> permissions = activerUser.getPermissions();
        if(Objects.equals(user.getUserType(), Constant.USER_TYPE_SUPER)) {
            authorizationInfo.addStringPermission("*:*");
        }else {
            if(Objects.nonNull(permissions) && permissions.size()>0) {
                authorizationInfo.addStringPermissions(permissions);
            }
        }
        return authorizationInfo;
    }

    /**
     * 认证
     * @author     xinchuang
     * @param token :
     * @return : org.apache.shiro.authc.AuthenticationInfo
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String userName = token.getPrincipal().toString();
        // 查询用户
        QueryWrapper<WilliamUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name",userName);
        WilliamUser williamUser = userService.getOne(queryWrapper);
        if(Objects.nonNull(williamUser)){
            ActiverUser activerUser = new ActiverUser();
            activerUser.setWilliamUser(williamUser);
            // 查询用户permission
            QueryWrapper<WilliamMenu> menuQueryWrapper = new QueryWrapper<>();
            // 只查询菜单
            menuQueryWrapper.eq("type", Constant.TYPE_MNEU);
            menuQueryWrapper.eq("status" , Constant.AVAILABLE_TRUE);
            menuQueryWrapper.eq("show_flag",Constant.AVAILABLE_TRUE);
            // uid + 角色 + 权限查询
            String uid = williamUser.getId();
            // 查询角色
            List<Integer> currentRoleIds = roleService.getUserRoleIdsByUid(uid);
            // 角色查询权限和菜单id
            Set<Integer> mids = new HashSet<>();
            for (Integer currentRoleId : currentRoleIds) {
               List<Integer> menuAndPIds =  roleService.getRoleMenuAndPIdsByRid(currentRoleId);
               mids.addAll(menuAndPIds);
            }
            List<WilliamMenu> list = new ArrayList<>();
            // 根据角色ID查询权限
            if(mids.size() > 0){
                menuQueryWrapper.in("id",mids);
                list = menuService.list(menuQueryWrapper);
            }
            List<String> permissions = new ArrayList<>();
            for (WilliamMenu williamMenu : list) {
                permissions.add(williamMenu.getPermission());
            }
            activerUser.setPermissions(permissions);
            ByteSource credentialsSalt = ByteSource.Util.bytes(userName.getBytes());
            SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(activerUser, williamUser.getPassWord(), credentialsSalt, getName());
            Session session = SecurityUtils.getSubject().getSession();
            session.setAttribute("userSession",activerUser);
            return authenticationInfo;
        }
        return null;
    }

}
