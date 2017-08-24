package com.lbs.commons.converter;

import java.sql.Date;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;

/**
 * <p>Title: leaf</p>
 * <p>Description: 日期类型转换类</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author chensc
 * @version 1.0
 */

public final class DateConverter
    implements Converter {

  // ----------------------------------------------------------- Constructors


  /**
   * Create a {@link Converter} that will throw a {@link ConversionException}
   * if a conversion error occurs.
   */
  public DateConverter() {

    this.defaultValue = null;
    this.useDefault = false;

  }

  /**
   * Create a {@link Converter} that will return the specified default value
   * if a conversion error occurs.
   *
   * @param defaultValue The default value to be returned
   */
  public DateConverter(Object defaultValue) {

    this.defaultValue = defaultValue;
    this.useDefault = true;

  }

  // ----------------------------------------------------- Instance Variables


  /**
   * The default value specified to our Constructor, if any.
   */
  private Object defaultValue = null;

  /**
   * Should we return the default value on conversion errors?
   */
  private boolean useDefault = true;

  // --------------------------------------------------------- Public Methods


  /**
   * Convert the specified input object into an output object of the
   * specified type.
   *
   * @param type Data type to which this value should be converted
   * @param value The input value to be converted
   *
   * @exception ConversionException if conversion cannot be performed
   *  successfully
   */
  public Object convert(Class type, Object value) {

    if (value == null || ("").equals(value.toString().trim())) {
      return (defaultValue);
    }

    if (value instanceof Date) {
      return (value);
    }

    try {
      return (Date.valueOf(value.toString()));
    }
    catch (Exception e) {
      if (useDefault) {
        return (defaultValue);
      }
      else {
        throw new ConversionException(e);
      }
    }

  }

}
