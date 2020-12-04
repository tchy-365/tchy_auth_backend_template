package com.william.bc_william_server.redis;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 
 * @author Yong
 *
 */
@Service
public class RedisService {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Resource(name = "stringRedisTemplate")
    ValueOperations<String, String> valOpsStr;

    @Autowired
    RedisTemplate<Object, Object> redisTemplate;

    @Resource(name = "redisTemplate")
    ValueOperations<Object, Object> valOpsObj;

    /**
     * 设置Key的失效时间毫秒
     * @author     xinchuang
     * @param
     * @return
     * @exception
     */
    public boolean setExpire(String key,long time){
       return stringRedisTemplate.expire(key,time,TimeUnit.SECONDS);
    }

    /**
     * 根据指定key获取String
     * @param key
     * @return
     */
    public String getStr(String key){
        return valOpsStr.get(key);
    }
    public Set  getStrLike(String key){
    	return stringRedisTemplate.keys("*"+key+"*");
    }
    /**
     * 设置Str缓存
     * @param key
     * @param val
     */
    public void setStr(String key, String val){
        valOpsStr.set(key,val);
    }

    /**
     * 设置KEY失效时间（分钟）
     * @param key
     * @param value
     * @param timeout
     */
    public void steMinutsStr(String key,String value,int timeout){
        valOpsStr.set(key, value, timeout*60, TimeUnit.SECONDS);
    }

    /**
     * 设置KEY失效时间（小时）
     * @param key
     * @param value
     * @param timeout
     */
    public void stetStr(String key,String value,int timeout){
    	valOpsStr.set(key, value, timeout*60*60L, TimeUnit.SECONDS);
    }
    /**
     * 设置KEY失效时间（秒）
     * @param key
     * @param value
     * @param timeout
     */
    public void stetStrTime(String key,String value,int timeout){
    	valOpsStr.set(key, value, timeout, TimeUnit.SECONDS);
    }
    /**
     * 根据key获取过期时间(秒)
     * @param key
     */
    public Long getExpire(String key){
    	return stringRedisTemplate.getExpire(key, TimeUnit.SECONDS);
    }
    /**
     * 删除指定key
     * @param key
     */
    public void del(String key){
        stringRedisTemplate.delete(key);
    }

    /**
     * 根据指定o获取Object
     * @param o
     * @return
     */
    public Object getObj(Object o){
        return valOpsObj.get(o);
    }

    /**
     * 设置obj缓存
     * @param o1
     * @param o2
     */
    public void setObj(Object o1, Object o2){
        valOpsObj.set(o1, o2);
    }

    /**
     * 删除Obj缓存
     * @param o
     */
    public void delObj(Object o){
        redisTemplate.delete(o);
    }
    /**
     * 查看缓存中是否存在指定KEY
     * @param key
     * @return
     */
    public boolean ishasKey(String key){
    	return this.stringRedisTemplate.hasKey(key);
    }

    /**
     * SET赋值
     * @param key
     * @param values
     * @return
     */
    public Long addSet(String key,String... values){
        return this.stringRedisTemplate.opsForSet().add(key, values);
    }

    /**
     * set 删除
     * @author     xinchuang
     * @param key :
     * @param member :
     * @return : java.lang.Long
     */
    public Long remove(String key,String member){
        return stringRedisTemplate.opsForSet().remove(key,member);
    }

    /**
     * set 中是否有member
     * @author     xinchuang
     * @param key :
     * @param member :
     * @return : java.lang.Boolean
     */
    public Boolean isMember(String key,String member){
        return stringRedisTemplate.opsForSet().isMember(key, member);
    }

}