package actions;

import java.util.ArrayList;
import java.util.Iterator;

import dao.MediaDao;
import dao.MediacontentDao;
import tables.Media;
import tables.Mediacontent;

public class Album extends BaseAction{
	public static class GetAlbumsByUserIdParam{
		int uId;
	}
	
	public String getAlbumsByUserId(){
		GetAlbumsByUserIdParam param = (GetAlbumsByUserIdParam) getParam(GetAlbumsByUserIdParam.class);
		MediaDao md = new MediaDao();
		ArrayList<Media> m = md.getMediaByUid(param.uId);
		
		return jsonResult(m);
	}
	
	
	public static class GetAlbumByIdParam{
		int albumId;
	}
	
	public String getAlbumById(){
		GetAlbumByIdParam param = (GetAlbumByIdParam) getParam(GetAlbumByIdParam.class);
		MediaDao md = new MediaDao();
		ArrayList<Media> m = null;
		//m = md.getMediaByid(param.albumId);
		return jsonResult(m);
	}
	
	
	public static class GetMediasByAlbumIdParam{
		int albumId;
	}
	
	public String getMediasByAlbumId(){
		GetMediasByAlbumIdParam param = (GetMediasByAlbumIdParam) getParam(GetMediasByAlbumIdParam.class);
		MediaDao md = new MediaDao();
		ArrayList<Mediacontent> mc = md.getMediacontent(param.albumId);
		return jsonResult(mc);
	}
	
	
	public static class EditAlbumParam{
		int albumId;
		String mediaName;
		String mediaCover;
	}
	
	public String editAlbum(){
		EditAlbumParam param = (EditAlbumParam) getParam(EditAlbumParam.class);
		Media m = null;

		
		if (param.mediaName == null || param.mediaName.equals("")){
			return jsonResult("mediaName");
		}
		if (param.mediaCover == null || param.mediaCover.equals("")){
			return jsonResult("mediaCover");
		}
		
		MediaDao md = new MediaDao();
		//m = md.getMediaById(param.albumId);
		if (m == null){
			return jsonResult("albumId");
		}
		
		m.setMediaName(param.mediaName);
		m.setMediaCover(param.mediaCover);
		return jsonResult("ok");
	}
	
	
	public static class EditMediaParam{
		int mediaId;
		String headline;
		String mediaAbstract;
	}
	
	public String editMedia(){
		EditMediaParam param = (EditMediaParam) getParam(EditMediaParam.class);
		
		if(param.headline == null || param.headline.equals("")){
			return jsonResult("headline");
		}
		
		if(param.mediaAbstract == null || param.mediaAbstract.equals("")){
			return jsonResult("mediaAbstract");
		}
		
		MediacontentDao mcd = new MediacontentDao();
		Mediacontent mc = null;
		//mc = mcd.findMediacontentById(param.mediaId);
		if(mc == null){
			return jsonResult("mediaId");
		}
		
		mc.setHeadline(param.headline);
		mc.setMediaAbstract(param.mediaAbstract);
		mcd.updateMediacontent(mc);		
		return jsonResult("ok");
	}
	
	
	public static class DeleteAlbumParam{
		int albumId;
	}
	
	public String deleteAlbum(){
		DeleteAlbumParam param = (DeleteAlbumParam) getParam(DeleteAlbumParam.class);
		MediacontentDao mcd = new MediacontentDao();
		ArrayList<Mediacontent> list = null;
		//list = mcd.getMediacontentByMediaId(param.albumId);
		if(list != null){
			Iterator i = list.iterator();
			while(i.hasNext()){
				//mcd.deleteMediacontent(((Mediacontent)i.next()).getMediaId());
			}
		}
		MediaDao md = new MediaDao();
		//md.deleteMediaById(param.albumId);
		return jsonResult("ok");
	}
	
	
	public static class DeleteMediaParam{
		int mediaId;
	}
	
	public String deleteMedia(){
		DeleteMediaParam param = (DeleteMediaParam) getParam(DeleteMediaParam.class);
		MediacontentDao mcd = new MediacontentDao();
		//mcd.deleteMediacontent(param.mediaId);
		return jsonResult("ok");
	}
}
