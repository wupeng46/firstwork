package com.lbs.apps.emptybox.action;

import com.lbs.apps.common.ApplicationException;
import com.lbs.apps.common.CommonDTO;
import com.lbs.apps.common.CommonQueryAction;
import com.lbs.apps.emptybox.dao.EmptyboxDAO;

public class EmptyboxAction extends CommonQueryAction {
	private EmptyboxDAO emptyboxDAO;
	
	public EmptyboxDAO getEmptyboxDAO() {
		return emptyboxDAO;
	}


	public void setEmptyboxDAO(EmptyboxDAO emptyboxDAO) {
		this.emptyboxDAO = emptyboxDAO;
	}


	public String EmptyBoxInPark() throws ApplicationException {
		if (!super.CheckMsgdata()){
			return super.QueryException("���ݰ�ǩ���쳣");			
		}
		CommonDTO dto=emptyboxDAO.EmptyBoxInPark(msgdata);
		return PrintDTO(dto);		
	}

	
	public String EmptyBoxInPlace()throws ApplicationException {
		if (!super.CheckMsgdata()){
			return super.QueryException("���ݰ�ǩ���쳣");			
		}
		CommonDTO dto=emptyboxDAO.EmptyBoxInPlace(msgdata);
		return PrintDTO(dto);	
	}

	
	public String EmptyBoxOutPlace()throws ApplicationException {
		if (!super.CheckMsgdata()){
			return super.QueryException("���ݰ�ǩ���쳣");			
		}
		CommonDTO dto=emptyboxDAO.EmptyBoxOutPlace(msgdata);
		return PrintDTO(dto);	
	}

	
	public String EmptyBoxOutPark()throws ApplicationException {
		if (!super.CheckMsgdata()){
			return super.QueryException("���ݰ�ǩ���쳣");			
		}
		CommonDTO dto=emptyboxDAO.EmptyBoxOutPark(msgdata);
		return PrintDTO(dto);	
	}

	
	public String SearchEmptyBoxWarm()throws ApplicationException {
		if (!super.CheckMsgdata()){
			return super.QueryException("���ݰ�ǩ���쳣");			
		}
		String  ls_sql=emptyboxDAO.SearchEmptyBoxWarm(msgdata);
		return super.QueryDataBySql(ls_sql,convertcode); // ���ø���Ĳ�ѯ�������ؽ����ǰ̨JSP(��ҳJSON)
	}

}
