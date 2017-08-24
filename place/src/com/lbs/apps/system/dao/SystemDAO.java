package com.lbs.apps.system.dao;

import java.io.File;

import com.lbs.apps.common.ApplicationException;

public interface SystemDAO {	
	public String CheckSql_Where(String msgdata);
	public String GetSql_SearchSysrole(String msgdata)throws ApplicationException;
	public String AddSysrole(String msgdata)throws ApplicationException; //����ϵͳ��ɫ
	public String ModSysrole(String msgdata)throws ApplicationException; //�޸�ϵͳ��ɫ
	public String DelSysrole(String msgdata)throws ApplicationException; //ɾ��ϵͳ��ɫ	
	public String GetSql_SearchSyspara(String msgdata)throws ApplicationException;
	public String AddSyspara(String msgdata)throws ApplicationException; //����ϵͳ����
	public String ModSyspara(String msgdata)throws ApplicationException; //�޸�ϵͳ����
	public String DelSyspara(String msgdata)throws ApplicationException; //ɾ��ϵͳ����	
	public String GetSql_SearchSyscode(String msgdata)throws ApplicationException;
	public String AddSyscode(String msgdata)throws ApplicationException; //����ϵͳ����
	public String ModSyscode(String msgdata)throws ApplicationException; //�޸�ϵͳ����
	public String DelSyscode(String msgdata)throws ApplicationException; //ɾ��ϵͳ����
	public String GetSql_SearchSysgroup(String msgdata)throws ApplicationException;
	public String GetSql_SearchChildSysgroup(String msgdata)throws ApplicationException;  //��ȡ��ѯ�ӻ����б�SQL
	public String AddSysgroup(String msgdata)throws ApplicationException; //����ϵͳ����
	public String ModSysgroup(String msgdata)throws ApplicationException; //�޸�ϵͳ����
	public String DelSysgroup(String msgdata)throws ApplicationException; //ɾ��ϵͳ����
	public String GetSql_SearchStateList(String msgdata)throws ApplicationException;  //��ȡʡ�б�
	public String GetSql_SearchCityList(String msgdata)throws ApplicationException;   //����ʡ�����ȡ�м��б�
	public String GetSql_SearchAreaList(String msgdata)throws ApplicationException;   //�����д����ȡ�����б�
	public String GetSql_SearchTownList(String msgdata)throws ApplicationException;   //�������ش����ȡ����ֵ��б�
	
	public String GetSql_SearchArea(String msgdata)throws ApplicationException;
	public String AddArea(String msgdata)throws ApplicationException; //������������
	public String ModArea(String msgdata)throws ApplicationException; //�޸���������
	public String DelArea(String msgdata)throws ApplicationException; //ɾ����������
	
	public String GetSql_SearchSystemlog(String msgdata)throws ApplicationException; //��ȡ��ѯϵͳ��־SQL
	public String GetSql_SearchSyserrorlog(String msgdata)throws ApplicationException; //��ȡ��ѯϵͳ������־SQL
	public String GetSql_SearchLoginlog(String msgdata)throws ApplicationException; //��ȡ��ѯϵͳ��¼��־SQL
	
	public String GetSql_SearchSysuser(String msgdata)throws ApplicationException;
	public String GetSql_SearchUserAccount(String msgdata)throws ApplicationException;  //��ȡ�û��ʻ���Ϣ
	public String GetSql_SearchUserImgList(String msgdata)throws ApplicationException;  //��ȡ��ԱͼƬ�б�	
	public String GetSql_SearchUserrole(String msgdata)throws ApplicationException;  //��ѯ��Ա������ɫ
	public String AddSysuser(String msgdata)throws ApplicationException; //����ϵͳ�û���Ϣ
	public String ModSysuser(String msgdata)throws ApplicationException; //�޸�ϵͳ�û���Ϣ
	public String ModPassword(String msgdata)throws ApplicationException; //�����û�����
	public String ResetPassword(String msgdata)throws ApplicationException; //�����û�����
	public String DelSysuser(String msgdata)throws ApplicationException; //ɾ��ϵͳ�û���Ϣ
	public String ModUserRole(String msgdata)throws ApplicationException; //�޸��û�������ɫ	
	public String ModRoleMenu(String msgdata)throws ApplicationException; //�޸Ľ�ɫ��Ӧ�Ĳ˵�
	public String GetSql_SearchSysroleMenu(String msgdata)throws ApplicationException;  //��ȡ��ɫ��Ӧ��ϵͳ�Ĳ˵�Ȩ�޹���(���ݽ�ɫ�������ϵͳ����)  ���ڽ�ɫ�˵���Ȩʱ��ȡ����Ȩ���б�
	public String GetSql_SearchSysuserMenu(String msgdata)throws ApplicationException;  //��ȡ�û���Ӧ��ϵͳ�Ĳ˵�����(�����û��������ϵͳ����)  �����û���¼��̬���ɹ��ܲ˵�
	public String CheckUserLogin(String msgdata)throws ApplicationException;    //��֤�û���¼���ɹ�����0��ʧ�ܷ����쳣ԭ��
	public String Logoff(String msgdata)throws ApplicationException; //�û��˳�
	//public String AddSmsVerifyCode(String msgdata)throws ApplicationException;  //���Ͷ�����֤��(�ɹ�����0��ʧ�ܷ����쳣ԭ��)
	public String CheckRegUser(String msgdata)throws ApplicationException;  //�ж��û��Ƿ�ע��
	public String RegUser(String msgdata)throws ApplicationException;  //ע���û�
	public String CheckUser(String msgdata)throws ApplicationException;  //����û�
	
	public String GetSql_SearchCountNoticeMsg(String msgdata)throws ApplicationException;
	public String GetSql_SeeNoticeMsg(String msgdata)throws ApplicationException;
	public String GetSql_SearchPersonCenter(String msgdata)throws ApplicationException;
	
	public String UploadImg(File file)throws ApplicationException;
	public String UploadImgByBase64(String msgdata)throws ApplicationException;	
}
