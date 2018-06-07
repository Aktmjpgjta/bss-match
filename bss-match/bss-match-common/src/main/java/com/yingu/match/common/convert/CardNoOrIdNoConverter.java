package com.yingu.match.common.convert;

import com.yingu.match.common.util.StringUtils;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @Author : yaoyi
 * @Description: 卡号和身份证号的脱敏转换器
 * @Date: Created in 14:29 on 2017/12/26.
 * @Modified By：
 */
public class CardNoOrIdNoConverter extends JsonSerializer<String> {
    protected Logger logger = LoggerFactory.getLogger(CardNoOrIdNoConverter.class);
    private  static  final int START_INDEX = 4;

    @Override
    public void serialize(String number, JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        StringBuilder builder = new StringBuilder();
        if(StringUtils.isEmpty(number)) {
            builder.append(number);
        }
        //起始索引位置不能大于字符串总长度
        if(number.length() <= 8){
            builder.append(number);
        }else{
            //构建覆盖原内容的脱敏字符串
            String regexStr = buildMatchStr(START_INDEX , number.length()-START_INDEX);
            //生成脱敏字符串
            builder.append(number.substring(0,START_INDEX)).append(regexStr).append(number.substring(number.length()-START_INDEX , number.length()));
        }
        jsonGenerator.writeString(builder.toString());
    }

    private String buildMatchStr(int start, int end){
        if(start >= end){
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for(int i=start; i<end;i++){
            sb.append("*");
        }
        return sb.toString();
    }

}
