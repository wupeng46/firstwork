package com.lbs.apps.basic.dao;

import com.lbs.apps.common.ApplicationException;
import com.lbs.apps.common.CommonDTO;

public interface BasicDAO {
	//获取查询委托方的SQL
	public String SearchMember(String msgdata)throws ApplicationException;
	//获取查询承运商的SQL
	public String SearchCompany(String msgdata)throws ApplicationException;
	//增加承运商
	public CommonDTO AddCompany(String msgdata)throws ApplicationException;
	//修改承运商
	public CommonDTO ModCompany(String msgdata)throws ApplicationException;
	//删除承运商
	public CommonDTO DelCompany(String msgdata)throws ApplicationException;
	//获取查看承运商的SQL
	public String ViewCompany(String msgdata)throws ApplicationException;
	
	//获取查询集装箱的SQL
	public String SearchBox(String msgdata)throws ApplicationException;
	//增加集装箱
	public CommonDTO AddBox(String msgdata)throws ApplicationException;
	//修改集装箱
	public CommonDTO ModBox(String msgdata)throws ApplicationException;
	//删除集装箱
	public CommonDTO DelBox(String msgdata)throws ApplicationException;
	//获取查看集装箱的SQL
	public String ViewBox(String msgdata)throws ApplicationException;
	//获取查询集装箱管理的SQL
	public String SearchBoxMana(String msgdata)throws ApplicationException;
	
	
	//获取查询堆场的SQL
	public String SearchPlace(String msgdata)throws ApplicationException;
	//增加堆场
	public CommonDTO AddPlace(String msgdata)throws ApplicationException;
	//修改堆场
	public CommonDTO ModPlace(String msgdata)throws ApplicationException;
	//删除堆场
	public CommonDTO DelPlace(String msgdata)throws ApplicationException;
	//获取查看堆场的SQL
	public String ViewPlace(String msgdata)throws ApplicationException;
	//生成堆场箱位编号
	public CommonDTO BuildPlaceBoxNo(String msgdata)throws ApplicationException;
	//获取查询堆场管理的SQL
	public String SearchPlaceMana(String msgdata)throws ApplicationException;
	//堆场箱位加锁
	public CommonDTO LockPlaceBox(String msgdata)throws ApplicationException;
	//堆场箱位解锁
	public CommonDTO UnLockPlaceBox(String msgdata)throws ApplicationException;
	//获取查询堆场箱位平面图的SQL
	public String SearchPlacePlan(String msgdata)throws ApplicationException;
	//获取查询堆场各层使用状态的SQL
	public String SearchPlaceLevelStatus(String msgdata)throws ApplicationException;

}
