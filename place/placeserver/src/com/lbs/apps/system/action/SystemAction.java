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
	private File file; // ����ͼƬ�ļ�

	private String uid; // �û�id��Ψһ��(int)
	private String password; // ����,aes���ܡ�
	private String user_type; // ��˾/����(string)
	private String user_id; // ��¼ʹ�õģ�Ψһ�������ʽ�ӿ�ʹ�ø�ƾ֤��
	private String mobile; // �ֻ��ţ�Ψһ
	private String salt; // md5���μ���ʹ�õ�
	private String card_type; // ����ǹ�˾�û���ͳһ������ô���֤;����Ǹ��ˣ��������֤
	private String id; // ����ǹ�˾����֤�ţ����������֤��
	private String digest; // ��ǰ�����ݰ������secret��md5

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

	// ��ѯϵͳ��ɫ
	public String SearchSysrole() throws Exception {
		if (!super.CheckMsgdata()) {
			super.QueryException("���ݰ�ǩ���쳣");
			return SUCCESS;
		}
		String ls_msg = systemDAO.CheckSql_Where(msgdata);
		if (ls_msg.equals("0")) { // ��֤�ɹ�
			String ls_sql = systemDAO.GetSql_SearchSysrole(msgdata);
			super.QueryDataBySql(ls_sql, convertcode); // ��ѯҵ������
		} else {
			super.QueryException(ls_msg); // �׳��쳣��ʾ
		}
		return SUCCESS;
	}

	public String AddSysrole() throws ApplicationException {
		if (!super.CheckMsgdata()) {
			super.QueryException("���ݰ�ǩ���쳣");
			return SUCCESS;
		}
		CommonDTO dto = new CommonDTO();
		success = "false";
		msg = systemDAO.AddSysrole(msgdata);
		if (msg.equals("0")) {
			success = "true";
			msg = "�����ɫ���ݳɹ�";
		}
		dto.setSuccess(success);
		dto.setMsg(msg);
		HttpServletResponse response = ServletActionContext.getResponse();
		returnExtJSONToBrowser(response, dto, callback);
		return null;
	}

	public String ModSysrole() throws ApplicationException {
		if (!super.CheckMsgdata()) {
			super.QueryException("���ݰ�ǩ���쳣");
			return SUCCESS;
		}
		CommonDTO dto = new CommonDTO();
		success = "false";
		msg = systemDAO.ModSysrole(msgdata);
		if (msg.equals("0")) {
			success = "true";
			msg = "�޸Ľ�ɫ���ݳɹ�";
		}
		dto.setSuccess(success);
		dto.setMsg(msg);
		HttpServletResponse response = ServletActionContext.getResponse();
		returnExtJSONToBrowser(response, dto, callback);
		return null;
	}

	public String DelSysrole() throws ApplicationException {
		if (!super.CheckMsgdata()) {
			super.QueryException("���ݰ�ǩ���쳣");
			return SUCCESS;
		}
		CommonDTO dto = new CommonDTO();
		success = "false";
		msg = systemDAO.DelSysrole(msgdata);
		if (msg.equals("0")) {
			success = "true";
			msg = "ɾ����ɫ���ݳɹ�";
		}
		dto.setSuccess(success);
		dto.setMsg(msg);
		HttpServletResponse response = ServletActionContext.getResponse();
		returnExtJSONToBrowser(response, dto, callback);
		return null;
	}

	// ��ѯϵͳ����
	public String SearchSyspara() throws Exception {
		if (!super.CheckMsgdata()) {
			super.QueryException("���ݰ�ǩ���쳣");
			return SUCCESS;
		}
		String ls_msg = systemDAO.CheckSql_Where(msgdata);
		if (ls_msg.equals("0")) { // ��֤�ɹ�
			String ls_sql = systemDAO.GetSql_SearchSyspara(msgdata);
			super.QueryDataBySql(ls_sql, convertcode); // ��ѯҵ������
		} else {
			super.QueryException(ls_msg); // �׳��쳣��ʾ
		}
		return SUCCESS;
	}

	public String AddSyspara() throws ApplicationException {
		if (!super.CheckMsgdata()) {
			super.QueryException("���ݰ�ǩ���쳣");
			return SUCCESS;
		}
		CommonDTO dto = new CommonDTO();
		success = "false";
		msg = systemDAO.AddSyspara(msgdata);
		if (msg.equals("0")) {
			success = "true";
			msg = "����������ݳɹ�";
		}
		dto.setSuccess(success);
		dto.setMsg(msg);
		HttpServletResponse response = ServletActionContext.getResponse();
		returnExtJSONToBrowser(response, dto, callback);
		return null;
	}

	public String ModSyspara() throws ApplicationException {
		if (!super.CheckMsgdata()) {
			super.QueryException("���ݰ�ǩ���쳣");
			return SUCCESS;
		}
		CommonDTO dto = new CommonDTO();
		success = "false";
		msg = systemDAO.ModSyspara(msgdata);
		if (msg.equals("0")) {
			success = "true";
			msg = "�޸Ĳ������ݳɹ�";
		}
		dto.setSuccess(success);
		dto.setMsg(msg);
		HttpServletResponse response = ServletActionContext.getResponse();
		returnExtJSONToBrowser(response, dto, callback);
		return null;
	}

	public String DelSyspara() throws ApplicationException {
		if (!super.CheckMsgdata()) {
			super.QueryException("���ݰ�ǩ���쳣");
			return SUCCESS;
		}
		CommonDTO dto = new CommonDTO();
		success = "false";
		msg = systemDAO.DelSyspara(msgdata);
		if (msg.equals("0")) {
			success = "true";
			msg = "ɾ���������ݳɹ�";
		}
		dto.setSuccess(success);
		dto.setMsg(msg);
		HttpServletResponse response = ServletActionContext.getResponse();
		returnExtJSONToBrowser(response, dto, callback);
		return null;
	}

	// ��ѯϵͳ����
	public String SearchSyscode() throws Exception {
		if (!super.CheckMsgdata()) {
			super.QueryException("���ݰ�ǩ���쳣");
			return SUCCESS;
		}
		String ls_msg = systemDAO.CheckSql_Where(msgdata);
		if (ls_msg.equals("0")) { // ��֤�ɹ�
			String ls_sql = systemDAO.GetSql_SearchSyscode(msgdata);
			super.QueryDataBySql(ls_sql, convertcode); // ��ѯҵ������
		} else {
			super.QueryException(ls_msg); // �׳��쳣��ʾ
		}
		return SUCCESS;
	}

	public String AddSyscode() throws ApplicationException {
		if (!super.CheckMsgdata()) {
			super.QueryException("���ݰ�ǩ���쳣");
			return SUCCESS;
		}
		CommonDTO dto = new CommonDTO();
		success = "false";
		msg = systemDAO.AddSyscode(msgdata);
		if (msg.equals("0")) {
			success = "true";
			msg = "����������ݳɹ�";
		}
		dto.setSuccess(success);
		dto.setMsg(msg);
		HttpServletResponse response = ServletActionContext.getResponse();
		returnExtJSONToBrowser(response, dto, callback);
		return null;
	}

	public String ModSyscode() throws ApplicationException {
		if (!super.CheckMsgdata()) {
			super.QueryException("���ݰ�ǩ���쳣");
			return SUCCESS;
		}
		CommonDTO dto = new CommonDTO();
		success = "false";
		msg = systemDAO.ModSyscode(msgdata);
		if (msg.equals("0")) {
			success = "true";
			msg = "�޸Ĵ������ݳɹ�";
		}
		dto.setSuccess(success);
		dto.setMsg(msg);
		HttpServletResponse response = ServletActionContext.getResponse();
		returnExtJSONToBrowser(response, dto, callback);
		return null;
	}

	public String DelSyscode() throws ApplicationException {
		if (!super.CheckMsgdata()) {
			super.QueryException("���ݰ�ǩ���쳣");
			return SUCCESS;
		}
		CommonDTO dto = new CommonDTO();
		success = "false";
		msg = systemDAO.DelSyscode(msgdata);
		if (msg.equals("0")) {
			success = "true";
			msg = "ɾ���������ݳɹ�";
		}
		dto.setSuccess(success);
		dto.setMsg(msg);
		HttpServletResponse response = ServletActionContext.getResponse();
		returnExtJSONToBrowser(response, dto, callback);
		return null;
	}

	// ��ѯϵͳ����
	public String SearchSysgroup() throws Exception {
		if (!super.CheckMsgdata()) {
			super.QueryException("���ݰ�ǩ���쳣");
			return SUCCESS;
		}
		String ls_msg = systemDAO.CheckSql_Where(msgdata);
		if (ls_msg.equals("0")) { // ��֤�ɹ�
			String ls_sql = systemDAO.GetSql_SearchSysgroup(msgdata);
			super.QueryDataBySql(ls_sql, convertcode); // ��ѯҵ������
		} else {
			super.QueryException(ls_msg); // �׳��쳣��ʾ
		}
		return SUCCESS;
	}

	// ��ѯϵͳ�ӻ���
	public String SearchChildSysgroup() throws Exception {
		if (!super.CheckMsgdata()) {
			super.QueryException("���ݰ�ǩ���쳣");
			return SUCCESS;
		}
		String ls_msg = systemDAO.CheckSql_Where(msgdata);
		if (ls_msg.equals("0")) { // ��֤�ɹ�
			String ls_sql = systemDAO.GetSql_SearchChildSysgroup(msgdata);
			super.QueryDataBySql(ls_sql, convertcode); // ��ѯҵ������
		} else {
			super.QueryException(ls_msg); // �׳��쳣��ʾ
		}
		return SUCCESS;
	}

	public String AddSysgroup() throws ApplicationException {
		if (!super.CheckMsgdata()) {
			super.QueryException("���ݰ�ǩ���쳣");
			return SUCCESS;
		}
		CommonDTO dto = new CommonDTO();
		success = "false";
		msg = systemDAO.AddSysgroup(msgdata);
		if (msg.equals("0")) {
			success = "true";
			msg = "����������ݳɹ�";
		}
		dto.setSuccess(success);
		dto.setMsg(msg);
		HttpServletResponse response = ServletActionContext.getResponse();
		returnExtJSONToBrowser(response, dto, callback);
		return null;
	}

	public String ModSysgroup() throws ApplicationException {
		if (!super.CheckMsgdata()) {
			super.QueryException("���ݰ�ǩ���쳣");
			return SUCCESS;
		}
		CommonDTO dto = new CommonDTO();
		success = "false";
		msg = systemDAO.ModSysgroup(msgdata);
		if (msg.equals("0")) {
			success = "true";
			msg = "�޸Ļ������ݳɹ�";
		}
		dto.setSuccess(success);
		dto.setMsg(msg);
		HttpServletResponse response = ServletActionContext.getResponse();
		returnExtJSONToBrowser(response, dto, callback);
		return null;
	}

	public String DelSysgroup() throws ApplicationException {
		if (!super.CheckMsgdata()) {
			super.QueryException("���ݰ�ǩ���쳣");
			return SUCCESS;
		}
		CommonDTO dto = new CommonDTO();
		success = "false";
		msg = systemDAO.DelSysgroup(msgdata);
		if (msg.equals("0")) {
			success = "true";
			msg = "ɾ���������ݳɹ�";
		}
		dto.setSuccess(success);
		dto.setMsg(msg);
		HttpServletResponse response = ServletActionContext.getResponse();
		returnExtJSONToBrowser(response, dto, callback);
		return null;
	}

	// ��ѯ��������
	public String SearchArea() throws Exception {
		if (!super.CheckMsgdata()) {
			super.QueryException("���ݰ�ǩ���쳣");
			return SUCCESS;
		}
		String ls_msg = systemDAO.CheckSql_Where(msgdata);
		if (ls_msg.equals("0")) { // ��֤�ɹ�
			String ls_sql = systemDAO.GetSql_SearchArea(msgdata);
			super.QueryDataBySql(ls_sql, convertcode); // ��ѯҵ������
		} else {
			super.QueryException(ls_msg); // �׳��쳣��ʾ
		}
		return SUCCESS;
	}

	// ��ѯʡ���б�
	public String SearchStateList() throws Exception {
		if (!super.CheckMsgdata()) {
			super.QueryException("���ݰ�ǩ���쳣");
			return SUCCESS;
		}
		String ls_msg = systemDAO.CheckSql_Where(msgdata);
		if (ls_msg.equals("0")) { // ��֤�ɹ�
			String ls_sql = systemDAO.GetSql_SearchStateList(msgdata);
			super.QueryDataBySqlForAll(ls_sql); // ��ѯҵ������
		} else {
			super.QueryException(ls_msg); // �׳��쳣��ʾ
		}
		return SUCCESS;
	}

	// ����ʡ�����ȡ�м��б�
	public String SearchCityList() throws Exception {
		if (!super.CheckMsgdata()) {
			super.QueryException("���ݰ�ǩ���쳣");
			return SUCCESS;
		}
		String ls_msg = systemDAO.CheckSql_Where(msgdata);
		if (ls_msg.equals("0")) { // ��֤�ɹ�
			String ls_sql = systemDAO.GetSql_SearchCityList(msgdata);
			super.QueryDataBySqlForAll(ls_sql); // ��ѯҵ������
		} else {
			super.QueryException(ls_msg); // �׳��쳣��ʾ
		}
		return SUCCESS;
	}

	// �����д����ȡ�����б�
	public String SearchAreaList() throws Exception {
		if (!super.CheckMsgdata()) {
			super.QueryException("���ݰ�ǩ���쳣");
			return SUCCESS;
		}
		String ls_msg = systemDAO.CheckSql_Where(msgdata);
		if (ls_msg.equals("0")) { // ��֤�ɹ�
			String ls_sql = systemDAO.GetSql_SearchAreaList(msgdata);
			super.QueryDataBySqlForAll(ls_sql); // ��ѯҵ������
		} else {
			super.QueryException(ls_msg); // �׳��쳣��ʾ
		}
		return SUCCESS;
	}

	// �������ش����ȡ����ֵ��б�
	public String SearchTownList() throws Exception {
		if (!super.CheckMsgdata()) {
			super.QueryException("���ݰ�ǩ���쳣");
			return SUCCESS;
		}
		String ls_msg = systemDAO.CheckSql_Where(msgdata);
		if (ls_msg.equals("0")) { // ��֤�ɹ�
			String ls_sql = systemDAO.GetSql_SearchTownList(msgdata);
			super.QueryDataBySqlForAll(ls_sql); // ��ѯҵ������
		} else {
			super.QueryException(ls_msg); // �׳��쳣��ʾ
		}
		return SUCCESS;
	}

	public String AddArea() throws ApplicationException {
		if (!super.CheckMsgdata()) {
			super.QueryException("���ݰ�ǩ���쳣");
			return SUCCESS;
		}
		CommonDTO dto = new CommonDTO();
		success = "false";
		msg = systemDAO.AddArea(msgdata);
		if (msg.equals("0")) {
			success = "true";
			msg = "���������������ݳɹ�";
		}
		dto.setSuccess(success);
		dto.setMsg(msg);
		HttpServletResponse response = ServletActionContext.getResponse();
		returnExtJSONToBrowser(response, dto, callback);
		return null;
	}

	public String ModArea() throws ApplicationException {
		if (!super.CheckMsgdata()) {
			super.QueryException("���ݰ�ǩ���쳣");
			return SUCCESS;
		}
		CommonDTO dto = new CommonDTO();
		success = "false";
		msg = systemDAO.ModArea(msgdata);
		if (msg.equals("0")) {
			success = "true";
			msg = "�޸������������ݳɹ�";
		}
		dto.setSuccess(success);
		dto.setMsg(msg);
		HttpServletResponse response = ServletActionContext.getResponse();
		returnExtJSONToBrowser(response, dto, callback);
		return null;
	}

	public String DelArea() throws ApplicationException {
		if (!super.CheckMsgdata()) {
			super.QueryException("���ݰ�ǩ���쳣");
			return SUCCESS;
		}
		CommonDTO dto = new CommonDTO();
		success = "false";
		msg = systemDAO.DelArea(msgdata);
		if (msg.equals("0")) {
			success = "true";
			msg = "ɾ�������������ݳɹ�";
		}
		dto.setSuccess(success);
		dto.setMsg(msg);
		HttpServletResponse response = ServletActionContext.getResponse();
		returnExtJSONToBrowser(response, dto, callback);
		return null;
	}

	// ��ѯϵͳ��־
	public String SearchSystemlog() throws Exception {
		if (!super.CheckMsgdata()) {
			super.QueryException("���ݰ�ǩ���쳣");
			return SUCCESS;
		}
		String ls_msg = systemDAO.CheckSql_Where(msgdata);
		if (ls_msg.equals("0")) { // ��֤�ɹ�
			String ls_sql = systemDAO.GetSql_SearchSystemlog(msgdata);
			super.QueryDataBySql(ls_sql, convertcode); // ��ѯҵ������
		} else {
			super.QueryException(ls_msg); // �׳��쳣��ʾ
		}
		return SUCCESS;
	}

	// ��ѯ�쳣��־
	public String SearchSyserrorlog() throws Exception {
		if (!super.CheckMsgdata()) {
			super.QueryException("���ݰ�ǩ���쳣");
			return SUCCESS;
		}
		String ls_msg = systemDAO.CheckSql_Where(msgdata);
		if (ls_msg.equals("0")) { // ��֤�ɹ�
			String ls_sql = systemDAO.GetSql_SearchSyserrorlog(msgdata);
			super.QueryDataBySql(ls_sql, convertcode); // ��ѯҵ������
		} else {
			super.QueryException(ls_msg); // �׳��쳣��ʾ
		}
		return SUCCESS;
	}

	// ��ѯ��¼��־
	public String SearchLoginlog() throws Exception {
		if (!super.CheckMsgdata()) {
			super.QueryException("���ݰ�ǩ���쳣");
			return SUCCESS;
		}
		String ls_msg = systemDAO.CheckSql_Where(msgdata);
		if (ls_msg.equals("0")) { // ��֤�ɹ�
			String ls_sql = systemDAO.GetSql_SearchLoginlog(msgdata);
			super.QueryDataBySql(ls_sql, convertcode); // ��ѯҵ������
		} else {
			super.QueryException(ls_msg); // �׳��쳣��ʾ
		}
		return SUCCESS;
	}

	// ��ѯϵͳ�û�
	public String SearchSysuser() throws Exception {
		if (!super.CheckMsgdata()) {
			super.QueryException("���ݰ�ǩ���쳣");
			return SUCCESS;
		}
		String ls_msg = systemDAO.CheckSql_Where(msgdata);
		if (ls_msg.equals("0")) { // ��֤�ɹ�
			String ls_sql = systemDAO.GetSql_SearchSysuser(msgdata);
			super.QueryDataBySql(ls_sql, convertcode); // ��ѯҵ������
		} else {
			super.QueryException(ls_msg); // �׳��쳣��ʾ
		}
		return SUCCESS;
	}

	

	// ��ѯϵͳ�û��ʻ���Ϣ
	public String SearchUserAccount() throws Exception {
		if (!super.CheckMsgdata()) {
			super.QueryException("���ݰ�ǩ���쳣");
			return SUCCESS;
		}
		String ls_msg = systemDAO.CheckSql_Where(msgdata);
		if (ls_msg.equals("0")) { // ��֤�ɹ�
			String ls_sql = systemDAO.GetSql_SearchUserAccount(msgdata);
			super.QueryDataBySql(ls_sql, convertcode); // ��ѯҵ������
		} else {
			super.QueryException(ls_msg); // �׳��쳣��ʾ
		}
		return SUCCESS;
	}

	// ��ѯϵͳ�û�ͼƬ�б�
	public String SearchUserImgList() throws Exception {
		if (!super.CheckMsgdata()) {
			super.QueryException("���ݰ�ǩ���쳣");
			return SUCCESS;
		}
		String ls_msg = systemDAO.CheckSql_Where(msgdata);
		if (ls_msg.equals("0")) { // ��֤�ɹ�
			String ls_sql = systemDAO.GetSql_SearchUserImgList(msgdata);
			super.QueryDataBySqlForAll(ls_sql); // ��ѯҵ������
		} else {
			super.QueryException(ls_msg); // �׳��쳣��ʾ
		}
		return SUCCESS;
	}

	// ��ѯϵͳ�û�
	public String SearchUserrole() throws Exception {
		if (!super.CheckMsgdata()) {
			super.QueryException("���ݰ�ǩ���쳣");
			return SUCCESS;
		}
		String ls_msg = systemDAO.CheckSql_Where(msgdata);
		if (ls_msg.equals("0")) { // ��֤�ɹ�
			String ls_sql = systemDAO.GetSql_SearchUserrole(msgdata);
			super.QueryDataBySql(ls_sql, "false"); // ��ѯҵ������
		} else {
			super.QueryException(ls_msg); // �׳��쳣��ʾ
		}
		return SUCCESS;
	}

	
	public String AddSysuser() throws ApplicationException {
		if (!super.CheckMsgdata()) {
			super.QueryException("���ݰ�ǩ���쳣");
			return SUCCESS;
		}
		CommonDTO dto = new CommonDTO();
		success = "false";
		msg = systemDAO.AddSysuser(msgdata);
		if (msg.equals("0")) {
			success = "true";
			msg = "�����û����ݳɹ�";
		}
		dto.setSuccess(success);
		dto.setMsg(msg);
		HttpServletResponse response = ServletActionContext.getResponse();
		returnExtJSONToBrowser(response, dto, callback);
		return null;
	}

	public String ModSysuser() throws ApplicationException {
		if (!super.CheckMsgdata()) {
			super.QueryException("���ݰ�ǩ���쳣");
			return SUCCESS;
		}
		CommonDTO dto = new CommonDTO();
		success = "false";
		msg = systemDAO.ModSysuser(msgdata);
		if (msg.equals("0")) {
			success = "true";
			msg = "�޸��û����ݳɹ�";
		}
		dto.setSuccess(success);
		dto.setMsg(msg);
		HttpServletResponse response = ServletActionContext.getResponse();
		returnExtJSONToBrowser(response, dto, callback);
		return null;
	}

	public String ModPassword() throws ApplicationException {	
		if (!super.CheckMsgdata()) {
			super.QueryException("���ݰ�ǩ���쳣");
			return SUCCESS;
		}
		CommonDTO dto = new CommonDTO();
		success = "false";
		msg = systemDAO.ModPassword(msgdata);
		if (msg.equals("0")) {
			success = "true";
			msg = "�޸��û����ݳɹ�";
		}
		dto.setSuccess(success);
		dto.setMsg(msg);
		HttpServletResponse response = ServletActionContext.getResponse();
		returnExtJSONToBrowser(response, dto, callback);
		return null;		
	}

	public String ResetPassword() throws ApplicationException {
		if (!super.CheckMsgdata()) {
			super.QueryException("���ݰ�ǩ���쳣");
			return SUCCESS;
		}
		CommonDTO dto = new CommonDTO();
		success = "false";
		msg = systemDAO.ResetPassword(msgdata);
		if (msg.equals("0")) {
			success = "true";
			msg = "�޸��û����ݳɹ�";
		}
		dto.setSuccess(success);
		dto.setMsg(msg);
		HttpServletResponse response = ServletActionContext.getResponse();
		returnExtJSONToBrowser(response, dto, callback);
		return null;		
	}

	public String DelSysuser() throws ApplicationException {
		if (!super.CheckMsgdata()) {
			super.QueryException("���ݰ�ǩ���쳣");
			return SUCCESS;
		}
		CommonDTO dto = new CommonDTO();
		success = "false";
		msg = systemDAO.DelSysuser(msgdata);
		if (msg.equals("0")) {
			success = "true";
			msg = "ɾ���û����ݳɹ�";
		}
		dto.setSuccess(success);
		dto.setMsg(msg);
		HttpServletResponse response = ServletActionContext.getResponse();
		returnExtJSONToBrowser(response, dto, callback);
		return null;
	}

	public String ModUserRole() throws ApplicationException {
		if (!super.CheckMsgdata()) {
			super.QueryException("���ݰ�ǩ���쳣");
			return SUCCESS;
		}
		CommonDTO dto = new CommonDTO();
		success = "false";
		msg = systemDAO.ModUserRole(msgdata);
		if (msg.equals("0")) {
			success = "true";
			msg = "����û�������ɫ���ݳɹ�";
		}
		dto.setSuccess(success);
		dto.setMsg(msg);
		HttpServletResponse response = ServletActionContext.getResponse();
		returnExtJSONToBrowser(response, dto, callback);
		return null;
	}

	public String ModRoleMenu() throws ApplicationException {
		if (!super.CheckMsgdata()) {
			super.QueryException("���ݰ�ǩ���쳣");
			return SUCCESS;
		}
		CommonDTO dto = new CommonDTO();
		success = "false";
		msg = systemDAO.ModRoleMenu(msgdata);
		if (msg.equals("0")) {
			success = "true";
			msg = "�����ɫ�˵���Ȩ�ɹ�";
		}
		dto.setSuccess(success);
		dto.setMsg(msg);
		HttpServletResponse response = ServletActionContext.getResponse();
		returnExtJSONToBrowser(response, dto, callback);
		return null;
	}

	// �û���¼
	public String CheckUserLogin() throws Exception {
		if (!super.CheckMsgdata()) {
			super.QueryException("���ݰ�ǩ���쳣");
			return SUCCESS;
		}
		String ls_msg = systemDAO.CheckUserLogin(msgdata);
		if (ls_msg.substring(0, 1).equals("0")) { // ��֤�ɹ�
			String[] array_msg = ls_msg.split("\\|");
			String userid = array_msg[1]; // ��ȡ���ص��û�����
			// String ls_sql=systemDAO.GetSql_SearchSysuser(msgdata);
			String ls_sql = "select a.userid,memberid,companyid,groupid,username,loginid,password,usertype,isvalid,userstatus,telephone,phonearea,phoneno,qq,email,wxopenid,address,convert(varchar,birthday,120) birthday,cardtype,identifyid,sex,userlevel,headimg,memo,totalamount,useramount,freezeamount,integral,qualificationstatus,regusertype from Sysuser a,Account b where a.userid=b.userid and a.userid="
					+ userid;
			super.QueryDataBySql(ls_sql, "false"); // ��ѯҵ������
		} else {
			super.QueryException(ls_msg); // �׳��쳣��ʾ
		}
		return SUCCESS;
	}
	
	
	
	// �û��˳�
	public String Logoff() throws Exception {
		if (!super.CheckMsgdata()) {
			super.QueryException("���ݰ�ǩ���쳣");
			return SUCCESS;
		}		
		CommonDTO dto = new CommonDTO();
		success = "false";
		msg = systemDAO.Logoff(msgdata);
		if (msg.equals("0")) {
			success = "true";
			msg = "�����ɹ�";
		}
		dto.setSuccess(success);
		dto.setMsg(msg);
		HttpServletResponse response = ServletActionContext.getResponse();
		returnExtJSONToBrowser(response, dto, callback);
		return null;		
	}

	// ��ȡ��ɫ��Ӧ��ϵͳ�Ĳ˵�Ȩ�޹���(���ݽ�ɫ�������ϵͳ����) ���ڽ�ɫ�˵���Ȩʱ��ȡ����Ȩ���б�
	public String SearchSysroleMenu() throws Exception {
		if (!super.CheckMsgdata()) {
			super.QueryException("���ݰ�ǩ���쳣");
			return SUCCESS;
		}
		String ls_msg = systemDAO.CheckSql_Where(msgdata);
		if (ls_msg.equals("0")) { // ��֤�ɹ�
			String ls_sql = systemDAO.GetSql_SearchSysroleMenu(msgdata);
			super.QueryDataBySqlForAll(ls_sql); // ��ѯҵ������
		} else {
			super.QueryException(ls_msg); // �׳��쳣��ʾ
		}
		return SUCCESS;
	}

	// ��ȡ�û���Ӧ��ϵͳ�Ĳ˵�����(�����û���¼������ϵͳ����) �����û���¼��̬���ɹ��ܲ˵�
	public String SearchSysuserMenu() throws Exception {
		if (!super.CheckMsgdata()) {
			super.QueryException("���ݰ�ǩ���쳣");
			return SUCCESS;
		}
		String ls_msg = systemDAO.CheckSql_Where(msgdata);
		if (ls_msg.equals("0")) { // ��֤�ɹ�
			String ls_sql = systemDAO.GetSql_SearchSysuserMenu(msgdata);
			super.QueryDataBySqlForAll(ls_sql); // ��ѯҵ������
		} else {
			super.QueryException(ls_msg); // �׳��쳣��ʾ
		}
		return SUCCESS;
	}

	
	// �ж��û��Ƿ����ע��
	public String CheckRegUser() throws ApplicationException {
		if (!super.CheckMsgdata()) {
			super.QueryException("���ݰ�ǩ���쳣");
			return SUCCESS;
		}
		CommonDTO dto = new CommonDTO();
		success = "false";
		msg = systemDAO.CheckRegUser(msgdata);
		if (msg.equals("0")) {
			success = "true";
			msg = "�����ɹ�";
		}
		dto.setSuccess(success);
		dto.setMsg(msg);
		HttpServletResponse response = ServletActionContext.getResponse();
		returnExtJSONToBrowser(response, dto, callback);
		return null;
	}

	// ע�����û�
	public String RegUser() throws ApplicationException {
		if (!super.CheckMsgdata()) {
			super.QueryException("���ݰ�ǩ���쳣");
			return SUCCESS;
		}
		String ls_msg = systemDAO.RegUser(msgdata);
		if (ls_msg.equals("0")) { // ��֤�ɹ�
			String ls_sql = systemDAO.GetSql_SearchSysuser(msgdata);
			super.QueryDataBySql(ls_sql, "false"); // ��ѯҵ������
		} else {
			super.QueryException(ls_msg); // �׳��쳣��ʾ
		}
		return SUCCESS;
	}

	// ����û�
	public String CheckUser() throws ApplicationException {
		if (!super.CheckMsgdata()) {
			super.QueryException("���ݰ�ǩ���쳣");
			return SUCCESS;
		}
		CommonDTO dto = new CommonDTO();
		success = "false";
		msg = systemDAO.CheckUser(msgdata);
		if (msg.equals("0")) {
			success = "true";
			msg = "�����ɹ�";
		}
		dto.setSuccess(success);
		dto.setMsg(msg);
		HttpServletResponse response = ServletActionContext.getResponse();
		returnExtJSONToBrowser(response, dto, callback);
		return null;
	}

	
	// �����ϴ�ͼƬ
	public String UploadImg() throws ApplicationException {
		CommonDTO dto = new CommonDTO();
		success = "false";
		msg = systemDAO.UploadImg(file);
		if (msg.substring(0, 1).equals("0")) {
			String[] array_msg = msg.split("\\|");
			String imgurl = array_msg[1];
			String ls_sql = "select '" + imgurl + "' imgurl";
			super.QueryDataBySqlForAll(ls_sql); // ��ѯҵ������
			return SUCCESS;
		}
		dto.setSuccess(success);
		dto.setMsg(msg);
		HttpServletResponse response = ServletActionContext.getResponse();
		returnExtJSONToBrowser(response, dto, callback);
		return null;
	}

	public String UploadImgByBase64() throws ApplicationException {
		// modify by whd 2016.9.1 ���ǵ�ǰ�˲��õ���JS���ã��ϴ�ͼƬʱ�ݲ�����
		// if (!super.CheckMsgdata()){
		// super.QueryException("���ݰ�ǩ���쳣");
		// return SUCCESS;
		// }
		CommonDTO dto = new CommonDTO();
		success = "false";
		msg = systemDAO.UploadImgByBase64(msgdata);
		if (msg.substring(0, 1).equals("0")) {
			String[] array_msg = msg.split("\\|");
			String imgurl = array_msg[1];
			String ls_sql = "select '" + imgurl + "' imgurl";
			super.QueryDataBySqlForAll(ls_sql); // ��ѯҵ������
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
			super.QueryException("���ݰ�ǩ���쳣");
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
			super.QueryException("���ݰ�ǩ���쳣");
			return SUCCESS;
		}
		String ls_msg = systemDAO.CheckSql_Where(msgdata);
		if (ls_msg.equals("0")) { // ��֤�ɹ�
			String ls_sql = systemDAO.GetSql_SearchCountNoticeMsg(msgdata);
			super.QueryDataBySqlForAll(ls_sql); // ��ѯҵ������
		} else {
			super.QueryException(ls_msg); // �׳��쳣��ʾ
		}
		return SUCCESS;
	}
	
	public String SeeNoticeMsg() throws Exception {
		if (!super.CheckMsgdata()) {
			super.QueryException("���ݰ�ǩ���쳣");
			return SUCCESS;
		}
		String ls_msg = systemDAO.CheckSql_Where(msgdata);
		if (ls_msg.equals("0")) { // ��֤�ɹ�
			String ls_sql = systemDAO.GetSql_SeeNoticeMsg(msgdata);
			super.QueryDataBySqlForAll(ls_sql); // ��ѯҵ������
		} else {
			super.QueryException(ls_msg); // �׳��쳣��ʾ
		}
		return SUCCESS;
	}
	
	public String SearchPersonCenter() throws Exception {
		if (!super.CheckMsgdata()) {
			super.QueryException("���ݰ�ǩ���쳣");
			return SUCCESS;
		}
		String ls_msg = systemDAO.CheckSql_Where(msgdata);
		if (ls_msg.equals("0")) { // ��֤�ɹ�
			String ls_sql = systemDAO.GetSql_SearchPersonCenter(msgdata);
			super.QueryDataBySqlForAll(ls_sql); // ��ѯҵ������
		} else {
			super.QueryException(ls_msg); // �׳��쳣��ʾ
		}
		return SUCCESS;
	}


	
}
