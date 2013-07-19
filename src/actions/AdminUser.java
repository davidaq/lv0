package actions;

import java.util.ArrayList;
import java.util.Date;

import com.opensymphony.xwork2.Result;

import tables.Admininfo;
import tables.Userinfo;
import dao.UserinfoDao;
import dao.AdminInfoDao;

public class AdminUser extends BaseAction{

    public static class AdminLoginParam {
        String adName;
        String adPassword;
        String spamcode;
    }
    public String adminlogin() {
    	AdminLoginParam param = (AdminLoginParam) getParam(AdminLoginParam.class);
        if(session("spamcode") == null || !param.spamcode.equals(session("spamcode")) ) {
            return jsonResult("spamcode");
        }
        
        if(param.adName == null || param.adName.equals("")){
        	return jsonResult("adName");
        }
        
        if(param.adPassword == null || param.adPassword.equals("")){
        	return jsonResult("password");
        }
        
        AdminInfoDao ud = new AdminInfoDao();
        Admininfo ai = ud.findAdminInfoByAdName(param.adName);
        if(ai == null){
        	return jsonResult("adName");
        }
        
        if(ai.getAdPassword().equals(MD5Util.MD5(param.adPassword))){
        	session("myAdmininfo",ai);
            return jsonResult("ok");
        }
        
        return jsonResult("adPassword");
    }
    
    
    public static class AdminAddParam {
        String adUsername;
        String adPassword[];
        String spamcode;
    }
    
    public String adminAdd(){
    	AdminAddParam param = (AdminAddParam) getParam(AdminAddParam.class);
        if(session("spamcode") == null || !param.spamcode.equals(session("spamcode")) ) {
            return jsonResult("spamcode");
        }
        
        if(param.adUsername == null || param.adUsername.equals("")){
        	return jsonResult("adUsername");
        }
        
        if(param.adPassword[0] == null || param.adPassword[0].equals("") || param.adPassword[1] == null || param.adPassword[1].equals("") || !param.adPassword[0].equals(param.adPassword[1])){
        	return jsonResult("adPassword");
        }
        AdminInfoDao aid = new AdminInfoDao();
        Admininfo ai = aid.findAdminInfoByAdName(param.adUsername);
        if(ai != null){
            return jsonResult("adUsername");
        }
        
        ai = new Admininfo();
        ai.setAdName(param.adUsername);
        ai.setAdPassword(MD5Util.MD5(param.adPassword[0]));
        aid.addAdminInfo(ai);
        
        session("myAdmininfo",ai);
    	return jsonResult("ok");
    }
    
    
    public static class AdminChangeAdPasswordParam {
        String oldAdPassword;
        String newAdPassword[];
    }
    
    public String adminChangePassword(){
    	AdminChangeAdPasswordParam param = (AdminChangeAdPasswordParam) getParam(AdminChangeAdPasswordParam.class);
    	if(param.oldAdPassword == null || param.oldAdPassword.equals("")){
    		return jsonResult("oldAdPassword");
    	}
    	
    	if(param.newAdPassword[0] == null || param.newAdPassword[0].equals("") || param.newAdPassword[1] == null || param.newAdPassword[1].equals("") || !param.newAdPassword[0].equals(param.newAdPassword[1])){
    		return jsonResult("newAdPassword");
    	}
    	
    	AdminInfoDao aid = new AdminInfoDao();
    	Admininfo ai = (Admininfo)session("myAdmininfo");
    	ai.setAdPassword(MD5Util.MD5(param.newAdPassword[0]));
    	aid.updateAdminInfo(ai);
    	
    	return jsonResult("ok");
    }
    
    
    public String getMyAdmininfo(){
    	Admininfo ai = (Admininfo)session("myAdmininfo");
        if(ai != null) {
            AdminInfoDao aid = new AdminInfoDao();
            aid.updateAdminInfo(ai);
        }
    	return jsonResult(ai);
    }
    
    
    public static class GetAdmininfoByIdParam{
    	int AdminId;
    }
    
    public String getAdmininfoById(){
    	GetAdmininfoByIdParam param = (GetAdmininfoByIdParam) getParam(GetAdmininfoByIdParam.class);
    	AdminInfoDao aid = new AdminInfoDao();
    	Admininfo ai = aid.findAdminInfoByid(param.AdminId);
    	return jsonResult(ai);
    }
    
    
    public static class GetAdnNameByIdParam{
    	int adminId;
    }
    
    public String getAdminnameById(){
    	GetAdnNameByIdParam param = (GetAdnNameByIdParam) getParam(GetAdnNameByIdParam.class);
    	AdminInfoDao aid = new AdminInfoDao();
    	Admininfo ai = aid.findAdminInfoByid(param.adminId);
    	
    	return jsonResult(ai.getAdName());
    }
 
    
    public String adminLogout(){
    	session("myAdmininfo",null);
    	return jsonResult("ok");
    }
}