package actions;

import java.util.ArrayList;
import java.util.Date;

import tables.Attention;
import tables.Comment;
import tables.Tourlog;
import tables.Userinfo;
import dao.AttentionDao;
import dao.CommentDao;
import dao.GoodDao;
import dao.TourlogDao;

public class TourLog extends BaseAction {
	
    public static class GetTourLogParam {
        int pageNow;
    }
	
	public String getTourLog(){
		GetTourLogParam param = (GetTourLogParam) getParam(GetTourLogParam.class);
		Userinfo myUserinfo = (Userinfo)session("myUserinfo");
		AttentionDao atd = new AttentionDao();
		ArrayList<Userinfo> list = atd.GetAttentionedByUserId(myUserinfo.getUid());
                list.add(myUserinfo);
                if(true)
                    return jsonResult(list);
		
		TourlogDao td = new TourlogDao();
		ArrayList<Tourlog> tlist = td.queryByPageUser(list, 16, param.pageNow);
		
		return jsonResult(tlist);
	}
	
	
	public static class SetAttentionParam{
		int attedUser;
	}
	
	public String setAttention(){
		SetAttentionParam param = (SetAttentionParam) getParam(SetAttentionParam.class);
		Userinfo myUserinfo = (Userinfo)session("myUserinfo");
		AttentionDao atd = new AttentionDao();
		Attention at = new Attention();
		at.setAttUser(myUserinfo.getUid());
		at.setAttedUser(param.attedUser);
		atd.addAttention(at);
		
		return jsonResult("ok");
	}
	
	
	public static class PublishTourLogParam{
		String content;
	}
	
	public String publishTourLog(){
		PublishTourLogParam param = (PublishTourLogParam) getParam(PublishTourLogParam.class);
		if(param.content == null || param.content.equals("")){
			return jsonResult("content");
		}
		Userinfo myUserinfo = (Userinfo)session("myUserinfo");
		Tourlog tl = new Tourlog();
		tl.setAuthor(myUserinfo.getUid());
		tl.setContent(param.content);
		tl.setAbstract_(param.content);
		tl.setDate(new Date());
		TourlogDao tld = new TourlogDao();
		tld.addTourlog(tl);
		return jsonResult("ok");
	}
	
	
	public static class PublishCommentParam{
		int tourLogID;
		String comContent;
	}
	
	public String publishComment(){
		PublishCommentParam param = (PublishCommentParam) getParam(PublishCommentParam.class);
		if(param.comContent == null || param.comContent.equals("")){
			return jsonResult("content");
		}
		Userinfo myUserinfo = (Userinfo)session("myUserinfo");
		Comment ct = new Comment();
		ct.setUid(myUserinfo.getUid());
		ct.setTourLogId(param.tourLogID);
		ct.setComContent(param.comContent);
		CommentDao cd = new CommentDao();
		cd.addComment(ct);
		
		return jsonResult("ok");
	}
	
	
	public static class GoodParam{
		int tourLogID;
	}
	
	public String good(){
		GoodParam param = (GoodParam) getParam(GoodParam.class);
		Userinfo myUserinfo = (Userinfo)session("myUserinfo");
		tables.Good gd = new tables.Good();
		gd.setUid(myUserinfo.getUid());
		gd.setTourLogId(param.tourLogID);
		gd.setDate(new Date());
		GoodDao gdd = new GoodDao();
		gdd.addGood(gd);		
		return jsonResult("ok");
	}
	
	
	public static class GetTourLogBySomeoneIdParam{
		int author;
		int pageNow;
	}
	
	public String getTourLogBySomeoneId(){
		GetTourLogBySomeoneIdParam param = (GetTourLogBySomeoneIdParam) getParam(GetTourLogBySomeoneIdParam.class);
		ArrayList<Userinfo> list = new ArrayList<Userinfo>();
		Userinfo myUserinfo = (Userinfo)session("myUserinfo");
		list.add(myUserinfo);
		TourlogDao td = new TourlogDao();
		ArrayList<Tourlog> tlist = td.queryByPageUser(list, 16, param.pageNow);
		
		return jsonResult(tlist);
	}
	
	
	public static class GetATourLogByIdParam{
		int tourLogID;
	}
	
	public String getATourLogById(){
		GetATourLogByIdParam param = (GetATourLogByIdParam) getParam(GetATourLogByIdParam.class);
		TourLog tl = null;
		Userinfo myUserinfo = (Userinfo)session("myUserinfo");
		TourlogDao td = new TourlogDao();
		
		//tl = td.findTourlog(param.tourLogID);
		
		return jsonResult(tl);
	}
}
