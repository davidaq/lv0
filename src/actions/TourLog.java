package actions;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import tables.Comment;
import tables.Good;
import tables.Tourlog;
import tables.Userinfo;
import dao.AttentionDao;
import dao.CommentDao;
import dao.GoodDao;
import dao.TourlogDao;
import dao.UserinfoDao;

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
		
		TourlogDao td = new TourlogDao();
		ArrayList<Tourlog> tlist = td.queryByPageUser(list, 16, param.pageNow);
		
		return jsonResult(tlist);
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
		tl.setAbstract_(this.toAbstract(param.content));
		tl.setDate(new Date());
		TourlogDao tld = new TourlogDao();
		tld.addTourlog(tl);
		return jsonResult("ok");
	}
	
	
	public static class PublishCommentParam{
		int tourLogId;
		String content;
	}
	
	public String publishComment(){
		PublishCommentParam param = (PublishCommentParam) getParam(PublishCommentParam.class);
		if(param.content == null || param.content.equals("")){
			return jsonResult("content");
		}
		Userinfo myUserinfo = (Userinfo)session("myUserinfo");
		Comment ct = new Comment();
		ct.setUid(myUserinfo.getUid());
		ct.setTourLogId(param.tourLogId);
		ct.setComContent(param.content);
		CommentDao cd = new CommentDao();
		cd.addComment(ct);
		
		return jsonResult("ok");
	}
	
	
	public static class GoodParam{
		int tourLogId;
	}
	
	public String good(){
		GoodParam param = (GoodParam) getParam(GoodParam.class);
		Userinfo myUserinfo = (Userinfo)session("myUserinfo");
		GoodDao gdd = new GoodDao();
		tables.Good gd = gdd.getGoodByLogIdAndUserId(param.tourLogId,myUserinfo.getUid());
		if(gd != null){
                    return "tourLogId";
		}
		gd = new Good();
		gd.setUid(myUserinfo.getUid());
		gd.setTourLogId(param.tourLogId);
		gd.setDate(new Date());
		gdd.addGood(gd);		
		return jsonResult("ok");
	}
	
	
	public static class GetGoodByTourLogIdParam{
		int tourLogId;
	}
	
	public String getGoodByTourLogId(){
		GetGoodByTourLogIdParam param = (GetGoodByTourLogIdParam) getParam(GetGoodByTourLogIdParam.class);
		GoodDao gd = new GoodDao();
		ArrayList<Good> gList = gd.getGoodByLogId(param.tourLogId);
		return jsonResult(gList.size());
	}
	
	
	public static class GetTourLogBySomeoneIdParam{
		int author;
		int pageNow;
	}
	
	public String getTourLogBySomeoneId(){
		GetTourLogBySomeoneIdParam param = (GetTourLogBySomeoneIdParam) getParam(GetTourLogBySomeoneIdParam.class);
		ArrayList<Userinfo> list = new ArrayList<Userinfo>();
		UserinfoDao ud = new UserinfoDao();
		Userinfo myUserinfo = ud.findUserinfoByid(param.author);
		list.add(myUserinfo);
		TourlogDao td = new TourlogDao();
		ArrayList<Tourlog> tlist = td.queryByPageUser(list, 16, param.pageNow);
		
		return jsonResult(tlist);
	}
	
	public static class GetATourLogByIdParam{
		int tourLogId;
	}
	
	public String getATourLogById(){
		GetATourLogByIdParam param = (GetATourLogByIdParam) getParam(GetATourLogByIdParam.class);
		Tourlog tl = null;
		TourlogDao td = new TourlogDao();
		tl = td.findTourlogyid(param.tourLogId);
		return jsonResult(tl);
	}
	
	
	public static class DeleteTourLogByIdParam{
		int tourLogId;
	}
	
	public String deleteTourLogById(){
		DeleteTourLogByIdParam param = (DeleteTourLogByIdParam) getParam(DeleteTourLogByIdParam.class);
		TourlogDao td = new TourlogDao();
		
		Tourlog t = td.findTourlogyid(param.tourLogId);
		if(t == null){
			return jsonResult("tourLogId");
		}
		Userinfo ui = (Userinfo)session("myUserinfo");
		if(!t.getAuthor().equals(ui.getUid())){
			return jsonResult("tourLogId");
		}
		//delete goods
		GoodDao gd = new GoodDao();
		ArrayList<Good> gList = null;
		gList = gd.getGoodByLogId(param.tourLogId);
		if(gList.size() > 0){
			Iterator<Good> gIterator = gList.iterator();
			
			while(gIterator.hasNext()){
				Good g = gIterator.next();
				gd.deleteGood(g);
			}
		}
		//delete comments
		CommentDao cd = new CommentDao();
		ArrayList<Comment> cList = cd.getCommentByLogId(param.tourLogId);
		if(cList.size() > 0){
			Iterator<Comment> cIterator = cList.iterator();
			while (cIterator.hasNext()) {
				Comment comment = cIterator.next();
				cd.deleteComment(comment);
			}
		}
		//delete Tourlog
		td.deleteTourlog(t);
		return jsonResult("ok");
	}
	
	
	public static class DeleteCommentByIdParam{
		int comId;
	}
	
	public String deleteCommentById(){
		DeleteCommentByIdParam param = (DeleteCommentByIdParam) getParam(DeleteCommentByIdParam.class);
		CommentDao cd = new CommentDao();
		Comment c = cd.findCommentbyid(param.comId);
		if (c == null){
			return jsonResult("comId");
		}
		TourlogDao td = new TourlogDao();
		Tourlog t = td.findTourlogyid(c.getTourLogId());
		if (t == null){
			return jsonResult("tourLogId");
		}
		
		Userinfo ui = (Userinfo)session("myUserinfo");
		
		
		if(!t.getAuthor().equals(ui.getUid()) && !c.getUid().equals(ui.getUid())){
			return jsonResult("uid");
		}
		
		cd.deleteComment(c);
		return jsonResult("ok");
	}
	
	
	public static class GetCommentByTourLogIdParam{
		int tourLogId;		
	}
	
	public String getCommentByTourLogId(){
		GetCommentByTourLogIdParam param = (GetCommentByTourLogIdParam) getParam(GetCommentByTourLogIdParam.class);
		CommentDao cd = new CommentDao();
		ArrayList<Comment> cList = cd.getCommentByLogId(param.tourLogId);
		return jsonResult(cList);
	}
	
	
	
	public static class TranspondTourLogParam{
		int tourLogId;
	}
	
	public String transpondTourLog(){
		TranspondTourLogParam param = (TranspondTourLogParam) getParam(TranspondTourLogParam.class);
		TourlogDao td = new TourlogDao();
		Tourlog tl = null;
		tl = td.findTourlogyid(param.tourLogId);
		if (tl == null){
			return jsonResult("tourLogId");
		}
		
		tl.setTourLogId(null);
		if(tl.getRelaySourceId() == null || tl.getRelaySourceId() < 0){
			tl.setRelaySourceId(tl.getAuthor());
		}
		tl.setRelayFromId(tl.getAuthor());
		Userinfo ui = (Userinfo)session("myUserinfo");
		tl.setAuthor(ui.getUid());
		td.addTourlog(tl);
		
		return jsonResult("ok");
	}
}
