package com.lbs.apps.api.action;

import com.lbs.apps.api.dao.ApiDAO;
import com.lbs.apps.common.ApplicationException;
import com.lbs.apps.common.CommonQueryAction;

public class ApiAction extends CommonQueryAction {
	private ApiDAO apiDAO;

	public ApiDAO getApiDAO() {
		return apiDAO;
	}

	public void setApiDAO(ApiDAO apiDAO) {
		this.apiDAO = apiDAO;
	}
	
	/**
	 * ��ѯ�����̽��պ�ͬͳ����Ϣ
	 * @return
	 * @throws Exception
	 */
	public String Pt_SearchTodayCompanyContractCount() throws Exception {
		/*
		if (!super.CheckMsgdata()) {
			super.QueryException("���ݰ�ǩ���쳣");
			return SUCCESS;
		}
		*/
		String ls_msg = apiDAO.CheckSql_Where(msgdata);
		if (ls_msg.equals("0")) { // ��֤�ɹ�
			String ls_sql = apiDAO.GetSql_SearchTodayCompanyContractCount(msgdata);
			super.QueryDataBySqlForAll(ls_sql, convertcode); // ��ѯҵ������
		} else {
			super.QueryException(ls_msg); // �׳��쳣��ʾ
		}
		return SUCCESS;
	}
	
	/**
	 * ��ѯ���������к�ͬͳ����Ϣ
	 * @return
	 * @throws Exception
	 */
	public String Pt_SearchAllCompanyContractCount() throws Exception {
		/*
		if (!super.CheckMsgdata()) {
			super.QueryException("���ݰ�ǩ���쳣");
			return SUCCESS;
		}
		*/
		String ls_msg = apiDAO.CheckSql_Where(msgdata);
		if (ls_msg.equals("0")) { // ��֤�ɹ�
			String ls_sql = apiDAO.GetSql_SearchAllCompanyContractCount(msgdata);
			super.QueryDataBySqlForAll(ls_sql, convertcode); // ��ѯҵ������
		} else {
			super.QueryException(ls_msg); // �׳��쳣��ʾ
		}
		return SUCCESS;
	}

	
	/**
	 * ��ѯ��������ʷ�˵���Ϣ�б�
	 * @return
	 * @throws Exception
	 */
	public String Pt_SearchCompanyWaybill() throws Exception{
		/*
		if (!super.CheckMsgdata()) {
			super.QueryException("���ݰ�ǩ���쳣");
			return SUCCESS;
		}
		*/
		String ls_msg = apiDAO.CheckSql_Where(msgdata);
		if (ls_msg.equals("0")) { // ��֤�ɹ�
			String ls_sql = apiDAO.GetSql_SearchCompanyWaybill(msgdata);
			super.QueryDataBySql(ls_sql, convertcode); // ��ѯҵ������
		} else {
			super.QueryException(ls_msg); // �׳��쳣��ʾ
		}
		return SUCCESS;
	}
	
	/**
	 * �������κŲ�ѯ�˵���Ϣ
	 * @param msgdata
	 * @return
	 * @throws ApplicationException
	 */
	public String SearchWaybillByBatch() throws Exception{
		String ls_msg = apiDAO.CheckSql_Where(msgdata);
		if (ls_msg.equals("0")) { // ��֤�ɹ�
			String ls_sql = apiDAO.SearchWaybillByBatch(msgdata);
			super.QueryDataBySql(ls_sql, convertcode); // ��ѯҵ������
		} else {
			super.QueryException(ls_msg); // �׳��쳣��ʾ
		}
		return SUCCESS;
	}

}
