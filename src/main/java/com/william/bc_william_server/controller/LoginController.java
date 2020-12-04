package com.william.bc_william_server.controller;

import cn.hutool.json.JSONUtil;
import com.william.bc_william_server.constants.Constant;
import com.william.bc_william_server.redis.RedisService;
import com.william.bc_william_server.utils.CodeUtil;
import com.william.constant.RespCodeAndMsg;
import com.william.pojo.ActiverUser;
import com.william.pojo.Result;
import com.william.pojo.WilliamUser;
import com.william.pojo.req.Login;
import com.william.utils.WebUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



/**
 * @author xinchuang
 * @version v1.0
 * @date 2019/11/12 19:08
 * @since Copyright(c) 爱睿智健康科技
 */
@RestController
@RequestMapping(value = "/login")
public class LoginController {

    @Autowired
    private RedisService redisService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostMapping(value = "/getCode")
    public String getCode(){
        logger.info("getcode info");
        String s = CodeUtil.generateCode();
        String replace = s.toUpperCase().replace(",", "");
        redisService.stetStrTime(Constant.VERIFYCODE + replace,"",120);
        return s;
    }

    @RequestMapping(value = "/getInfo")
    public Result getInfo(){
        Object principal = SecurityUtils.getSubject().getPrincipal();
        return Result.getResult(RespCodeAndMsg.LOGIN_SUCCESS,principal);
    }


    @RequestMapping(value = "/login")
    public Result getLogin(@RequestBody Login login){
        boolean b = redisService.ishasKey(Constant.VERIFYCODE + login.getCode().toUpperCase());
        if(!b){
            return Result.getResult(RespCodeAndMsg.CAPTCH_ERROR);
        }
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(login.getUsername(), login.getPassword());
        Subject subject = SecurityUtils.getSubject();
        String token;
        try {
            subject.login(usernamePasswordToken);
            ActiverUser activerUser=(ActiverUser) subject.getPrincipal();
            redisService.setStr("user",JSONUtil.toJsonStr(activerUser));
            token = subject.getSession().getId().toString();
            WilliamUser williamUser = JSONUtil.toBean(JSONUtil.toJsonStr(subject.getPrincipal()),WilliamUser.class);
            System.out.println("token  ===== " + token);
//            ActiverUser activerUser = (ActiverUser) subject.getPrincipal();
//            redisService.setStr("USER_" + token.getPrincipal(),JSONUtil.toJsonStr(activerUser.getWilliamUser()));
            //记录登陆日志
//            Loginfo entity=new Loginfo();
//            entity.setLoginname(activerUser.getUser().getName()+"-"+activerUser.getUser().getLoginname());
//            entity.setLoginip(WebUtils.getRequest().getRemoteAddr());
//            entity.setLogintime(new Date());
//            loginfoService.save(entity);
            return Result.getResult(RespCodeAndMsg.LOGIN_SUCCESS,token);
        } catch (AccountException e) {
            // 用户不存在或已过期 (用户名不存在)
            return Result.getResult(RespCodeAndMsg.LOGIN_ERROR_NON_ACCOUNT,e.getMessage());
        } catch (CredentialsException e){
            // 用户名或密码错误 (密码错误)
            return Result.getResult(RespCodeAndMsg.LOGIN_ERROR_NON_PASS,e.getMessage());
        } catch (AuthenticationException e){
            // 请求错误
            return Result.getResult(RespCodeAndMsg.UNIFY_EXCEPTION);
        }
    }
}
