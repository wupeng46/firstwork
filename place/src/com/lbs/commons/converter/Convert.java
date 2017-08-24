	package com.lbs.commons.converter;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.FastHashMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lbs.commons.SubmitDataMap;

	/**
	 * 将HttpServletRequest中的数组组装成实体集合
	 * <p>Title: </p>
	 * <p>Description: </p>
	 * <p>Copyright: Copyright (c) 2003</p>
	 * <p>Company: LBS</p>
	 * @author chenkl <chenkl@bjlbs.com.cn>
	 * @version 1.0
	 */

	public class Convert {

	  private static FastHashMap converters = new FastHashMap();

	  static {
	    converters.setFast(false);
	    register();
	    converters.setFast(true);
	  }

	  private static Log log = LogFactory.getLog("com.lbs.commons.converter.Convert");

	  /**
	 * 将HttpServletRequest中的参数组装成指定实体集合，要求实体只实现java.io.Serializble
	 * @param form - ActionForm
	 * @param entity - Class
	 * @return - Collection
	 * @throws InvocationTargetException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	  public static synchronized Collection getCollection(SubmitDataMap parameters,
	      Class entity) throws
	      InvocationTargetException,
	      InstantiationException,
	      IllegalAccessException {
	    Object dto = entity.newInstance();
	    PropertyDescriptor origDescriptors[] = PropertyUtils.getPropertyDescriptors(dto);

	    String propName = null;
        int length = 0;
        String[] tmp = null;
        for(int i = 0; i < origDescriptors.length; i++){
          propName = origDescriptors[i].getName();
          //属性不能是class
          if(!"class".equals(propName)){
            tmp = parameters.getParameterValues(propName);
            if(null != tmp){
              length = tmp.length;
              break;
            }
          }
        }

	    Collection result = new ArrayList();
	    for (int j = 0; j < length; j++) {
	      Object item = entity.newInstance();
	      for (int i = 0; i < origDescriptors.length; i++) {
	        if (origDescriptors[i].getReadMethod() == null) {
	          if (log.isTraceEnabled()) {
	            log.trace("-->No getter on JavaBean for " + origDescriptors[i].getName() + ", skipping");
	          }
	          continue;
	        }
	        String name = origDescriptors[i].getName();
	        if ("class".equals(name)) {
	          continue; // No point in trying to set an object's class
	        }
            Object value = null;
            tmp =  parameters.getParameterValues(name);
            if(null != tmp)
              value = tmp[j];
            BeanUtils.copyProperty(item, name, value);
	      }
	      result.add(item);
	    }
	    return result;
	  }

	  /**
	   * 将String转换成指定类型的值
	   * @param value String，待转换的字符串
	   * @param clazz Class
	   * @return Object
	   */
	  public static Object convert(String value, Class clazz) {
	    Converter converter = (Converter) converters.get(clazz);
	    if (null == converter) {
	      converter = (Converter) converters.get(String.class);
	    }
	    return (converter.convert(clazz, value));

	  }

	  /**
	   * 将String数组转换成指定的类型的数组
	   * @param values String[]  要转换的数组
	   * @param clazz Class 指定的类型
	   * @return Object
	   */
	  public static Object convert(String values[], Class clazz) {

	    Class type = clazz;
	    if (clazz.isArray()) {
	      type = clazz.getComponentType();
	    }

	    Converter converter = (Converter) converters.get(type);
	    if (null == converter) {
	      converter = (Converter) converters.get(String.class);
	    }

	    Object array = Array.newInstance(type, values.length);
	    for (int i = 0; i < values.length; i++) {
	      Array.set(array, i, converter.convert(type, values[i]));
	    }
	    return (array);

	  }

	  /**
	   * 注册转换数组值需要的类
	   */
	  private static void register() {
	    converters.put(BigDecimal.class, new BigDecimalConverter(null));
	    converters.put(BigInteger.class, new BigIntegerConverter(null));
	    converters.put(Boolean.TYPE, new BooleanConverter(null));
	    converters.put(Boolean.class, new BooleanConverter(null));
	    converters.put(Byte.TYPE, new ByteConverter(null));
	    converters.put(Byte.class, new ByteConverter(null));
	    converters.put(Character.TYPE, new CharacterConverter(null));
	    converters.put(Character.class, new CharacterConverter(null));
	    converters.put(Double.TYPE, new DoubleConverter(null));
	    converters.put(Double.class, new DoubleConverter(null));
	    converters.put(Float.TYPE, new FloatConverter(null));
	    converters.put(Float.class, new FloatConverter(null));
	    converters.put(Integer.TYPE, new IntegerConverter(null));
	    converters.put(Integer.class, new IntegerConverter(null));
	    converters.put(Long.TYPE, new LongConverter(null));
	    converters.put(Long.class, new LongConverter(null));
	    converters.put(Short.TYPE, new ShortConverter(null));
	    converters.put(Short.class, new ShortConverter(null));
	    converters.put(String.class, new StringConverter());
	    converters.put(java.sql.Date.class, new DateConverter(null));
	  }



	}
