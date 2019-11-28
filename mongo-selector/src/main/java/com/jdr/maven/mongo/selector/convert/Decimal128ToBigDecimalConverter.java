package com.jdr.maven.mongo.selector.convert;

import org.bson.types.Decimal128;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @author zhoude
 * @date 2019/11/28 11:35
 */
@ReadingConverter
public class Decimal128ToBigDecimalConverter implements Converter<Decimal128, BigDecimal> {

    @Override
    public BigDecimal convert(Decimal128 source) {
        return source.bigDecimalValue();
    }
}
