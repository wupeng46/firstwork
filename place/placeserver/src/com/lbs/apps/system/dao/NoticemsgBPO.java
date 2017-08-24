package com.lbs.apps.system.dao;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;

import com.eon.uc.util.MD5;
import com.lbs.apps.common.ApplicationException;
import com.lbs.apps.common.HttpUtil;
import com.lbs.apps.common.NoticeUtil;
import com.lbs.apps.system.po.Noticemsg;
import com.lbs.apps.system.po.Userrole;
import com.lbs.commons.ClassHelper;
import com.lbs.commons.DateUtil;
import com.lbs.commons.GlobalNames;
import com.lbs.commons.TransManager;
import com.lbs.commons.log.LogHelper;
import com.lbs.commons.op.NotFindException;
import com.lbs.commons.op.OPException;
import com.lbs.commons.op.OPManager;

public class NoticemsgBPO {
	LogHelper log = new LogHelper(this.getClass());
	TransManager trans = new TransManager();
	OPManager op = new OPManager();

	/**
	 * 查询当前操作员未读的消息记录列表
	 * 
	 * @param msgdata
	 * @param receiverid
	 *            Int 是 操作人编号
	 * @param noticetype
	 *            String 否 消息类型
	 * @param msgcontent
	 *            String 否 消息内容
	 * 
	 * @return
	 * @throws ApplicationException
	 */
	public String GetSql_SearchCountNoticeMsg(String msgdata)
			throws ApplicationException {
		NoticemsgDTO dto = new NoticemsgDTO();
		ClassHelper.copyProperties(msgdata, dto);
		String receiverid = dto.getReceiverid();
		if (StringUtils.isBlank(receiverid)) {
			return "-1";
		}
		String sql = "select a.createdby,convert(varchar,a.createddate,120) createddate,a.isread,a.msgcontent,"
				+ "a.noticemsgid,a.noticetype,convert(varchar,a.readtime,120) readtime,a.receiverid from noticemsg a where 1=1 and isread='101'"
				+ " and a.receiverid = " + receiverid;
		StringBuffer sb = new StringBuffer(sql);
		if (!StringUtils.isBlank(dto.getNoticetype())) {
			sb.append(" and a.noticetype='").append(dto.getNoticetype())
					.append("'");
		}
		if (!StringUtils.isBlank(dto.getMsgcontent())) {
			sb.append(" and a.msgcontent like '%").append(dto.getMsgcontent())
					.append("%'");
		}
		return sb.toString();
	}

	/**
	 * 查询当前选中消息记录并设置为已读状态
	 * 
	 * @param msgdata
	 * @param receiverid
	 *             操作人编号
	 * @param noticemsgid
	 *             消息标号
	 * @param noticetype
	 *             消息类型
	 * @param msgcontent
	 *            否 消息内容
	 * 
	 * @return
	 * @throws ApplicationException
	 */
	public String GetSql_SeeNoticeMsg(String msgdata)
			throws ApplicationException {
		NoticemsgDTO dto = new NoticemsgDTO();
		ClassHelper.copyProperties(msgdata, dto);
		String sql = "select a.createdby,convert(varchar,a.createddate,120) createddate,a.isread,a.msgcontent,"
				+ "a.noticemsgid,a.noticetype,convert(varchar,a.readtime,120) readtime,a.receiverid from noticemsg a where 1=1 and isread='102'";
		StringBuffer sb = new StringBuffer(sql);
		sb.append(" and a.noticemsgid= ").append(dto.getNoticemsgid());
		sb.append(" and a.receiverid = " + dto.getReceiverid());
		if (StringUtils.isBlank(dto.getNoticemsgid())
				|| StringUtils.isBlank(dto.getReceiverid())) {
			return "-1";
		}
		Noticemsg msg = null;
		try {
			msg = (Noticemsg) op.retrieveObj(Noticemsg.class,
					new Integer(dto.getNoticemsgid()));
		} catch (NumberFormatException e) {
			return "-1";
		} catch (OPException e) {
			return "-1";
		} catch (NotFindException e) {
			return "-1";
		}
		msg.setIsread("102");
		msg.setReadtime(DateUtil.dateToTimestamp(DateUtil.getCurrentDate()));
		try {
			trans.begin();
			op.updateObj(msg);
			trans.commit();
		} catch (Exception e) {
			try {
				trans.rollback();
			} catch (Exception e2) {
				return "-1";
			}
			return "-1";
		}
		return sb.toString();
	}
	
	
	
}
