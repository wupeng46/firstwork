package com.lbs.apps.order.action;

import com.lbs.apps.common.ApplicationException;
import com.lbs.apps.common.CommonDTO;
import com.lbs.apps.common.CommonQueryAction;
import com.lbs.apps.order.dao.OrderDAO;
import com.lbs.apps.order.dao.ReturnDTO;

public class OrderAction extends CommonQueryAction {
	private OrderDAO orderDAO;

	public OrderDAO getOrderDAO() {
		return orderDAO;
	}

	public void setOrderDAO(OrderDAO orderDAO) {
		this.orderDAO = orderDAO;
	}
	
	//查询订单
	public String SearchOrder()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("数据包签名异常");			
		}
		String  ls_sql=orderDAO.SearchOrder(msgdata);
		return super.QueryDataBySql(ls_sql,convertcode); // 调用父类的查询方法返回结果到前台JSP(分页JSON)		
	}
	//保存线下订单
	public String AddOrder()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("数据包签名异常");			
		}
		ReturnDTO dto=orderDAO.AddOrder(msgdata);
		return PrintDTO(dto);
	}
	//修改订单
	public String ModOrder()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("数据包签名异常");			
		}
		CommonDTO dto=orderDAO.ModOrder(msgdata);
		return PrintDTO(dto);
	}
	//查看订单
	public String ViewOrder()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("数据包签名异常");			
		}
		String  ls_sql=orderDAO.ViewOrder(msgdata);
		return super.QueryDataBySql(ls_sql,convertcode); // 调用父类的查询方法返回结果到前台JSP(分页JSON)
	}
	//获取订单下承运商列表
	public String SearchOrderCompany()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("数据包签名异常");			
		}
		String  ls_sql=orderDAO.SearchOrderCompany(msgdata);
		return super.QueryDataBySql(ls_sql,convertcode); // 调用父类的查询方法返回结果到前台JSP(分页JSON)
	}
	//查询待排期的订单
	public String SearchOrderToPlan()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("数据包签名异常");			
		}
		String  ls_sql=orderDAO.SearchOrderToPlan(msgdata);
		return super.QueryDataBySql(ls_sql,convertcode); // 调用父类的查询方法返回结果到前台JSP(分页JSON)
	}
	//保存订单排期计划
	public String SaveOrderPlan()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("数据包签名异常");			
		}
		CommonDTO dto=orderDAO.SaveOrderPlan(msgdata);
		return PrintDTO(dto);
	}
	//修改订单排期
	public String ModOrderPlan()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("数据包签名异常");			
		}
		CommonDTO dto=orderDAO.ModOrderPlan(msgdata);
		return PrintDTO(dto);
	}
	//订单完成
	public String OrderFinish()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("数据包签名异常");			
		}
		CommonDTO dto=orderDAO.OrderFinish(msgdata);
		return PrintDTO(dto);
	}
	//查询订单排期计划
	public String SearchOrderPlan()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("数据包签名异常");			
		}
		String  ls_sql=orderDAO.SearchOrderPlan(msgdata);
		return super.QueryDataBySql(ls_sql,convertcode); // 调用父类的查询方法返回结果到前台JSP(分页JSON)
	}
	//查看订单排期计划
	public String ViewOrderPlan()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("数据包签名异常");			
		}
		String  ls_sql=orderDAO.ViewOrderPlan(msgdata);
		return super.QueryDataBySql(ls_sql,convertcode); // 调用父类的查询方法返回结果到前台JSP(分页JSON)
	}
	//查看完成进度
	public String ViewOrderProcess()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("数据包签名异常");			
		}
		String  ls_sql=orderDAO.ViewOrderProcess(msgdata);
		return super.QueryDataBySql(ls_sql,convertcode); // 调用父类的查询方法返回结果到前台JSP(分页JSON)
	}
	
	//查询待入场的过磅单
	public String SearchBoxToInPlace()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("数据包签名异常");			
		}
		String  ls_sql=orderDAO.SearchBoxToInPlace(msgdata);
		return super.QueryDataBySql(ls_sql,convertcode); // 调用父类的查询方法返回结果到前台JSP(分页JSON)
	}
	//重箱批量入场确认
	public String BoxInPlaceConfirm()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("数据包签名异常");			
		}
		CommonDTO dto=orderDAO.BoxInPlaceConfirm(msgdata);
		return PrintDTO(dto);
	}
	//查询待出场的过磅单
	public String SearchBoxToOutPlace()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("数据包签名异常");			
		}
		String  ls_sql=orderDAO.SearchBoxToOutPlace(msgdata);
		return super.QueryDataBySql(ls_sql,convertcode); // 调用父类的查询方法返回结果到前台JSP(分页JSON)
	}
	//重箱批量出场确认
	public String BoxOutPlaceConfirm()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("数据包签名异常");			
		}
		CommonDTO dto=orderDAO.BoxOutPlaceConfirm(msgdata);
		return PrintDTO(dto);
	}
	//查询待出园的过磅单
	public String SearchBoxToOutPark()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("数据包签名异常");			
		}
		String  ls_sql=orderDAO.SearchBoxToOutPark(msgdata);
		return super.QueryDataBySql(ls_sql,convertcode); // 调用父类的查询方法返回结果到前台JSP(分页JSON)
	}
	//重箱批量出园确认
	public String BoxOutParkConfirm()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("数据包签名异常");			
		}
		CommonDTO dto=orderDAO.BoxOutParkConfirm(msgdata);
		return PrintDTO(dto);
	}
	//保存外部过磅单
	public String SaveOutWeigh()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("数据包签名异常");			
		}
		CommonDTO dto=orderDAO.SaveOutWeigh(msgdata);
		return PrintDTO(dto);
	}	
	//查询过磅单
	public String SearchWeigh()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("数据包签名异常");			
		}
		String  ls_sql=orderDAO.SearchWeigh(msgdata);
		return super.QueryDataBySql(ls_sql,convertcode); // 调用父类的查询方法返回结果到前台JSP(分页JSON)
	}
	//查看过磅单
	public String ViewWeigh()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("数据包签名异常");			
		}
		String  ls_sql=orderDAO.ViewWeigh(msgdata);
		return super.QueryDataBySql(ls_sql,convertcode); // 调用父类的查询方法返回结果到前台JSP(分页JSON)
	}
	//查询已入场过磅
	public String SearchInPlaceWeigh()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("数据包签名异常");			
		}
		String  ls_sql=orderDAO.SearchInPlaceWeigh(msgdata);
		return super.QueryDataBySql(ls_sql,convertcode); // 调用父类的查询方法返回结果到前台JSP(分页JSON)
	}
	//取消已入场过磅单
	public String CancelInPlaceWeight()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("数据包签名异常");			
		}
		CommonDTO dto=orderDAO.CancelInPlaceWeight(msgdata);
		return PrintDTO(dto);
	}
	//查询已出场过磅单
	public String SearchOutPlaceWeigh()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("数据包签名异常");			
		}
		String  ls_sql=orderDAO.SearchOutPlaceWeigh(msgdata);
		return super.QueryDataBySql(ls_sql,convertcode); // 调用父类的查询方法返回结果到前台JSP(分页JSON)
	}
	//取消已出场过磅单
	public String CancelOutPlaceWeight()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("数据包签名异常");			
		}
		CommonDTO dto=orderDAO.CancelOutPlaceWeight(msgdata);
		return PrintDTO(dto);
	}	
	//查询已出园过磅单
	public String SearchOutParkWeigh()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("数据包签名异常");			
		}
		String  ls_sql=orderDAO.SearchOutParkWeigh(msgdata);
		return super.QueryDataBySql(ls_sql,convertcode); // 调用父类的查询方法返回结果到前台JSP(分页JSON)
	}
	//取消已出园过磅单
	public String CancelOutParkWeight()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("数据包签名异常");			
		}
		CommonDTO dto=orderDAO.CancelOutParkWeight(msgdata);
		return PrintDTO(dto);
	}
	
	//查询未完成的订单
	public String SearchNoFinishOrder()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("数据包签名异常");			
		}
		String  ls_sql=orderDAO.SearchNoFinishOrder(msgdata);
		return super.QueryDataBySql(ls_sql,convertcode); // 调用父类的查询方法返回结果到前台JSP(分页JSON)
	}
	//保存运单信息
	public String AddWaybill()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("数据包签名异常");			
		}
		CommonDTO dto=orderDAO.AddWaybill(msgdata);
		return PrintDTO(dto);
	}
	//查询批次运单信息
	public String SearchWaybill()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("数据包签名异常");			
		}
		String  ls_sql=orderDAO.SearchWaybill(msgdata);
		return super.QueryDataBySql(ls_sql,convertcode); // 调用父类的查询方法返回结果到前台JSP(分页JSON)
	}
	//查询批次运单明细
	public String SearchWaybillDetail()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("数据包签名异常");			
		}
		String  ls_sql=orderDAO.SearchWaybillDetail(msgdata);
		return super.QueryDataBySql(ls_sql,convertcode); // 调用父类的查询方法返回结果到前台JSP(分页JSON)
	}
	//查看批次运单详情
	public String ViewWaybill()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("数据包签名异常");			
		}
		String  ls_sql=orderDAO.ViewWaybill(msgdata);
		return super.QueryDataBySql(ls_sql,convertcode); // 调用父类的查询方法返回结果到前台JSP(分页JSON)
	}
	//修改批次运单信息
	public String ModWaybill()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("数据包签名异常");			
		}
		CommonDTO dto=orderDAO.ModWaybill(msgdata);
		return PrintDTO(dto);
	}
	//运单完成
	public String FinishWaybill()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("数据包签名异常");			
		}
		CommonDTO dto=orderDAO.FinishWaybill(msgdata);
		return PrintDTO(dto);
	}
	
	//导入物流订单
	public String ImportOrder()throws ApplicationException{
		//导入接口不采用加密码通讯
		/*
		if (!super.CheckMsgdata()){
			return super.QueryException("数据包签名异常");			
		}
		*/
		ReturnDTO dto=orderDAO.ImportOrder(msgdata);
		return PrintDTO(dto);
	}

}
