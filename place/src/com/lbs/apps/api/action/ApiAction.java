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
	 * 查询承运商今日合同统计信息
	 * @return
	 * @throws Exception
	 */
	public String Pt_SearchTodayCompanyContractCount() throws Exception {
		/*
		if (!super.CheckMsgdata()) {
			super.QueryException("数据包签名异常");
			return SUCCESS;
		}
		*/
		String ls_msg = apiDAO.CheckSql_Where(msgdata);
		if (ls_msg.equals("0")) { // 验证成功
			String ls_sql = apiDAO.GetSql_SearchTodayCompanyContractCount(msgdata);
			super.QueryDataBySqlForAll(ls_sql, convertcode); // 查询业务数据
		} else {
			super.QueryException(ls_msg); // 抛出异常提示
		}
		return SUCCESS;
	}
	
	/**
	 * 查询承运商所有合同统计信息
	 * @return
	 * @throws Exception
	 */
	public String Pt_SearchAllCompanyContractCount() throws Exception {
		/*
		if (!super.CheckMsgdata()) {
			super.QueryException("数据包签名异常");
			return SUCCESS;
		}
		*/
		String ls_msg = apiDAO.CheckSql_Where(msgdata);
		if (ls_msg.equals("0")) { // 验证成功
			String ls_sql = apiDAO.GetSql_SearchAllCompanyContractCount(msgdata);
			super.QueryDataBySqlForAll(ls_sql, convertcode); // 查询业务数据
		} else {
			super.QueryException(ls_msg); // 抛出异常提示
		}
		return SUCCESS;
	}

	
	/**
	 * 查询承运商历史运单信息列表
	 * @return
	 * @throws Exception
	 */
	public String Pt_SearchCompanyWaybill() throws Exception{
		/*
		if (!super.CheckMsgdata()) {
			super.QueryException("数据包签名异常");
			return SUCCESS;
		}
		*/
		String ls_msg = apiDAO.CheckSql_Where(msgdata);
		if (ls_msg.equals("0")) { // 验证成功
			String ls_sql = apiDAO.GetSql_SearchCompanyWaybill(msgdata);
			super.QueryDataBySql(ls_sql, convertcode); // 查询业务数据
		} else {
			super.QueryException(ls_msg); // 抛出异常提示
		}
		return SUCCESS;
	}
	
	/**
	 * 根据批次号查询运单信息
	 * @param msgdata
	 * @return
	 * @throws ApplicationException
	 */
	public String SearchWaybillByBatch() throws Exception{
		String ls_msg = apiDAO.CheckSql_Where(msgdata);
		if (ls_msg.equals("0")) { // 验证成功
			String ls_sql = apiDAO.SearchWaybillByBatch(msgdata);
			super.QueryDataBySql(ls_sql, convertcode); // 查询业务数据
		} else {
			super.QueryException(ls_msg); // 抛出异常提示
		}
		return SUCCESS;
	}

}
