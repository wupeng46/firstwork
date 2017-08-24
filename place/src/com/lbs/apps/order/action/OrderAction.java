package com.lbs.apps.order.action;

import com.lbs.apps.common.ApplicationException;
import com.lbs.apps.common.CommonDTO;
import com.lbs.apps.common.CommonQueryAction;
import com.lbs.apps.order.dao.OrderDAO;
import com.lbs.apps.order.dao.ReturnDTO;

public class OrderAction extends CommonQueryAction {
	private OrderDAO orderDAO;

	public OrderDAO getOrderDAO() {
		return orderDAO;
	}

	public void setOrderDAO(OrderDAO orderDAO) {
		this.orderDAO = orderDAO;
	}
	
	//��ѯ����
	public String SearchOrder()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("���ݰ�ǩ���쳣");			
		}
		String  ls_sql=orderDAO.SearchOrder(msgdata);
		return super.QueryDataBySql(ls_sql,convertcode); // ���ø���Ĳ�ѯ�������ؽ����ǰ̨JSP(��ҳJSON)		
	}
	//�������¶���
	public String AddOrder()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("���ݰ�ǩ���쳣");			
		}
		ReturnDTO dto=orderDAO.AddOrder(msgdata);
		return PrintDTO(dto);
	}
	//�޸Ķ���
	public String ModOrder()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("���ݰ�ǩ���쳣");			
		}
		CommonDTO dto=orderDAO.ModOrder(msgdata);
		return PrintDTO(dto);
	}
	//�鿴����
	public String ViewOrder()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("���ݰ�ǩ���쳣");			
		}
		String  ls_sql=orderDAO.ViewOrder(msgdata);
		return super.QueryDataBySql(ls_sql,convertcode); // ���ø���Ĳ�ѯ�������ؽ����ǰ̨JSP(��ҳJSON)
	}
	//��ȡ�����³������б�
	public String SearchOrderCompany()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("���ݰ�ǩ���쳣");			
		}
		String  ls_sql=orderDAO.SearchOrderCompany(msgdata);
		return super.QueryDataBySql(ls_sql,convertcode); // ���ø���Ĳ�ѯ�������ؽ����ǰ̨JSP(��ҳJSON)
	}
	//��ѯ�����ڵĶ���
	public String SearchOrderToPlan()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("���ݰ�ǩ���쳣");			
		}
		String  ls_sql=orderDAO.SearchOrderToPlan(msgdata);
		return super.QueryDataBySql(ls_sql,convertcode); // ���ø���Ĳ�ѯ�������ؽ����ǰ̨JSP(��ҳJSON)
	}
	//���涩�����ڼƻ�
	public String SaveOrderPlan()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("���ݰ�ǩ���쳣");			
		}
		CommonDTO dto=orderDAO.SaveOrderPlan(msgdata);
		return PrintDTO(dto);
	}
	//�޸Ķ�������
	public String ModOrderPlan()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("���ݰ�ǩ���쳣");			
		}
		CommonDTO dto=orderDAO.ModOrderPlan(msgdata);
		return PrintDTO(dto);
	}
	//�������
	public String OrderFinish()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("���ݰ�ǩ���쳣");			
		}
		CommonDTO dto=orderDAO.OrderFinish(msgdata);
		return PrintDTO(dto);
	}
	//��ѯ�������ڼƻ�
	public String SearchOrderPlan()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("���ݰ�ǩ���쳣");			
		}
		String  ls_sql=orderDAO.SearchOrderPlan(msgdata);
		return super.QueryDataBySql(ls_sql,convertcode); // ���ø���Ĳ�ѯ�������ؽ����ǰ̨JSP(��ҳJSON)
	}
	//�鿴�������ڼƻ�
	public String ViewOrderPlan()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("���ݰ�ǩ���쳣");			
		}
		String  ls_sql=orderDAO.ViewOrderPlan(msgdata);
		return super.QueryDataBySql(ls_sql,convertcode); // ���ø���Ĳ�ѯ�������ؽ����ǰ̨JSP(��ҳJSON)
	}
	//�鿴��ɽ���
	public String ViewOrderProcess()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("���ݰ�ǩ���쳣");			
		}
		String  ls_sql=orderDAO.ViewOrderProcess(msgdata);
		return super.QueryDataBySql(ls_sql,convertcode); // ���ø���Ĳ�ѯ�������ؽ����ǰ̨JSP(��ҳJSON)
	}
	
	//��ѯ���볡�Ĺ�����
	public String SearchBoxToInPlace()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("���ݰ�ǩ���쳣");			
		}
		String  ls_sql=orderDAO.SearchBoxToInPlace(msgdata);
		return super.QueryDataBySql(ls_sql,convertcode); // ���ø���Ĳ�ѯ�������ؽ����ǰ̨JSP(��ҳJSON)
	}
	//���������볡ȷ��
	public String BoxInPlaceConfirm()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("���ݰ�ǩ���쳣");			
		}
		CommonDTO dto=orderDAO.BoxInPlaceConfirm(msgdata);
		return PrintDTO(dto);
	}
	//��ѯ�������Ĺ�����
	public String SearchBoxToOutPlace()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("���ݰ�ǩ���쳣");			
		}
		String  ls_sql=orderDAO.SearchBoxToOutPlace(msgdata);
		return super.QueryDataBySql(ls_sql,convertcode); // ���ø���Ĳ�ѯ�������ؽ����ǰ̨JSP(��ҳJSON)
	}
	//������������ȷ��
	public String BoxOutPlaceConfirm()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("���ݰ�ǩ���쳣");			
		}
		CommonDTO dto=orderDAO.BoxOutPlaceConfirm(msgdata);
		return PrintDTO(dto);
	}
	//��ѯ����԰�Ĺ�����
	public String SearchBoxToOutPark()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("���ݰ�ǩ���쳣");			
		}
		String  ls_sql=orderDAO.SearchBoxToOutPark(msgdata);
		return super.QueryDataBySql(ls_sql,convertcode); // ���ø���Ĳ�ѯ�������ؽ����ǰ̨JSP(��ҳJSON)
	}
	//����������԰ȷ��
	public String BoxOutParkConfirm()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("���ݰ�ǩ���쳣");			
		}
		CommonDTO dto=orderDAO.BoxOutParkConfirm(msgdata);
		return PrintDTO(dto);
	}
	//�����ⲿ������
	public String SaveOutWeigh()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("���ݰ�ǩ���쳣");			
		}
		CommonDTO dto=orderDAO.SaveOutWeigh(msgdata);
		return PrintDTO(dto);
	}	
	//��ѯ������
	public String SearchWeigh()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("���ݰ�ǩ���쳣");			
		}
		String  ls_sql=orderDAO.SearchWeigh(msgdata);
		return super.QueryDataBySql(ls_sql,convertcode); // ���ø���Ĳ�ѯ�������ؽ����ǰ̨JSP(��ҳJSON)
	}
	//�鿴������
	public String ViewWeigh()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("���ݰ�ǩ���쳣");			
		}
		String  ls_sql=orderDAO.ViewWeigh(msgdata);
		return super.QueryDataBySql(ls_sql,convertcode); // ���ø���Ĳ�ѯ�������ؽ����ǰ̨JSP(��ҳJSON)
	}
	//��ѯ���볡����
	public String SearchInPlaceWeigh()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("���ݰ�ǩ���쳣");			
		}
		String  ls_sql=orderDAO.SearchInPlaceWeigh(msgdata);
		return super.QueryDataBySql(ls_sql,convertcode); // ���ø���Ĳ�ѯ�������ؽ����ǰ̨JSP(��ҳJSON)
	}
	//ȡ�����볡������
	public String CancelInPlaceWeight()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("���ݰ�ǩ���쳣");			
		}
		CommonDTO dto=orderDAO.CancelInPlaceWeight(msgdata);
		return PrintDTO(dto);
	}
	//��ѯ�ѳ���������
	public String SearchOutPlaceWeigh()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("���ݰ�ǩ���쳣");			
		}
		String  ls_sql=orderDAO.SearchOutPlaceWeigh(msgdata);
		return super.QueryDataBySql(ls_sql,convertcode); // ���ø���Ĳ�ѯ�������ؽ����ǰ̨JSP(��ҳJSON)
	}
	//ȡ���ѳ���������
	public String CancelOutPlaceWeight()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("���ݰ�ǩ���쳣");			
		}
		CommonDTO dto=orderDAO.CancelOutPlaceWeight(msgdata);
		return PrintDTO(dto);
	}	
	//��ѯ�ѳ�԰������
	public String SearchOutParkWeigh()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("���ݰ�ǩ���쳣");			
		}
		String  ls_sql=orderDAO.SearchOutParkWeigh(msgdata);
		return super.QueryDataBySql(ls_sql,convertcode); // ���ø���Ĳ�ѯ�������ؽ����ǰ̨JSP(��ҳJSON)
	}
	//ȡ���ѳ�԰������
	public String CancelOutParkWeight()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("���ݰ�ǩ���쳣");			
		}
		CommonDTO dto=orderDAO.CancelOutParkWeight(msgdata);
		return PrintDTO(dto);
	}
	
	//��ѯδ��ɵĶ���
	public String SearchNoFinishOrder()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("���ݰ�ǩ���쳣");			
		}
		String  ls_sql=orderDAO.SearchNoFinishOrder(msgdata);
		return super.QueryDataBySql(ls_sql,convertcode); // ���ø���Ĳ�ѯ�������ؽ����ǰ̨JSP(��ҳJSON)
	}
	//�����˵���Ϣ
	public String AddWaybill()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("���ݰ�ǩ���쳣");			
		}
		CommonDTO dto=orderDAO.AddWaybill(msgdata);
		return PrintDTO(dto);
	}
	//��ѯ�����˵���Ϣ
	public String SearchWaybill()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("���ݰ�ǩ���쳣");			
		}
		String  ls_sql=orderDAO.SearchWaybill(msgdata);
		return super.QueryDataBySql(ls_sql,convertcode); // ���ø���Ĳ�ѯ�������ؽ����ǰ̨JSP(��ҳJSON)
	}
	//��ѯ�����˵���ϸ
	public String SearchWaybillDetail()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("���ݰ�ǩ���쳣");			
		}
		String  ls_sql=orderDAO.SearchWaybillDetail(msgdata);
		return super.QueryDataBySql(ls_sql,convertcode); // ���ø���Ĳ�ѯ�������ؽ����ǰ̨JSP(��ҳJSON)
	}
	//�鿴�����˵�����
	public String ViewWaybill()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("���ݰ�ǩ���쳣");			
		}
		String  ls_sql=orderDAO.ViewWaybill(msgdata);
		return super.QueryDataBySql(ls_sql,convertcode); // ���ø���Ĳ�ѯ�������ؽ����ǰ̨JSP(��ҳJSON)
	}
	//�޸������˵���Ϣ
	public String ModWaybill()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("���ݰ�ǩ���쳣");			
		}
		CommonDTO dto=orderDAO.ModWaybill(msgdata);
		return PrintDTO(dto);
	}
	//�˵����
	public String FinishWaybill()throws ApplicationException{
		if (!super.CheckMsgdata()){
			return super.QueryException("���ݰ�ǩ���쳣");			
		}
		CommonDTO dto=orderDAO.FinishWaybill(msgdata);
		return PrintDTO(dto);
	}
	
	//������������
	public String ImportOrder()throws ApplicationException{
		//����ӿڲ����ü�����ͨѶ
		/*
		if (!super.CheckMsgdata()){
			return super.QueryException("���ݰ�ǩ���쳣");			
		}
		*/
		ReturnDTO dto=orderDAO.ImportOrder(msgdata);
		return PrintDTO(dto);
	}

}
