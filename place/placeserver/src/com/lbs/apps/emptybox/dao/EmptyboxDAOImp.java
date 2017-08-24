package com.lbs.apps.emptybox.dao;

import com.lbs.apps.common.ApplicationException;
import com.lbs.apps.common.CommonDTO;

public class EmptyboxDAOImp implements EmptyboxDAO {

	@Override
	public CommonDTO EmptyBoxInPark(String msgdata) throws ApplicationException {
		EmptyboxBPO bpo=new EmptyboxBPO();
		return bpo.EmptyBoxInPark(msgdata);
	}

	@Override
	public CommonDTO EmptyBoxInPlace(String msgdata)
			throws ApplicationException {
		EmptyboxBPO bpo=new EmptyboxBPO();
		return bpo.EmptyBoxInPlace(msgdata);
	}

	@Override
	public CommonDTO EmptyBoxOutPlace(String msgdata)
			throws ApplicationException {
		EmptyboxBPO bpo=new EmptyboxBPO();
		return bpo.EmptyBoxOutPlace(msgdata);
	}

	@Override
	public CommonDTO EmptyBoxOutPark(String msgdata)
			throws ApplicationException {
		EmptyboxBPO bpo=new EmptyboxBPO();
		return bpo.EmptyBoxOutPark(msgdata);
	}

	@Override
	public String SearchEmptyBoxWarm(String msgdata)
			throws ApplicationException {
		EmptyboxBPO bpo=new EmptyboxBPO();
		return bpo.SearchEmptyBoxWarm(msgdata);
	}

}
