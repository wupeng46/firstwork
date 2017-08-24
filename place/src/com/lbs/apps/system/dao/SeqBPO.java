package com.lbs.apps.system.dao;

import com.lbs.apps.common.ApplicationException;
import com.lbs.apps.system.po.Sysseq;
import com.lbs.commons.DateUtil;
import com.lbs.commons.TransManager;
import com.lbs.commons.op.NotFindException;
import com.lbs.commons.op.OPException;
import com.lbs.commons.op.OPManager;

public class SeqBPO {	
	//获取序列号
	public static String GetSequence(String seqname)throws ApplicationException{
		TransManager trans = new TransManager();
		OPManager op = new OPManager();
		String ls_seq="0";
		
		boolean lb_exists=true;
		Sysseq sysseq=null;
		try {
			sysseq=(Sysseq)op.retrieveObj(Sysseq.class, seqname);
			if (sysseq.getIsdaynum()==null || sysseq.getIsdaynum().equals("100")){
				sysseq.setSeqvalue(sysseq.getSeqvalue()+1);
			}else{  //判断是不是当前日期
				if (DateUtil.getCurrentDate_String("YYYY-MM-DD").equals(sysseq.getCurrdate())){
					sysseq.setSeqvalue(sysseq.getSeqvalue()+1);
				}else{  //不是当前日期序列号重新设置为1，并设置为当前日期
					sysseq.setCurrdate(DateUtil.getCurrentDate_String("YYYY-MM-DD"));
					sysseq.setSeqvalue(new Integer(1));
				}				
			}			
		} catch (OPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotFindException e) {
			lb_exists=false;
			sysseq=new Sysseq();
			sysseq.setSeqname(seqname.toUpperCase());
			sysseq.setSeqvalue(new Integer(1));
			sysseq.setIsdaynum("100");  //默认不按天生成
		}
		ls_seq=sysseq.getSeqvalue().toString();
		try {
			trans.begin();	
			if (lb_exists){
				op.updateObj(sysseq);
			}else{
				op.saveObj(sysseq);
			}
			trans.commit();
		} catch (OPException e1) {
		    try {
				trans.rollback();
			} catch (OPException e2) {
				return e2.getMessage();
			}	
			return e1.getMessage();
		}		
		return ls_seq;
	}
	
	//获取有规则的合同编号,-1代表失败
	public static String GetOrdercontractid(){
		OPManager op = new OPManager();
		String ls_return="";
		String ls_now=DateUtil.getCurrentDate_String("YYYYMMDD");
		String ls_seq="0";
		try {
			ls_seq=GetSequence("SEQ_ORDERCONTRACTID");
		} catch (ApplicationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//String ls_sql="select 'WHT"+ls_now+"'+right('000000'+convert(varchar(20),count(1)+1),6) from ordercontract where ismaster='101' and convert(varchar,createddate,112)='"+ls_now+"'";
		String ls_sql="select 'WHT"+ls_now+"'+right('000000'+convert(varchar(20),"+ls_seq+"),6)";		
		try {
			ls_return=op.executeMinMaxSQLQuery(ls_sql);
		} catch (OPException e) {
			return "-1";
		}
		return ls_return;
	}
	
	//获取有规则的订单编号,-1代表失败
	public static String GetOrderid(){
		OPManager op = new OPManager();
		String ls_return="";
		String ls_now=DateUtil.getCurrentDate_String("YYYYMMDD");
		String ls_seq="0";
		try {
			ls_seq=GetSequence("SEQ_ORDERID");
		} catch (ApplicationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//String ls_sql="select 'DHD"+ls_now+"'+right('000000'+convert(varchar(20),count(1)+1),6) from orderinfo where convert(varchar,createddate,112)='"+ls_now+"'";
		String ls_sql="select 'DHD"+ls_now+"'+right('000000'+convert(varchar(20),"+ls_seq+"),6)";
		try {
			ls_return=op.executeMinMaxSQLQuery(ls_sql);
		} catch (OPException e) {
			return "-1";
		}
		return ls_return;
	}
	
	//获取有规则的过磅单号,-1代表失败
	public static String GetWeighid(){
		OPManager op = new OPManager();
		String ls_return="";
		String ls_now=DateUtil.getCurrentDate_String("YYYYMMDD");
		String ls_seq="0";
		try {
			ls_seq=GetSequence("SEQ_WEIGHID");
		} catch (ApplicationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//String ls_sql="select 'GBD"+ls_now+"'+right('000000'+convert(varchar(20),count(1)+1),6) from orderinfo where convert(varchar,createddate,112)='"+ls_now+"'";
		String ls_sql="select 'GBD"+ls_now+"'+right('000000'+convert(varchar(20),"+ls_seq+"),6)";
		try {
			ls_return=op.executeMinMaxSQLQuery(ls_sql);
		} catch (OPException e) {
			return "-1";
		}
		return ls_return;
	}
}
