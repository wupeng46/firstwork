package com.lbs.commons.op;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.lbs.commons.GlobalNames;

/**
 * Session工厂
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company: LBS
 * </p>
 *
 * @author chenkl <chenkl@bjlbs.com.cn>
 * @version 1.0
 */

public class HibernateSession {
	private static Logger logger = Logger.getLogger("com.lbs.commons.op.HibernateSession");
	/**
	 * 取得Session
	 *
	 * @return @throws
	 *         OPException
	 */
	public static Session currentSession() throws OPException {
		Session s = (Session) session.get();

		try {
			if (s == null) {
				if (sf == null) {
						//cfg= new Configuration().configure();
					    //modify by whd 2013.5.14 更换成SPRING的配置文件
						////BeanFactory f = new ClassPathXmlApplicationContext(new String[] {"applicationContext.xml"});  
				        //LocalSessionFactoryBean configBean = (AnnotationSessionFactoryBean) (f.getBean("&sessionFactory"));  						
						////LocalSessionFactoryBean configBean = (LocalSessionFactoryBean) (f.getBean("&sessionFactory"));
				        ////cfg = configBean.getConfiguration(); 
				        
						//sf = cfg.buildSessionFactory();   //modify by whd 2013.5.14 更换成SSH的方法
						////sf = cfg.configure().buildSessionFactory();			
						
						
						Resource resource=new ClassPathResource("applicationContext.xml"); 
                        BeanFactory factory=new XmlBeanFactory(resource); 
                        sf = (SessionFactory)factory.getBean("sessionFactory");  
						
						try {
							//String batchSize = cfg.getProperty("jdbc.batch_size");
							String batchSize = "50";                                   //modify by whd 2013.5.14 spring配置中暂不配置此属性
							if(null != batchSize && !"".equals(batchSize)){
								GlobalNames.JDBC_BATCH_SIZE = Integer.parseInt(batchSize);
								logger.info("=========GlobalNames.JDBC_BATCH_SIZE: " + GlobalNames.JDBC_BATCH_SIZE);
							}
						} catch (NumberFormatException e) {
							GlobalNames.JDBC_BATCH_SIZE = 50;
						}
				}
				s = sf.openSession();
				session.set(s);
			}
		} catch (Exception he) {
			throw new OPException(he);
		}
		return s;

	}

	/**
	 * 关闭当前session
	 *
	 * @throws OPException
	 */
	public static void closeSession() throws OPException {
		Session s = (Session) session.get();
		session.set(null);

		if (s != null) {
			try {
				s.close();
			} catch (HibernateException he) {
				throw new OPException(he);
			}
		}
	}
	/**
	 * Comment for <code>session</code>
	 *
	 */
	public static final ThreadLocal session = new ThreadLocal();
	private static  Configuration cfg;
	private static SessionFactory sf;
    
    public static Configuration getConfiguration() {
        return cfg;
    }

}
