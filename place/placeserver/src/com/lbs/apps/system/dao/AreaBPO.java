package com.lbs.apps.system.dao;

import net.sf.json.JSONObject;

import com.lbs.apps.common.ApplicationException;
import com.lbs.apps.common.JsonValidator;
import com.lbs.apps.system.po.Area;
import com.lbs.commons.ClassHelper;
import com.lbs.commons.TransManager;
import com.lbs.commons.log.LogHelper;
import com.lbs.commons.op.NotFindException;
import com.lbs.commons.op.OPException;
import com.lbs.commons.op.OPManager;

public class AreaBPO {
	LogHelper log = new LogHelper(this.getClass());	
	TransManager trans = new TransManager();
	OPManager op = new OPManager();
	
	//�жϲ�ѯ�����Ƿ�OK���ɹ�����0�����򷵻��쳣ԭ��
		public String CheckSql_Where(String msgdata){
			String ls_return="0";
			if (msgdata==null || msgdata.equals("")){  //û�д��ݲ���
				ls_return="��������쳣";			
			}else{					 
				try{
					JSONObject obj = JSONObject.fromObject(msgdata);   //����JSON���ݰ�				
					//if (!obj.has("roleid")){					
					//	ls_return="���ݰ���δ����roleid������Ϣ";									
					//}
				}catch(Exception e){
					ls_return="msgdata�����ַ����Ƿ�";						
				}			
			}	
			return ls_return;		
		}
		/**
		 * ��ѯ��������
		 * @param msgdata
		 *@param areaid	   ������������
          @param areafullname  ��������ȫ��
          @param arealevel  ������������
          @param statecode	ʡ����
          @param statename	ʡ����
          @param citycode	�д���
          @param cityname   ������
          @param areacode	���ش���
          @param areaname	��������
          @param towncode	����ֵ�����
          @param townname	����ֵ�����
          @param isvalid	�Ƿ���Ч
         * @return
		 * @throws ApplicationException
		 */
		public String GetSql_SearchArea(String msgdata)throws ApplicationException {
			AreaDTO dto=new AreaDTO();
			ClassHelper.copyProperties(msgdata, dto);		 
			StringBuffer sb=new StringBuffer("select areaid,areafullname,arealevel,statecode,statename,citycode,cityname,areacode,areaname,towncode,townname,isvalid from Area where 1=1");
			if (dto.getAreaid()!=null){
				sb.append(" and areaid='").append(dto.getAreaid()+"'");
			}
			if (!(dto.getAreafullname()==null || dto.getAreafullname().equals(""))){
				sb.append(" and areafullname like '%").append(dto.getAreafullname()).append("%'");
			}
			if (!(dto.getArealevel()==null || dto.getArealevel().equals(""))){
				sb.append(" and arealevel = '").append(dto.getArealevel()).append("'");
			}
			if (!(dto.getStatecode()==null || dto.getStatecode().equals(""))){
				sb.append(" and statecode = '").append(dto.getStatecode()).append("'");
			}
			if (!(dto.getStatename()==null || dto.getStatename().equals(""))){
				sb.append(" and statename = '").append(dto.getStatename()).append("'");
			}
			if (!(dto.getCitycode()==null || dto.getCitycode().equals(""))){
				sb.append(" and citycode = '").append(dto.getCitycode()).append("'");
			}
			if (!(dto.getCityname()==null || dto.getCityname().equals(""))){
				sb.append(" and cityname = '").append(dto.getCityname()).append("'");
			}
			if (!(dto.getAreacode()==null || dto.getAreacode().equals(""))){
				sb.append(" and areacode = '").append(dto.getAreacode()).append("'");
			}
			if (!(dto.getAreaname()==null || dto.getAreaname().equals(""))){
				sb.append(" and areaname = '").append(dto.getAreaname()).append("'");
			}
			if (!(dto.getTowncode()==null || dto.getTowncode().equals(""))){
				sb.append(" and towncode = '").append(dto.getTowncode()).append("'");
			}
			if (!(dto.getTownname()==null || dto.getTownname().equals(""))){
				sb.append(" and townname = '").append(dto.getTownname()).append("'");
			}
			if (!(dto.getIsvalid()==null || dto.getIsvalid().equals(""))){
				sb.append(" and isvalid = '").append(dto.getIsvalid()).append("'");
			}
			sb.append(" order by arealevel");
			String ls_sql=sb.toString();		
			return ls_sql;		
		}	
		
		/**
		 * ��ȡʡ�б�
		 * @param msgdata
		 * @return
		 * @throws ApplicationException
		 */
		public String GetSql_SearchStateList(String msgdata)throws ApplicationException {
			return "select statecode,statename from area where arealevel='2' and isvalid='101'";	
		}
		
		/**
		 * ����ʡ�����ȡ�м��б�
		 * @param msgdata
		 * @param statecode ʡ����
		 * @return
		 * @throws ApplicationException
		 */
		public String GetSql_SearchCityList(String msgdata)throws ApplicationException {
			AreaDTO dto=new AreaDTO();
			ClassHelper.copyProperties(msgdata, dto);
			if (dto.getStatecode()==null) dto.setStatecode("");
			String ls_sql="select citycode,cityname from area where arealevel='3'  and isvalid='101' and statecode='"+dto.getStatecode()+"'";
			return ls_sql;
		}
		/**
		 * �����д����ȡ�����б�
		 * @param msgdata
		 * @param citycode �д���
		 * @return
		 * @throws ApplicationException
		 */
		public String GetSql_SearchAreaList(String msgdata)throws ApplicationException {
			AreaDTO dto=new AreaDTO();
			ClassHelper.copyProperties(msgdata, dto);
			if (dto.getCitycode()==null) dto.setCitycode("");
			String ls_sql="select areacode,areaname from area where arealevel='4'  and isvalid='101'and citycode='"+dto.getCitycode()+"'";
			return ls_sql;
		}
		/**
		 * �������ش����ȡ����ֵ��б�
		 * @param msgdata
		 * @param areacode ���ش���
		 * @return
		 * @throws ApplicationException
		 */
		public String GetSql_SearchTownList(String msgdata)throws ApplicationException {
			AreaDTO dto=new AreaDTO();
			ClassHelper.copyProperties(msgdata, dto);
			if (dto.getAreacode()==null) dto.setAreacode("");
			String ls_sql="select towncode,townname from area where arealevel='5'  and isvalid='101'and areacode='"+dto.getAreacode()+"'";
			return ls_sql;
		}
		/**
		 * ������������
		 * @param msgdata
		 *@param areaid	String	��	������������
          @param areafullname  ��������ȫ��
          @param arealevel  ������������
          @param statecode	ʡ����
          @param statename	ʡ����
          @param citycode	�д���
          @param cityname   ������
          @param areacode	���ش���
          @param areaname	��������
          @param towncode	����ֵ�����
          @param townname	����ֵ�����
          @param isvalid	�Ƿ���Ч
         * @return
		 * @throws ApplicationException
		 */
		public String AddArea(String msgdata)throws ApplicationException{ //������������
			String ls_return="0";
			//json��֤
			JsonValidator jsonValidator=new JsonValidator();
			if (!jsonValidator.validate(msgdata)) return "msgdata���ݰ���Ч";
			Area area=new Area();   //ʵ����������
			ClassHelper.copyProperties(msgdata, area);	
			if (area.getAreaid()==null) return "�����������벻��Ϊ��";
			if (area.getAreafullname()==null || area.getAreafullname().equals("")) return "�����������Ʋ���Ϊ��";
			if (area.getArealevel()==null || area.getArealevel().equals("")) return "��������������Ϊ��";
			String hql="select count(*) from Area where areaid='"+area.getAreaid()+"' or areafullname='"+area.getAreafullname()+"'";
			try {
				if (op.getCount(hql).intValue()>0){
					return "������������������Ѵ���";
				}
			} catch (OPException e) {
				return e.getMessage();
			}
			if (area.getArealevel().equals("2")){  //ʡ
				if (area.getStatecode()==null || area.getStatecode().equals("") || area.getStatecode().equals("null")){
					area.setStatecode(area.getAreaid());
					area.setStatename(area.getAreafullname());
					area.setAreafullname(area.getStatename());
				}				
			}else if (area.getArealevel().equals("3")){  //��
				if (area.getCitycode()==null || area.getCitycode().equals("") || area.getCitycode().equals("null")){
					area.setCitycode(area.getAreaid());
					area.setCityname(area.getAreafullname());
					area.setAreafullname(area.getStatename()+area.getCityname());
				}
			}else if (area.getArealevel().equals("4")){  //����
				if (area.getAreacode()==null || area.getAreacode().equals("") || area.getAreacode().equals("null")){
					area.setAreacode(area.getAreaid());
					area.setAreaname(area.getAreafullname());
					area.setAreafullname(area.getStatename()+area.getCityname()+area.getAreaname());
				}
			}else if (area.getArealevel().equals("5")){  //����ֵ�
				if (area.getTowncode()==null || area.getTowncode().equals("") || area.getTowncode().equals("null")){
					area.setTowncode(area.getAreaid());
					area.setTownname(area.getAreafullname());
					area.setAreafullname(area.getStatename()+area.getCityname()+area.getAreaname()+area.getTownname());
				}
			}
			//���ݱ���
			try {
				trans.begin();						
				op.saveObj(area);					
				trans.commit();
			} catch (OPException e1) {
			    try {
					trans.rollback();
				} catch (OPException e2) {
					return e2.getMessage();
				}	
				return e1.getMessage();
			}		
			return ls_return;		
		}
		/**
		 * �޸���������
		 * @param msgdata
		 *@param areaid	String	��	������������
          @param areafullname  ��������ȫ��
          @param arealevel  ������������
          @param statecode	ʡ����
          @param statename	ʡ����
          @param citycode	�д���
          @param cityname   ������
          @param areacode	���ش���
          @param areaname	��������
          @param towncode	����ֵ�����
          @param townname	����ֵ�����
          @param isvalid	�Ƿ���Ч
         * @return
		 * @throws ApplicationException
		 */
		public String ModArea(String msgdata)throws ApplicationException{ //�޸���������
			String ls_return="0";
			//json��֤
			JsonValidator jsonValidator=new JsonValidator();
			if (!jsonValidator.validate(msgdata)) return "msgdata���ݰ���Ч";
			Area Area=new Area();   //ʵ����������
			ClassHelper.copyProperties(msgdata, Area);	
			if (Area.getAreaid()==null) return "�����������벻��Ϊ��";
			if (Area.getAreafullname()==null || Area.getAreafullname().equals("")) return "�����������Ʋ���Ϊ��";
			if (Area.getArealevel()==null || Area.getArealevel().equals("")) return "��������������Ϊ��";
			//���ݱ���
			try {
				trans.begin();						
				op.updateObj(Area);					
				trans.commit();
			} catch (OPException e1) {
			    try {
					trans.rollback();
				} catch (OPException e2) {
					return e2.getMessage();
				}	
				return e1.getMessage();
			}		
			return ls_return;	
		}
		/**
		 * ɾ����������
		 * @param msgdata
		 *@param areaid	������������
         * @return
		 * @throws ApplicationException
		 */
		public String DelArea(String msgdata)throws ApplicationException{ //ɾ����������
			String ls_return="0";
			//json��֤
			JsonValidator jsonValidator=new JsonValidator();
			if (!jsonValidator.validate(msgdata)) return "msgdata���ݰ���Ч";
			AreaDTO dto=new AreaDTO();   //ʵ����������
			ClassHelper.copyProperties(msgdata, dto);	
			if (dto.getAreaid()==null) return "�����������벻��Ϊ��";
			//���ݱ���
			try {
				trans.begin();						
				op.removeObjs( "delete from Area a where a.areaid='"+dto.getAreaid()+"'");					
				trans.commit();
			} catch (OPException e1) {
			    try {
					trans.rollback();
				} catch (OPException e2) {
					return e2.getMessage();
				}	
				return e1.getMessage();
			}		
			return ls_return;	
		}

}
