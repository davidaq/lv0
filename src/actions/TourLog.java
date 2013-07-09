package actions;

import java.util.ArrayList;
import java.util.Date;

import javax.servlet.jsp.JspApplicationContext;

import actions.User.RegisterParam;
import tables.Attention;
import tables.Tourlog;
import tables.Userinfo;
import dao.AttentionDao;
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
}
