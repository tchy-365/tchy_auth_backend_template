package com.william.bc_william_server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xinchuang
 * @version v1.0
 * @date 2019/11/16 16:03
 * @since Copyright(c) 爱睿智健康科技
 */
@RestController
@RequestMapping
public class WeChatController {

    @GetMapping(value = "/wx")
    public String returnToken(){
        return "williamXinchuang";
    }


}
