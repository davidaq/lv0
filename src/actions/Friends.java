
package actions;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import tables.Attention;
import tables.Userinfo;
import dao.AttentionDao;
import dao.UserinfoDao;

public class Friends extends BaseAction {
    public static class FriendItem {
        public int uid;
        public String name, avatar, relation;
        public boolean online;
        FriendItem(int uid, String name, String avatar, String relation, boolean online) {
            this.uid = uid;
            this.name = name;
            this.avatar = avatar;
            this.relation = relation;
            this.online = online;
        }
    }
    public String list() {
    	AttentionDao ad = new AttentionDao();
    	Userinfo ui = (Userinfo)session("myUserinfo");
    	UserinfoDao ud = new UserinfoDao();
    	ArrayList<Attention> aList = ad.GetAttentionsByUserId(ui.getUid());
    	FriendItem[] list = new FriendItem[aList.size()];
    	if(aList.size() > 0){
    		int i = 0;
	    	for(Attention attention : aList){
	    		Userinfo u = ud.findUserinfoByid(attention.getAttedUser());
	    		boolean online = false;
	    		if(u.getUstate() != null && ((new Date()).getTime() - u.getUstate().getTime()) <= 10 * 1000 * 60){
	    			online = true;
	    		}
	    		String relation = "watch";
	    		if(ad.findAttentionByAttid(attention.getAttedUser(), attention.getAttUser()) != null){
	    			relation = "friend";
	    		}
	    		list[i++] = new FriendItem(attention.getAttedUser(), u.getUname(), u.getUportrait(),relation , online);
	    	}
    	}
        return jsonResult(list);
    }
    
    
	
	public static class SetAttentionParam{
		int attedUser;
	}
	
	public String setAttention(){
		SetAttentionParam param = (SetAttentionParam) getParam(SetAttentionParam.class);
		Userinfo myUserinfo = (Userinfo)session("myUserinfo");
		if(param.attedUser == myUserinfo.getUid()){
			return jsonResult("attedUser");
		}
		AttentionDao atd = new AttentionDao();
		Attention a = atd.findAttentionByAttid(myUserinfo.getUid(), param.attedUser);
		if(a != null){
			return jsonResult("attedUser");
		}
		a = new Attention();
		a.setAttentionId(null);
		a.setAttUser(myUserinfo.getUid());
		a.setAttedUser(param.attedUser);
		atd.addAttention(a);
		
		return jsonResult("ok");
	}
	
	
	public static class DeleteAttentionParam{
		int uid;
	}
	
	public String deleteAttention(){
		DeleteAttentionParam param = (DeleteAttentionParam) getParam(DeleteAttentionParam.class);
		AttentionDao ad = new AttentionDao();
		Userinfo ui = (Userinfo)session("myUserinfo");
		Attention a = ad.findAttentionByAttid(ui.getUid(), param.uid);
		if (a == null){
			return jsonResult("attentionId");
		}		
		if (a.getAttUser() != ui.getUid()){
			return jsonResult("attentionId");
		}
		ad.deleteAttention(a);		
		return jsonResult("ok");
	}
	
	
	public static class GetAttentionsByAttUserParam{
		int attUser;
	}
	
	public String getAttentionsByAttUser(){
		GetAttentionsByAttUserParam param = (GetAttentionsByAttUserParam) getParam(GetAttentionsByAttUserParam.class);
		AttentionDao ad = new AttentionDao();
		ArrayList<Attention> aList = ad.GetAttentionsByUserId(param.attUser);
		if(aList == null){
			return jsonResult("attUser");
		}
		return jsonResult(aList);
	}
	
}
