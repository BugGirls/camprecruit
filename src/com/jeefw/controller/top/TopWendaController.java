package com.jeefw.controller.top;
   
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jeefw.core.Constant;
import com.jeefw.core.JavaEEFrameworkBaseController;
import com.jeefw.model.sys.TzzAnswer;
import com.jeefw.model.sys.TzzDictionary;
import com.jeefw.model.sys.TzzQuestion;
import com.jeefw.model.sys.TzzUser;
import com.jeefw.service.sys.TzzAnswerService;
import com.jeefw.service.sys.TzzDictionaryService;
import com.jeefw.service.sys.TzzQuestionService;
import com.jeefw.service.sys.TzzUserService;

import core.util.PTUtils;
import net.sf.json.JSONObject;
 
 

/**
 * 问答
 */
@Controller
@RequestMapping("/top/wenda") 
public class TopWendaController extends JavaEEFrameworkBaseController<TzzQuestion> implements Constant {

	@Resource
	private TzzUserService tzzUserService;
	@Resource
	private TzzDictionaryService tzzDictionaryService;
	@Resource
	private TzzQuestionService tzzQuestionService;
	@Resource
	private TzzAnswerService tzzAnswerService;
	
	/**
	 * 获取问答信息列表
	 * /top/wenda/getallq
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getallq", method = { RequestMethod.POST, RequestMethod.GET })
	public void getallq(HttpServletRequest request, HttpServletResponse response) throws Exception { 
		wendashuju(request, response);
		List<TzzQuestion> qlist =tzzQuestionService.getstaticQuestionslist(9);
		request.setAttribute("qlist", qlist);
		request.getRequestDispatcher("/jsp/peixun_wenda.jsp").forward(request,response); 
	}
	
	
	/**
	 * 按条件查询问答
	 * /top/wenda/searchq 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/searchq", method = { RequestMethod.POST, RequestMethod.GET })
	public void searchq(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String family;
		String order; 
		String pagenum;
		List<TzzDictionary> tdlist=tzzDictionaryService.doQueryAll();
		Map<Integer, String>map=new HashMap<Integer, String>();
		for (int i = 0; i < tdlist.size(); i++) {
			 map.put(tdlist.get(i).getId(), tdlist.get(i).getName());
		} 
		if(request.getParameter("order")!=null&&!request.getParameter("order").equals("")){
			order=request.getParameter("order"); 
			request.setAttribute("order", order);
		}else {		
			order="1";
		} 
		if(request.getParameter("family")!=null&&!request.getParameter("family").equals("")){
			family=request.getParameter("family");
			request.setAttribute("family", family);
			request.setAttribute("familycn",map.get(Integer.parseInt(family))); 
		}else {		
			family="0";
		} 
		if(request.getParameter("pagenum")!=null&&!request.getParameter("pagenum").equals("")){
			pagenum=request.getParameter("pagenum");
			request.setAttribute("pagenum", pagenum);
		}else {
			pagenum="1";
		} 
		List<TzzQuestion> qlist=new ArrayList<TzzQuestion>(); 
 		wendashuju(request, response); 
		qlist=tzzQuestionService.searchQuestionslist(family,order, Integer.parseInt(pagenum),9); 
		int pages =tzzQuestionService.searchQuestionspagenum(family, order, 9);
		request.setAttribute("page",pages ); 
		request.setAttribute("qlist", qlist);
		request.getRequestDispatcher("/jsp/peixun_wenda.jsp").forward(request,response); 
	}
	
	/**
	 * 问答页面默认数据
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void wendashuju(HttpServletRequest request, HttpServletResponse response ) throws Exception {
		
		
		//总页数
	    int allq = Integer.valueOf(tzzQuestionService.countAll()+"");
	    int pages=0;
	    if(allq%9==0){
	    	pages=allq/9;
	    }else {
	    	pages=allq/9+1;
		}
		request.setAttribute("page",pages ); 
		//分类列表 
		List<TzzDictionary> dList= tzzDictionaryService.queryByProerties("type", "1");
		request.setAttribute("fllist", dList);
		//热门问答
		List<TzzQuestion> qlist =tzzQuestionService.gethotQuestionslist(9); 
		request.setAttribute("rmqlist", qlist);
	}
	
	
	/**
	 * 问答详情
	 * /top/wenda/qxq
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/qxq", method = { RequestMethod.POST, RequestMethod.GET })
	public void qxq(HttpServletRequest request, HttpServletResponse response) throws Exception {
	 
		int qid=Integer.parseInt( request.getParameter("qid"));
		TzzQuestion tzzQuestion = tzzQuestionService.get(qid);
		tzzQuestion.setBrowseNum(tzzQuestion.getBrowseNum()+1);
		
		tzzQuestionService.merge(tzzQuestion);
		Map<String, String> sortedCondition=new HashMap<>();
		sortedCondition.put("adopt", "desc"); 
		sortedCondition.put("createTime", "asc");
		//页码
		int pagenum=1;
		int pageSize=8;
		if(request.getParameter("pagenum")!=null&&!request.getParameter("pagenum").equals("")){
			pagenum=Integer.parseInt(request.getParameter("pagenum")); 
		}
		int nowpage=pagenum;
		String[] ns = new String[2];
		Integer[] vs =new Integer[2];
		ns[0]="questionId";
		ns[1]="type";
		vs[0]=qid;
		vs[1]=1;
		//评论
		List<TzzAnswer> aplist = new ArrayList<TzzAnswer>();
				
		aplist=tzzAnswerService.queryByProerties(ns, vs, sortedCondition);
		//回复
		ns = new String[2];
		vs =new Integer[2];
		ns[0]="questionId";
		ns[1]="type";
		vs[0]=qid;
		vs[1]=2;
		List<TzzAnswer> ahlist=new ArrayList<>();
				
		ahlist=	tzzAnswerService.queryByProerties(ns, vs );
		ahlist=zhuanhuan(ahlist);
		int nums=aplist.size();
		int uid=0;
		if(request.getSession().getAttribute("tzzuser")!=null){
			uid=((TzzUser)request.getSession().getAttribute("tzzuser")).getId();
		}
		
		aplist=tzzAnswerService.getTzzAnswerlist(aplist, pagenum,pageSize); 
		for (int i = 0; i < aplist.size(); i++) {
			String[]names=new String[2];
			String[]vals=new String[2];
			names[0]="type";
			vals[0]="5";
			names[1]="name";
			vals[1]=uid+"z"+aplist.get(i).getId(); 
			if(tzzDictionaryService.getByProerties(names, vals)!=null){
				aplist.get(i).setZanhou("1");
			}else {
				aplist.get(i).setZanhou("0");
			}
		}
		
		pagenum=nums;
		if(nums<pageSize){
			pagenum=1;
		}else{
			if(nums%pageSize==0){
				pagenum=nums/pageSize;
			}else {
				pagenum=nums/pageSize+1;
			}
		}  
		
		int anum=tzzAnswerService.queryByProerties("questionId",tzzQuestion.getId() ).size();
		if(anum >1000){ 
			tzzQuestion.setAnswernum(anum/1000+"K");
		}else {
			tzzQuestion.setAnswernum(anum+"");
		} 
		if(tzzQuestion.getBrowseNum()>1000){
			tzzQuestion.setClicknumstr(tzzQuestion.getBrowseNum()/1000+"K"); 
		}else {
			tzzQuestion.setClicknumstr(tzzQuestion.getBrowseNum()+""); 
		}
		tzzQuestion.setUserimg(tzzUserService.get(tzzQuestion.getUserId()).getImage());
		request.setAttribute("nowpage",nowpage);
		request.setAttribute("qid", qid);
		request.setAttribute("page", pagenum);
		request.setAttribute("ahlist", ahlist);
		request.setAttribute("alist", aplist);
		request.setAttribute("question", tzzQuestion);
		
		//热门问答
		List<TzzQuestion> qlist =tzzQuestionService.gethotQuestionslist(9); 
		request.setAttribute("rmqlist", qlist);
		request.getRequestDispatcher("/jsp/peixun_wenda_xq.jsp").forward(request,response); 
	}
	
	
 public List<TzzAnswer> zhuanhuan(List<TzzAnswer> list){
	 for (int i = 0; i < list.size(); i++) { 
		 if(list.get(i).getUserId()!=0){
				TzzUser tzzUser=new TzzUser();
				tzzUser=tzzUserService.get(list.get(i).getUserId());
				list.get(i).setUsername(tzzUser.getUsername());
				list.get(i).setUserimg(tzzUser.getImage());
			}else {
				list.get(i).setUsername("管理员");
				list.get(i).setUserimg("/static/dist/img/admin.png");
			} 
		 list.get(i).setTime(PTUtils.gettime(list.get(i).getCreateTime()));
	}
	
	 return list;
 }
	/**
	 * 回复
	 *  /top/wenda/answer
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/answer", method = { RequestMethod.POST, RequestMethod.GET })
	public void answer(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("回复");
		JSONObject jo = new JSONObject();
	    int aid=0;
	    if(request.getParameter("aid")!=null&&!request.getParameter("aid").equals("")){
	    	aid=Integer.parseInt(request.getParameter("aid"));
	    }
		if(request.getSession().getAttribute("tzzuser")!=null&&!request.getSession().getAttribute("tzzuser").equals("")){  
			int uid=((TzzUser)request.getSession().getAttribute("tzzuser")).getId(); 
			int qid=Integer.parseInt(request.getParameter("qid"));
			TzzQuestion tzzQuestion=tzzQuestionService.get(qid);
			if(Integer.parseInt(tzzQuestion.getState())<2){
				tzzQuestion.setState("1");
			}
			
			tzzQuestionService.persist(tzzQuestion); 
			String content=request.getParameter("content");
			TzzAnswer tzzAnswer=new TzzAnswer();
			tzzAnswer.setUserId(uid);
			tzzAnswer.setQuestionId(qid);
			tzzAnswer.setCreateTime(new Date());
			tzzAnswer.setAdopt("0");
			tzzAnswer.setZan(0);
			tzzAnswer.setContent(content);
			
			if(aid!=0){
				//System.out.println("答案"+tzzAnswer.getId()); 
				tzzAnswer.setType(2);
				tzzAnswer.setAnswerId(aid);
				TzzAnswer ta=new TzzAnswer();
				ta=tzzAnswerService.get(aid);
				TzzDictionary td=new TzzDictionary();
				td.setName(uid+"h"+ta.getUserId());
				td.setTitle(uid+"回复了"+ta.getUserId());
				td.setParentId(aid);
				td.setType("6"); 
				td.setCreateTime(new Date());
				tzzDictionaryService.persist(td); 
			}else {
				tzzAnswer.setType(1);
				tzzAnswer.setAnswerId(0);
				TzzDictionary td=new TzzDictionary();
				td.setName(uid+"p"+tzzQuestionService.get(qid).getUserId()+"d"+qid);
				td.setTitle("用户"+uid+"评论了"+"用户"+tzzQuestionService.get(qid).getUserId()+"的问题"+qid);
				td.setParentId(qid);
				td.setType("6"); 
				td.setCreateTime(new Date());
				tzzDictionaryService.persist(td);  
				
			}
			tzzAnswerService.persist(tzzAnswer); 
			jo.put("a", 1);
			writeJSON(response, jo);
			return;
		} else { 
			request.getRequestDispatcher("/jsp/peixun_wenda_xq.jsp").forward(request,response); 
		} 
	}
	
	/**
	 * 赞
	 */
	@RequestMapping(value = "/zan", method = { RequestMethod.POST, RequestMethod.GET })
	public void zan(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int aid=Integer.parseInt( request.getParameter("aid"));
		int uid =Integer.parseInt(request.getParameter("uid")); 
		TzzAnswer a= tzzAnswerService.get(aid);
		JSONObject jo =new JSONObject(); 
		TzzDictionary tzzDictionary=tzzDictionaryService.getByProerties("name", uid+"z"+aid);
		if(tzzDictionary!=null){
			a.setZan(a.getZan()-1); 
			jo.put("zanh", 0);
			tzzDictionaryService.delete(tzzDictionary); 
		}else {
			TzzDictionary td=new TzzDictionary();
			td.setName(uid+"z"+aid);
			td.setTitle(uid+"赞了"+aid);
			td.setParentId(0);
			td.setType("5");
			td.setLevel("0");
			td.setCreateTime(new Date());
			tzzDictionaryService.persist(td);
			a.setZan(a.getZan()+1);
			jo.put("zanh",1);
			
		}   
	    tzzAnswerService.merge(a);
	    jo.put("zan", a.getZan());
	    writeJSON(response, jo);
	}
	/**
	 * 采纳
	 * /top/wenda/caina
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/caina", method = { RequestMethod.POST, RequestMethod.GET })
	public void caina(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//int uid= Integer.parseInt(request.getParameter("uid"));
		int qid= Integer.parseInt(request.getParameter("qid"));
		int aid=Integer.parseInt(request.getParameter("aid"));	
		System.out.println("aid"+aid+"qid"+qid );
		TzzQuestion tzzQuestion=tzzQuestionService.get(qid);
		tzzQuestion.setState("2");
		tzzQuestionService.merge(tzzQuestion);
		TzzAnswer tzzAnswer=tzzAnswerService.get(aid);
		tzzAnswer.setAdopt("1");
		tzzAnswerService.merge(tzzAnswer);
		JSONObject jo = new JSONObject();
		jo.put("err", 0);
		writeJSON(response, jo);
		}
	/**
	 * 提交问题
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/postq", method = { RequestMethod.POST, RequestMethod.GET })
	public void postq(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String content = request.getParameter("content");
		String title = request.getParameter("title");
		int familyid = Integer.parseInt( request.getParameter("fid"));
		JSONObject jsonObject=new JSONObject();
		if(request.getSession().getAttribute("tzzuser")!=null){
			TzzQuestion tzzQuestion=new TzzQuestion();
			tzzQuestion.setContent(content);
			tzzQuestion.setTitle(title);
			tzzQuestion.setUserId(((TzzUser)request.getSession().getAttribute("tzzuser")).getId());
			tzzQuestion.setFamilyId(familyid);
			tzzQuestion.setCreateTime(new Date());
			tzzQuestion.setBrowseNum(0);
			tzzQuestion.setTop("0");
			tzzQuestion.setState("0");
			tzzQuestion.setEssence("0");
			tzzQuestionService.persist(tzzQuestion);
			System.out.println( tzzQuestion.getId());
			jsonObject.put("qid", tzzQuestion.getId());
			jsonObject.put("err", 0);
		}
		else {
			jsonObject.put("err", 1);
		}
		writeJSON(response, jsonObject);
		 
	}
	/**
	 * 级联
	 */
	@RequestMapping(value = "/selects", method = { RequestMethod.POST, RequestMethod.GET })
	public void selects(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int pid=0;
		if(request.getParameter("pid")!=null&&!request.getParameter("pid").equals("")){ 
			pid=Integer.parseInt(request.getParameter("pid"));
		}
		 String[] keys = new String[2];
		 keys[0] = "parentId";
		 keys[1] = "type";
		 Object[] values = new Object[2];
		 
		 values[0] = pid;
		 values[1] = "1";

		 List tzzdicList = tzzDictionaryService.queryByProerties(keys, values);
		
		 StringBuilder builder = new StringBuilder();  
		 if(tzzdicList.size()==0){
			 builder.append("<option value=''>没有子分类</option>"); 
		 }
		 for (int i = 0; i < tzzdicList.size(); i++) {
		     builder.append("<option value='" + ((TzzDictionary)tzzdicList.get(i)).getId() + "' >" + ((TzzDictionary)tzzdicList.get(i)).getName() + "</option>");
		 } 
		 writeJSON(response, builder.toString());
	}
	
	
}
