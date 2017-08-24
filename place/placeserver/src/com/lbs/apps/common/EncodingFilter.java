package com.lbs.apps.common;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.lbs.commons.GlobalNames;
/**
 * 编码过滤器
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: LBS</p>
 * @author chenkl <chenkl@bjlbs.com.cn>
 * @version 1.0
 */

public class EncodingFilter implements Filter {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(EncodingFilter.class);

    protected String encoding = null;
    protected FilterConfig filterConfig = null;

    /**
     * 析构函数
     */
    public void destroy() {
        this.encoding = null;
        this.filterConfig = null;
    }

    /**
     * 设置编码
     * @param request ServletRequest
     * @param response ServletResponse
     * @param chain FilterChain
     * @throws IOException
     * @throws ServletException
     */
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain)
        throws IOException, ServletException {
		HttpServletRequest req=(HttpServletRequest) request;
/*		if (logger.isInfoEnabled()) {
			String s;
			StringBuffer sb=new StringBuffer();
			sb.append("process request : \n");
			s=req.getContextPath();
			if (s!=null) {
				sb.append(s);
			}
			s=req.getServletPath();
			if (s!=null) {
				sb.append(s);
			}
			s=req.getQueryString();
			if (s!=null) {
				sb.append("?").append(s);
			}

			sb.append("\n.......");
			logger.info(sb.toString());
		}*/
      if (encoding != null) {
        request.setCharacterEncoding(encoding);
      } else { //设置为缺省的GBK
        request.setCharacterEncoding(GlobalNames.DEFAULT_ENCODING);
      }
	  //防止代理服务器缓存
	    HttpServletResponse res=(HttpServletResponse)response;
	    res.setHeader("Cache-Control","no-cache");
//	    res.setDateHeader("Expires",0);
//	    res.setHeader("Pragma","No-cache");
      //为了经办人和经办机构而暂时添加的部分
  //==============================================================================
//      HttpServletRequest req = (HttpServletRequest) request;
//      CurrentUser cu = new CurrentUser();
//      SysUser su = new SysUser();
//      su.setUserid("test");
//      su.setDept("test dept");
//      su.setDescription("test");
//      su.setOperatorname("test");
//
//      SysFunction sf = new SysFunction();
//      sf.setFunctionid("test");
//      sf.setLog("1");
//      sf.setDescription("test");
//
//      cu.setUser(su);
//      cu.setCurFunction(sf);
//      cu.setOrg("test");
//      req.getSession().setAttribute(GlobalNames.CURRENT_USER, cu);
  //==========================================================================


      chain.doFilter(request, response);
    }

    /**
     * 初始化
     * @param filterConfig FilterConfig
     * @throws ServletException
     */
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
        this.encoding = filterConfig.getInitParameter(GlobalNames.ENCODING);
    }

}

