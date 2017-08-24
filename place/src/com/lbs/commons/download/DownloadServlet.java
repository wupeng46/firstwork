package com.lbs.commons.download;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.lbs.commons.GlobalNames;
import com.lbs.commons.StringHelper;

/**
 * <p>Title: ����Ͷ���ҵ����ϵͳ</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: ����ɷ�</p>
 * @author �����
 * @modified by chenkl
 * @version 1.0
 */

public class DownloadServlet
    extends HttpServlet {

  //Initialize global variables
  public void init() throws ServletException {
  }

  //Process the HTTP Get request
  public void service(HttpServletRequest request, HttpServletResponse response) throws
      ServletException, IOException {
    HttpSession session = request.getSession(false);
    if (session == null) {
      response.getWriter().println(StringHelper.ChineseStringToAscii(
          "�����´���û��Ȩ�ޣ�"));
      return;
    }

    try {
      String type = (null == request.getAttribute("type")) ?
          request.getParameter("type") : (String) request.getAttribute("type");
      String name = (null == request.getAttribute("name")) ?
          request.getParameter("name") : (String) request.getAttribute("name");
      if (name == null || type == null) {
        return;
      }

      //����ѯ���ݵ�����excel
      if(type.equals("excel")){
        //HSSFWorkbook book = (HSSFWorkbook)request.getAttribute("excelData");
    	HSSFWorkbook book = (HSSFWorkbook)session.getAttribute("excelData");        //�´���ģʽ    	  
//        setResponse(response,name,".xls");
        response.setContentType("application/vnd.ms-excel");
        StringBuffer contentDisposition = new StringBuffer("\"attachment; filename=\"");
        contentDisposition.append(name).append(".xls");
        response.setHeader("Content-disposition", contentDisposition.toString());
        ServletOutputStream out = response.getOutputStream();
        book.write(out);
        out.flush();
        out.close();
        return;
      }
      //����ѯ���ݵ�����txt
      if(type.equals("txt")){
        //String logData = (String)request.getAttribute(GlobalNames.LOG_DATA);
    	String logData=(String)session.getAttribute(GlobalNames.LOG_DATA);        //�´���ģʽ
        setResponse(response,name+".txt","");
        ServletOutputStream out = response.getOutputStream();
        out.write(logData.getBytes(GlobalNames.DEFAULT_ENCODING));
        out.flush();
        out.close();
        return;
      }
      //����ΪEXCEL��ʽ
      if(type.equals("xls")){
    	  response.setHeader("Content-Disposition","attachment;filename="+name+".xls");//ָ�����ص��ļ���
    	  response.setContentType("application/x-msdownload");    	  
      	  String logData=(String)session.getAttribute(GlobalNames.LOG_DATA);        //�´���ģʽ          
          ServletOutputStream out = response.getOutputStream();
          out.write(logData.getBytes(GlobalNames.DEFAULT_ENCODING));
          out.flush();
          out.close();
          return;
        }

      StringBuffer fileName = new StringBuffer(256);
      //����
      if (type.equals("report")) {
        fileName.append(this.getServletContext().getRealPath(""));
        fileName.append(File.separatorChar);
        fileName.append("reports");
        fileName.append(File.separatorChar);
        fileName.append(name);
        fileName.append(".xml");
        setResponse(response,name,".xml");
      }
      //ͼƬ
      if (type.equals("photo")) {
        fileName.append(this.getServletContext().getRealPath(GlobalNames.TEMP_PHOTO_PATH));
        fileName.append(File.separatorChar);
        fileName.append(name);
        setResponse(response,null,null);
      }

      FileInputStream in = new FileInputStream(fileName.toString());
      ServletOutputStream out = response.getOutputStream();
      int b;

      while ( (b = in.read()) != -1) {
        out.write(b);
      }
      in.close();
    } catch (Exception e) {
      return;
    }

  }

//Clean up resources
  public void destroy() {
  }

  /**
   * ����response����
   * @param response HttpServletResponse
   * @param name String
   * @param ext String
   */
  private void setResponse(HttpServletResponse response,String name,String ext){
    if(null == name || null == ext){
       response.setContentType("text/html; charset=GBK");
    }else{
      response.setContentType("application/octet-stream; charset=GBK");//iso-8859-1
      StringBuffer contentDisposition = new StringBuffer("\"attachment; filename=\"");
      contentDisposition.append(name).append(ext);
      response.setHeader("Content-disposition", contentDisposition.toString());
    }

  }
}
