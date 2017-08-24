package util.com;

/**
 * 获得数据源
 */
import javax.naming.*;
import javax.sql.*;
import java.io.*;

public class DataSourceFactory implements Serializable {
    Context ctx = null;

    Log log = null;

    String jndi_ds = "zdjk";
    

    public DataSourceFactory (String jndi_ds) {
    	this.jndi_ds=jndi_ds;
        log = new Log();
    }
    
    
    //数据库参数名
    public DataSource getDataSource () throws NamingException {
        try {
            ctx = new InitialContext();
        }
        catch ( NamingException e ) {
            log.log( Log.ERROR , "Couldn't build an initial context : " + e );
            throw new NamingException();
        }
        try {
            DataSource ds = ( DataSource ) ctx.lookup( "java:/comp/env/"+jndi_ds );
        	//DataSource ds = ( DataSource ) ctx.lookup( "java:/comp/env/jdbc/dcjk");  //tomcat    
        	//DataSource ds = ( DataSource ) ctx.lookup( jndi_ds );   //weblogic
            return ds;
        }
        catch ( Exception e ) {
            log.log( Log.ERROR , "JNDI lookup failed : " + e );
            throw new NamingException();
        }
    }
}
