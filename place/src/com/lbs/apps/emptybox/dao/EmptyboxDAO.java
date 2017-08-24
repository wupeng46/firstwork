package com.lbs.apps.emptybox.dao;

import com.lbs.apps.common.ApplicationException;
import com.lbs.apps.common.CommonDTO;

public interface EmptyboxDAO {
	//������԰�Ǽ�
	public CommonDTO EmptyBoxInPark(String msgdata)throws ApplicationException;
	//�����볡�Ǽ�
	public CommonDTO EmptyBoxInPlace(String msgdata)throws ApplicationException;
	//��������Ǽ�
	public CommonDTO EmptyBoxOutPlace(String msgdata)throws ApplicationException;
	//�����԰�Ǽ�
	public CommonDTO EmptyBoxOutPark(String msgdata)throws ApplicationException;
	//����Ԥ��
	public String SearchEmptyBoxWarm(String msgdata)throws ApplicationException;
	

}
