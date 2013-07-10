
package actions;

import java.util.ArrayList;
import java.util.Date;

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
    
    public static class SearchResortByNameParam{
    	String searchText;
    }
    
    public String searchResortByName(){
    	SearchResortByNameParam param = (SearchResortByNameParam) getParam(SearchResortByNameParam.class);
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
    	ResortDao rd = new ResortDao();
    	ArrayList<Ressupplement> rs = null;
    	rs = rd.findResSuplementByid(param.resortId);
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
    	String resHeadline;	
    	String resContent;
    }
    
    public String editResspupplement(){
    	EditResspupplementParam param = (EditResspupplementParam) getParam(EditResspupplementParam.class);
    	if (param.resHeadline == null || param.resHeadline.equals("")){
    		return jsonResult("resHeadline");
    	}
    	if (param.resContent == null || param.resContent.equals("")){
    		return jsonResult("resContent");
    	}
    	
    	RessupplementDao rsd = new RessupplementDao();
    	Ressupplement rs = null;
    	rs = rsd.findRessupplementbyid(param.resSupplementId);
    	if (rs == null){
    		return jsonResult("resSupplementId");
    	}
    	
    	rs.setResHeadline(param.resHeadline);
    	rs.setResContent(param.resContent);
    	return jsonResult("ok");
    }
    
    
    public static class AddResortParam{
    	tables.Resort resort;
    }
    
    public String addResort(){
    	AddResortParam param = (AddResortParam) getParam(AddResortParam.class);
    	if(param.resort == null){
    		return jsonResult("resort");
    	}
    	
    	param.resort.setResortId(null);
    	
    	if(param.resort.getResName() == null || param.resort.getResName().equals("")){
    		return jsonResult("resName");
    	}
    	if(param.resort.getResAddress() == null || param.resort.getResAddress().equals("")){
    		return jsonResult("resAddress");
    	}
    	if(param.resort.getResLabel() == null || param.resort.getResLabel().equals("")){
    		return jsonResult("resLabel");
    	}
    	if(param.resort.getResState() == null || param.resort.getResState().equals("")){
    		param.resort.setResState("待审核");
    	}
    	
    	ResortDao rd = new ResortDao();
    	rd.addResort(param.resort);    	
    	return jsonResult("ok");
    }
    
    
    public static class AddRessupplementParam{
    	Ressupplement ressupplement;
    }
    
    public String addRessupplement(){
    	AddRessupplementParam param = (AddRessupplementParam) getParam(AddRessupplementParam.class);
    	if(param.ressupplement == null){
    		return jsonResult("ressupplement");
    	}
    	
    	param.ressupplement.setResSupplementId(null);
    	
    	if(param.ressupplement.getResortId() == null || param.ressupplement.getResortId().equals("")){
    		return jsonResult("ressortId");
    	}
    	if(param.ressupplement.getResHeadline() == null || param.ressupplement.getResHeadline().equals("")){
    		return jsonResult("resHeadline");
    	}
    	if(param.ressupplement.getResContent() == null || param.ressupplement.getResContent().equals("")){
    		return jsonResult("resContent");
    	}
    	if(param.ressupplement.getResAuthor() == null || param.ressupplement.getResAuthor().equals("")){
    		return jsonResult("resAuthor");
    	}
    	if(param.ressupplement.getResDate() == null || param.ressupplement.getResDate().equals("")){
    		param.ressupplement.setResDate(new Date());
    	}
    	
    	RessupplementDao rsd = new RessupplementDao();
    	rsd.addRessupplement(param.ressupplement);   	
    	
    	return jsonResult("ok");
    }
}
