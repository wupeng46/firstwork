package com.lbs.apps.system.dao;

import java.io.File;

import com.lbs.apps.common.ApplicationException;

public interface SystemDAO {	
	public String CheckSql_Where(String msgdata);
	public String GetSql_SearchSysrole(String msgdata)throws ApplicationException;
	public String AddSysrole(String msgdata)throws ApplicationException; //增加系统角色
	public String ModSysrole(String msgdata)throws ApplicationException; //修改系统角色
	public String DelSysrole(String msgdata)throws ApplicationException; //删除系统角色	
	public String GetSql_SearchSyspara(String msgdata)throws ApplicationException;
	public String AddSyspara(String msgdata)throws ApplicationException; //增加系统参数
	public String ModSyspara(String msgdata)throws ApplicationException; //修改系统参数
	public String DelSyspara(String msgdata)throws ApplicationException; //删除系统参数	
	public String GetSql_SearchSyscode(String msgdata)throws ApplicationException;
	public String AddSyscode(String msgdata)throws ApplicationException; //增加系统代码
	public String ModSyscode(String msgdata)throws ApplicationException; //修改系统代码
	public String DelSyscode(String msgdata)throws ApplicationException; //删除系统代码
	public String GetSql_SearchSysgroup(String msgdata)throws ApplicationException;
	public String GetSql_SearchChildSysgroup(String msgdata)throws ApplicationException;  //获取查询子机构列表SQL
	public String AddSysgroup(String msgdata)throws ApplicationException; //增加系统机构
	public String ModSysgroup(String msgdata)throws ApplicationException; //修改系统机构
	public String DelSysgroup(String msgdata)throws ApplicationException; //删除系统机构
	public String GetSql_SearchStateList(String msgdata)throws ApplicationException;  //获取省列表
	public String GetSql_SearchCityList(String msgdata)throws ApplicationException;   //根据省代码获取市级列表
	public String GetSql_SearchAreaList(String msgdata)throws ApplicationException;   //根据市代码获取区县列表
	public String GetSql_SearchTownList(String msgdata)throws ApplicationException;   //根据区县代码获取乡镇街道列表
	
	public String GetSql_SearchArea(String msgdata)throws ApplicationException;
	public String AddArea(String msgdata)throws ApplicationException; //增加行政区划
	public String ModArea(String msgdata)throws ApplicationException; //修改行政区划
	public String DelArea(String msgdata)throws ApplicationException; //删除行政区划
	
	public String GetSql_SearchSystemlog(String msgdata)throws ApplicationException; //获取查询系统日志SQL
	public String GetSql_SearchSyserrorlog(String msgdata)throws ApplicationException; //获取查询系统错误日志SQL
	public String GetSql_SearchLoginlog(String msgdata)throws ApplicationException; //获取查询系统登录日志SQL
	
	public String GetSql_SearchSysuser(String msgdata)throws ApplicationException;
	public String GetSql_SearchUserAccount(String msgdata)throws ApplicationException;  //获取用户帐户信息
	public String GetSql_SearchUserImgList(String msgdata)throws ApplicationException;  //获取会员图片列表	
	public String GetSql_SearchUserrole(String msgdata)throws ApplicationException;  //查询会员隶属角色
	public String AddSysuser(String msgdata)throws ApplicationException; //增加系统用户信息
	public String ModSysuser(String msgdata)throws ApplicationException; //修改系统用户信息
	public String ModPassword(String msgdata)throws ApplicationException; //更改用户密码
	public String ResetPassword(String msgdata)throws ApplicationException; //重置用户密码
	public String DelSysuser(String msgdata)throws ApplicationException; //删除系统用户信息
	public String ModUserRole(String msgdata)throws ApplicationException; //修改用户隶属角色	
	public String ModRoleMenu(String msgdata)throws ApplicationException; //修改角色对应的菜单
	public String GetSql_SearchSysroleMenu(String msgdata)throws ApplicationException;  //获取角色对应子系统的菜单权限功能(根据角色编码和子系统名称)  用于角色菜单授权时获取已授权树列表
	public String GetSql_SearchSysuserMenu(String msgdata)throws ApplicationException;  //获取用户对应子系统的菜单功能(根据用户编码和子系统名称)  用于用户登录后动态生成功能菜单
	public String CheckUserLogin(String msgdata)throws ApplicationException;    //验证用户登录，成功返回0，失败返回异常原因
	public String Logoff(String msgdata)throws ApplicationException; //用户退出
	//public String AddSmsVerifyCode(String msgdata)throws ApplicationException;  //发送短信验证码(成功返回0，失败返回异常原因)
	public String CheckRegUser(String msgdata)throws ApplicationException;  //判断用户是否注册
	public String RegUser(String msgdata)throws ApplicationException;  //注册用户
	public String CheckUser(String msgdata)throws ApplicationException;  //审核用户
	
	public String GetSql_SearchCountNoticeMsg(String msgdata)throws ApplicationException;
	public String GetSql_SeeNoticeMsg(String msgdata)throws ApplicationException;
	public String GetSql_SearchPersonCenter(String msgdata)throws ApplicationException;
	
	public String UploadImg(File file)throws ApplicationException;
	public String UploadImgByBase64(String msgdata)throws ApplicationException;	
}
