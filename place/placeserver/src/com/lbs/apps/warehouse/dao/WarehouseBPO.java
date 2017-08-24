package com.lbs.apps.warehouse.dao;

import com.lbs.apps.common.ApplicationException;
import com.lbs.commons.ClassHelper;

public class WarehouseBPO {
	public String SearchStock(String msgdata) throws ApplicationException {
		WarehouseDTO dto=new WarehouseDTO();
		ClassHelper.copyProperties(msgdata, dto);
		StringBuffer sb=new StringBuffer("select stockid,goodstype,productlevel,brand,yearnum,sourcename,origin,instock,outstock,stock from stockinfo")
		.append(" where 1=1");
		if (!(dto.getStockid()==null || dto.getStockid().equals(""))){
			sb.append(" and stockid='").append(dto.getStockid()).append("'");
		}
		if (!(dto.getGoodstype()==null || dto.getGoodstype().equals(""))){
			sb.append(" and goodstype='").append(dto.getGoodstype()).append("'");
		}
		if (!(dto.getProductlevel()==null || dto.getProductlevel().equals(""))){
			sb.append(" and productlevel='").append(dto.getProductlevel()).append("'");
		}
		if (!(dto.getBrand()==null || dto.getBrand().equals(""))){
			sb.append(" and brand='").append(dto.getBrand()).append("'");
		}
		if (!(dto.getSourcename()==null || dto.getSourcename().equals(""))){
			sb.append(" and sourcename='").append(dto.getSourcename()).append("'");
		}
		return sb.toString();
	}

	public String ViewStockDetail(String msgdata) throws ApplicationException {
		WarehouseDTO dto=new WarehouseDTO();
		ClassHelper.copyProperties(msgdata, dto);
		StringBuffer sb=new StringBuffer("select stockid,mxid,stocktype,water,impurity,capacity,smell,imperfecttotal,imperfectnum,imperfectnum2,qty,price,amount,createdby,convert(varchar,createddate,120) createddate from stockdetail where 1=1");
		if (!(dto.getStockid()==null || dto.getStockid().equals(""))){
			sb.append(" and stockid='").append(dto.getStockid()).append("'");
		}else{
			sb.append(" and 1=2");
		}		
		return sb.toString();		
	}

	public String SearchStockDetail(String msgdata) throws ApplicationException {
		WarehouseDTO dto=new WarehouseDTO();
		ClassHelper.copyProperties(msgdata, dto);
		StringBuffer sb=new StringBuffer("select a.stockid,mxid,stocktype,goodstype,productlevel,brand,yearnum,sourcename,origin,water,impurity,capacity,smell,")
		.append(" imperfecttotal,imperfectnum,imperfectnum2,qty,price,amount,b.createdby,convert(varchar,b.createddate,120) createddate") 
		.append(" from stockinfo a,stockdetail b where 1=1");
		if (!(dto.getStockid()==null || dto.getStockid().equals(""))){
			sb.append(" and stockid='").append(dto.getStockid()).append("'");
		}
		if (!(dto.getMxid()==null || dto.getMxid().intValue()==0)){
			sb.append(" and mxid=").append(dto.getMxid());
		}
		if (!(dto.getGoodstype()==null || dto.getGoodstype().equals(""))){
			sb.append(" and a.goodstype='").append(dto.getGoodstype()).append("'");
		}
		if (!(dto.getProductlevel()==null || dto.getProductlevel().equals(""))){
			sb.append(" and a.productlevel='").append(dto.getProductlevel()).append("'");
		}
		if (!(dto.getBrand()==null || dto.getBrand().equals(""))){
			sb.append(" and a.brand='").append(dto.getBrand()).append("'");
		}
		if (!(dto.getStocktype()==null || dto.getStocktype().equals(""))){
			sb.append(" and stocktype='").append(dto.getStocktype()).append("'");
		}
		if (!(dto.getSourcename()==null || dto.getSourcename().equals(""))){
			sb.append(" and a.sourcename='").append(dto.getSourcename()).append("'");
		}
		if (!(dto.getStarttime()==null || dto.getStarttime().equals(""))){
			sb.append(" and b.createddate >= '").append(dto.getStarttime()).append(" 00:00:00'");				
		}
		if (!(dto.getEndtime()==null || dto.getEndtime().equals(""))){
			sb.append(" and b.createddate <= '").append(dto.getEndtime()).append(" 23:59:59'");
		}		
		return sb.toString();
	}


}
