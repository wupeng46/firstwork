package com.lbs.commons.converter;


import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;


/**
 * <p>Standard {@link Converter} implementation that converts an incoming
 * String into a <code>java.lang.Boolean</code> object, optionally using a
 * default value or throwing a {@link ConversionException} if a conversion
 * error occurs.</p>
 *
 * @author Craig R. McClanahan
 * @version $Revision: 1.1 $ $Date: 2004/09/20 02:24:33 $
 * @since 1.3
 */

public final class BooleanConverter implements Converter {


    // ----------------------------------------------------------- Constructors


    /**
     * Create a {@link Converter} that will throw a {@link ConversionException}
     * if a conversion error occurs.
     */
    public BooleanConverter() {

        this.defaultValue = null;
        this.useDefault = false;

    }


    /**
     * Create a {@link Converter} that will return the specified default value
     * if a conversion error occurs.
     *
     * @param defaultValue The default value to be returned
     */
    public BooleanConverter(Object defaultValue) {

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

        if (value == null || "".equals(value.toString().trim())) {
                throw new ConversionException("No value specified");
        }

        if (value instanceof Boolean) {
            return (value);
        }

        try {
            String stringValue = value.toString();
            if (stringValue.equalsIgnoreCase("yes") ||
                stringValue.equalsIgnoreCase("y") ||
                stringValue.equalsIgnoreCase("true") ||
                stringValue.equalsIgnoreCase("on") ||
                stringValue.equalsIgnoreCase("1")) {
                return (Boolean.TRUE);
            } else if (stringValue.equalsIgnoreCase("no") ||
                       stringValue.equalsIgnoreCase("n") ||
                       stringValue.equalsIgnoreCase("false") ||
                       stringValue.equalsIgnoreCase("off") ||
                       stringValue.equalsIgnoreCase("0")) {
                return (Boolean.FALSE);
            } else if (useDefault) {
                return (defaultValue);
            } else {
                throw new ConversionException(stringValue);
            }
        } catch (ClassCastException e) {
            if (useDefault) {
                return (defaultValue);
            } else {
                throw new ConversionException(e);
            }
        }

    }


}
