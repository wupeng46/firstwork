package com.lbs.apps.order.dao;

import com.lbs.apps.common.ApplicationException;
import com.lbs.apps.common.CommonDTO;

public class OrderDAOImp implements OrderDAO {

	@Override
	public String SearchOrder(String msgdata) throws ApplicationException {
		OrderBPO bpo = new OrderBPO();
		return bpo.SearchOrder(msgdata);
	}

	@Override
	public ReturnDTO AddOrder(String msgdata) throws ApplicationException {
		OrderBPO bpo = new OrderBPO();
		return bpo.AddOrder(msgdata);
	}

	@Override
	public CommonDTO ModOrder(String msgdata) throws ApplicationException {
		OrderBPO bpo = new OrderBPO();
		return bpo.ModOrder(msgdata);
	}

	@Override
	public String ViewOrder(String msgdata) throws ApplicationException {
		OrderBPO bpo = new OrderBPO();
		return bpo.ViewOrder(msgdata);
	}
	
	@Override
	public String SearchOrderCompany(String msgdata) throws ApplicationException {
		OrderBPO bpo = new OrderBPO();
		return bpo.SearchOrderCompany(msgdata);
	}

	@Override
	public String SearchOrderToPlan(String msgdata) throws ApplicationException {
		OrderBPO bpo = new OrderBPO();
		return bpo.SearchOrderToPlan(msgdata);
	}

	@Override
	public CommonDTO SaveOrderPlan(String msgdata) throws ApplicationException {
		OrderBPO bpo = new OrderBPO();
		return bpo.SaveOrderPlan(msgdata);
	}

	@Override
	public CommonDTO ModOrderPlan(String msgdata) throws ApplicationException {
		OrderBPO bpo = new OrderBPO();
		return bpo.ModOrderPlan(msgdata);
	}

	@Override
	public CommonDTO OrderFinish(String msgdata) throws ApplicationException {
		OrderBPO bpo = new OrderBPO();
		return bpo.OrderFinish(msgdata);
	}
	
	@Override
	public String SearchOrderPlan(String msgdata) throws ApplicationException {
		OrderBPO bpo = new OrderBPO();
		return bpo.SearchOrderPlan(msgdata);
	}

	@Override
	public String ViewOrderPlan(String msgdata) throws ApplicationException {
		OrderBPO bpo = new OrderBPO();
		return bpo.ViewOrderPlan(msgdata);
	}

	@Override
	public String ViewOrderProcess(String msgdata) throws ApplicationException {
		OrderBPO bpo = new OrderBPO();
		return bpo.ViewOrderProcess(msgdata);
	}

	@Override
	public String SearchBoxToInPlace(String msgdata)
			throws ApplicationException {
		OrderBPO bpo = new OrderBPO();
		return bpo.SearchBoxToInPlace(msgdata);
	}

	@Override
	public CommonDTO BoxInPlaceConfirm(String msgdata)
			throws ApplicationException {
		OrderBPO bpo = new OrderBPO();
		return bpo.BoxInPlaceConfirm(msgdata);
	}

	@Override
	public String SearchBoxToOutPlace(String msgdata)
			throws ApplicationException {
		OrderBPO bpo = new OrderBPO();
		return bpo.SearchBoxToOutPlace(msgdata);
	}

	@Override
	public CommonDTO BoxOutPlaceConfirm(String msgdata)
			throws ApplicationException {
		OrderBPO bpo = new OrderBPO();
		return bpo.BoxOutPlaceConfirm(msgdata);
	}

	@Override
	public String SearchBoxToOutPark(String msgdata)
			throws ApplicationException {
		OrderBPO bpo = new OrderBPO();
		return bpo.SearchBoxToOutPark(msgdata);
	}

	@Override
	public CommonDTO BoxOutParkConfirm(String msgdata)
			throws ApplicationException {
		OrderBPO bpo = new OrderBPO();
		return bpo.BoxOutParkConfirm(msgdata);
	}

	@Override
	public CommonDTO SaveOutWeigh(String msgdata) throws ApplicationException {
		OrderBPO bpo = new OrderBPO();
		return bpo.SaveOutWeigh(msgdata);
	}

	@Override
	public String SearchWeigh(String msgdata) throws ApplicationException {
		OrderBPO bpo = new OrderBPO();
		return bpo.SearchWeigh(msgdata);
	}

	@Override
	public String ViewWeigh(String msgdata) throws ApplicationException {
		OrderBPO bpo = new OrderBPO();
		return bpo.ViewWeigh(msgdata);
	}

	@Override
	public String SearchInPlaceWeigh(String msgdata)
			throws ApplicationException {
		OrderBPO bpo = new OrderBPO();
		return bpo.SearchInPlaceWeigh(msgdata);
	}

	@Override
	public CommonDTO CancelInPlaceWeight(String msgdata)
			throws ApplicationException {
		OrderBPO bpo = new OrderBPO();
		return bpo.CancelInPlaceWeight(msgdata);
	}

	@Override
	public String SearchOutPlaceWeigh(String msgdata)
			throws ApplicationException {
		OrderBPO bpo = new OrderBPO();
		return bpo.SearchOutPlaceWeigh(msgdata);
	}

	@Override
	public CommonDTO CancelOutPlaceWeight(String msgdata)
			throws ApplicationException {
		OrderBPO bpo = new OrderBPO();
		return bpo.CancelOutPlaceWeight(msgdata);
	}

	@Override
	public String SearchOutParkWeigh(String msgdata)
			throws ApplicationException {
		OrderBPO bpo = new OrderBPO();
		return bpo.SearchOutParkWeigh(msgdata);
	}

	@Override
	public CommonDTO CancelOutParkWeight(String msgdata)
			throws ApplicationException {
		OrderBPO bpo = new OrderBPO();
		return bpo.CancelOutParkWeight(msgdata);
	}

	@Override
	public String SearchNoFinishOrder(String msgdata)
			throws ApplicationException {
		OrderBPO bpo = new OrderBPO();
		return bpo.SearchNoFinishOrder(msgdata);
	}

	@Override
	public CommonDTO AddWaybill(String msgdata) throws ApplicationException {
		OrderBPO bpo = new OrderBPO();
		return bpo.AddWaybill(msgdata);
	}

	@Override
	public String SearchWaybill(String msgdata) throws ApplicationException {
		OrderBPO bpo = new OrderBPO();
		return bpo.SearchWaybill(msgdata);
	}

	@Override
	public String SearchWaybillDetail(String msgdata)
			throws ApplicationException {
		OrderBPO bpo = new OrderBPO();
		return bpo.SearchWaybillDetail(msgdata);
	}

	@Override
	public String ViewWaybill(String msgdata) throws ApplicationException {
		OrderBPO bpo = new OrderBPO();
		return bpo.ViewWaybill(msgdata);
	}

	@Override
	public CommonDTO ModWaybill(String msgdata) throws ApplicationException {
		OrderBPO bpo = new OrderBPO();
		return bpo.ModWaybill(msgdata);
	}

	@Override
	public CommonDTO FinishWaybill(String msgdata) throws ApplicationException {
		OrderBPO bpo = new OrderBPO();
		return bpo.FinishWaybill(msgdata);
	}

	@Override
	public ReturnDTO ImportOrder(String msgdata) throws ApplicationException {
		OrderBPO bpo = new OrderBPO();
		return bpo.ImportOrder(msgdata);
	}

}
