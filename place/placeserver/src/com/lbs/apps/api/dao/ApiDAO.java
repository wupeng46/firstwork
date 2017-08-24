package com.lbs.apps.api.dao;

import com.lbs.apps.common.ApplicationException;

public interface ApiDAO {
	public String CheckSql_Where(String msgdata) ;
	public String GetSql_SearchTodayCompanyContractCount(String msgdata)throws ApplicationException;
	public String GetSql_SearchAllCompanyContractCount(String msgdata)throws ApplicationException;
	public String GetSql_SearchCompanyWaybill(String msgdata)throws ApplicationException;
	public String SearchWaybillByBatch(String msgdata)throws ApplicationException;
}
