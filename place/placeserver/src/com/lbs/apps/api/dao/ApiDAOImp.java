package com.lbs.apps.api.dao;

import com.lbs.apps.common.ApplicationException;

public class ApiDAOImp implements ApiDAO {
	@Override
	public String CheckSql_Where(String msgdata){
		ApiBPO bpo=new ApiBPO();
		return bpo.CheckSql_Where(msgdata);
	}

	@Override
	public String GetSql_SearchTodayCompanyContractCount(String msgdata)
			throws ApplicationException {
		ApiBPO bpo=new ApiBPO();
		return bpo.GetSql_SearchTodayCompanyContractCount(msgdata);
	}

	@Override
	public String GetSql_SearchAllCompanyContractCount(String msgdata)
			throws ApplicationException {
		ApiBPO bpo=new ApiBPO();
		return bpo.GetSql_SearchAllCompanyContractCount(msgdata);
	}

	@Override
	public String GetSql_SearchCompanyWaybill(String msgdata)
			throws ApplicationException {
		ApiBPO bpo=new ApiBPO();
		return bpo.GetSql_SearchCompanyWaybill(msgdata);
	}

	@Override
	public String SearchWaybillByBatch(String msgdata)
			throws ApplicationException {
		ApiBPO bpo=new ApiBPO();
		return bpo.SearchWaybillByBatch(msgdata);
	}

}
