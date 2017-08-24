package com.lbs.apps.basic.dao;

import com.lbs.apps.common.ApplicationException;
import com.lbs.apps.common.CommonDTO;

public interface BasicDAO {
	//��ȡ��ѯί�з���SQL
	public String SearchMember(String msgdata)throws ApplicationException;
	//��ȡ��ѯ�����̵�SQL
	public String SearchCompany(String msgdata)throws ApplicationException;
	//���ӳ�����
	public CommonDTO AddCompany(String msgdata)throws ApplicationException;
	//�޸ĳ�����
	public CommonDTO ModCompany(String msgdata)throws ApplicationException;
	//ɾ��������
	public CommonDTO DelCompany(String msgdata)throws ApplicationException;
	//��ȡ�鿴�����̵�SQL
	public String ViewCompany(String msgdata)throws ApplicationException;
	
	//��ȡ��ѯ��װ���SQL
	public String SearchBox(String msgdata)throws ApplicationException;
	//���Ӽ�װ��
	public CommonDTO AddBox(String msgdata)throws ApplicationException;
	//�޸ļ�װ��
	public CommonDTO ModBox(String msgdata)throws ApplicationException;
	//ɾ����װ��
	public CommonDTO DelBox(String msgdata)throws ApplicationException;
	//��ȡ�鿴��װ���SQL
	public String ViewBox(String msgdata)throws ApplicationException;
	//��ȡ��ѯ��װ������SQL
	public String SearchBoxMana(String msgdata)throws ApplicationException;
	
	
	//��ȡ��ѯ�ѳ���SQL
	public String SearchPlace(String msgdata)throws ApplicationException;
	//���Ӷѳ�
	public CommonDTO AddPlace(String msgdata)throws ApplicationException;
	//�޸Ķѳ�
	public CommonDTO ModPlace(String msgdata)throws ApplicationException;
	//ɾ���ѳ�
	public CommonDTO DelPlace(String msgdata)throws ApplicationException;
	//��ȡ�鿴�ѳ���SQL
	public String ViewPlace(String msgdata)throws ApplicationException;
	//���ɶѳ���λ���
	public CommonDTO BuildPlaceBoxNo(String msgdata)throws ApplicationException;
	//��ȡ��ѯ�ѳ������SQL
	public String SearchPlaceMana(String msgdata)throws ApplicationException;
	//�ѳ���λ����
	public CommonDTO LockPlaceBox(String msgdata)throws ApplicationException;
	//�ѳ���λ����
	public CommonDTO UnLockPlaceBox(String msgdata)throws ApplicationException;
	//��ȡ��ѯ�ѳ���λƽ��ͼ��SQL
	public String SearchPlacePlan(String msgdata)throws ApplicationException;
	//��ȡ��ѯ�ѳ�����ʹ��״̬��SQL
	public String SearchPlaceLevelStatus(String msgdata)throws ApplicationException;

}
