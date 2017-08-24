package com.lbs.apps.order.dao;

import com.lbs.apps.common.ApplicationException;
import com.lbs.apps.common.CommonDTO;

public interface OrderDAO {	
	//查询订单
	public String SearchOrder(String msgdata)throws ApplicationException;
	//保存线下订单
	public ReturnDTO AddOrder(String msgdata)throws ApplicationException;
	//修改订单
	public CommonDTO ModOrder(String msgdata)throws ApplicationException;
	//查看订单
	public String ViewOrder(String msgdata)throws ApplicationException;
	//获取订单下承运商列表
	public String SearchOrderCompany(String msgdata) throws ApplicationException;
	//查询待排期的订单
	public String SearchOrderToPlan(String msgdata)throws ApplicationException;
	//保存订单排期计划
	public CommonDTO SaveOrderPlan(String msgdata)throws ApplicationException;
	//修改订单排期
	public CommonDTO ModOrderPlan(String msgdata)throws ApplicationException;
	//订单完成
	public CommonDTO OrderFinish(String msgdata)throws ApplicationException;
	//查询订单排期计划
	public String SearchOrderPlan(String msgdata)throws ApplicationException;
	//查看订单排期计划
	public String ViewOrderPlan(String msgdata)throws ApplicationException;
	//查看完成进度
	public String ViewOrderProcess(String msgdata)throws ApplicationException;	
	
	//查询待入场的过磅单
	public String SearchBoxToInPlace(String msgdata)throws ApplicationException;
	//重箱批量入场确认
	public CommonDTO BoxInPlaceConfirm(String msgdata)throws ApplicationException;
	//查询待出场的过磅单
	public String SearchBoxToOutPlace(String msgdata)throws ApplicationException;
	//重箱批量出场确认
	public CommonDTO BoxOutPlaceConfirm(String msgdata)throws ApplicationException;	
	//查询待出园的过磅单
	public String SearchBoxToOutPark(String msgdata)throws ApplicationException;
	//重箱批量出园确认
	public CommonDTO BoxOutParkConfirm(String msgdata)throws ApplicationException;
	//保存外部过磅单
	public CommonDTO SaveOutWeigh(String msgdata)throws ApplicationException;	
	//查询过磅单
	public String SearchWeigh(String msgdata)throws ApplicationException;
	//查看过磅单
	public String ViewWeigh(String msgdata)throws ApplicationException;	
	//查询已入场过磅
	public String SearchInPlaceWeigh(String msgdata)throws ApplicationException;
	//取消已入场过磅单
	public CommonDTO CancelInPlaceWeight(String msgdata)throws ApplicationException;	
	//查询已出场过磅单
	public String SearchOutPlaceWeigh(String msgdata)throws ApplicationException;
	//取消已出场过磅单
	public CommonDTO CancelOutPlaceWeight(String msgdata)throws ApplicationException;	
	//查询已出园过磅单
	public String SearchOutParkWeigh(String msgdata)throws ApplicationException;
	//取消已出园过磅单
	public CommonDTO CancelOutParkWeight(String msgdata)throws ApplicationException;
	
	//查询未完成的订单
	public String SearchNoFinishOrder(String msgdata)throws ApplicationException;
	//保存运单信息
	public CommonDTO AddWaybill(String msgdata)throws ApplicationException;
	//查询批次运单信息
	public String SearchWaybill(String msgdata)throws ApplicationException;	
	//查询批次运单明细
	public String SearchWaybillDetail(String msgdata)throws ApplicationException;
	//查看批次运单详情
	public String ViewWaybill(String msgdata)throws ApplicationException;
	//修改批次运单信息
	public CommonDTO ModWaybill(String msgdata)throws ApplicationException;
	//运单完成
	public CommonDTO FinishWaybill(String msgdata)throws ApplicationException;
    //物流订单导入
	public ReturnDTO ImportOrder(String msgdata) throws ApplicationException;

}
