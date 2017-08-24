package com.lbs.apps.finance.dao;

import com.lbs.apps.common.ApplicationException;
import com.lbs.apps.common.CommonDTO;

public class FinanceDAOImp implements FinanceDAO {

	@Override
	public String SearchReceivableToApply(String msgdata)
			throws ApplicationException {
		FinanceBPO bpo=new FinanceBPO();
		return bpo.SearchReceivableToApply(msgdata);
	}

	@Override
	public CommonDTO ReceivableApply(String msgdata)
			throws ApplicationException {
		FinanceBPO bpo=new FinanceBPO();
		return bpo.ReceivableApply(msgdata);
	}

	@Override
	public String SearchReceivableToConform(String msgdata)
			throws ApplicationException {
		FinanceBPO bpo=new FinanceBPO();
		return bpo.SearchReceivableToConform(msgdata);
	}

	@Override
	public CommonDTO ReceivableConfirm(String msgdata)
			throws ApplicationException {
		FinanceBPO bpo=new FinanceBPO();
		return bpo.ReceivableConfirm(msgdata);
	}

	@Override
	public String SearchReceivableApply(String msgdata)
			throws ApplicationException {
		FinanceBPO bpo=new FinanceBPO();
		return bpo.SearchReceivableApply(msgdata);
	}

	@Override
	public CommonDTO CancelReceivableApply(String msgdata)
			throws ApplicationException {
		FinanceBPO bpo=new FinanceBPO();
		return bpo.CancelReceivableApply(msgdata);
	}

	@Override
	public String SearchReceivableConfirm(String msgdata)
			throws ApplicationException {
		FinanceBPO bpo=new FinanceBPO();
		return bpo.SearchReceivableConfirm(msgdata);
	}

	@Override
	public CommonDTO CancelReceivableConfirm(String msgdata)
			throws ApplicationException {
		FinanceBPO bpo=new FinanceBPO();
		return bpo.CancelReceivableConfirm(msgdata);
	}

	@Override
	public String SearchReceivable(String msgdata) throws ApplicationException {
		FinanceBPO bpo=new FinanceBPO();
		return bpo.SearchReceivable(msgdata);
	}

	@Override
	public String SearchPayableToApply(String msgdata)
			throws ApplicationException {
		FinanceBPO bpo=new FinanceBPO();
		return bpo.SearchPayableToApply(msgdata);
	}

	@Override
	public CommonDTO PayableApply(String msgdata) throws ApplicationException {
		FinanceBPO bpo=new FinanceBPO();
		return bpo.PayableApply(msgdata);
	}

	@Override
	public String SearchPayableToConform(String msgdata)
			throws ApplicationException {
		FinanceBPO bpo=new FinanceBPO();
		return bpo.SearchPayableToConform(msgdata);
	}

	@Override
	public CommonDTO PayableConfirm(String msgdata) throws ApplicationException {
		FinanceBPO bpo=new FinanceBPO();
		return bpo.PayableConfirm(msgdata);
	}

	@Override
	public String SearchPayableApply(String msgdata)
			throws ApplicationException {
		FinanceBPO bpo=new FinanceBPO();
		return bpo.SearchPayableApply(msgdata);
	}

	@Override
	public CommonDTO CancelPayableApply(String msgdata)
			throws ApplicationException {
		FinanceBPO bpo=new FinanceBPO();
		return bpo.CancelPayableApply(msgdata);
	}

	@Override
	public String SearchPayableConfirm(String msgdata)
			throws ApplicationException {
		FinanceBPO bpo=new FinanceBPO();
		return bpo.SearchPayableConfirm(msgdata);
	}

	@Override
	public CommonDTO CancelPayableConfirm(String msgdata)
			throws ApplicationException {
		FinanceBPO bpo=new FinanceBPO();
		return bpo.CancelPayableConfirm(msgdata);
	}

	@Override
	public String SearchPayable(String msgdata) throws ApplicationException {
		FinanceBPO bpo=new FinanceBPO();
		return bpo.SearchPayable(msgdata);
	}

}
