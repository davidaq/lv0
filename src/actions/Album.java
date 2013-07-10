package actions;

import java.util.ArrayList;
import java.util.Date;
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
		Media m = null;
		m = md.getMedia(param.albumId);
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
		m = md.getMedia(param.albumId);
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
		mc = mcd.findMediacontentbyid(param.mediaId);
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
		MediaDao md = new MediaDao();
		ArrayList<Mediacontent> list = null;
		list = md.getMediacontent(param.albumId);
		if(list != null){
			Iterator i = list.iterator();
			while(i.hasNext()){
				mcd.deleteMediacontent((Mediacontent)i.next());
			}
		}
		
		Media m = md.getMedia(param.albumId);
		if(m == null){
			return jsonResult("albumId");
		}
		md.deleteMedia(m);
		return jsonResult("ok");
	}
	
	
	public static class DeleteMediaParam{
		int mediaId;
	}
	
	public String deleteMedia(){
		DeleteMediaParam param = (DeleteMediaParam) getParam(DeleteMediaParam.class);
		MediacontentDao mcd = new MediacontentDao();
		Mediacontent m = mcd.findMediacontentbyid(param.mediaId);
		if(m == null){
			return jsonResult("mediaId");
		}		
		mcd.deleteMediacontent(m);
		return jsonResult("ok");
	}
	
	
	public static class AddAlbumParam{
		Media album;
	}
	
	public String addAlbum(){
		AddAlbumParam param = (AddAlbumParam) getParam(AddAlbumParam.class);
		if (param.album == null){
			return jsonResult("album");
		}
		param.album.setMediaId(null);
		if (param.album.getMediaName() == null || param.album.getMediaName().equals("")){
			return jsonResult("mediaName");
		}
		if (param.album.getUid() == null || param.album.getUid().equals("")){
			return jsonResult("uid");
		}
		if (param.album.getDate() == null || param.album.getDate().equals("")){
			param.album.setDate(new Date());
		}
		
		MediaDao md = new MediaDao();
		md.addMedia(param.album);
		
		return jsonResult("ok");
	}
	
	
	public static class AddMediaParam{
		Mediacontent media;
	}
	
	public String addMedia(){
		AddMediaParam param = (AddMediaParam) getParam(AddMediaParam.class);
		if (param.media == null){
			return jsonResult("media");
		}
		param.media.setMediaContentId(null);
		if (param.media.getUid() == null || param.media.getUid().equals("")){
			return jsonResult("uid");
		}
		if (param.media.getType() == null || param.media.getType().equals("")){
			return jsonResult("type");
		}
		if (param.media.getAddress() == null || param.media.getAddress().equals("")){
			return jsonResult("address");
		}
		if (param.media.getHeadline() == null || param.media.getHeadline().equals("")){
			return jsonResult("headline");
		}
		if (param.media.getMediaId() == null || param.media.getMediaId().equals("")){
			return jsonResult("mediaId");
		}
		
		if (param.media.getDate() == null || param.media.getDate().equals("")){
			param.media.setDate(new Date());
		}
		
		MediacontentDao mcd = new MediacontentDao();
		mcd.addMediacontent(param.media);
		
		return jsonResult("ok");
	}
}
