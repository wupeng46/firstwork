package com.lbs.apps.warehouse.dao;

import com.lbs.apps.common.ApplicationException;

public class WarehouseDAOImp implements WarehouseDAO {

	@Override
	public String SearchStock(String msgdata) throws ApplicationException {
		WarehouseBPO bpo=new WarehouseBPO();
		return bpo.SearchStock(msgdata);
	}

	@Override
	public String ViewStockDetail(String msgdata) throws ApplicationException {
		WarehouseBPO bpo=new WarehouseBPO();
		return bpo.ViewStockDetail(msgdata);
	}

	@Override
	public String SearchStockDetail(String msgdata) throws ApplicationException {
		WarehouseBPO bpo=new WarehouseBPO();
		return bpo.SearchStockDetail(msgdata);
	}

}
