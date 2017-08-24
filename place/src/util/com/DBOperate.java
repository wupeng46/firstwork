package util.com;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import sun.jdbc.rowset.CachedRowSet;

/**
 * <p>Title: 东创通用接口管理系统</p>
 * <p>Description: </p>
 * <p>Copyright: 2004.1.6</p>
 * <p>Company: 东创</p>
 * @author 文辉东
 * @rework 文辉东
 * @version 1.0
 */

public class DBOperate implements Serializable {
    //定义预处理SQL对象
    private PreparedStatement prestmt = null;
    //定义直接处理SQL对象
    private Statement stmt = null;
    //定义存储过程处理SQL对象
    private CallableStatement callStat = null;
    //定义数据库连接对象
    private Connection con = null;
    //定义获取数据源对象
    private DataSourceFactory dataSource = new DataSourceFactory("jdbc/zdjk");    
    //定义日志处理对象
    private Log log = null;

    public DBOperate() {
        try {
            con = dataSource.getDataSource().getConnection();
            log = Log.getInstance();
        }catch( Exception e ) {
        	System.out.print("与数据库连接失败");
            log.log(Log.ERROR, "DataBase Connected Error......");
        }
    }

//初始化连接
    public void init() {
        if ( con == null ) {
            try {
				System.out.println("JVM MAX MEMORY: "+Runtime.getRuntime().maxMemory()/1024/1024+"M");
				System.out.println("JVM IS USING MEMORY:"+Runtime.getRuntime().totalMemory()/1024/1024+"M");
				System.out.println("JVM IS Free MEMORY:"+Runtime.getRuntime().freeMemory()/1024/1024+"M");
                con = dataSource.getDataSource().getConnection();
            }
            catch ( Exception e ) {
				System.out.print("与数据库连接失败");
                log.log( Log.ERROR , "DataBase Connected Error......" );
            }
        }
    }


/**批处理插入或更新.......
    * 成功 --- 返回 1
     * 失败 --- 返回 0
     */


    public int executeBatch ( Object o ) throws Exception {
        int isSccess = 0;
        init();
        if( o instanceof String[] ) {
            String[] v = (String[]) o;
            try {
                this.con.setAutoCommit( false );
                if ( stmt == null ) {
                    stmt = con.createStatement();
                }
                for ( int i = 0; i < v.length; i++ ) {
                    stmt.addBatch( v[i] );
                    System.out.println(v[i]);
                }
                stmt.executeBatch();
                this.con.commit();
                this.close();
                stmt = null;
                isSccess = 1;

            }
            catch ( SQLException se ) {
                this.con.rollback();
                this.close();
                isSccess = 0;
                se.printStackTrace();
            }

        }
        if( o instanceof Vector) {
            Vector v = (Vector) o;
            try {
                this.con.setAutoCommit( false );
                if ( stmt == null ) {
                    stmt = con.createStatement();
                }
                for ( int i = 0; i < v.size(); i++ ) {
                	System.out.println(( String ) v.elementAt( i ));
                    stmt.addBatch( ( String ) v.elementAt( i ) );
                }
                stmt.executeBatch();
                this.con.commit();
                this.close();
                stmt = null;
                isSccess = 1;

            }
            catch ( SQLException se ) {
                this.con.rollback();
                this.close();
                isSccess = 0;
                throw se;
            }
        }
        close();
        return isSccess;
    }

    /**执行单条SQL修改操作
     * 传入参数：  sqlString ---- SQL语句
     *            param ---- 预处理参数值
     *            paramType ---- 参数值类型
     * 成功 --- 返回 1
     * 失败 --- 返回 0
     */
    public int executeMaintain( String sqlString, Object param, Object paramType ) {
        init();
        int flag = 0;
        //获得列表值
        String[] paramValue = (String[]) param;
        //获取参数值类型
        String[] Type = (String[]) paramType;
        try {
            System.out.println("Start Execute.............................");
            System.out.println("SQL ================= " + sqlString);
            prestmt = con.prepareStatement(sqlString);
            for(int i = 0; i < paramValue.length; i++) {
                setValue(i+1, paramValue[i], Type[i]);
                System.out.println("paramValue["+i+"] :                     " + paramValue[i]);
            }
            //获得数据库执行结果
            flag = prestmt.executeUpdate();
            close();
            System.out.println("End Execute.............................");
            if(flag>0){
              return 1;
            }else{
              return flag;
            }
        }catch(SQLException exep ){
		  try{close();}catch(Exception ex){}
		  log.log(Log.ERROR, "DataBase Query Error......");
		  exep.printStackTrace();
		  return flag;
	    }
        catch(Exception e) {
            log.log(Log.ERROR, "Execute DataBase Error........");
            try{this.close();}catch(Exception ex){ex.printStackTrace();}
            return flag;
        }
    }

    /** 执行查询操作
     * 传入参数: sqlString ---- SQL查询语句
     *          o ----- 参数列表 String[]类型
     *          columnCount ---- 查询字段数
     *          type ---- 参数列表值的类型
    */
    public Vector getPreparedResult(int columnCount, String sqlString, Object o, Object type ) {
        init();
        //保存结果集
        Vector v = new Vector();
        //获得参数列表
        String[] param = (String[]) o;
        //获得参数类型
        String[] paramType = (String[]) type;
        //查询结果集
        ResultSet rs = null;
        try {
            System.out.println("Start Query............................. ");
            System.out.println("SQL ================= " + sqlString );
            prestmt = con.prepareStatement(sqlString);
            //循环设置参数值
            for(int i =0 ; i < param.length; i++ ) {
                setValue(i+1, param[i], paramType[i]);
                System.out.println("paramValue["+i+"] :           " + paramType[i] +"  "+ param[i]);
            }
            rs = prestmt.executeQuery();

            if (rs==null)
            {System.out.print("rs is null");}
            if( rs != null ) {
                while( rs.next() ) {
                    //将每条结果集放入一个String[]
                    String[] value = new String[columnCount];
                    for( int j = 1; j <= columnCount; j ++ ) {
                        value[j-1] = rs.getString(j);
                    }
                    //将String[]对象放入Vector
                    v.add(value);
                }
            }
            System.out.println("End Query..................................");
            rs.close();
            close();
            return v;
        }
        catch(SQLException exep ){
			try{close();}catch(Exception ex){}
			log.log(Log.ERROR, "DataBase Query Error......");
			exep.printStackTrace();
        	return null;
        }
        catch( Exception e ) {
        	try{close();}catch(Exception ex){}
            log.log(Log.ERROR, "DataBase Query Error......");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 执行存储过程方法
     * 传入参数：  sqlString --- SQL语句
     * 回返参数：   1 --- 成功
     *            0 --- 失败
     */
    public int executeCallPreStat(String sqlString,Object o, Object type ) {
        init();
        //获得参数列表
        String[] param = (String[]) o;
        //获得参数类型
        String[] paramType = (String[]) type;
        int flag = 0;
        try {

            callStat = con.prepareCall(sqlString);

            for(int i =0 ; i < param.length; i++ ) {
               setCallValue(i+1, param[i], paramType[i]);
               System.out.println("paramValue["+i+"] :           " + paramType[i] +"  "+ param[i]);
           }
           callStat.execute();
           return 1;
        }catch(SQLException exep ){
		  try{close();}catch(Exception ex){}
		  log.log(Log.ERROR, "DataBase Query Error......");
		  exep.printStackTrace();
		  return 0;
	     }
        catch( Exception e ) {
            log.log(Log.ERROR, "Callable SQL Execute Error......");
            return 0;
        }
    }
    
    
    /**
     * 执行包体过程方法
     * 传入参数：  包体过程名称，过程入参
     * 回返参数：  包体过程的返回结果
     *            
     */
    public String executeCallPreStat(String ProcedureName,String ls_para ) {
        init();
        String ls_result="";
        try {
        	this.con.setAutoCommit( false );            
            callStat = con.prepareCall("{call "+ProcedureName+"(?,?)}");        	
            callStat.setString(1,ls_para);                //设置输入参数                         
            //callStat.registerOutParameter(1,java.sql.Types.INTEGER);         //sqlserver新增
            //callStat.registerOutParameter(2,Types.CHAR);  //设置输出参数
            callStat.registerOutParameter(2,java.sql.Types.VARCHAR);  //设置输出参数            
            //callStat.executeQuery();                                //返回结果集的执行
            callStat.execute();
            //int returnValue = callStat.getInt(1);                           //sqlserver新增
            ls_result = callStat.getString(2); 
            //判断是提交还是回滚
            System.out.println("result:"+ls_result.substring(1,2));
            if (ls_result.substring(0,3).equals("|0|")){
            	this.con.commit();
            }else{
            	this.con.rollback();
            }
            close();
        }catch(SQLException exep ){
		  try{
			  this.con.rollback();
			  close();
		  }catch(Exception ex){}
		  log.log(Log.ERROR, "DataBase Query Error......");
		  exep.printStackTrace();
		  return ls_result;
	    }catch( Exception e ) {
            log.log(Log.ERROR, "Callable SQL Execute Error......");
            return ls_result;
        }
	    if (!ProcedureName.equals("op_checkXml")){  //非通用校验函数，都要去掉前面的|0|或|1|才是正确的XML返回数据
	    	ls_result=ls_result.substring(3);
	    }
	    return ls_result;
    }
    
    
    /**
     * 执行包体过程方法:返回查询结果
     * 传入参数：  包体过程名称，过程入参
     * 回返参数：  包体过程的返回结果
     * @throws SQLException 
     *            
     */
    public CachedRowSet executeCallPreStatReturnRowSet(String ProcedureName,String ls_para ) throws SQLException {
        init();
        int rownum=0;
        CachedRowSet crs =new CachedRowSet();        
        try {
        	this.con.setAutoCommit( false );            
            callStat = con.prepareCall("{call "+ProcedureName+"(?)}");        	
            callStat.setString(1,ls_para);                //设置输入参数                         
            //callStat.registerOutParameter(1,java.sql.Types.INTEGER);         //sqlserver新增
            //callStat.registerOutParameter(2,Types.CHAR);  //设置输出参数            
            //callStat.registerOutParameter(2,java.sql.Types.VARCHAR);  //设置输出参数    
            //callStat.executeQuery();                                //返回结果集的执行            
            ResultSet rs=callStat.executeQuery();                                //返回结果集的执行
            crs.populate(rs);
            rs.close();
            //callStat.execute();
            //int returnValue = callStat.getInt(1);                           //sqlserver新增
            //ls_result = callStat.getString(2);            
            close();            
        }catch(SQLException exep ){
		  try{
			  this.con.rollback();
			  close();
		  }catch(Exception ex){}
		  log.log(Log.ERROR, "DataBase Query Error......");
		  exep.printStackTrace();		  
	    }catch( Exception e ) {
            log.log(Log.ERROR, "Callable SQL Execute Error......");            
        }
	    try
	      {
	        if (crs != null)
	        {
	          crs.last();
	          rownum = crs.getRow();
	          crs.beforeFirst();
	        }
	      }
	      catch(SQLException se){se.printStackTrace();}
	      if(crs!=null&&rownum>0)
	         {
	           log.log(Log.DEGUG,"in db,crs is not null");
	           return crs;
	         }
	      else return null;
    }


    /**
	 * @param sqlString
	 * @return
	 */
	/**    执行简单SQL语句
     *    输入参数：  sqlString ---- SQL语句
     *    返回参数：  1 ---- 成功
     *              0 ---- 失败
     */
    public int executeSimpleSQL(String sqlString) {
        init();
        int flag = 0;
        try {
            System.out.println("Start update............................. ");
            System.out.println("SQL ================= " + sqlString );
            stmt = con.createStatement();
            flag = stmt.executeUpdate(sqlString);
            close();
            System.out.println("End update............................. ");
            if(flag>0){
             return 1;
            }else{
             return flag;
            }

        }
		catch(SQLException exep ){
					System.out.println("执行SQL时出错:"+exep.getMessage());
					try{close();}catch(Exception ex){}
					log.log(Log.ERROR, "DataBase Query Error......");
					exep.printStackTrace();
					return flag;
		}
        catch (Exception e) {
        	System.out.println("执行SQL出错:"+e.getMessage());
            try {
                close();
            }catch(Exception ex) {
                ex.printStackTrace();
            }
            log.log(Log.ERROR, "Execute DataBase Error................");
            return flag;
        }
    }


  /**根据sql语句取得缓存行集,用于非表格显示的数据
  /* 传入参数：   sqlstr -----  SQL查询语句
     * 传回参数： crs   -----  CachedRowSet 集
     * @return
     */

   public CachedRowSet getCachedRowSet(String sqlstr) throws Exception
    {
      CachedRowSet crs =new CachedRowSet();
      int rownum=0;
      try
      {
        init();
        this.stmt=this.con.createStatement();
        System.out.println("SQL ================= " + sqlstr );
        ResultSet rs=stmt.executeQuery(sqlstr);
        crs.populate(rs);
        rs.close();
        close();
      } catch(SQLException se) {close();}
      try
      {
        if (crs != null)
        {
          crs.last();
          rownum = crs.getRow();
          crs.beforeFirst();
        }
      }
      catch(SQLException se){se.printStackTrace();}
      if(crs!=null&&rownum>0)
         {
           log.log(Log.DEGUG,"in db,crs is not null");
           return crs;
         }
      else return null;

    }

//设置预处理参数
    private void setValue(int index, String value, String type ) throws Exception {
        //设置字符型
        if("varchar".equals(type.toLowerCase()) || "char".equals(type.toLowerCase())) {
            prestmt.setString(index, value);
        }
        //设置日期型
        if("date".equals(type.toLowerCase())) {
        	prestmt.setDate(index, SqlDateFormat.format(value));
           }
        //设置长整型
        if("long".equals(type.toLowerCase())) {
            prestmt.setLong(index, Long.parseLong(value));
        }
        //设置整型
        if("int".equals(type.toLowerCase())) {
            prestmt.setInt(index, Integer.parseInt(value));
        }
        //设置短整型
        if("short".equals(type.toLowerCase())) {
            prestmt.setShort(index, Short.parseShort(value));
        }
        //设置浮点型
        if("float".equals(type.toLowerCase())) {
            prestmt.setFloat(index, Float.parseFloat(value));
        }
        //设置双精度型
        if("double".equals(type.toLowerCase())) {
            prestmt.setDouble(index, Double.parseDouble(value));
        }
    }

  //设置储存过程参数
     private void setCallValue(int index,
                                        String value, String type ) throws Exception {
         //设置字符型
         if("varchar".equals(type.toLowerCase()) || "char".equals(type.toLowerCase())) {
             callStat.setString(index, value);
         }
         //设置日期型
         if("date".equals(type.toLowerCase())) {
             callStat.setDate(index, SqlDateFormat.format(value));
         }
         //设置长整型
         if("long".equals(type.toLowerCase())) {
             callStat.setLong(index, Long.parseLong(value));
         }
         //设置整型
         if("int".equals(type.toLowerCase())) {
             callStat.setInt(index, Integer.parseInt(value));
         }
         //设置短整型
         if("short".equals(type.toLowerCase())) {
             callStat.setShort(index, Short.parseShort(value));
         }
         //设置浮点型
         if("float".equals(type.toLowerCase())) {
             callStat.setFloat(index, Float.parseFloat(value));
         }
         //设置双精度型
         if("double".equals(type.toLowerCase())) {
             callStat.setDouble(index, Double.parseDouble(value));
         }
     }


//释放资源.......
    public void close() throws Exception {
                if (this.stmt != null)  {
                        this.stmt.close();
                        this.stmt = null;
                }
                if (this.prestmt != null) {
                        this.prestmt.close();
                        this.prestmt = null;
                }
                if (this.con!=null)
                {
                        this.con.close();
                        this.con=null;
                }
    }
}
