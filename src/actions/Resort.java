
package actions;

import java.util.ArrayList;

import javax.mail.search.SearchTerm;

import org.apache.commons.collections.map.StaticBucketMap;

import tables.Ressupplement;

import dao.ResortDao;
import dao.RessupplementDao;


public class Resort extends BaseAction {
    
    static final int pageSize = 3;
    
    static class ResortPage {
        public int page;
    }
    public String list() {
        ResortPage param = (ResortPage) getParam(ResortPage.class);
        ResortDao dao = new ResortDao();
        return jsonResult(dao.queryByPage(pageSize, param.page));
    }
    
    public static class SearchResportByIdParam{
    	String searchText;
    }
    
    public String searchResportById(){
    	SearchResportByIdParam param = (SearchResportByIdParam) getParam(SearchResportByIdParam.class);
    	ResortDao rd = new ResortDao();
    	ArrayList<tables.Resort> list = rd.findResortLikename(param.searchText);
    	
    	return jsonResult(list);
    }
    
    
    public static class GetResortByIdParam{
    	int resortId;
    }
    
    public String getResortById (){
    	GetResortByIdParam param = (GetResortByIdParam) getParam(GetResortByIdParam.class);
    	ResortDao rd = new ResortDao();
    	tables.Resort r = rd.findResortById(param.resortId);    	
    	
    	return jsonResult(r);
	}
    
    
    public static class GetRessupplementsByResortIdParam{
    	int resortId;
    }
    
    public String getRessupplementsByResortId(){
    	GetRessupplementsByResortIdParam param = (GetRessupplementsByResortIdParam) getParam(GetRessupplementsByResortIdParam.class);
    	RessupplementDao rsd = new RessupplementDao();
    	ArrayList<Ressupplement> rs = null;
    	//rs = rsd.findRessupplement(param.resortId);
    	return jsonResult(rs);
    }
    
    
    public static class EditResortParam{
    	int resortId;
    	String resPicture;
    	String resName;
    	String resAddress;
    	String resLabel;
    }
    
    public String editResort(){
    	EditResortParam param = (EditResortParam) getParam(EditResortParam.class);
    	if(param.resName == null || param.resName.equals("")){
    		return jsonResult("resName");
    	}
    	if(param.resAddress == null || param.resAddress.equals("")){
    		return jsonResult("resAddress");
    	}    	
    	
    	ResortDao rd = new ResortDao();
    	tables.Resort rs;
    	rs = rd.findResortById(param.resortId);
    	if(rs == null){
    		return jsonResult("resortId");
    	}
    	rs.setResName(param.resName);
    	rs.setResPicture(param.resPicture);
    	rs.setResAddress(param.resAddress);
    	rs.setResLabel(param.resLabel);
    	rd.updateResort(rs);
    	
    	return jsonResult("ok");
    }
    
    
    public static class EditResspupplementParam{
    	int resSupplementId;
    	int resHeadline;	
    	int resContent;
    }
    
    public String editResspupplement(){
    	
    	return jsonResult("ok");
    }
}
