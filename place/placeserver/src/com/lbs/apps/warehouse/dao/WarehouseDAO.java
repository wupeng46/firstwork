package com.lbs.apps.warehouse.dao;

import com.lbs.apps.common.ApplicationException;

public interface WarehouseDAO {
	
	public String SearchStock(String msgdata)throws ApplicationException;
	public String ViewStockDetail(String msgdata)throws ApplicationException;
	public String SearchStockDetail(String msgdata)throws ApplicationException;
}
