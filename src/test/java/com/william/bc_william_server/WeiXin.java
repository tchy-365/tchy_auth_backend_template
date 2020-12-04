package com.william.bc_william_server;

import cn.hutool.http.HttpUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xinchuang
 * @version v1.0
 * @date 2019/11/15 11:52
 * @since Copyright(c) 爱睿智健康科技
 */
public class WeiXin {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    public void testWechatPush(){
        String open_id = "";
        String access_token = "";
        String tamplate = "";

//        String postUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + ;

        Map<String,Object> map = new HashMap<>();
        map.put("touser", open_id); //推送用户openid
        map.put("template_id", "TM00498"); //指定模版ID
        map.put("url", "http://www.ismarthealth.com/home");  //点击模版跳转地址
        Map<String, Object> data =new HashMap<String, Object>();

        Map<String, Object> first = new HashMap<String, Object>();
        first.put("value", "Hello this is william test msg");
        first.put("color", "#000000");
//        Map<String,Object> Conten



    }


    /**
     * 体脂数据推送信息给微信
     * @param access_token
     * @param open_id
     * @param highPressure
     * @param lowPressure
     * @param heart
     * @param bloodPressureLevel
     */
//    public  void sendBodyCompostionInfoToWeChart(final String access_token,final String open_id,final String user_name,final String bodyCompostionText,final String test_time){
    @Test
    public  void sendBodyCompostionInfoToWeChart(){
            String secret  = "5a90a79b1b0e7b8e6951d6cd37e27bab";
            String appId = "wx22f419e7e9c221d8";
            String grant_type = "admin";
            String open_id = "";
            String access_token ="";
        // 获取access token
        String accessUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type="+grant_type +"&appid="+ appId +"&secret=" + secret;
        String s = HttpUtil.get(accessUrl);
        System.out.println(s);
        // 获取所有关注这openId
        String getAllUser = "https://api.weixin.qq.com/cgi-bin/user/get?access_token="+ access_token +"&next_openid=";
        // openId获取用户信息
        String getUserInfo = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=" + access_token+ "&openid="+open_id+"&lang=zh_CN";




//        try {
////            String regUser = "http://wxdemo.xxxx.com/wxdevices/bodycompositionmain?openId="+open_id;
//            String regUser = "http://www.ismarthealth.com/home";
//            String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+access_token;
//            Map<String, Object> map = new HashMap<String, Object>();
//            map.put("touser", open_id); //推送用户openid
//            map.put("template_id", "TM00498"); //指定模版ID
//            map.put("url", regUser);  //点击模版跳转地址
//            Map<String, Object> data_map =new HashMap<String, Object>();
//            Map<String, Object> first = new HashMap<String, Object>();
//            first.put("value", "Hello this is william test msg");
//            first.put("color", "#000000");
//            Map<String, Object> keynote1 = new HashMap<String, Object>();
//            keynote1.put("value", "Hello this is william test msg");
//            keynote1.put("color", "#000000");
//            Map<String, Object> keynote2 = new HashMap<String, Object>();
//            keynote2.put("value", "Hello this is william test msg");
//            keynote2.put("color", "#000000");
//            Map<String, Object> keynote3 = new HashMap<String, Object>();
//            keynote3.put("value", "Hello this is william test msg");
//            keynote3.put("color", "#000000");
//            Map<String, Object> remark = new HashMap<String, Object>();
//            remark.put("value", "点击查看详情");
//            remark.put("color", "#000000");
//            data_map.put("first", first);
//            data_map.put("keyword1", keynote1);
//            data_map.put("keyword2", keynote2);
//            data_map.put("keyword3", keynote3);
//            data_map.put("remark", remark);
//            map.put("data", data_map);
//            JSONObject json = JSONObject.fromObject(map);
//            String result = HttpUtil.post(url, json.toString());
//            System.out.println(result);
//            JSONObject json_arr = JSONObject.fromObject(result);
//            String isOk = String.valueOf(json_arr.get("errmsg"));
//            logger.info(" errmsg >>>>>>>>> "+isOk);
//            if(isOk.equals("ok")){
//                logger.info("体脂据推送成功 , "+open_id );
//            }else{
//                logger.info("体脂据推送失败 , "+open_id );
//            }
//        } catch (Exception e) {
//            logger.error("", e);
//        }

    }


}
