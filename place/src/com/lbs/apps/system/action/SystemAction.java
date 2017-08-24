package com.lbs.apps.system.action;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.lbs.apps.common.ApplicationException;
import com.lbs.apps.common.CommonDTO;
import com.lbs.apps.common.CommonQueryAction;
import com.lbs.apps.system.dao.SyscodeBPO;
import com.lbs.apps.system.dao.SystemDAO;

public class SystemAction extends CommonQueryAction {
	private String sdate;
	private String edate;
	private File[] myfile;
	private File file; // 单个图片文件

	private String uid; // 用户id，唯一的(int)
	private String password; // 密码,aes加密。
	private String user_type; // 公司/个人(string)
	private String user_id; // 登录使用的，唯一（调用资金接口使用该凭证）
	private String mobile; // 手机号，唯一
	private String salt; // md5二次加密使用的
	private String card_type; // 如果是公司用户是统一社会信用代码证;如果是个人，则是身份证
	private String id; // 如果是公司，是证号；个人是身份证号
	private String digest; // 对前面数据按次序和secret的md5

	private SystemDAO systemDAO;

	public String getSdate() {
		return sdate;
	}

	public void setSdate(String sdate) {
		this.sdate = sdate;
	}

	public String getEdate() {
		return edate;
	}

	public void setEdate(String edate) {
		this.edate = edate;
	}

	public File[] getMyfile() {
		return myfile;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public void setMyfile(File[] myfile) {
		this.myfile = myfile;
	}

	public SystemDAO getSystemDAO() {
		return systemDAO;
	}

	public void setSystemDAO(SystemDAO systemDAO) {
		this.systemDAO = systemDAO;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUser_type() {
		return user_type;
	}

	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getCard_type() {
		return card_type;
	}

	public void setCard_type(String card_type) {
		this.card_type = card_type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDigest() {
		return digest;
	}

	public void setDigest(String digest) {
		this.digest = digest;
	}

	// 查询系统角色
	public String SearchSysrole() throws Exception {
		if (!super.CheckMsgdata()) {
			super.QueryException("数据包签名异常");
			return SUCCESS;
		}
		String ls_msg = systemDAO.CheckSql_Where(msgdata);
		if (ls_msg.equals("0")) { // 验证成功
			String ls_sql = systemDAO.GetSql_SearchSysrole(msgdata);
			super.QueryDataBySql(ls_sql, convertcode); // 查询业务数据
		} else {
			super.QueryException(ls_msg); // 抛出异常提示
		}
		return SUCCESS;
	}

	public String AddSysrole() throws ApplicationException {
		if (!super.CheckMsgdata()) {
			super.QueryException("数据包签名异常");
			return SUCCESS;
		}
		CommonDTO dto = new CommonDTO();
		success = "false";
		msg = systemDAO.AddSysrole(msgdata);
		if (msg.equals("0")) {
			success = "true";
			msg = "保存角色数据成功";
		}
		dto.setSuccess(success);
		dto.setMsg(msg);
		HttpServletResponse response = ServletActionContext.getResponse();
		returnExtJSONToBrowser(response, dto, callback);
		return null;
	}

	public String ModSysrole() throws ApplicationException {
		if (!super.CheckMsgdata()) {
			super.QueryException("数据包签名异常");
			return SUCCESS;
		}
		CommonDTO dto = new CommonDTO();
		success = "false";
		msg = systemDAO.ModSysrole(msgdata);
		if (msg.equals("0")) {
			success = "true";
			msg = "修改角色数据成功";
		}
		dto.setSuccess(success);
		dto.setMsg(msg);
		HttpServletResponse response = ServletActionContext.getResponse();
		returnExtJSONToBrowser(response, dto, callback);
		return null;
	}

	public String DelSysrole() throws ApplicationException {
		if (!super.CheckMsgdata()) {
			super.QueryException("数据包签名异常");
			return SUCCESS;
		}
		CommonDTO dto = new CommonDTO();
		success = "false";
		msg = systemDAO.DelSysrole(msgdata);
		if (msg.equals("0")) {
			success = "true";
			msg = "删除角色数据成功";
		}
		dto.setSuccess(success);
		dto.setMsg(msg);
		HttpServletResponse response = ServletActionContext.getResponse();
		returnExtJSONToBrowser(response, dto, callback);
		return null;
	}

	// 查询系统参数
	public String SearchSyspara() throws Exception {
		if (!super.CheckMsgdata()) {
			super.QueryException("数据包签名异常");
			return SUCCESS;
		}
		String ls_msg = systemDAO.CheckSql_Where(msgdata);
		if (ls_msg.equals("0")) { // 验证成功
			String ls_sql = systemDAO.GetSql_SearchSyspara(msgdata);
			super.QueryDataBySql(ls_sql, convertcode); // 查询业务数据
		} else {
			super.QueryException(ls_msg); // 抛出异常提示
		}
		return SUCCESS;
	}

	public String AddSyspara() throws ApplicationException {
		if (!super.CheckMsgdata()) {
			super.QueryException("数据包签名异常");
			return SUCCESS;
		}
		CommonDTO dto = new CommonDTO();
		success = "false";
		msg = systemDAO.AddSyspara(msgdata);
		if (msg.equals("0")) {
			success = "true";
			msg = "保存参数数据成功";
		}
		dto.setSuccess(success);
		dto.setMsg(msg);
		HttpServletResponse response = ServletActionContext.getResponse();
		returnExtJSONToBrowser(response, dto, callback);
		return null;
	}

	public String ModSyspara() throws ApplicationException {
		if (!super.CheckMsgdata()) {
			super.QueryException("数据包签名异常");
			return SUCCESS;
		}
		CommonDTO dto = new CommonDTO();
		success = "false";
		msg = systemDAO.ModSyspara(msgdata);
		if (msg.equals("0")) {
			success = "true";
			msg = "修改参数数据成功";
		}
		dto.setSuccess(success);
		dto.setMsg(msg);
		HttpServletResponse response = ServletActionContext.getResponse();
		returnExtJSONToBrowser(response, dto, callback);
		return null;
	}

	public String DelSyspara() throws ApplicationException {
		if (!super.CheckMsgdata()) {
			super.QueryException("数据包签名异常");
			return SUCCESS;
		}
		CommonDTO dto = new CommonDTO();
		success = "false";
		msg = systemDAO.DelSyspara(msgdata);
		if (msg.equals("0")) {
			success = "true";
			msg = "删除参数数据成功";
		}
		dto.setSuccess(success);
		dto.setMsg(msg);
		HttpServletResponse response = ServletActionContext.getResponse();
		returnExtJSONToBrowser(response, dto, callback);
		return null;
	}

	// 查询系统代码
	public String SearchSyscode() throws Exception {
		if (!super.CheckMsgdata()) {
			super.QueryException("数据包签名异常");
			return SUCCESS;
		}
		String ls_msg = systemDAO.CheckSql_Where(msgdata);
		if (ls_msg.equals("0")) { // 验证成功
			String ls_sql = systemDAO.GetSql_SearchSyscode(msgdata);
			super.QueryDataBySql(ls_sql, convertcode); // 查询业务数据
		} else {
			super.QueryException(ls_msg); // 抛出异常提示
		}
		return SUCCESS;
	}

	public String AddSyscode() throws ApplicationException {
		if (!super.CheckMsgdata()) {
			super.QueryException("数据包签名异常");
			return SUCCESS;
		}
		CommonDTO dto = new CommonDTO();
		success = "false";
		msg = systemDAO.AddSyscode(msgdata);
		if (msg.equals("0")) {
			success = "true";
			msg = "保存代码数据成功";
		}
		dto.setSuccess(success);
		dto.setMsg(msg);
		HttpServletResponse response = ServletActionContext.getResponse();
		returnExtJSONToBrowser(response, dto, callback);
		return null;
	}

	public String ModSyscode() throws ApplicationException {
		if (!super.CheckMsgdata()) {
			super.QueryException("数据包签名异常");
			return SUCCESS;
		}
		CommonDTO dto = new CommonDTO();
		success = "false";
		msg = systemDAO.ModSyscode(msgdata);
		if (msg.equals("0")) {
			success = "true";
			msg = "修改代码数据成功";
		}
		dto.setSuccess(success);
		dto.setMsg(msg);
		HttpServletResponse response = ServletActionContext.getResponse();
		returnExtJSONToBrowser(response, dto, callback);
		return null;
	}

	public String DelSyscode() throws ApplicationException {
		if (!super.CheckMsgdata()) {
			super.QueryException("数据包签名异常");
			return SUCCESS;
		}
		CommonDTO dto = new CommonDTO();
		success = "false";
		msg = systemDAO.DelSyscode(msgdata);
		if (msg.equals("0")) {
			success = "true";
			msg = "删除代码数据成功";
		}
		dto.setSuccess(success);
		dto.setMsg(msg);
		HttpServletResponse response = ServletActionContext.getResponse();
		returnExtJSONToBrowser(response, dto, callback);
		return null;
	}

	// 查询系统机构
	public String SearchSysgroup() throws Exception {
		if (!super.CheckMsgdata()) {
			super.QueryException("数据包签名异常");
			return SUCCESS;
		}
		String ls_msg = systemDAO.CheckSql_Where(msgdata);
		if (ls_msg.equals("0")) { // 验证成功
			String ls_sql = systemDAO.GetSql_SearchSysgroup(msgdata);
			super.QueryDataBySql(ls_sql, convertcode); // 查询业务数据
		} else {
			super.QueryException(ls_msg); // 抛出异常提示
		}
		return SUCCESS;
	}

	// 查询系统子机构
	public String SearchChildSysgroup() throws Exception {
		if (!super.CheckMsgdata()) {
			super.QueryException("数据包签名异常");
			return SUCCESS;
		}
		String ls_msg = systemDAO.CheckSql_Where(msgdata);
		if (ls_msg.equals("0")) { // 验证成功
			String ls_sql = systemDAO.GetSql_SearchChildSysgroup(msgdata);
			super.QueryDataBySql(ls_sql, convertcode); // 查询业务数据
		} else {
			super.QueryException(ls_msg); // 抛出异常提示
		}
		return SUCCESS;
	}

	public String AddSysgroup() throws ApplicationException {
		if (!super.CheckMsgdata()) {
			super.QueryException("数据包签名异常");
			return SUCCESS;
		}
		CommonDTO dto = new CommonDTO();
		success = "false";
		msg = systemDAO.AddSysgroup(msgdata);
		if (msg.equals("0")) {
			success = "true";
			msg = "保存机构数据成功";
		}
		dto.setSuccess(success);
		dto.setMsg(msg);
		HttpServletResponse response = ServletActionContext.getResponse();
		returnExtJSONToBrowser(response, dto, callback);
		return null;
	}

	public String ModSysgroup() throws ApplicationException {
		if (!super.CheckMsgdata()) {
			super.QueryException("数据包签名异常");
			return SUCCESS;
		}
		CommonDTO dto = new CommonDTO();
		success = "false";
		msg = systemDAO.ModSysgroup(msgdata);
		if (msg.equals("0")) {
			success = "true";
			msg = "修改机构数据成功";
		}
		dto.setSuccess(success);
		dto.setMsg(msg);
		HttpServletResponse response = ServletActionContext.getResponse();
		returnExtJSONToBrowser(response, dto, callback);
		return null;
	}

	public String DelSysgroup() throws ApplicationException {
		if (!super.CheckMsgdata()) {
			super.QueryException("数据包签名异常");
			return SUCCESS;
		}
		CommonDTO dto = new CommonDTO();
		success = "false";
		msg = systemDAO.DelSysgroup(msgdata);
		if (msg.equals("0")) {
			success = "true";
			msg = "删除机构数据成功";
		}
		dto.setSuccess(success);
		dto.setMsg(msg);
		HttpServletResponse response = ServletActionContext.getResponse();
		returnExtJSONToBrowser(response, dto, callback);
		return null;
	}

	// 查询行政区划
	public String SearchArea() throws Exception {
		if (!super.CheckMsgdata()) {
			super.QueryException("数据包签名异常");
			return SUCCESS;
		}
		String ls_msg = systemDAO.CheckSql_Where(msgdata);
		if (ls_msg.equals("0")) { // 验证成功
			String ls_sql = systemDAO.GetSql_SearchArea(msgdata);
			super.QueryDataBySql(ls_sql, convertcode); // 查询业务数据
		} else {
			super.QueryException(ls_msg); // 抛出异常提示
		}
		return SUCCESS;
	}

	// 查询省级列表
	public String SearchStateList() throws Exception {
		if (!super.CheckMsgdata()) {
			super.QueryException("数据包签名异常");
			return SUCCESS;
		}
		String ls_msg = systemDAO.CheckSql_Where(msgdata);
		if (ls_msg.equals("0")) { // 验证成功
			String ls_sql = systemDAO.GetSql_SearchStateList(msgdata);
			super.QueryDataBySqlForAll(ls_sql); // 查询业务数据
		} else {
			super.QueryException(ls_msg); // 抛出异常提示
		}
		return SUCCESS;
	}

	// 根据省代码获取市级列表
	public String SearchCityList() throws Exception {
		if (!super.CheckMsgdata()) {
			super.QueryException("数据包签名异常");
			return SUCCESS;
		}
		String ls_msg = systemDAO.CheckSql_Where(msgdata);
		if (ls_msg.equals("0")) { // 验证成功
			String ls_sql = systemDAO.GetSql_SearchCityList(msgdata);
			super.QueryDataBySqlForAll(ls_sql); // 查询业务数据
		} else {
			super.QueryException(ls_msg); // 抛出异常提示
		}
		return SUCCESS;
	}

	// 根据市代码获取区县列表
	public String SearchAreaList() throws Exception {
		if (!super.CheckMsgdata()) {
			super.QueryException("数据包签名异常");
			return SUCCESS;
		}
		String ls_msg = systemDAO.CheckSql_Where(msgdata);
		if (ls_msg.equals("0")) { // 验证成功
			String ls_sql = systemDAO.GetSql_SearchAreaList(msgdata);
			super.QueryDataBySqlForAll(ls_sql); // 查询业务数据
		} else {
			super.QueryException(ls_msg); // 抛出异常提示
		}
		return SUCCESS;
	}

	// 根据区县代码获取乡镇街道列表
	public String SearchTownList() throws Exception {
		if (!super.CheckMsgdata()) {
			super.QueryException("数据包签名异常");
			return SUCCESS;
		}
		String ls_msg = systemDAO.CheckSql_Where(msgdata);
		if (ls_msg.equals("0")) { // 验证成功
			String ls_sql = systemDAO.GetSql_SearchTownList(msgdata);
			super.QueryDataBySqlForAll(ls_sql); // 查询业务数据
		} else {
			super.QueryException(ls_msg); // 抛出异常提示
		}
		return SUCCESS;
	}

	public String AddArea() throws ApplicationException {
		if (!super.CheckMsgdata()) {
			super.QueryException("数据包签名异常");
			return SUCCESS;
		}
		CommonDTO dto = new CommonDTO();
		success = "false";
		msg = systemDAO.AddArea(msgdata);
		if (msg.equals("0")) {
			success = "true";
			msg = "保存行政区划数据成功";
		}
		dto.setSuccess(success);
		dto.setMsg(msg);
		HttpServletResponse response = ServletActionContext.getResponse();
		returnExtJSONToBrowser(response, dto, callback);
		return null;
	}

	public String ModArea() throws ApplicationException {
		if (!super.CheckMsgdata()) {
			super.QueryException("数据包签名异常");
			return SUCCESS;
		}
		CommonDTO dto = new CommonDTO();
		success = "false";
		msg = systemDAO.ModArea(msgdata);
		if (msg.equals("0")) {
			success = "true";
			msg = "修改行政区划数据成功";
		}
		dto.setSuccess(success);
		dto.setMsg(msg);
		HttpServletResponse response = ServletActionContext.getResponse();
		returnExtJSONToBrowser(response, dto, callback);
		return null;
	}

	public String DelArea() throws ApplicationException {
		if (!super.CheckMsgdata()) {
			super.QueryException("数据包签名异常");
			return SUCCESS;
		}
		CommonDTO dto = new CommonDTO();
		success = "false";
		msg = systemDAO.DelArea(msgdata);
		if (msg.equals("0")) {
			success = "true";
			msg = "删除行政区划数据成功";
		}
		dto.setSuccess(success);
		dto.setMsg(msg);
		HttpServletResponse response = ServletActionContext.getResponse();
		returnExtJSONToBrowser(response, dto, callback);
		return null;
	}

	// 查询系统日志
	public String SearchSystemlog() throws Exception {
		if (!super.CheckMsgdata()) {
			super.QueryException("数据包签名异常");
			return SUCCESS;
		}
		String ls_msg = systemDAO.CheckSql_Where(msgdata);
		if (ls_msg.equals("0")) { // 验证成功
			String ls_sql = systemDAO.GetSql_SearchSystemlog(msgdata);
			super.QueryDataBySql(ls_sql, convertcode); // 查询业务数据
		} else {
			super.QueryException(ls_msg); // 抛出异常提示
		}
		return SUCCESS;
	}

	// 查询异常日志
	public String SearchSyserrorlog() throws Exception {
		if (!super.CheckMsgdata()) {
			super.QueryException("数据包签名异常");
			return SUCCESS;
		}
		String ls_msg = systemDAO.CheckSql_Where(msgdata);
		if (ls_msg.equals("0")) { // 验证成功
			String ls_sql = systemDAO.GetSql_SearchSyserrorlog(msgdata);
			super.QueryDataBySql(ls_sql, convertcode); // 查询业务数据
		} else {
			super.QueryException(ls_msg); // 抛出异常提示
		}
		return SUCCESS;
	}

	// 查询登录日志
	public String SearchLoginlog() throws Exception {
		if (!super.CheckMsgdata()) {
			super.QueryException("数据包签名异常");
			return SUCCESS;
		}
		String ls_msg = systemDAO.CheckSql_Where(msgdata);
		if (ls_msg.equals("0")) { // 验证成功
			String ls_sql = systemDAO.GetSql_SearchLoginlog(msgdata);
			super.QueryDataBySql(ls_sql, convertcode); // 查询业务数据
		} else {
			super.QueryException(ls_msg); // 抛出异常提示
		}
		return SUCCESS;
	}

	// 查询系统用户
	public String SearchSysuser() throws Exception {
		if (!super.CheckMsgdata()) {
			super.QueryException("数据包签名异常");
			return SUCCESS;
		}
		String ls_msg = systemDAO.CheckSql_Where(msgdata);
		if (ls_msg.equals("0")) { // 验证成功
			String ls_sql = systemDAO.GetSql_SearchSysuser(msgdata);
			super.QueryDataBySql(ls_sql, convertcode); // 查询业务数据
		} else {
			super.QueryException(ls_msg); // 抛出异常提示
		}
		return SUCCESS;
	}

	

	// 查询系统用户帐户信息
	public String SearchUserAccount() throws Exception {
		if (!super.CheckMsgdata()) {
			super.QueryException("数据包签名异常");
			return SUCCESS;
		}
		String ls_msg = systemDAO.CheckSql_Where(msgdata);
		if (ls_msg.equals("0")) { // 验证成功
			String ls_sql = systemDAO.GetSql_SearchUserAccount(msgdata);
			super.QueryDataBySql(ls_sql, convertcode); // 查询业务数据
		} else {
			super.QueryException(ls_msg); // 抛出异常提示
		}
		return SUCCESS;
	}

	// 查询系统用户图片列表
	public String SearchUserImgList() throws Exception {
		if (!super.CheckMsgdata()) {
			super.QueryException("数据包签名异常");
			return SUCCESS;
		}
		String ls_msg = systemDAO.CheckSql_Where(msgdata);
		if (ls_msg.equals("0")) { // 验证成功
			String ls_sql = systemDAO.GetSql_SearchUserImgList(msgdata);
			super.QueryDataBySqlForAll(ls_sql); // 查询业务数据
		} else {
			super.QueryException(ls_msg); // 抛出异常提示
		}
		return SUCCESS;
	}

	// 查询系统用户
	public String SearchUserrole() throws Exception {
		if (!super.CheckMsgdata()) {
			super.QueryException("数据包签名异常");
			return SUCCESS;
		}
		String ls_msg = systemDAO.CheckSql_Where(msgdata);
		if (ls_msg.equals("0")) { // 验证成功
			String ls_sql = systemDAO.GetSql_SearchUserrole(msgdata);
			super.QueryDataBySql(ls_sql, "false"); // 查询业务数据
		} else {
			super.QueryException(ls_msg); // 抛出异常提示
		}
		return SUCCESS;
	}

	
	public String AddSysuser() throws ApplicationException {
		if (!super.CheckMsgdata()) {
			super.QueryException("数据包签名异常");
			return SUCCESS;
		}
		CommonDTO dto = new CommonDTO();
		success = "false";
		msg = systemDAO.AddSysuser(msgdata);
		if (msg.equals("0")) {
			success = "true";
			msg = "保存用户数据成功";
		}
		dto.setSuccess(success);
		dto.setMsg(msg);
		HttpServletResponse response = ServletActionContext.getResponse();
		returnExtJSONToBrowser(response, dto, callback);
		return null;
	}

	public String ModSysuser() throws ApplicationException {
		if (!super.CheckMsgdata()) {
			super.QueryException("数据包签名异常");
			return SUCCESS;
		}
		CommonDTO dto = new CommonDTO();
		success = "false";
		msg = systemDAO.ModSysuser(msgdata);
		if (msg.equals("0")) {
			success = "true";
			msg = "修改用户数据成功";
		}
		dto.setSuccess(success);
		dto.setMsg(msg);
		HttpServletResponse response = ServletActionContext.getResponse();
		returnExtJSONToBrowser(response, dto, callback);
		return null;
	}

	public String ModPassword() throws ApplicationException {	
		if (!super.CheckMsgdata()) {
			super.QueryException("数据包签名异常");
			return SUCCESS;
		}
		CommonDTO dto = new CommonDTO();
		success = "false";
		msg = systemDAO.ModPassword(msgdata);
		if (msg.equals("0")) {
			success = "true";
			msg = "修改用户数据成功";
		}
		dto.setSuccess(success);
		dto.setMsg(msg);
		HttpServletResponse response = ServletActionContext.getResponse();
		returnExtJSONToBrowser(response, dto, callback);
		return null;		
	}

	public String ResetPassword() throws ApplicationException {
		if (!super.CheckMsgdata()) {
			super.QueryException("数据包签名异常");
			return SUCCESS;
		}
		CommonDTO dto = new CommonDTO();
		success = "false";
		msg = systemDAO.ResetPassword(msgdata);
		if (msg.equals("0")) {
			success = "true";
			msg = "修改用户数据成功";
		}
		dto.setSuccess(success);
		dto.setMsg(msg);
		HttpServletResponse response = ServletActionContext.getResponse();
		returnExtJSONToBrowser(response, dto, callback);
		return null;		
	}

	public String DelSysuser() throws ApplicationException {
		if (!super.CheckMsgdata()) {
			super.QueryException("数据包签名异常");
			return SUCCESS;
		}
		CommonDTO dto = new CommonDTO();
		success = "false";
		msg = systemDAO.DelSysuser(msgdata);
		if (msg.equals("0")) {
			success = "true";
			msg = "删除用户数据成功";
		}
		dto.setSuccess(success);
		dto.setMsg(msg);
		HttpServletResponse response = ServletActionContext.getResponse();
		returnExtJSONToBrowser(response, dto, callback);
		return null;
	}

	public String ModUserRole() throws ApplicationException {
		if (!super.CheckMsgdata()) {
			super.QueryException("数据包签名异常");
			return SUCCESS;
		}
		CommonDTO dto = new CommonDTO();
		success = "false";
		msg = systemDAO.ModUserRole(msgdata);
		if (msg.equals("0")) {
			success = "true";
			msg = "变更用户隶属角色数据成功";
		}
		dto.setSuccess(success);
		dto.setMsg(msg);
		HttpServletResponse response = ServletActionContext.getResponse();
		returnExtJSONToBrowser(response, dto, callback);
		return null;
	}

	public String ModRoleMenu() throws ApplicationException {
		if (!super.CheckMsgdata()) {
			super.QueryException("数据包签名异常");
			return SUCCESS;
		}
		CommonDTO dto = new CommonDTO();
		success = "false";
		msg = systemDAO.ModRoleMenu(msgdata);
		if (msg.equals("0")) {
			success = "true";
			msg = "保存角色菜单授权成功";
		}
		dto.setSuccess(success);
		dto.setMsg(msg);
		HttpServletResponse response = ServletActionContext.getResponse();
		returnExtJSONToBrowser(response, dto, callback);
		return null;
	}

	// 用户登录
	public String CheckUserLogin() throws Exception {
		if (!super.CheckMsgdata()) {
			super.QueryException("数据包签名异常");
			return SUCCESS;
		}
		String ls_msg = systemDAO.CheckUserLogin(msgdata);
		if (ls_msg.substring(0, 1).equals("0")) { // 验证成功
			String[] array_msg = ls_msg.split("\\|");
			String userid = array_msg[1]; // 获取返回的用户编码
			// String ls_sql=systemDAO.GetSql_SearchSysuser(msgdata);
			String ls_sql = "select a.userid,memberid,companyid,groupid,username,loginid,password,usertype,isvalid,userstatus,telephone,phonearea,phoneno,qq,email,wxopenid,address,convert(varchar,birthday,120) birthday,cardtype,identifyid,sex,userlevel,headimg,memo,totalamount,useramount,freezeamount,integral,qualificationstatus,regusertype from Sysuser a,Account b where a.userid=b.userid and a.userid="
					+ userid;
			super.QueryDataBySql(ls_sql, "false"); // 查询业务数据
		} else {
			super.QueryException(ls_msg); // 抛出异常提示
		}
		return SUCCESS;
	}
	
	
	
	// 用户退出
	public String Logoff() throws Exception {
		if (!super.CheckMsgdata()) {
			super.QueryException("数据包签名异常");
			return SUCCESS;
		}		
		CommonDTO dto = new CommonDTO();
		success = "false";
		msg = systemDAO.Logoff(msgdata);
		if (msg.equals("0")) {
			success = "true";
			msg = "操作成功";
		}
		dto.setSuccess(success);
		dto.setMsg(msg);
		HttpServletResponse response = ServletActionContext.getResponse();
		returnExtJSONToBrowser(response, dto, callback);
		return null;		
	}

	// 获取角色对应子系统的菜单权限功能(根据角色编码和子系统名称) 用于角色菜单授权时获取已授权树列表
	public String SearchSysroleMenu() throws Exception {
		if (!super.CheckMsgdata()) {
			super.QueryException("数据包签名异常");
			return SUCCESS;
		}
		String ls_msg = systemDAO.CheckSql_Where(msgdata);
		if (ls_msg.equals("0")) { // 验证成功
			String ls_sql = systemDAO.GetSql_SearchSysroleMenu(msgdata);
			super.QueryDataBySqlForAll(ls_sql); // 查询业务数据
		} else {
			super.QueryException(ls_msg); // 抛出异常提示
		}
		return SUCCESS;
	}

	// 获取用户对应子系统的菜单功能(根据用户登录名和子系统名称) 用于用户登录后动态生成功能菜单
	public String SearchSysuserMenu() throws Exception {
		if (!super.CheckMsgdata()) {
			super.QueryException("数据包签名异常");
			return SUCCESS;
		}
		String ls_msg = systemDAO.CheckSql_Where(msgdata);
		if (ls_msg.equals("0")) { // 验证成功
			String ls_sql = systemDAO.GetSql_SearchSysuserMenu(msgdata);
			super.QueryDataBySqlForAll(ls_sql); // 查询业务数据
		} else {
			super.QueryException(ls_msg); // 抛出异常提示
		}
		return SUCCESS;
	}

	
	// 判断用户是否可以注册
	public String CheckRegUser() throws ApplicationException {
		if (!super.CheckMsgdata()) {
			super.QueryException("数据包签名异常");
			return SUCCESS;
		}
		CommonDTO dto = new CommonDTO();
		success = "false";
		msg = systemDAO.CheckRegUser(msgdata);
		if (msg.equals("0")) {
			success = "true";
			msg = "操作成功";
		}
		dto.setSuccess(success);
		dto.setMsg(msg);
		HttpServletResponse response = ServletActionContext.getResponse();
		returnExtJSONToBrowser(response, dto, callback);
		return null;
	}

	// 注册新用户
	public String RegUser() throws ApplicationException {
		if (!super.CheckMsgdata()) {
			super.QueryException("数据包签名异常");
			return SUCCESS;
		}
		String ls_msg = systemDAO.RegUser(msgdata);
		if (ls_msg.equals("0")) { // 验证成功
			String ls_sql = systemDAO.GetSql_SearchSysuser(msgdata);
			super.QueryDataBySql(ls_sql, "false"); // 查询业务数据
		} else {
			super.QueryException(ls_msg); // 抛出异常提示
		}
		return SUCCESS;
	}

	// 审核用户
	public String CheckUser() throws ApplicationException {
		if (!super.CheckMsgdata()) {
			super.QueryException("数据包签名异常");
			return SUCCESS;
		}
		CommonDTO dto = new CommonDTO();
		success = "false";
		msg = systemDAO.CheckUser(msgdata);
		if (msg.equals("0")) {
			success = "true";
			msg = "操作成功";
		}
		dto.setSuccess(success);
		dto.setMsg(msg);
		HttpServletResponse response = ServletActionContext.getResponse();
		returnExtJSONToBrowser(response, dto, callback);
		return null;
	}

	
	// 单独上传图片
	public String UploadImg() throws ApplicationException {
		CommonDTO dto = new CommonDTO();
		success = "false";
		msg = systemDAO.UploadImg(file);
		if (msg.substring(0, 1).equals("0")) {
			String[] array_msg = msg.split("\\|");
			String imgurl = array_msg[1];
			String ls_sql = "select '" + imgurl + "' imgurl";
			super.QueryDataBySqlForAll(ls_sql); // 查询业务数据
			return SUCCESS;
		}
		dto.setSuccess(success);
		dto.setMsg(msg);
		HttpServletResponse response = ServletActionContext.getResponse();
		returnExtJSONToBrowser(response, dto, callback);
		return null;
	}

	public String UploadImgByBase64() throws ApplicationException {
		// modify by whd 2016.9.1 考虑到前端采用的是JS调用，上传图片时暂不加密
		// if (!super.CheckMsgdata()){
		// super.QueryException("数据包签名异常");
		// return SUCCESS;
		// }
		CommonDTO dto = new CommonDTO();
		success = "false";
		msg = systemDAO.UploadImgByBase64(msgdata);
		if (msg.substring(0, 1).equals("0")) {
			String[] array_msg = msg.split("\\|");
			String imgurl = array_msg[1];
			String ls_sql = "select '" + imgurl + "' imgurl";
			super.QueryDataBySqlForAll(ls_sql); // 查询业务数据
			return SUCCESS;
		}
		dto.setSuccess(success);
		dto.setMsg(msg);
		HttpServletResponse response = ServletActionContext.getResponse();
		returnExtJSONToBrowser(response, dto, callback);
		return null;
	}

	public String GetSyscodeList() throws ApplicationException, IOException {
		if (!super.CheckMsgdata()) {
			super.QueryException("数据包签名异常");
			return SUCCESS;
		}
		String ls_result = SyscodeBPO.GetSyscodeList(msgdata);
		if (!(callback == null || callback.equals(""))) {
			ls_result = callback + "(" + ls_result + ")";
		}
		super.WriteResult(ls_result);
		return null;
	}
	
	public String SearchCountNoticeMsg() throws Exception {
		if (!super.CheckMsgdata()) {
			super.QueryException("数据包签名异常");
			return SUCCESS;
		}
		String ls_msg = systemDAO.CheckSql_Where(msgdata);
		if (ls_msg.equals("0")) { // 验证成功
			String ls_sql = systemDAO.GetSql_SearchCountNoticeMsg(msgdata);
			super.QueryDataBySqlForAll(ls_sql); // 查询业务数据
		} else {
			super.QueryException(ls_msg); // 抛出异常提示
		}
		return SUCCESS;
	}
	
	public String SeeNoticeMsg() throws Exception {
		if (!super.CheckMsgdata()) {
			super.QueryException("数据包签名异常");
			return SUCCESS;
		}
		String ls_msg = systemDAO.CheckSql_Where(msgdata);
		if (ls_msg.equals("0")) { // 验证成功
			String ls_sql = systemDAO.GetSql_SeeNoticeMsg(msgdata);
			super.QueryDataBySqlForAll(ls_sql); // 查询业务数据
		} else {
			super.QueryException(ls_msg); // 抛出异常提示
		}
		return SUCCESS;
	}
	
	public String SearchPersonCenter() throws Exception {
		if (!super.CheckMsgdata()) {
			super.QueryException("数据包签名异常");
			return SUCCESS;
		}
		String ls_msg = systemDAO.CheckSql_Where(msgdata);
		if (ls_msg.equals("0")) { // 验证成功
			String ls_sql = systemDAO.GetSql_SearchPersonCenter(msgdata);
			super.QueryDataBySqlForAll(ls_sql); // 查询业务数据
		} else {
			super.QueryException(ls_msg); // 抛出异常提示
		}
		return SUCCESS;
	}


	
}
