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
 * <p>Title: ����ͨ�ýӿڹ���ϵͳ</p>
 * <p>Description: </p>
 * <p>Copyright: 2004.1.6</p>
 * <p>Company: ����</p>
 * @author �ĻԶ�
 * @rework �ĻԶ�
 * @version 1.0
 */

public class DBOperate implements Serializable {
    //����Ԥ����SQL����
    private PreparedStatement prestmt = null;
    //����ֱ�Ӵ���SQL����
    private Statement stmt = null;
    //����洢���̴���SQL����
    private CallableStatement callStat = null;
    //�������ݿ����Ӷ���
    private Connection con = null;
    //�����ȡ����Դ����
    private DataSourceFactory dataSource = new DataSourceFactory("jdbc/zdjk");    
    //������־�������
    private Log log = null;

    public DBOperate() {
        try {
            con = dataSource.getDataSource().getConnection();
            log = Log.getInstance();
        }catch( Exception e ) {
        	System.out.print("�����ݿ�����ʧ��");
            log.log(Log.ERROR, "DataBase Connected Error......");
        }
    }

//��ʼ������
    public void init() {
        if ( con == null ) {
            try {
				System.out.println("JVM MAX MEMORY: "+Runtime.getRuntime().maxMemory()/1024/1024+"M");
				System.out.println("JVM IS USING MEMORY:"+Runtime.getRuntime().totalMemory()/1024/1024+"M");
				System.out.println("JVM IS Free MEMORY:"+Runtime.getRuntime().freeMemory()/1024/1024+"M");
                con = dataSource.getDataSource().getConnection();
            }
            catch ( Exception e ) {
				System.out.print("�����ݿ�����ʧ��");
                log.log( Log.ERROR , "DataBase Connected Error......" );
            }
        }
    }


/**�������������.......
    * �ɹ� --- ���� 1
     * ʧ�� --- ���� 0
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

    /**ִ�е���SQL�޸Ĳ���
     * ���������  sqlString ---- SQL���
     *            param ---- Ԥ�������ֵ
     *            paramType ---- ����ֵ����
     * �ɹ� --- ���� 1
     * ʧ�� --- ���� 0
     */
    public int executeMaintain( String sqlString, Object param, Object paramType ) {
        init();
        int flag = 0;
        //����б�ֵ
        String[] paramValue = (String[]) param;
        //��ȡ����ֵ����
        String[] Type = (String[]) paramType;
        try {
            System.out.println("Start Execute.............................");
            System.out.println("SQL ================= " + sqlString);
            prestmt = con.prepareStatement(sqlString);
            for(int i = 0; i < paramValue.length; i++) {
                setValue(i+1, paramValue[i], Type[i]);
                System.out.println("paramValue["+i+"] :                     " + paramValue[i]);
            }
            //������ݿ�ִ�н��
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

    /** ִ�в�ѯ����
     * �������: sqlString ---- SQL��ѯ���
     *          o ----- �����б� String[]����
     *          columnCount ---- ��ѯ�ֶ���
     *          type ---- �����б�ֵ������
    */
    public Vector getPreparedResult(int columnCount, String sqlString, Object o, Object type ) {
        init();
        //��������
        Vector v = new Vector();
        //��ò����б�
        String[] param = (String[]) o;
        //��ò�������
        String[] paramType = (String[]) type;
        //��ѯ�����
        ResultSet rs = null;
        try {
            System.out.println("Start Query............................. ");
            System.out.println("SQL ================= " + sqlString );
            prestmt = con.prepareStatement(sqlString);
            //ѭ�����ò���ֵ
            for(int i =0 ; i < param.length; i++ ) {
                setValue(i+1, param[i], paramType[i]);
                System.out.println("paramValue["+i+"] :           " + paramType[i] +"  "+ param[i]);
            }
            rs = prestmt.executeQuery();

            if (rs==null)
            {System.out.print("rs is null");}
            if( rs != null ) {
                while( rs.next() ) {
                    //��ÿ�����������һ��String[]
                    String[] value = new String[columnCount];
                    for( int j = 1; j <= columnCount; j ++ ) {
                        value[j-1] = rs.getString(j);
                    }
                    //��String[]�������Vector
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
     * ִ�д洢���̷���
     * ���������  sqlString --- SQL���
     * �ط�������   1 --- �ɹ�
     *            0 --- ʧ��
     */
    public int executeCallPreStat(String sqlString,Object o, Object type ) {
        init();
        //��ò����б�
        String[] param = (String[]) o;
        //��ò�������
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
     * ִ�а�����̷���
     * ���������  ����������ƣ��������
     * �ط�������  ������̵ķ��ؽ��
     *            
     */
    public String executeCallPreStat(String ProcedureName,String ls_para ) {
        init();
        String ls_result="";
        try {
        	this.con.setAutoCommit( false );            
            callStat = con.prepareCall("{call "+ProcedureName+"(?,?)}");        	
            callStat.setString(1,ls_para);                //�����������                         
            //callStat.registerOutParameter(1,java.sql.Types.INTEGER);         //sqlserver����
            //callStat.registerOutParameter(2,Types.CHAR);  //�����������
            callStat.registerOutParameter(2,java.sql.Types.VARCHAR);  //�����������            
            //callStat.executeQuery();                                //���ؽ������ִ��
            callStat.execute();
            //int returnValue = callStat.getInt(1);                           //sqlserver����
            ls_result = callStat.getString(2); 
            //�ж����ύ���ǻع�
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
	    if (!ProcedureName.equals("op_checkXml")){  //��ͨ��У�麯������Ҫȥ��ǰ���|0|��|1|������ȷ��XML��������
	    	ls_result=ls_result.substring(3);
	    }
	    return ls_result;
    }
    
    
    /**
     * ִ�а�����̷���:���ز�ѯ���
     * ���������  ����������ƣ��������
     * �ط�������  ������̵ķ��ؽ��
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
            callStat.setString(1,ls_para);                //�����������                         
            //callStat.registerOutParameter(1,java.sql.Types.INTEGER);         //sqlserver����
            //callStat.registerOutParameter(2,Types.CHAR);  //�����������            
            //callStat.registerOutParameter(2,java.sql.Types.VARCHAR);  //�����������    
            //callStat.executeQuery();                                //���ؽ������ִ��            
            ResultSet rs=callStat.executeQuery();                                //���ؽ������ִ��
            crs.populate(rs);
            rs.close();
            //callStat.execute();
            //int returnValue = callStat.getInt(1);                           //sqlserver����
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
	/**    ִ�м�SQL���
     *    ���������  sqlString ---- SQL���
     *    ���ز�����  1 ---- �ɹ�
     *              0 ---- ʧ��
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
					System.out.println("ִ��SQLʱ����:"+exep.getMessage());
					try{close();}catch(Exception ex){}
					log.log(Log.ERROR, "DataBase Query Error......");
					exep.printStackTrace();
					return flag;
		}
        catch (Exception e) {
        	System.out.println("ִ��SQL����:"+e.getMessage());
            try {
                close();
            }catch(Exception ex) {
                ex.printStackTrace();
            }
            log.log(Log.ERROR, "Execute DataBase Error................");
            return flag;
        }
    }


  /**����sql���ȡ�û����м�,���ڷǱ����ʾ������
  /* ���������   sqlstr -----  SQL��ѯ���
     * ���ز����� crs   -----  CachedRowSet ��
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

//����Ԥ�������
    private void setValue(int index, String value, String type ) throws Exception {
        //�����ַ���
        if("varchar".equals(type.toLowerCase()) || "char".equals(type.toLowerCase())) {
            prestmt.setString(index, value);
        }
        //����������
        if("date".equals(type.toLowerCase())) {
        	prestmt.setDate(index, SqlDateFormat.format(value));
           }
        //���ó�����
        if("long".equals(type.toLowerCase())) {
            prestmt.setLong(index, Long.parseLong(value));
        }
        //��������
        if("int".equals(type.toLowerCase())) {
            prestmt.setInt(index, Integer.parseInt(value));
        }
        //���ö�����
        if("short".equals(type.toLowerCase())) {
            prestmt.setShort(index, Short.parseShort(value));
        }
        //���ø�����
        if("float".equals(type.toLowerCase())) {
            prestmt.setFloat(index, Float.parseFloat(value));
        }
        //����˫������
        if("double".equals(type.toLowerCase())) {
            prestmt.setDouble(index, Double.parseDouble(value));
        }
    }

  //���ô�����̲���
     private void setCallValue(int index,
                                        String value, String type ) throws Exception {
         //�����ַ���
         if("varchar".equals(type.toLowerCase()) || "char".equals(type.toLowerCase())) {
             callStat.setString(index, value);
         }
         //����������
         if("date".equals(type.toLowerCase())) {
             callStat.setDate(index, SqlDateFormat.format(value));
         }
         //���ó�����
         if("long".equals(type.toLowerCase())) {
             callStat.setLong(index, Long.parseLong(value));
         }
         //��������
         if("int".equals(type.toLowerCase())) {
             callStat.setInt(index, Integer.parseInt(value));
         }
         //���ö�����
         if("short".equals(type.toLowerCase())) {
             callStat.setShort(index, Short.parseShort(value));
         }
         //���ø�����
         if("float".equals(type.toLowerCase())) {
             callStat.setFloat(index, Float.parseFloat(value));
         }
         //����˫������
         if("double".equals(type.toLowerCase())) {
             callStat.setDouble(index, Double.parseDouble(value));
         }
     }


//�ͷ���Դ.......
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
