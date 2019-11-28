package com.jdr.maven.mongo.selector.config;

import com.jdr.maven.mongo.selector.convert.Decimal128ToBigDecimalConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhoude
 * @date 2019/11/28 11:39
 */
@Configuration
public class MongoConfig {

    @Bean
    public MongoCustomConversions customConversions() {
        List<Converter<?, ?>> converterList = new ArrayList<>();
        converterList.add(new Decimal128ToBigDecimalConverter());
        return new MongoCustomConversions(converterList);
    }
}
