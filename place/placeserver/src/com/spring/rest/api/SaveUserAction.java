package com.spring.rest.api;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.eon.uc.util.AESUtil;
import com.eon.uc.util.MD5;
import com.eon.uc.util.SignUtil;
import com.lbs.apps.common.ApplicationException;
import com.lbs.apps.system.dao.PlatUserDTO;
import com.lbs.apps.system.dao.SysuserBPO;
import com.lbs.commons.GlobalNames;

@Controller
public class SaveUserAction {	
	 /*
		 uid:�û�id��Ψһ��(int)
		 user_name����¼�û����ƣ����ǹ�˾����
		 password������,aes���ܡ�
		 user_type:��˾/����(string)
		 user_id:��¼ʹ�õģ�Ψһ�������ʽ�ӿ�ʹ�ø�ƾ֤��
		 mobile���ֻ��ţ�Ψһ
		 salt:md5���μ���ʹ�õ�
		 card_type������ǹ�˾�û���ͳһ������ô���֤;����Ǹ��ˣ��������֤
		 id:����ǹ�˾����֤�ţ����������֤��
		 com_name:��˾����
		 digest:��ǰ�����ݰ������secret��md5
	  */
	 //@RequestMapping(value="/saveUser/{uid}/{user_name}/{password}/{user_type}/{user_id}/{mobile}/{salt}/{card_type}/{id}/{digest}")
	 @RequestMapping(value="/saveUser")
	 public void SaveUser(HttpServletRequest request, HttpServletResponse response, 
			  String uid, 
			  String user_name,
			  String password,
			  String user_type,
			  String user_id,
			  String mobile,
			  String salt,
			  String card_type,
			  String id,
			  String com_name,
			  String digest) throws UnsupportedEncodingException {	
		 
		 Map<String, String> paraMap = new HashMap<String, String>();
		 paraMap.put("uid", uid);
		 paraMap.put("user_name", user_name);
		 paraMap.put("password", password);
		 paraMap.put("user_type", user_type);
		 paraMap.put("user_id", user_id);
		 paraMap.put("mobile", mobile);
		 paraMap.put("salt", salt);		 
		 paraMap.put("card_type", card_type);
		 paraMap.put("id", id);
		 paraMap.put("com_name", com_name);
		 String queryString=SignUtil.createLinkString(paraMap);
		 //System.out.println("������queryString:"+queryString);
		 //עGlobalNames.PLATFORM_AES_KEY=c5d608eb97d94123
		 String digest_local=MD5.sign(queryString+"&key="+GlobalNames.PLATFORM_AES_KEY,"UTF-8").toString();  //���ؼ���digest
		 if (!digest_local.equals(digest)){
			 printData(response,"digest��֤�쳣");
		 }
		 
		 PlatUserDTO dto=new PlatUserDTO();
		 dto.setUid(uid);
		 dto.setUser_name(user_name);
		 try {
			dto.setPassword(AESUtil.decrypt(password, GlobalNames.PLATFORM_AES_KEY));
		 } catch (Exception e1) {
			e1.printStackTrace();
		 }	
		 if (user_type==null || user_type.equals("����")){
			 user_type="101";  //�����û�ע��
		 }else{
			 user_type="102";  //��ҵ�û�ע��
		 }
		 dto.setUser_type(user_type);
		 dto.setUser_id(user_id);
		 dto.setMobile(mobile);
		 dto.setSalt(salt);
		 dto.setCard_type(card_type);
		 dto.setId(id);
		 dto.setCom_name(com_name);
		 dto.setDigest(digest);		 
	     
		 SysuserBPO bpo=new SysuserBPO();
		 //��֤digest�Ƿ���ȷ
		 String ls_return="";
		 try {
			ls_return= bpo.saveUser(dto);
		} catch (ApplicationException e) {
			ls_return= e.getMessage();
		}
		printData(response,ls_return);		  
	 }
	 
	 
	 @RequestMapping(value="/password")
	 public void password(HttpServletRequest request, HttpServletResponse response, 
			  String user_id, 
			  String password,
			  String salt,
			  String digest) throws UnsupportedEncodingException {
		 Map<String, String> paraMap = new HashMap<String, String>();
		 paraMap.put("user_id", user_id);
		 paraMap.put("password", password);
		 paraMap.put("salt", salt);
		 String queryString=SignUtil.createLinkString(paraMap);
		 System.out.println("������queryString:"+queryString);
		 //עGlobalNames.PLATFORM_AES_KEY=c5d608eb97d94123
		 String digest_local=MD5.sign(queryString+"&key="+GlobalNames.PLATFORM_AES_KEY,"UTF-8").toString();  //���ؼ���digest
		 if (!digest_local.equals(digest)){
			 printData(response,"digest��֤�쳣");
		 }		 
		 PlatUserDTO dto=new PlatUserDTO();
		 dto.setUser_id(user_id);
		 System.out.println("��������:"+password);
		 try {
			dto.setPassword(AESUtil.decrypt(password, GlobalNames.PLATFORM_AES_KEY));
		 } catch (Exception e1) {
			e1.printStackTrace();
		 }
		 dto.setSalt(salt);
		 dto.setDigest(digest);
		 SysuserBPO bpo=new SysuserBPO();
		 //��֤digest�Ƿ���ȷ
		 String ls_return="";
		 try {
			ls_return= bpo.ModPassword(dto);
		} catch (ApplicationException e) {
			ls_return= e.getMessage();
		}
		printData(response,ls_return);		 
	 }
	 
	 /*
	  * �û�������֤
	  */
	 @RequestMapping(value="/authorize")
	 public void authorize(HttpServletRequest request, HttpServletResponse response, 
			  String user_id, 
			  String legal_person,
			  String legal_person_id,
			  String company_name,
			  String company_type,
			  String bank,
			  String bank_user,
			  String bank_num,
			  String zip_code,
			  String tel,
			  String fax,
			  String email,
			  String mobile,
			  String contact,
			  String post_addr,
			  String post_user,
			  String post_mobile,
			  String reside_province,
			  String reside_city,
			  String reside_dist,
			  String address,
			  String digest) throws UnsupportedEncodingException {
		 Map<String, String> paraMap = new HashMap<String, String>();
		 paraMap.put("user_id", user_id);
		 paraMap.put("legal_person", legal_person);
		 paraMap.put("legal_person_id", legal_person_id);
		 paraMap.put("company_name", company_name);
		 paraMap.put("company_type", company_type);
		 paraMap.put("bank", bank);
		 paraMap.put("bank_user", bank_user);
		 paraMap.put("bank_num", bank_num);
		 paraMap.put("zip_code", zip_code);
		 paraMap.put("tel", tel);
		 paraMap.put("fax", fax);
		 paraMap.put("email", email);
		 paraMap.put("mobile", mobile);
		 paraMap.put("contact", contact);
		 paraMap.put("post_addr", post_addr);
		 paraMap.put("post_user", post_user);
		 paraMap.put("post_mobile", post_mobile);
		 paraMap.put("reside_province", reside_province);
		 paraMap.put("reside_city", reside_city);
		 paraMap.put("reside_dist", reside_dist);
		 paraMap.put("address", address);		 
		 
		 String queryString=SignUtil.createLinkString(paraMap);
		 System.out.println("��֤������queryString:"+queryString);
		 //עGlobalNames.PLATFORM_AES_KEY=c5d608eb97d94123
		 String digest_local=MD5.sign(queryString+"&key="+GlobalNames.PLATFORM_AES_KEY,"UTF-8").toString();  //���ؼ���digest
		 if (!digest_local.equals(digest)){
			 printData(response,"digest��֤�쳣");
		 }		 
		 PlatUserDTO dto=new PlatUserDTO();
		 dto.setUser_id(user_id);
		 dto.setLegal_person(legal_person);
		 dto.setLegal_person_id(legal_person_id);
		 dto.setCom_name(company_name);
		 dto.setCompany_type(company_type);
		 dto.setCompany_name(company_name);
		 dto.setCompany_type(company_type);
		 dto.setBank(bank);
		 dto.setBank_user(bank_user);
		 dto.setBank_num(bank_num);
		 dto.setZip_code(zip_code);
		 dto.setTel(tel);
		 dto.setFax(fax);
		 dto.setEmail(email);
		 dto.setMobile(mobile);
		 dto.setContact(contact);
		 dto.setPost_addr(post_addr);
		 dto.setPost_mobile(post_mobile);
		 dto.setPost_user(post_user);
		 dto.setReside_province(reside_province);
		 dto.setReside_city(reside_city);
		 dto.setReside_dist(reside_dist);		
		 dto.setAddress(address);
		 dto.setDigest(digest);		 
		 SysuserBPO bpo=new SysuserBPO();
		 System.out.println(dto.toString());
		 String ls_return="";		 
		 try {
			ls_return= bpo.Authorize(dto);   //�����û���֤����
			System.out.println("��֤���淵�ؽ��:"+ls_return);
		} catch (ApplicationException e) {
			ls_return= e.getMessage();
		}
		printData(response,ls_return);		 
	 }
	 
	 /*
	  * �û����ϸ���
	  */
	 @RequestMapping(value="/update")
	 public void update(HttpServletRequest request, HttpServletResponse response, 
			 String user_name,
			 String user_type,
			 String user_id,
			 String mobile,
			 String card_type,
			 String id,
			 String com_name,
			 String province,
			 String city,
			 String district,
			 String desc, 
			 String digest) throws UnsupportedEncodingException {
		 Map<String, String> paraMap = new HashMap<String, String>();
		 paraMap.put("user_name", user_name);
		 paraMap.put("user_type", user_type);
		 paraMap.put("user_id", user_id);
		 paraMap.put("mobile", mobile);
		 paraMap.put("card_type", card_type);
		 paraMap.put("id", id);
		 paraMap.put("com_name", com_name);
		 paraMap.put("province", province);
		 paraMap.put("city", city);
		 paraMap.put("district", district);
		 paraMap.put("desc", desc);		 
		 
		 String queryString=SignUtil.createLinkString(paraMap);
		 System.out.println("update������queryString:"+queryString);
		 //עGlobalNames.PLATFORM_AES_KEY=c5d608eb97d94123
		 String digest_local=MD5.sign(queryString+"&key="+GlobalNames.PLATFORM_AES_KEY,"UTF-8").toString();  //���ؼ���digest
		 if (!digest_local.equals(digest)){
			 printData(response,"digest��֤�쳣");
		 }		 
		 if (user_type==null || user_type.equals("����")){
			 user_type="101";  //�����û�ע��
		 }else{
			 user_type="102";  //��ҵ�û�ע��
		 }
		 PlatUserDTO dto=new PlatUserDTO();
		 dto.setUser_name(user_name);
		 dto.setUser_type(user_type);
		 dto.setUser_id(user_id);		 
		 dto.setMobile(mobile);
		 dto.setCard_type(card_type);
		 dto.setId(id);
		 dto.setCom_name(com_name);
		 dto.setCompany_name(com_name);
		 dto.setReside_province(province);
		 dto.setReside_city(city);
		 dto.setReside_dist(district);
		 dto.setDesc(desc);		 
		 dto.setDigest(digest);
		 
		 SysuserBPO bpo=new SysuserBPO();
		 System.out.println(dto.toString());
		 String ls_return="";		 
		 try {
			ls_return= bpo.updateUser(dto);   //�����û���֤����
			System.out.println("�޸��û����ؽ��:"+ls_return);
		} catch (ApplicationException e) {
			ls_return= e.getMessage();
		}
		printData(response,ls_return);		 
	 }
	 
	 /**
	  * ����ֻ�����
	  * @param request
	  * @param response
	  * @param user_id
	  * @param mobile
	  * @param digest
	  * @throws UnsupportedEncodingException
	  */
	 @RequestMapping(value="/mobile")
	 public void mobile(HttpServletRequest request, HttpServletResponse response, 
			 String user_id,
			 String mobile,
			 String digest) throws UnsupportedEncodingException {
		 Map<String, String> paraMap = new HashMap<String, String>();
		 paraMap.put("user_id", user_id);
		 paraMap.put("mobile", mobile);
		 String queryString=SignUtil.createLinkString(paraMap);
		 System.out.println("mobile������queryString:"+queryString);
		 //עGlobalNames.PLATFORM_AES_KEY=c5d608eb97d94123
		 String digest_local=MD5.sign(queryString+"&key="+GlobalNames.PLATFORM_AES_KEY,"UTF-8").toString();  //���ؼ���digest
		 if (!digest_local.equals(digest)){
			 printData(response,"digest��֤�쳣");
		 }
		 PlatUserDTO dto=new PlatUserDTO();
		 dto.setUser_id(user_id);		 
		 dto.setMobile(mobile);
		 
		 SysuserBPO bpo=new SysuserBPO();
		 System.out.println(dto.toString());
		 String ls_return="";		 
		 try {
			ls_return= bpo.updateUser(dto);   //�����û���֤����
			System.out.println("�޸��û����ؽ��:"+ls_return);
		} catch (ApplicationException e) {
			ls_return= e.getMessage();
		}
		printData(response,ls_return);		 
	 }
	 
	 private void printData(HttpServletResponse response, String msg) {
        try {
         response.setContentType("text/html;charset=utf-8");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), "UTF-8"));
            out.println(msg);
            out.close();
        } catch (Exception e) {  
            e.printStackTrace();  
        }
    }

}
