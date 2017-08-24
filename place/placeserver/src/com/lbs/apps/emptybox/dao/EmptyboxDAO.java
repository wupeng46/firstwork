package com.lbs.apps.emptybox.dao;

import com.lbs.apps.common.ApplicationException;
import com.lbs.apps.common.CommonDTO;

public interface EmptyboxDAO {
	//空箱入园登记
	public CommonDTO EmptyBoxInPark(String msgdata)throws ApplicationException;
	//空箱入场登记
	public CommonDTO EmptyBoxInPlace(String msgdata)throws ApplicationException;
	//空箱出场登记
	public CommonDTO EmptyBoxOutPlace(String msgdata)throws ApplicationException;
	//空箱出园登记
	public CommonDTO EmptyBoxOutPark(String msgdata)throws ApplicationException;
	//空箱预警
	public String SearchEmptyBoxWarm(String msgdata)throws ApplicationException;
	

}
