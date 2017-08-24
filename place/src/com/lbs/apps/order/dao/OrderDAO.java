package com.lbs.apps.order.dao;

import com.lbs.apps.common.ApplicationException;
import com.lbs.apps.common.CommonDTO;

public interface OrderDAO {	
	//��ѯ����
	public String SearchOrder(String msgdata)throws ApplicationException;
	//�������¶���
	public ReturnDTO AddOrder(String msgdata)throws ApplicationException;
	//�޸Ķ���
	public CommonDTO ModOrder(String msgdata)throws ApplicationException;
	//�鿴����
	public String ViewOrder(String msgdata)throws ApplicationException;
	//��ȡ�����³������б�
	public String SearchOrderCompany(String msgdata) throws ApplicationException;
	//��ѯ�����ڵĶ���
	public String SearchOrderToPlan(String msgdata)throws ApplicationException;
	//���涩�����ڼƻ�
	public CommonDTO SaveOrderPlan(String msgdata)throws ApplicationException;
	//�޸Ķ�������
	public CommonDTO ModOrderPlan(String msgdata)throws ApplicationException;
	//�������
	public CommonDTO OrderFinish(String msgdata)throws ApplicationException;
	//��ѯ�������ڼƻ�
	public String SearchOrderPlan(String msgdata)throws ApplicationException;
	//�鿴�������ڼƻ�
	public String ViewOrderPlan(String msgdata)throws ApplicationException;
	//�鿴��ɽ���
	public String ViewOrderProcess(String msgdata)throws ApplicationException;	
	
	//��ѯ���볡�Ĺ�����
	public String SearchBoxToInPlace(String msgdata)throws ApplicationException;
	//���������볡ȷ��
	public CommonDTO BoxInPlaceConfirm(String msgdata)throws ApplicationException;
	//��ѯ�������Ĺ�����
	public String SearchBoxToOutPlace(String msgdata)throws ApplicationException;
	//������������ȷ��
	public CommonDTO BoxOutPlaceConfirm(String msgdata)throws ApplicationException;	
	//��ѯ����԰�Ĺ�����
	public String SearchBoxToOutPark(String msgdata)throws ApplicationException;
	//����������԰ȷ��
	public CommonDTO BoxOutParkConfirm(String msgdata)throws ApplicationException;
	//�����ⲿ������
	public CommonDTO SaveOutWeigh(String msgdata)throws ApplicationException;	
	//��ѯ������
	public String SearchWeigh(String msgdata)throws ApplicationException;
	//�鿴������
	public String ViewWeigh(String msgdata)throws ApplicationException;	
	//��ѯ���볡����
	public String SearchInPlaceWeigh(String msgdata)throws ApplicationException;
	//ȡ�����볡������
	public CommonDTO CancelInPlaceWeight(String msgdata)throws ApplicationException;	
	//��ѯ�ѳ���������
	public String SearchOutPlaceWeigh(String msgdata)throws ApplicationException;
	//ȡ���ѳ���������
	public CommonDTO CancelOutPlaceWeight(String msgdata)throws ApplicationException;	
	//��ѯ�ѳ�԰������
	public String SearchOutParkWeigh(String msgdata)throws ApplicationException;
	//ȡ���ѳ�԰������
	public CommonDTO CancelOutParkWeight(String msgdata)throws ApplicationException;
	
	//��ѯδ��ɵĶ���
	public String SearchNoFinishOrder(String msgdata)throws ApplicationException;
	//�����˵���Ϣ
	public CommonDTO AddWaybill(String msgdata)throws ApplicationException;
	//��ѯ�����˵���Ϣ
	public String SearchWaybill(String msgdata)throws ApplicationException;	
	//��ѯ�����˵���ϸ
	public String SearchWaybillDetail(String msgdata)throws ApplicationException;
	//�鿴�����˵�����
	public String ViewWaybill(String msgdata)throws ApplicationException;
	//�޸������˵���Ϣ
	public CommonDTO ModWaybill(String msgdata)throws ApplicationException;
	//�˵����
	public CommonDTO FinishWaybill(String msgdata)throws ApplicationException;
    //������������
	public ReturnDTO ImportOrder(String msgdata) throws ApplicationException;

}
