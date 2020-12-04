package com.william.bc_william_server;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.william.bc_william_server.redis.RedisService;
import com.william.bc_william_server.service.IWilliamMenuService;
import com.william.bc_william_server.utils.ZTreeUtils;
import com.william.pojo.req.Login;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BcWilliamServerApplicationTests {

	@Autowired
	private RedisService redisService;

	@Autowired
	private RedisTemplate redisTemplate;

	@Test
	public void contextLoads() {

	}

	@Autowired
	private IWilliamMenuService iWilliamMenuService;

	@Autowired
	private ZTreeUtils zTreeUtils;

	@Test
	public void getMenusList(){
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.eq("root_id","/");
		List list = iWilliamMenuService.list(queryWrapper);
		List list1 = zTreeUtils.toZTreeNode(list);
		System.out.println(JSONUtil.toJsonStr(list1));
	}

	@Test
	public void test1(){
		Login login = new Login();
		login.setUsername("william");
		login.setPassword("123");
		login.setCode("1231");
		redisService.setStr("user" ,JSONUtil.toJsonStr(login));
//		boolean captchf1O8 = redisService.ishasKey("CAPTCHF1O8");
//		System.out.println(captchf1O8);
	}





}
