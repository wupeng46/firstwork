package com.lbs.apps.basic.action;

import com.lbs.apps.basic.dao.BasicDAO;
import com.lbs.apps.common.ApplicationException;
import com.lbs.apps.common.CommonDTO;
import com.lbs.apps.common.CommonQueryAction;

public class BasicAction extends CommonQueryAction {
	private BasicDAO basicDAO;

	public BasicDAO getBasicDAO() {
		return basicDAO;
	}

	public void setBasicDAO(BasicDAO basicDAO) {
		this.basicDAO = basicDAO;
	}
	//根据条件查询委托方资料
	public String SearchMember()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("数据包签名异常");			
		}
		String  ls_sql=basicDAO.SearchMember(msgdata);
		return super.QueryDataBySql(ls_sql,convertcode); // 调用父类的查询方法返回结果到前台JSP(分页JSON)
	}
	//根据条件查询承运商资料
	public String SearchCompany()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("数据包签名异常");			
		}
		String  ls_sql=basicDAO.SearchCompany(msgdata);
		return super.QueryDataBySql(ls_sql,convertcode); // 调用父类的查询方法返回结果到前台JSP(分页JSON)
	}
	//增加承运商资料
	public String AddCompany()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("数据包签名异常");			
		}
		CommonDTO dto=basicDAO.AddCompany(msgdata);
		return PrintDTO(dto);
	}
	
	//修改承运商资料
	public String ModCompany()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("数据包签名异常");			
		}
		CommonDTO dto=basicDAO.ModCompany(msgdata);
		return PrintDTO(dto);
	}
	
	//删除承运商资料
	public String DelCompany()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("数据包签名异常");			
		}
		CommonDTO dto=basicDAO.DelCompany(msgdata);
		return PrintDTO(dto);
	}
	
	//根据承运商编号查询承运商资料
	public String ViewCompany()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("数据包签名异常");			
		}
		String  ls_sql=basicDAO.ViewCompany(msgdata);
		return super.QueryDataBySql(ls_sql,convertcode); // 调用父类的查询方法返回结果到前台JSP(分页JSON)
	}
	
	
	//获取查询集装箱的SQL
	public String SearchBox()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("数据包签名异常");			
		}
		String  ls_sql=basicDAO.SearchBox(msgdata);
		return super.QueryDataBySql(ls_sql,convertcode); // 调用父类的查询方法返回结果到前台JSP(分页JSON)
	}
	//增加集装箱
	public String AddBox()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("数据包签名异常");			
		}
		CommonDTO dto=basicDAO.AddBox(msgdata);
		return PrintDTO(dto);
	}
	//修改集装箱
	public String ModBox()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("数据包签名异常");			
		}
		CommonDTO dto=basicDAO.ModBox(msgdata);
		return PrintDTO(dto);
	}
	//删除集装箱
	public String DelBox()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("数据包签名异常");			
		}
		CommonDTO dto=basicDAO.DelBox(msgdata);
		return PrintDTO(dto);
	}
	//获取查看集装箱的SQL
	public String ViewBox()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("数据包签名异常");			
		}
		String  ls_sql=basicDAO.ViewBox(msgdata);
		return super.QueryDataBySql(ls_sql,convertcode); // 调用父类的查询方法返回结果到前台JSP(分页JSON)
	}
	//获取查询集装箱管理的SQL
	public String SearchBoxMana()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("数据包签名异常");			
		}
		String  ls_sql=basicDAO.SearchBoxMana(msgdata);
		return super.QueryDataBySql(ls_sql,convertcode); // 调用父类的查询方法返回结果到前台JSP(分页JSON)
	}
	
	
	//获取查询堆场的SQL
	public String SearchPlace()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("数据包签名异常");			
		}
		String  ls_sql=basicDAO.SearchPlace(msgdata);
		return super.QueryDataBySql(ls_sql,convertcode); // 调用父类的查询方法返回结果到前台JSP(分页JSON)
	}
	//增加堆场
	public String AddPlace()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("数据包签名异常");			
		}
		CommonDTO dto=basicDAO.AddPlace(msgdata);
		return PrintDTO(dto);
	}
	//修改堆场
	public String ModPlace()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("数据包签名异常");			
		}
		CommonDTO dto=basicDAO.ModPlace(msgdata);
		return PrintDTO(dto);
	}
	//删除堆场
	public String DelPlace()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("数据包签名异常");			
		}
		CommonDTO dto=basicDAO.DelPlace(msgdata);
		return PrintDTO(dto);
	}
	//获取查看堆场的SQL
	public String ViewPlace()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("数据包签名异常");			
		}
		String  ls_sql=basicDAO.ViewPlace(msgdata);
		return super.QueryDataBySql(ls_sql,convertcode); // 调用父类的查询方法返回结果到前台JSP(分页JSON)
	}
	//生成堆场箱位编号
	public String BuildPlaceBoxNo()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("数据包签名异常");			
		}
		CommonDTO dto=basicDAO.BuildPlaceBoxNo(msgdata);
		return PrintDTO(dto);
	}
	//获取查询堆场管理的SQL
	public String SearchPlaceMana()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("数据包签名异常");			
		}
		String  ls_sql=basicDAO.SearchPlaceMana(msgdata);
		return super.QueryDataBySql(ls_sql,convertcode); // 调用父类的查询方法返回结果到前台JSP(分页JSON)
	}
	//堆场箱位加锁
	public String LockPlaceBox()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("数据包签名异常");			
		}
		CommonDTO dto=basicDAO.LockPlaceBox(msgdata);
		return PrintDTO(dto);
	}
	//堆场箱位解锁
	public String UnLockPlaceBox()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("数据包签名异常");			
		}
		CommonDTO dto=basicDAO.UnLockPlaceBox(msgdata);
		return PrintDTO(dto);
	}
	//获取查询堆场箱位平面图的SQL
	public String SearchPlacePlan()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("数据包签名异常");			
		}
		String  ls_sql=basicDAO.SearchPlacePlan(msgdata);
		return super.QueryDataBySqlForAll(ls_sql,convertcode); // 调用父类的查询方法返回结果到前台JSP(分页JSON)
	}
	//获取查询堆场各层使用状态的SQL
	public String SearchPlaceLevelStatus()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("数据包签名异常");			
		}
		String  ls_sql=basicDAO.SearchPlaceLevelStatus(msgdata);
		return super.QueryDataBySqlForAll(ls_sql,convertcode); // 调用父类的查询方法返回结果到前台JSP(分页JSON)
	}

}
