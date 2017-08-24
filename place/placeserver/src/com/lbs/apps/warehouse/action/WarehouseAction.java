package com.lbs.apps.warehouse.action;

import com.lbs.apps.common.ApplicationException;
import com.lbs.apps.common.CommonQueryAction;
import com.lbs.apps.warehouse.dao.WarehouseDAO;

public class WarehouseAction extends CommonQueryAction {
	private WarehouseDAO warehouseDAO;

	public WarehouseDAO getWarehouseDAO() {
		return warehouseDAO;
	}

	public void setWarehouseDAO(WarehouseDAO warehouseDAO) {
		this.warehouseDAO = warehouseDAO;
	}
	
	public String SearchStock()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("���ݰ�ǩ���쳣");			
		}
		String  ls_sql=warehouseDAO.SearchStock(msgdata);
		return super.QueryDataBySql(ls_sql,convertcode); // ���ø���Ĳ�ѯ�������ؽ����ǰ̨JSP(��ҳJSON)
	}
	public String ViewStockDetail()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("���ݰ�ǩ���쳣");			
		}
		String  ls_sql=warehouseDAO.ViewStockDetail(msgdata);
		return super.QueryDataBySql(ls_sql,convertcode); // ���ø���Ĳ�ѯ�������ؽ����ǰ̨JSP(��ҳJSON)
	}
	public String SearchStockDetail()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("���ݰ�ǩ���쳣");			
		}
		String  ls_sql=warehouseDAO.SearchStockDetail(msgdata);
		return super.QueryDataBySql(ls_sql,convertcode); // ���ø���Ĳ�ѯ�������ؽ����ǰ̨JSP(��ҳJSON)
	}

}
