package util.com;
/*
*把字符串形式的日期转换成java.sql.Date
*/
import java.text.SimpleDateFormat;

public class SqlDateFormat {

    public SqlDateFormat() {
    }
    public static java.sql.Date format(String dateStr) throws Exception
    {

      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
       java.util.Date dd=formatter.parse(dateStr);
       java.sql.Date da=new java.sql.Date(dd.getTime());
       return da;
    }

    public static java.sql.Date format(java.util.Date date)
    {
      return new java.sql.Date(date.getTime());
    }

}
