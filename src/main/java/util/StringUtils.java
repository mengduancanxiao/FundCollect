package util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.FundMain2;
import model.Option;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class StringUtils {
    public static FundMain2 str2FundMain2(String str){
        ObjectMapper objectMapper = new ObjectMapper();
        FundMain2 fund = null;
        try {
            if(org.apache.commons.lang.StringUtils.isNotBlank(str)){
                fund = objectMapper.readValue(str, FundMain2.class);
            }
        } catch (IOException e) {
            System.out.println("字符串转FundMain2对象发生异常！");
            e.printStackTrace();
        }
        return fund;
    }

    public static String map2json(Map<String, List<FundMain2>> map){
        String result = "";
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            result = objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String option2json(Option option){
        String result = "";
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            result = objectMapper.writeValueAsString(option);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }
}
