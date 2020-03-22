package com.van.mall.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.type.JavaType;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * @author Van
 * @date 2020/3/21 - 17:22
 */
@Slf4j
public class JsonUtil {
    private static ObjectMapper mapper=new ObjectMapper();
    static {
        //serialization config................................
        //include all field of a object
        mapper.setSerializationInclusion(JsonSerialize.Inclusion.ALWAYS);
        //cancel transform to timestamp automatic
        mapper.configure(SerializationConfig.Feature.WRITE_DATE_KEYS_AS_TIMESTAMPS,false);
        //ignore the exception that empty bean transform to json
        mapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS,false);
        //unify the time format to yyyy-MM-dd hh:mm:ss
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"));
        //end.............................................................
        //deserialization config............................................
        //ignore the exception when property exists in json but not in object
        mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES,false);
    }
    public static <T> String object2String(T object){
        if (object==null){
            return null;
        }
        try {
            return object instanceof String ? (String) object:mapper.writeValueAsString(object);
        } catch (IOException e) {
            log.warn("Parse object to string error",e);
            return null;
        }
    }
    public static <T> String object2StringPretty(T object){
        if (object==null){
            return null;
        }
        try {
            return object instanceof String ? (String) object:mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (IOException e) {
            log.warn("Parse object to string error",e);
            return null;
        }
    }

    public static <T> T string2Object(String str,Class<T>clazz){
        if (StringUtils.isEmpty(str)||clazz==null){
            return null;
        }
        try{
            return clazz.equals(String.class)? (T) str:mapper.readValue(str,clazz);
        }catch (Exception e){
            log.warn("Parse String to object error!",e);
            return null;
        }
    }
    public static <T> T string2Object(String str, TypeReference<T> typeReference){
        if (StringUtils.isEmpty(str)||typeReference==null){
            return null;
        }
        try{
            return typeReference.getType().equals(String.class)?(T)str:mapper.readValue(str,typeReference);

        }catch (Exception e){
            log.warn("Parse String to object error!",e);
            return null;
        }
    }
    public static <T> T string2Object(String str,Class<?> collectionClass,Class<?>... elementClass){
        JavaType javaType=mapper.getTypeFactory().constructParametricType(collectionClass,elementClass);
        try{
            return mapper.readValue(str,javaType);
        }catch (Exception e){
        log.warn("Parse String to object error!",e);
        return null;
        }
    }


}
