package org.shop.api.config;


import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.io.IOException;
import java.util.List;

@Configuration
public class WebConfiguration extends WebMvcConfigurationSupport {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("*")
                .allowedOrigins("*")
                .allowedHeaders("*");
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter< ? >> converters) {
        GsonHttpMessageConverter  gsonConverter = new GsonHttpMessageConverter ();
        Gson gson = new GsonBuilder()
                //.serializeNulls()
                .setDateFormat("yyyy-MM-dd HH:mm:ss") //设置日期的格式
                .disableHtmlEscaping()//防止对网址乱码 忽略对特殊字符的转换
                .registerTypeAdapterFactory(nullString2EmptyFactory())
                .create();
        gsonConverter.setGson(gson);
        converters.add(gsonConverter);
    }

    @Bean
    public TypeAdapter<String> nullString2Empty(){
        TypeAdapter<String> adapter = new TypeAdapter<String>() {
            @Override
            public String read(JsonReader reader) throws IOException {
                // TODO Auto-generated method stub
                if (reader.peek() == JsonToken.NULL) {
                    reader.nextNull();
                    return "";
                }
                return reader.nextString();
            }

            @Override
            public void write(JsonWriter writer, String value) throws IOException {
                // TODO Auto-generated method stub
                if (value == null) {
                    writer.value("");
                    return;
                }
                writer.value(value);
            }
        };
        return adapter;
    }

    @Bean
    public TypeAdapterFactory nullString2EmptyFactory(){
        TypeAdapterFactory ta = new TypeAdapterFactory() {
            @Override
            public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
                Class<T> rawType = (Class<T>) type.getRawType();
                if (rawType != String.class) {
                    return null;
                }
                return (TypeAdapter<T>) nullString2Empty();
            }
        };
        return ta;
    }


}
