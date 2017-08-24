package com.lbs.apps.finance.dao;

import com.lbs.apps.common.ApplicationException;
import com.lbs.apps.common.CommonDTO;

public interface FinanceDAO {
	//��ȡ���ᱨ���տ�ᵥ��SQL
	public String SearchReceivableToApply(String msgdata)throws ApplicationException;
	//�����ᱨ�տ�ᵥ
	public CommonDTO ReceivableApply(String msgdata)throws ApplicationException;
	//��ѯ���ᱨδȷ���տ�ᵥ
	public String SearchReceivableToConform(String msgdata)throws ApplicationException;
	//�տ�ȷ��
	public CommonDTO ReceivableConfirm(String msgdata)throws ApplicationException;
	//��ȡ��ѯ���ᱨδȷ���տ�ᵥ��SQL
	public String SearchReceivableApply(String msgdata)throws ApplicationException;
	//�����ᱨ�տ�ᵥȡ��
	public CommonDTO CancelReceivableApply(String msgdata)throws ApplicationException;
	//��ȡ��ѯ�������տ��¼��SQL
	public String SearchReceivableConfirm(String msgdata)throws ApplicationException;
	//ȡ���տ�ȷ��
	public CommonDTO CancelReceivableConfirm(String msgdata)throws ApplicationException;
	//��ȡ��ѯ�տ�ᵥ��SQL
	public String SearchReceivable(String msgdata)throws ApplicationException;
	
	//��ȡ���ᱨ�ĸ���ᵥ��SQL
	public String SearchPayableToApply(String msgdata)throws ApplicationException;
	//�����ᱨ����ᵥ
	public CommonDTO PayableApply(String msgdata)throws ApplicationException;
	//��ȡ��ѯ���ᱨδȷ�ϸ���ᵥ��SQL
	public String SearchPayableToConform(String msgdata)throws ApplicationException;
	//����ȷ��
	public CommonDTO PayableConfirm(String msgdata)throws ApplicationException;	
	//��ѯ���ᱨδȷ�ϸ���ᵥ
	public String SearchPayableApply(String msgdata)throws ApplicationException;
	//�����ᱨȡ��
	public CommonDTO CancelPayableApply(String msgdata)throws ApplicationException;
	//��ѯ�����Ѹ����¼
	public String SearchPayableConfirm(String msgdata)throws ApplicationException;
	//ȡ������ȷ��
	public CommonDTO CancelPayableConfirm(String msgdata)throws ApplicationException;
	//��ѯ����ᵥ
	public String SearchPayable(String msgdata)throws ApplicationException;

		

}
