package util;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.FundMain2;
import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FundCollect {

    public static void main(String[] args) {
        collect();
    }

    public static List<FundMain2> collect(){
        List<FundMain2> list = new ArrayList<FundMain2>();
        try {
            String fundcode[] = new String[]{"519066","000457","110022","020026","001542","001410"};
            for (String s : fundcode) {
                String result = getData(s);
                FundMain2 fund = StringUtils.str2FundMain2(result);
                list.add(fund);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static String getData(String fundcode) throws Exception{
        String url_str = "http://fundgz.1234567.com.cn/js/" + fundcode + ".js?rt=1463558676006";//获取用户认证的帐号URL
        URL url = new URL(url_str);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.connect();
        int code = connection.getResponseCode();
        if (code == 404) {
            throw new Exception("认证无效，找不到此次认证的会话信息！");
        }
        if (code == 500) {
            throw new Exception("认证服务器发生内部错误！");
        }
        if (code != 200) {
            throw new Exception("发生其它错误，认证服务器返回 " + code);
        }
        InputStream is = connection.getInputStream();
        byte[] response = new byte[is.available()];
        is.read(response);
        is.close();
        if (response == null || response.length == 0) {
            throw new Exception("认证无效，找不到此次认证的会话信息！");
        }
        String result = new String(response, "UTF-8");
        result = result.substring(8,result.length()-2);
        System.out.println(result);

        return result;
    }

    public static List<FundMain2> aaa(){
        /*String fundstr[] = new String[]{
                "{\"fundcode\":\"001542\",\"name\":\"国泰互联网+股票\",\"jzrq\":\"2018-03-23\",\"dwjz\":\"1.8500\",\"gsz\":\"1.8500\",\"gszzl\":\"-2.2199\",\"gztime\":\"2018-03-24 10:00\"}",
                "{\"fundcode\":\"519066\",\"name\":\"汇添富蓝筹稳健\",\"jzrq\":\"2018-03-23\",\"dwjz\":\"2.427\",\"gsz\":\"2.427\",\"gszzl\":\"-2.334\",\"gztime\":\"2018-03-24 10:00\"},",
                "{\"fundcode\":\"000457\",\"name\":\"上投摩根核心成长\",\"jzrq\":\"2018-03-23\",\"dwjz\":\"1.983\",\"gsz\":\"1.983\",\"gszzl\":\"-5.3912\",\"gztime\":\"2018-03-24 10:00\"},",
                "{\"fundcode\":\"110022\",\"name\":\"易方达消费行业\",\"jzrq\":\"2018-03-23\",\"dwjz\":\"2.275\",\"gsz\":\"2.275\",\"gszzl\":\"-1.4298\",\"gztime\":\"2018-03-24 10:00\"},",
                "{\"fundcode\":\"020026\",\"name\":\"国泰成长优选混合\",\"jzrq\":\"2018-03-23\",\"dwjz\":\"3.023\",\"gsz\":\"3.023\",\"gszzl\":\"-5.0565\",\"gztime\":\"2018-03-24 10:00\"},",
                "{\"fundcode\":\"001410\",\"name\":\"信达澳银新能源产业股票\",\"jzrq\":\"2018-03-23\",\"dwjz\":\"1.407\",\"gsz\":\"1.407\",\"gszzl\":\"-6.0748\",\"gztime\":\"2018-03-24 10:00\"}"
        };*/
        /*String fundstr[] = new String[]{
                "{\"fundcode\":\"001542\",\"name\":\"国泰互联网+股票\",\"jzrq\":\"2018-03-26\",\"dwjz\":\"1.819\",\"gsz\":\"1.819\",\"gszzl\":\"-1.6757\",\"gztime\":\"2018-03-27 10:00\"}",
                "{\"fundcode\":\"519066\",\"name\":\"汇添富蓝筹稳健\",\"jzrq\":\"2018-03-26\",\"dwjz\":\"2.419\",\"gsz\":\"2.419\",\"gszzl\":\"-0.3296\",\"gztime\":\"2018-03-27 10:00\"}",
                "{\"fundcode\":\"000457\",\"name\":\"上投摩根核心成长\",\"jzrq\":\"2018-03-26\",\"dwjz\":\"2.025\",\"gsz\":\"2.025\",\"gszzl\":\"2.118\",\"gztime\":\"2018-03-27 10:00\"}",
                "{\"fundcode\":\"110022\",\"name\":\"易方达消费行业\",\"jzrq\":\"2018-03-26\",\"dwjz\":\"2.252\",\"gsz\":\"2.252\",\"gszzl\":\"-1.011\",\"gztime\":\"2018-03-27 10:00\"}",
                "{\"fundcode\":\"020026\",\"name\":\"国泰成长优选混合\",\"jzrq\":\"2018-03-26\",\"dwjz\":\"3.102\",\"gsz\":\"3.102\",\"gszzl\":\"2.6133\",\"gztime\":\"2018-03-27 10:00\"}",
                "{\"fundcode\":\"001410\",\"name\":\"信达澳银新能源产业股票\",\"jzrq\":\"2018-03-26\",\"dwjz\":\"1.448\",\"gsz\":\"1.448\",\"gszzl\":\"2.914\",\"gztime\":\"2018-03-27 10:00\"}"
        };*/
        String fundstr[] = new String[]{
                "{\"fundcode\":\"001542\",\"name\":\"国泰互联网+股票\",\"jzrq\":\"2018-03-27\",\"dwjz\":\"1.819\",\"gsz\":\"1.819\",\"gszzl\":\"0.0\",\"gztime\":\"2018-03-28 10:00\"}",
                        "{\"fundcode\":\"519066\",\"name\":\"汇添富蓝筹稳健\",\"jzrq\":\"2018-03-27\",\"dwjz\":\"2.414\",\"gsz\":\"2.414\",\"gszzl\":\"-0.2067\",\"gztime\":\"2018-03-28 10:00\"}",
                        "{\"fundcode\":\"000457\",\"name\":\"上投摩根核心成长\",\"jzrq\":\"2018-03-27\",\"dwjz\":\"2.069\",\"gsz\":\"2.069\",\"gszzl\":\"2.1728\",\"gztime\":\"2018-03-28 10:00\"}",
                        "{\"fundcode\":\"110022\",\"name\":\"易方达消费行业\",\"jzrq\":\"2018-03-27\",\"dwjz\":\"2.236\",\"gsz\":\"2.236\",\"gszzl\":\"-0.7105\",\"gztime\":\"2018-03-28 10:00\"}",
                        "{\"fundcode\":\"020026\",\"name\":\"国泰成长优选混合\",\"jzrq\":\"2018-03-27\",\"dwjz\":\"3.167\",\"gsz\":\"3.167\",\"gszzl\":\"2.0954\",\"gztime\":\"2018-03-28 10:00\"}",
                        "{\"fundcode\":\"001410\",\"name\":\"信达澳银新能源产业股票\",\"jzrq\":\"2018-03-27\",\"dwjz\":\"1.495\",\"gsz\":\"1.495\",\"gszzl\":\"3.2459\",\"gztime\":\"2018-03-28 10:00\"}"
        };
        List<FundMain2> list = new ArrayList<FundMain2>();
        for (String s : fundstr) {
//            System.out.println(s);
            FundMain2 fund = StringUtils.str2FundMain2(s);
            list.add(fund);
        }
//        System.out.println(list.size());

        return list;
    }

}
