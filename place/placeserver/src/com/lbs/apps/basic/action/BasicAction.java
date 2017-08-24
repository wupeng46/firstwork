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
	//����������ѯί�з�����
	public String SearchMember()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("���ݰ�ǩ���쳣");			
		}
		String  ls_sql=basicDAO.SearchMember(msgdata);
		return super.QueryDataBySql(ls_sql,convertcode); // ���ø���Ĳ�ѯ�������ؽ����ǰ̨JSP(��ҳJSON)
	}
	//����������ѯ����������
	public String SearchCompany()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("���ݰ�ǩ���쳣");			
		}
		String  ls_sql=basicDAO.SearchCompany(msgdata);
		return super.QueryDataBySql(ls_sql,convertcode); // ���ø���Ĳ�ѯ�������ؽ����ǰ̨JSP(��ҳJSON)
	}
	//���ӳ���������
	public String AddCompany()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("���ݰ�ǩ���쳣");			
		}
		CommonDTO dto=basicDAO.AddCompany(msgdata);
		return PrintDTO(dto);
	}
	
	//�޸ĳ���������
	public String ModCompany()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("���ݰ�ǩ���쳣");			
		}
		CommonDTO dto=basicDAO.ModCompany(msgdata);
		return PrintDTO(dto);
	}
	
	//ɾ������������
	public String DelCompany()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("���ݰ�ǩ���쳣");			
		}
		CommonDTO dto=basicDAO.DelCompany(msgdata);
		return PrintDTO(dto);
	}
	
	//���ݳ����̱�Ų�ѯ����������
	public String ViewCompany()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("���ݰ�ǩ���쳣");			
		}
		String  ls_sql=basicDAO.ViewCompany(msgdata);
		return super.QueryDataBySql(ls_sql,convertcode); // ���ø���Ĳ�ѯ�������ؽ����ǰ̨JSP(��ҳJSON)
	}
	
	
	//��ȡ��ѯ��װ���SQL
	public String SearchBox()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("���ݰ�ǩ���쳣");			
		}
		String  ls_sql=basicDAO.SearchBox(msgdata);
		return super.QueryDataBySql(ls_sql,convertcode); // ���ø���Ĳ�ѯ�������ؽ����ǰ̨JSP(��ҳJSON)
	}
	//���Ӽ�װ��
	public String AddBox()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("���ݰ�ǩ���쳣");			
		}
		CommonDTO dto=basicDAO.AddBox(msgdata);
		return PrintDTO(dto);
	}
	//�޸ļ�װ��
	public String ModBox()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("���ݰ�ǩ���쳣");			
		}
		CommonDTO dto=basicDAO.ModBox(msgdata);
		return PrintDTO(dto);
	}
	//ɾ����װ��
	public String DelBox()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("���ݰ�ǩ���쳣");			
		}
		CommonDTO dto=basicDAO.DelBox(msgdata);
		return PrintDTO(dto);
	}
	//��ȡ�鿴��װ���SQL
	public String ViewBox()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("���ݰ�ǩ���쳣");			
		}
		String  ls_sql=basicDAO.ViewBox(msgdata);
		return super.QueryDataBySql(ls_sql,convertcode); // ���ø���Ĳ�ѯ�������ؽ����ǰ̨JSP(��ҳJSON)
	}
	//��ȡ��ѯ��װ������SQL
	public String SearchBoxMana()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("���ݰ�ǩ���쳣");			
		}
		String  ls_sql=basicDAO.SearchBoxMana(msgdata);
		return super.QueryDataBySql(ls_sql,convertcode); // ���ø���Ĳ�ѯ�������ؽ����ǰ̨JSP(��ҳJSON)
	}
	
	
	//��ȡ��ѯ�ѳ���SQL
	public String SearchPlace()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("���ݰ�ǩ���쳣");			
		}
		String  ls_sql=basicDAO.SearchPlace(msgdata);
		return super.QueryDataBySql(ls_sql,convertcode); // ���ø���Ĳ�ѯ�������ؽ����ǰ̨JSP(��ҳJSON)
	}
	//���Ӷѳ�
	public String AddPlace()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("���ݰ�ǩ���쳣");			
		}
		CommonDTO dto=basicDAO.AddPlace(msgdata);
		return PrintDTO(dto);
	}
	//�޸Ķѳ�
	public String ModPlace()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("���ݰ�ǩ���쳣");			
		}
		CommonDTO dto=basicDAO.ModPlace(msgdata);
		return PrintDTO(dto);
	}
	//ɾ���ѳ�
	public String DelPlace()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("���ݰ�ǩ���쳣");			
		}
		CommonDTO dto=basicDAO.DelPlace(msgdata);
		return PrintDTO(dto);
	}
	//��ȡ�鿴�ѳ���SQL
	public String ViewPlace()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("���ݰ�ǩ���쳣");			
		}
		String  ls_sql=basicDAO.ViewPlace(msgdata);
		return super.QueryDataBySql(ls_sql,convertcode); // ���ø���Ĳ�ѯ�������ؽ����ǰ̨JSP(��ҳJSON)
	}
	//���ɶѳ���λ���
	public String BuildPlaceBoxNo()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("���ݰ�ǩ���쳣");			
		}
		CommonDTO dto=basicDAO.BuildPlaceBoxNo(msgdata);
		return PrintDTO(dto);
	}
	//��ȡ��ѯ�ѳ������SQL
	public String SearchPlaceMana()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("���ݰ�ǩ���쳣");			
		}
		String  ls_sql=basicDAO.SearchPlaceMana(msgdata);
		return super.QueryDataBySql(ls_sql,convertcode); // ���ø���Ĳ�ѯ�������ؽ����ǰ̨JSP(��ҳJSON)
	}
	//�ѳ���λ����
	public String LockPlaceBox()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("���ݰ�ǩ���쳣");			
		}
		CommonDTO dto=basicDAO.LockPlaceBox(msgdata);
		return PrintDTO(dto);
	}
	//�ѳ���λ����
	public String UnLockPlaceBox()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("���ݰ�ǩ���쳣");			
		}
		CommonDTO dto=basicDAO.UnLockPlaceBox(msgdata);
		return PrintDTO(dto);
	}
	//��ȡ��ѯ�ѳ���λƽ��ͼ��SQL
	public String SearchPlacePlan()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("���ݰ�ǩ���쳣");			
		}
		String  ls_sql=basicDAO.SearchPlacePlan(msgdata);
		return super.QueryDataBySqlForAll(ls_sql,convertcode); // ���ø���Ĳ�ѯ�������ؽ����ǰ̨JSP(��ҳJSON)
	}
	//��ȡ��ѯ�ѳ�����ʹ��״̬��SQL
	public String SearchPlaceLevelStatus()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("���ݰ�ǩ���쳣");			
		}
		String  ls_sql=basicDAO.SearchPlaceLevelStatus(msgdata);
		return super.QueryDataBySqlForAll(ls_sql,convertcode); // ���ø���Ĳ�ѯ�������ؽ����ǰ̨JSP(��ҳJSON)
	}

}
