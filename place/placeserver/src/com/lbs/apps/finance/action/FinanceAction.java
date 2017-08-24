package com.lbs.apps.finance.action;

import com.lbs.apps.common.ApplicationException;
import com.lbs.apps.common.CommonDTO;
import com.lbs.apps.common.CommonQueryAction;
import com.lbs.apps.finance.dao.FinanceDAO;

public class FinanceAction extends CommonQueryAction {
	private FinanceDAO financeDAO;

	public FinanceDAO getFinanceDAO() {
		return financeDAO;
	}

	public void setFinanceDAO(FinanceDAO financeDAO) {
		this.financeDAO = financeDAO;
	}
	
	//获取待提报的收款报结单的SQL
	public String SearchReceivableToApply()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("数据包签名异常");			
		}
		String  ls_sql=financeDAO.SearchReceivableToApply(msgdata);
		return super.QueryDataBySql(ls_sql,convertcode); // 调用父类的查询方法返回结果到前台JSP(分页JSON)
	}
	//批量提报收款报结单
	public String ReceivableApply()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("数据包签名异常");			
		}
		CommonDTO dto=financeDAO.ReceivableApply(msgdata);
		return PrintDTO(dto);
	}
	//查询已提报未确认收款报结单
	public String SearchReceivableToConform()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("数据包签名异常");			
		}
		String  ls_sql=financeDAO.SearchReceivableToConform(msgdata);
		return super.QueryDataBySql(ls_sql,convertcode); // 调用父类的查询方法返回结果到前台JSP(分页JSON)
	}
	//收款确认
	public String ReceivableConfirm()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("数据包签名异常");			
		}
		CommonDTO dto=financeDAO.ReceivableConfirm(msgdata);
		return PrintDTO(dto);
	}
	//获取查询已提报未确认收款报结单的SQL
	public String SearchReceivableApply()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("数据包签名异常");			
		}
		String  ls_sql=financeDAO.SearchReceivableApply(msgdata);
		return super.QueryDataBySql(ls_sql,convertcode); // 调用父类的查询方法返回结果到前台JSP(分页JSON)
	}
	//批量提报收款报结单取消
	public String CancelReceivableApply()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("数据包签名异常");			
		}
		CommonDTO dto=financeDAO.CancelReceivableApply(msgdata);
		return PrintDTO(dto);
	}
	//获取查询订单已收款记录的SQL
	public String SearchReceivableConfirm()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("数据包签名异常");			
		}
		String  ls_sql=financeDAO.SearchReceivableConfirm(msgdata);
		return super.QueryDataBySql(ls_sql,convertcode); // 调用父类的查询方法返回结果到前台JSP(分页JSON)
    }
	//取消收款确认
	public String CancelReceivableConfirm()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("数据包签名异常");			
		}
		CommonDTO dto=financeDAO.CancelReceivableConfirm(msgdata);
		return PrintDTO(dto);
	}
	//获取查询收款报结单的SQL
	public String SearchReceivable()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("数据包签名异常");			
		}
		String  ls_sql=financeDAO.SearchReceivable(msgdata);
		return super.QueryDataBySql(ls_sql,convertcode); // 调用父类的查询方法返回结果到前台JSP(分页JSON)
	}
	
	//获取待提报的付款报结单的SQL
	public String SearchPayableToApply()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("数据包签名异常");			
		}
		String  ls_sql=financeDAO.SearchPayableToApply(msgdata);
		return super.QueryDataBySql(ls_sql,convertcode); // 调用父类的查询方法返回结果到前台JSP(分页JSON)
	}
	//批量提报付款报结单
	public String PayableApply()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("数据包签名异常");			
		}
		CommonDTO dto=financeDAO.PayableApply(msgdata);
		return PrintDTO(dto);
	}
	//获取查询已提报未确认付款报结单的SQL
	public String SearchPayableToConform()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("数据包签名异常");			
		}
		String  ls_sql=financeDAO.SearchPayableToConform(msgdata);
		return super.QueryDataBySql(ls_sql,convertcode); // 调用父类的查询方法返回结果到前台JSP(分页JSON)
	}
	//付款确认
	public String PayableConfirm()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("数据包签名异常");			
		}
		CommonDTO dto=financeDAO.PayableConfirm(msgdata);
		return PrintDTO(dto);
	}	
	//查询已提报未确认付款报结单
	public String SearchPayableApply()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("数据包签名异常");			
		}
		String  ls_sql=financeDAO.SearchPayableApply(msgdata);
		return super.QueryDataBySql(ls_sql,convertcode); // 调用父类的查询方法返回结果到前台JSP(分页JSON)
	}
	//付款提报取消
	public String CancelPayableApply()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("数据包签名异常");			
		}
		CommonDTO dto=financeDAO.CancelPayableApply(msgdata);
		return PrintDTO(dto);
	}
	//查询订单已付款记录
	public String SearchPayableConfirm()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("数据包签名异常");			
		}
		String  ls_sql=financeDAO.SearchPayableConfirm(msgdata);
		return super.QueryDataBySql(ls_sql,convertcode); // 调用父类的查询方法返回结果到前台JSP(分页JSON)
	}
	//取消付款确认
	public String CancelPayableConfirm()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("数据包签名异常");			
		}
		CommonDTO dto=financeDAO.CancelPayableConfirm(msgdata);
		return PrintDTO(dto);
	}
	//查询付款报结单
	public String SearchPayable()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("数据包签名异常");			
		}
		String  ls_sql=financeDAO.SearchPayable(msgdata);
		return super.QueryDataBySql(ls_sql,convertcode); // 调用父类的查询方法返回结果到前台JSP(分页JSON)
	}

}
