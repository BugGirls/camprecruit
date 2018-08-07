package com.jeefw.service.sys.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jeefw.dao.sys.PersonalMessageDao;
import com.jeefw.dao.sys.TzzUserDao;
import com.jeefw.model.sys.PersonalMessage;
import com.jeefw.service.sys.PersonalMessageService;

import core.service.BaseService;
import core.util.NumberHelper;
import core.util.StringHelper;

/**
 * 个人消息的业务逻辑层的实现
 * @ 
 */
@Service
public class PersonalMessageServiceImpl extends BaseService<PersonalMessage> implements PersonalMessageService {

	private PersonalMessageDao personalMessageDao;
	@Resource
	private TzzUserDao tzzUserDao;

	@Resource
	public void setPersonalMessageDao(PersonalMessageDao messageDao) {
		this.personalMessageDao = messageDao;
		this.dao = messageDao;
	}

	@Override
	public void creditsExpireNotice() {
		String hql="select count(count),userId from TzzUserCreditRecord where to_days(validtime) - to_days(now()) =1 group by userId";
		List <Object[]> usercredits = personalMessageDao.queryValidCredit( hql); 
		for (Object[] obj : usercredits) {
			
			Long credits = NumberHelper.string2Long(StringHelper.null2String(obj[0]),0);
			int userid= NumberHelper.string2Int(StringHelper.null2String(obj[1]),0);
			if(credits>0){
				PersonalMessage personalMessage = new PersonalMessage();
				personalMessage.setContent("你有"+credits+"积分明天就要到期，请尽快到亲子淘平台使用积分。");
				personalMessage.setUserid(userid);
				personalMessage.setSendtime(new Date());
				personalMessage.setStatus(0);
				personalMessage.setType(2);
				personalMessageDao.persist(personalMessage);
			}
//			tzzReservation.getOpenid();
//			WeixinUtil util = new WeixinUtil();
////			AccessToken token = util.getAccessToken2(Constants.APPID, Constants.SECRET);
//			
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//			String date = sdf.format(tzzReservation.getReservationdate());
//			String param = "{\"touser\": \""+tzzReservation.getOpenid()+"\",\"template_id\": \""+MessageUtil.TMP_MSG_TYPE_RESERVATIONEXPIRE+"\",\"url\": \"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxba45fac20fff6a0f&redirect_uri=http://www.zhihuiyingdi.com/weixin/top/reservation/login&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect\","
//					+"\"data\":{"
//					+" \"first\": {\"value\":\"【冒险家】您好，您报名参与的"+tzzReservation.getName()+"就要到了!\",\"color\":\"#173177\"},"
//					+" \"keyword1\": {\"value\":\""+date+" 17:30-19:00 \"},"
//					+" \"keyword2\": {\"value\":\""+tzzReservation.getStorename()+"\"},"
//					+" \"remark\": {\"value\":\"订单号:"+tzzReservation.getOrderid()+"  如有疑问请拨打【400-612-3280】，如期参加，逾期作废。\",\"color\":\"#173177\"}}}";
//			
//			boolean sendok = util.sendModelMsg(Constants.ACCESS_TOKEN,param);
		}
	}
	@Override
	public void couponExpireNotice() {
		String hql="select c.conponname,uc.userid from UserConpon uc,coupon c where to_days(uc.dateto) - to_days(now()) =1";
		List <Object[]> usercredits = personalMessageDao.queryValidCredit( hql); 
		for (Object[] obj : usercredits) {
			
			String couponName = StringHelper.null2String(obj[0]);
			int userid= NumberHelper.string2Int(StringHelper.null2String(obj[1]),0);
			if(StringHelper.isNotEmpty(couponName)){
				PersonalMessage personalMessage = new PersonalMessage();
				personalMessage.setContent("你的优惠券"+couponName+"明天就要到期，请尽快到亲子淘平台使用。");
				personalMessage.setUserid(userid);
				personalMessage.setSendtime(new Date());
				personalMessage.setStatus(0);
				personalMessage.setType(2);
				personalMessageDao.persist(personalMessage);
			}
		}
	}
	
	@Override
	public void prepareExpireNotice() {
		
		System.out.println("活动提醒查询开始了");
		String hql="from TzzNewReservation where to_days(reservationdate) - to_days(now()) =1";
//		List <TzzNewReservation> reservations = personalMessageDao.queryReservationRemind(hql); 
//		for (TzzNewReservation obj : reservations) {
//			TzzUser tzzuser = tzzUserDao.getByProerties("phone", obj.getPhone());
//			PersonalMessage personalMessage = new PersonalMessage();
//			personalMessage.setContent("你报名的活动:"+obj.getPromotion().getName()+"明天就要开始了，请如期参加，逾期作废。");
//			personalMessage.setUserid(tzzuser.getId());
//			personalMessage.setSendtime(new Date());
//			personalMessage.setStatus(0);
//			personalMessage.setType(2);
//			personalMessageDao.persist(personalMessage);
//		}
	}
}
