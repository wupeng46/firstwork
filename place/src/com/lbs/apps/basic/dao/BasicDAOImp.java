package com.lbs.apps.basic.dao;

import com.lbs.apps.common.ApplicationException;
import com.lbs.apps.common.CommonDTO;

public class BasicDAOImp implements BasicDAO {

	@Override
	public String SearchMember(String msgdata) throws ApplicationException {
		CompanyBPO bpo=new CompanyBPO();
		return bpo.SearchMember(msgdata);
	}
	@Override
	public String SearchCompany(String msgdata) throws ApplicationException {
		CompanyBPO bpo=new CompanyBPO();
		return bpo.SearchCompany(msgdata);
	}

	@Override
	public CommonDTO AddCompany(String msgdata) throws ApplicationException {
		CompanyBPO bpo=new CompanyBPO();
		return bpo.AddCompany(msgdata);
	}

	@Override
	public CommonDTO ModCompany(String msgdata) throws ApplicationException {
		CompanyBPO bpo=new CompanyBPO();
		return bpo.ModCompany(msgdata);
	}

	@Override
	public CommonDTO DelCompany(String msgdata) throws ApplicationException {
		CompanyBPO bpo=new CompanyBPO();
		return bpo.DelCompany(msgdata);
	}

	@Override
	public String ViewCompany(String msgdata) throws ApplicationException {
		CompanyBPO bpo=new CompanyBPO();
		return bpo.ViewCompany(msgdata);
	}

	@Override
	public String SearchBox(String msgdata) throws ApplicationException {
		BoxBPO bpo=new BoxBPO();
		return bpo.SearchBox(msgdata);
	}

	@Override
	public CommonDTO AddBox(String msgdata) throws ApplicationException {
		BoxBPO bpo=new BoxBPO();
		return bpo.AddBox(msgdata);
	}

	@Override
	public CommonDTO ModBox(String msgdata) throws ApplicationException {
		BoxBPO bpo=new BoxBPO();
		return bpo.ModBox(msgdata);
	}

	@Override
	public CommonDTO DelBox(String msgdata) throws ApplicationException {
		BoxBPO bpo=new BoxBPO();
		return bpo.DelBox(msgdata);
	}

	@Override
	public String ViewBox(String msgdata) throws ApplicationException {
		BoxBPO bpo=new BoxBPO();
		return bpo.ViewBox(msgdata);
	}

	@Override
	public String SearchBoxMana(String msgdata) throws ApplicationException {
		BoxBPO bpo=new BoxBPO();
		return bpo.SearchBoxMana(msgdata);
	}

	@Override
	public String SearchPlace(String msgdata) throws ApplicationException {
		PlaceBPO bpo=new PlaceBPO();
		return bpo.SearchPlace(msgdata);
	}

	@Override
	public CommonDTO AddPlace(String msgdata) throws ApplicationException {
		PlaceBPO bpo=new PlaceBPO();
		return bpo.AddPlace(msgdata);
	}

	@Override
	public CommonDTO ModPlace(String msgdata) throws ApplicationException {
		PlaceBPO bpo=new PlaceBPO();
		return bpo.ModPlace(msgdata);
	}

	@Override
	public CommonDTO DelPlace(String msgdata) throws ApplicationException {
		PlaceBPO bpo=new PlaceBPO();
		return bpo.DelPlace(msgdata);
	}

	@Override
	public String ViewPlace(String msgdata) throws ApplicationException {
		PlaceBPO bpo=new PlaceBPO();
		return bpo.ViewPlace(msgdata);
	}

	@Override
	public CommonDTO BuildPlaceBoxNo(String msgdata)
			throws ApplicationException {
		PlaceBPO bpo=new PlaceBPO();
		return bpo.BuildPlaceBoxNo(msgdata);
	}

	@Override
	public String SearchPlaceMana(String msgdata) throws ApplicationException {
		PlaceBPO bpo=new PlaceBPO();
		return bpo.SearchPlaceMana(msgdata);
	}

	@Override
	public CommonDTO LockPlaceBox(String msgdata) throws ApplicationException {
		PlaceBPO bpo=new PlaceBPO();
		return bpo.LockPlaceBox(msgdata);
	}

	@Override
	public CommonDTO UnLockPlaceBox(String msgdata) throws ApplicationException {
		PlaceBPO bpo=new PlaceBPO();
		return bpo.UnLockPlaceBox(msgdata);
	}

	@Override
	public String SearchPlacePlan(String msgdata) throws ApplicationException {
		PlaceBPO bpo=new PlaceBPO();
		return bpo.SearchPlacePlan(msgdata);
	}

	@Override
	public String SearchPlaceLevelStatus(String msgdata)
			throws ApplicationException {
		PlaceBPO bpo=new PlaceBPO();
		return bpo.SearchPlaceLevelStatus(msgdata);
	}

}
