package com.lbs.commons.converter;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.SqlTimeConverter;
/**
 * <p>Title: leaf</p>
 * <p>Description: 注册数据转换类</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: LBS</p>
 * @author chenkl <chenkl@bjlbs.com.cn>
 * @version 1.0
 */

public class ConvertRegister {
  
/**
 * 注册用于数据类型转换的各种类 
 */
public static void register(){
  ConvertUtils.register(new BigDecimalConverter(null),BigDecimal.class);
  ConvertUtils.register(new BigIntegerConverter(null),BigInteger.class);
  ConvertUtils.register(new BooleanConverter(null),Boolean.TYPE);
  ConvertUtils.register(new BooleanConverter(null),Boolean.class);
  ConvertUtils.register(new ByteConverter(null),Byte.TYPE);
  ConvertUtils.register(new ByteConverter(null),Byte.class);
  ConvertUtils.register(new CharacterConverter(null),Character.TYPE);
  ConvertUtils.register(new CharacterConverter(null),Character.class);
  ConvertUtils.register(new DoubleConverter(null),Double.TYPE);
  ConvertUtils.register(new DoubleConverter(null),Double.class);
  ConvertUtils.register(new FloatConverter(null),Float.TYPE);
  ConvertUtils.register(new FloatConverter(null),Float.class);
  ConvertUtils.register(new IntegerConverter(null),Integer.TYPE);
  ConvertUtils.register(new IntegerConverter(null),Integer.class);
  ConvertUtils.register(new LongConverter(null),Long.TYPE);
  ConvertUtils.register(new LongConverter(null),Long.class);
  ConvertUtils.register(new ShortConverter(null),Short.TYPE);
  ConvertUtils.register(new ShortConverter(null),Short.class);
  ConvertUtils.register(new StringConverter(),String.class);
  ConvertUtils.register(new DateConverter(null),java.sql.Date.class);
  ConvertUtils.register(new SqlTimeConverter(null),java.sql.Time.class);
  ConvertUtils.register(new SqlTimestampConverter(null),java.sql.Timestamp.class);

}
}
