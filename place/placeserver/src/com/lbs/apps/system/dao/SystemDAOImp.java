package com.lbs.apps.system.dao;

import java.io.File;

import com.lbs.apps.common.ApplicationException;


public class SystemDAOImp implements SystemDAO {

	public String CheckSql_Where(String msgdata) {
		SysroleBPO bpo = new SysroleBPO();
		return bpo.CheckSql_Where(msgdata);
	}

	public String GetSql_SearchSysrole(String msgdata)
			throws ApplicationException {
		SysroleBPO bpo = new SysroleBPO();
		return bpo.GetSql_SearchSysrole(msgdata);
	}

	@Override
	public String AddSysrole(String msgdata) throws ApplicationException {
		SysroleBPO bpo = new SysroleBPO();
		return bpo.AddSysrole(msgdata);
	}

	@Override
	public String ModSysrole(String msgdata) throws ApplicationException {
		SysroleBPO bpo = new SysroleBPO();
		return bpo.ModSysrole(msgdata);
	}

	@Override
	public String DelSysrole(String msgdata) throws ApplicationException {
		SysroleBPO bpo = new SysroleBPO();
		return bpo.DelSysrole(msgdata);
	}

	public String GetSql_SearchSyspara(String msgdata)
			throws ApplicationException {
		SysparaBPO bpo = new SysparaBPO();
		return bpo.GetSql_SearchSyspara(msgdata);
	}

	public String AddSyspara(String msgdata) throws ApplicationException { // 增加系统参数
		SysparaBPO bpo = new SysparaBPO();
		return bpo.AddSyspara(msgdata);
	}

	public String ModSyspara(String msgdata) throws ApplicationException { // 修改系统参数
		SysparaBPO bpo = new SysparaBPO();
		return bpo.ModSyspara(msgdata);
	}

	public String DelSyspara(String msgdata) throws ApplicationException { // 删除系统参数
		SysparaBPO bpo = new SysparaBPO();
		return bpo.DelSyspara(msgdata);
	}

	@Override
	public String GetSql_SearchSyscode(String msgdata)
			throws ApplicationException {
		SyscodeBPO bpo = new SyscodeBPO();
		return bpo.GetSql_SearchSyscode(msgdata);
	}

	@Override
	public String AddSyscode(String msgdata) throws ApplicationException {
		SyscodeBPO bpo = new SyscodeBPO();
		return bpo.AddSyscode(msgdata);
	}

	@Override
	public String ModSyscode(String msgdata) throws ApplicationException {
		SyscodeBPO bpo = new SyscodeBPO();
		return bpo.ModSyscode(msgdata);
	}

	@Override
	public String DelSyscode(String msgdata) throws ApplicationException {
		SyscodeBPO bpo = new SyscodeBPO();
		return bpo.DelSyscode(msgdata);
	}

	@Override
	public String GetSql_SearchSysgroup(String msgdata)
			throws ApplicationException {
		SysgroupBPO bpo = new SysgroupBPO();
		return bpo.GetSql_SearchSysgroup(msgdata);
	}

	@Override
	public String GetSql_SearchChildSysgroup(String msgdata)
			throws ApplicationException {
		SysgroupBPO bpo = new SysgroupBPO();
		return bpo.GetSql_SearchChildSysgroup(msgdata);
	}

	@Override
	public String AddSysgroup(String msgdata) throws ApplicationException {
		SysgroupBPO bpo = new SysgroupBPO();
		return bpo.AddSysgroup(msgdata);
	}

	@Override
	public String ModSysgroup(String msgdata) throws ApplicationException {
		SysgroupBPO bpo = new SysgroupBPO();
		return bpo.ModSysgroup(msgdata);
	}

	@Override
	public String DelSysgroup(String msgdata) throws ApplicationException {
		SysgroupBPO bpo = new SysgroupBPO();
		return bpo.DelSysgroup(msgdata);
	}

	@Override
	public String GetSql_SearchArea(String msgdata) throws ApplicationException {
		AreaBPO bpo = new AreaBPO();
		return bpo.GetSql_SearchArea(msgdata);
	}

	@Override
	public String AddArea(String msgdata) throws ApplicationException {
		AreaBPO bpo = new AreaBPO();
		return bpo.AddArea(msgdata);
	}

	@Override
	public String ModArea(String msgdata) throws ApplicationException {
		AreaBPO bpo = new AreaBPO();
		return bpo.ModArea(msgdata);
	}

	@Override
	public String DelArea(String msgdata) throws ApplicationException {
		AreaBPO bpo = new AreaBPO();
		return bpo.DelArea(msgdata);
	}

	@Override
	public String GetSql_SearchSystemlog(String msgdata)
			throws ApplicationException {
		SystemlogBPO bpo = new SystemlogBPO();
		return bpo.GetSql_SearchSystemlog(msgdata);
	}

	@Override
	public String GetSql_SearchSyserrorlog(String msgdata)
			throws ApplicationException {
		SyserrorlogBPO bpo = new SyserrorlogBPO();
		return bpo.GetSql_SearchSyserrorlog(msgdata);
	}

	@Override
	public String GetSql_SearchLoginlog(String msgdata)
			throws ApplicationException {
		LoginlogBPO bpo = new LoginlogBPO();
		return bpo.GetSql_SearchLoginlog(msgdata);
	}

	@Override
	public String GetSql_SearchSysuser(String msgdata)
			throws ApplicationException {
		SysuserBPO bpo = new SysuserBPO();
		return bpo.GetSql_SearchSysuser(msgdata);
	}

	@Override
	public String GetSql_SearchUserrole(String msgdata)
			throws ApplicationException { // 查询会员隶属角色
		SysuserBPO bpo = new SysuserBPO();
		return bpo.GetSql_SearchUserrole(msgdata);
	}

	@Override
	public String AddSysuser(String msgdata) throws ApplicationException {
		SysuserBPO bpo = new SysuserBPO();
		return bpo.AddSysuser(msgdata);
	}

	@Override
	public String ModSysuser(String msgdata) throws ApplicationException {
		SysuserBPO bpo = new SysuserBPO();
		return bpo.ModSysuser(msgdata);
	}

	@Override
	public String ModPassword(String msgdata) throws ApplicationException { // 更改用户密码
		SysuserBPO bpo = new SysuserBPO();
		return bpo.ModPassword(msgdata);
	}

	@Override
	public String DelSysuser(String msgdata) throws ApplicationException {
		SysuserBPO bpo = new SysuserBPO();
		return bpo.DelSysuser(msgdata);
	}

	@Override
	public String ModUserRole(String msgdata) throws ApplicationException {
		SysuserBPO bpo = new SysuserBPO();
		return bpo.ModUserRole(msgdata);
	}

	@Override
	public String ModRoleMenu(String msgdata) throws ApplicationException {
		SysuserBPO bpo = new SysuserBPO();
		return bpo.ModRoleMenu(msgdata);
	}

	@Override
	public String GetSql_SearchSysroleMenu(String msgdata)
			throws ApplicationException {
		SysuserBPO bpo = new SysuserBPO();
		return bpo.GetSql_SearchSysroleMenu(msgdata);
	}

	@Override
	public String GetSql_SearchSysuserMenu(String msgdata)
			throws ApplicationException {
		SysuserBPO bpo = new SysuserBPO();
		return bpo.GetSql_SearchSysuserMenu(msgdata);
	}

	@Override
	public String CheckUserLogin(String msgdata) throws ApplicationException {
		SysuserBPO bpo = new SysuserBPO();
		return bpo.CheckUserLogin(msgdata);
	}
	
	@Override
	public String Logoff(String msgdata)throws ApplicationException{ //用户退出
		SysuserBPO bpo=new SysuserBPO();
		return bpo.Logoff(msgdata);
	}

	@Override
	public String GetSql_SearchStateList(String msgdata)
			throws ApplicationException {
		AreaBPO bpo = new AreaBPO();
		return bpo.GetSql_SearchStateList(msgdata);
	}

	@Override
	public String GetSql_SearchCityList(String msgdata)
			throws ApplicationException {
		AreaBPO bpo = new AreaBPO();
		return bpo.GetSql_SearchCityList(msgdata);
	}

	@Override
	public String GetSql_SearchAreaList(String msgdata)
			throws ApplicationException {
		AreaBPO bpo = new AreaBPO();
		return bpo.GetSql_SearchAreaList(msgdata);
	}

	@Override
	public String GetSql_SearchTownList(String msgdata)
			throws ApplicationException {
		AreaBPO bpo = new AreaBPO();
		return bpo.GetSql_SearchTownList(msgdata);
	}
	

	@Override
	public String CheckRegUser(String msgdata) throws ApplicationException {
		SysuserBPO bpo = new SysuserBPO();
		return bpo.CheckRegUser(msgdata);
	}

	@Override
	public String RegUser(String msgdata) throws ApplicationException {
		SysuserBPO bpo = new SysuserBPO();
		return bpo.RegUser(msgdata);
	}

	@Override
	public String CheckUser(String msgdata) throws ApplicationException {
		SysuserBPO bpo = new SysuserBPO();
		return bpo.CheckUser(msgdata);
	}

	
	@Override
	public String GetSql_SearchUserImgList(String msgdata)
			throws ApplicationException {
		SysuserBPO bpo = new SysuserBPO();
		return bpo.GetSql_SearchUserImgList(msgdata);
	}

	
	@Override
	public String UploadImg(File file) throws ApplicationException {
		SysuserBPO bpo = new SysuserBPO();
		return bpo.UploadImg(file);
	}

	@Override
	public String UploadImgByBase64(String msgdata) throws ApplicationException {
		SysuserBPO bpo = new SysuserBPO();
		return bpo.UploadImgByBase64(msgdata);
	}

	@Override
	public String GetSql_SearchUserAccount(String msgdata)
			throws ApplicationException {
		SysuserBPO bpo = new SysuserBPO();
		return bpo.GetSql_SearchUserAccount(msgdata);
	}

	@Override
	public String ResetPassword(String msgdata) throws ApplicationException {
		SysuserBPO bpo = new SysuserBPO();
		return bpo.ResetPassword(msgdata);
	}

	@Override
	public String GetSql_SearchCountNoticeMsg(String msgdata)
			throws ApplicationException {
		NoticemsgBPO bpo =new NoticemsgBPO();
		return bpo.GetSql_SearchCountNoticeMsg(msgdata);
	}

	@Override
	public String GetSql_SeeNoticeMsg(String msgdata)
			throws ApplicationException {
		NoticemsgBPO bpo =new NoticemsgBPO();
		return bpo.GetSql_SeeNoticeMsg(msgdata);
	}

	@Override
	public String GetSql_SearchPersonCenter(String msgdata)
			throws ApplicationException {
		SysuserBPO bpo = new SysuserBPO();
		return bpo.GetSql_SearchPersonCenter(msgdata);
	}

	

}
