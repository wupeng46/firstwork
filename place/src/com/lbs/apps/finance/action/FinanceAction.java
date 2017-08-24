package com.lbs.apps.finance.action;

import com.lbs.apps.common.ApplicationException;
import com.lbs.apps.common.CommonDTO;
import com.lbs.apps.common.CommonQueryAction;
import com.lbs.apps.finance.dao.FinanceDAO;

public class FinanceAction extends CommonQueryAction {
	private FinanceDAO financeDAO;

	public FinanceDAO getFinanceDAO() {
		return financeDAO;
	}

	public void setFinanceDAO(FinanceDAO financeDAO) {
		this.financeDAO = financeDAO;
	}
	
	//��ȡ���ᱨ���տ�ᵥ��SQL
	public String SearchReceivableToApply()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("���ݰ�ǩ���쳣");			
		}
		String  ls_sql=financeDAO.SearchReceivableToApply(msgdata);
		return super.QueryDataBySql(ls_sql,convertcode); // ���ø���Ĳ�ѯ�������ؽ����ǰ̨JSP(��ҳJSON)
	}
	//�����ᱨ�տ�ᵥ
	public String ReceivableApply()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("���ݰ�ǩ���쳣");			
		}
		CommonDTO dto=financeDAO.ReceivableApply(msgdata);
		return PrintDTO(dto);
	}
	//��ѯ���ᱨδȷ���տ�ᵥ
	public String SearchReceivableToConform()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("���ݰ�ǩ���쳣");			
		}
		String  ls_sql=financeDAO.SearchReceivableToConform(msgdata);
		return super.QueryDataBySql(ls_sql,convertcode); // ���ø���Ĳ�ѯ�������ؽ����ǰ̨JSP(��ҳJSON)
	}
	//�տ�ȷ��
	public String ReceivableConfirm()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("���ݰ�ǩ���쳣");			
		}
		CommonDTO dto=financeDAO.ReceivableConfirm(msgdata);
		return PrintDTO(dto);
	}
	//��ȡ��ѯ���ᱨδȷ���տ�ᵥ��SQL
	public String SearchReceivableApply()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("���ݰ�ǩ���쳣");			
		}
		String  ls_sql=financeDAO.SearchReceivableApply(msgdata);
		return super.QueryDataBySql(ls_sql,convertcode); // ���ø���Ĳ�ѯ�������ؽ����ǰ̨JSP(��ҳJSON)
	}
	//�����ᱨ�տ�ᵥȡ��
	public String CancelReceivableApply()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("���ݰ�ǩ���쳣");			
		}
		CommonDTO dto=financeDAO.CancelReceivableApply(msgdata);
		return PrintDTO(dto);
	}
	//��ȡ��ѯ�������տ��¼��SQL
	public String SearchReceivableConfirm()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("���ݰ�ǩ���쳣");			
		}
		String  ls_sql=financeDAO.SearchReceivableConfirm(msgdata);
		return super.QueryDataBySql(ls_sql,convertcode); // ���ø���Ĳ�ѯ�������ؽ����ǰ̨JSP(��ҳJSON)
    }
	//ȡ���տ�ȷ��
	public String CancelReceivableConfirm()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("���ݰ�ǩ���쳣");			
		}
		CommonDTO dto=financeDAO.CancelReceivableConfirm(msgdata);
		return PrintDTO(dto);
	}
	//��ȡ��ѯ�տ�ᵥ��SQL
	public String SearchReceivable()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("���ݰ�ǩ���쳣");			
		}
		String  ls_sql=financeDAO.SearchReceivable(msgdata);
		return super.QueryDataBySql(ls_sql,convertcode); // ���ø���Ĳ�ѯ�������ؽ����ǰ̨JSP(��ҳJSON)
	}
	
	//��ȡ���ᱨ�ĸ���ᵥ��SQL
	public String SearchPayableToApply()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("���ݰ�ǩ���쳣");			
		}
		String  ls_sql=financeDAO.SearchPayableToApply(msgdata);
		return super.QueryDataBySql(ls_sql,convertcode); // ���ø���Ĳ�ѯ�������ؽ����ǰ̨JSP(��ҳJSON)
	}
	//�����ᱨ����ᵥ
	public String PayableApply()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("���ݰ�ǩ���쳣");			
		}
		CommonDTO dto=financeDAO.PayableApply(msgdata);
		return PrintDTO(dto);
	}
	//��ȡ��ѯ���ᱨδȷ�ϸ���ᵥ��SQL
	public String SearchPayableToConform()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("���ݰ�ǩ���쳣");			
		}
		String  ls_sql=financeDAO.SearchPayableToConform(msgdata);
		return super.QueryDataBySql(ls_sql,convertcode); // ���ø���Ĳ�ѯ�������ؽ����ǰ̨JSP(��ҳJSON)
	}
	//����ȷ��
	public String PayableConfirm()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("���ݰ�ǩ���쳣");			
		}
		CommonDTO dto=financeDAO.PayableConfirm(msgdata);
		return PrintDTO(dto);
	}	
	//��ѯ���ᱨδȷ�ϸ���ᵥ
	public String SearchPayableApply()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("���ݰ�ǩ���쳣");			
		}
		String  ls_sql=financeDAO.SearchPayableApply(msgdata);
		return super.QueryDataBySql(ls_sql,convertcode); // ���ø���Ĳ�ѯ�������ؽ����ǰ̨JSP(��ҳJSON)
	}
	//�����ᱨȡ��
	public String CancelPayableApply()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("���ݰ�ǩ���쳣");			
		}
		CommonDTO dto=financeDAO.CancelPayableApply(msgdata);
		return PrintDTO(dto);
	}
	//��ѯ�����Ѹ����¼
	public String SearchPayableConfirm()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("���ݰ�ǩ���쳣");			
		}
		String  ls_sql=financeDAO.SearchPayableConfirm(msgdata);
		return super.QueryDataBySql(ls_sql,convertcode); // ���ø���Ĳ�ѯ�������ؽ����ǰ̨JSP(��ҳJSON)
	}
	//ȡ������ȷ��
	public String CancelPayableConfirm()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("���ݰ�ǩ���쳣");			
		}
		CommonDTO dto=financeDAO.CancelPayableConfirm(msgdata);
		return PrintDTO(dto);
	}
	//��ѯ����ᵥ
	public String SearchPayable()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("���ݰ�ǩ���쳣");			
		}
		String  ls_sql=financeDAO.SearchPayable(msgdata);
		return super.QueryDataBySql(ls_sql,convertcode); // ���ø���Ĳ�ѯ�������ؽ����ǰ̨JSP(��ҳJSON)
	}

}
