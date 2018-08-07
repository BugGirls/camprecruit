package core.util;
 
import java.text.SimpleDateFormat;
import java.util.Calendar; 
import java.util.Date;
 
 

/**
 * 处理常用代码
 * @ 
 */
public class PTUtils {

	/**
	 * 后台购买
	 * 订单编号（编号规则：年+管理员id*3+月+用户ID*13+日+递增）
	 * @param strText original string
	 * @param KeepLen expect length
	 * @return
	 */
	public static String getorderNum(Long sysid, int userid) {
		String str = "";
		Calendar calendar = Calendar.getInstance();   
		str +=  String.valueOf(calendar.get(Calendar.YEAR))+
				String.valueOf(sysid*3)+
				String.valueOf(calendar.get(Calendar.MONTH)+1)+
				String.valueOf(userid*13)+
				String.valueOf(calendar.get(Calendar.DATE)); 
//		System.out.println(str);
		return str;
	}
  
	public static String gettime(Date date){
		SimpleDateFormat sf1=new SimpleDateFormat("HH:mm");
		SimpleDateFormat sf2=new SimpleDateFormat("MM月dd日 HH:mm");
		SimpleDateFormat sf3=new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
		Date date2=new Date();
		Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    long time1 = cal.getTimeInMillis();
	    cal.setTime(date2);
	    long time2 = cal.getTimeInMillis();
	    long between_day=(time2-time1)/(1000*3600*24);
	    long between_house=(time2-time1)/(1000*3600);
	    long between_mm=(time2-time1)/(1000*60);
	    String time="";
	    if(between_mm<60){
	    	time=between_mm+"分钟前 ";
	    }else if(between_house<24){
	    	time=between_house+"小时前 ";
		}else if(between_house<48){
			time="昨天 "+sf1.format(date)+"  ";
		}else if(between_house<72){
			time="前天 "+sf1.format(date)+" ";
		}else if(between_day<365){
			time=sf2.format(date)+" ";
		}else {
			time=sf3.format(date)+" ";
		} 
	    
	    return time;
		
	}
	
	public static boolean dongtaimatimeX(Date date){
		Date date2=new Date();
		Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    long time1 = cal.getTimeInMillis();
	    cal.setTime(date2);
	    long time2 = cal.getTimeInMillis(); 
	    long between_mm=(time2-time1)/(1000*60);
	    if(between_mm>2){
	    	return false;
	    }else {
			return true;
		}
	} 
 
}
