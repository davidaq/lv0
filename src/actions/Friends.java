
package actions;

import tables.Attention;
import tables.Userinfo;
import dao.AttentionDao;

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
        FriendItem[] list = new FriendItem[] {
            new FriendItem(10123,"David","static/images/bulb.png","watch",true),
            new FriendItem(10124,"AQ","static/images/pi.png","friend",false),
            new FriendItem(10125,"FG","static/images/picmi.png","friend",false),
            new FriendItem(10127,"HWB","static/images/logo.png","watch",true)
        };
        return jsonResult(list);
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
	
}
