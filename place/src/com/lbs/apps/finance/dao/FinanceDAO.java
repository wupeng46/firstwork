package com.lbs.apps.finance.dao;

import com.lbs.apps.common.ApplicationException;
import com.lbs.apps.common.CommonDTO;

public interface FinanceDAO {
	//获取待提报的收款报结单的SQL
	public String SearchReceivableToApply(String msgdata)throws ApplicationException;
	//批量提报收款报结单
	public CommonDTO ReceivableApply(String msgdata)throws ApplicationException;
	//查询已提报未确认收款报结单
	public String SearchReceivableToConform(String msgdata)throws ApplicationException;
	//收款确认
	public CommonDTO ReceivableConfirm(String msgdata)throws ApplicationException;
	//获取查询已提报未确认收款报结单的SQL
	public String SearchReceivableApply(String msgdata)throws ApplicationException;
	//批量提报收款报结单取消
	public CommonDTO CancelReceivableApply(String msgdata)throws ApplicationException;
	//获取查询订单已收款记录的SQL
	public String SearchReceivableConfirm(String msgdata)throws ApplicationException;
	//取消收款确认
	public CommonDTO CancelReceivableConfirm(String msgdata)throws ApplicationException;
	//获取查询收款报结单的SQL
	public String SearchReceivable(String msgdata)throws ApplicationException;
	
	//获取待提报的付款报结单的SQL
	public String SearchPayableToApply(String msgdata)throws ApplicationException;
	//批量提报付款报结单
	public CommonDTO PayableApply(String msgdata)throws ApplicationException;
	//获取查询已提报未确认付款报结单的SQL
	public String SearchPayableToConform(String msgdata)throws ApplicationException;
	//付款确认
	public CommonDTO PayableConfirm(String msgdata)throws ApplicationException;	
	//查询已提报未确认付款报结单
	public String SearchPayableApply(String msgdata)throws ApplicationException;
	//付款提报取消
	public CommonDTO CancelPayableApply(String msgdata)throws ApplicationException;
	//查询订单已付款记录
	public String SearchPayableConfirm(String msgdata)throws ApplicationException;
	//取消付款确认
	public CommonDTO CancelPayableConfirm(String msgdata)throws ApplicationException;
	//查询付款报结单
	public String SearchPayable(String msgdata)throws ApplicationException;

		

}
