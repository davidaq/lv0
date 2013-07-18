package actions;

import dao.AttentionDao;
import dao.MediaDao;
import dao.MediacontentDao;
import dao.ResortDao;
import dao.UserinfoDao;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import javax.servlet.ServletContext;
import org.apache.struts2.util.ServletContextAware;

import tables.Attention;
import tables.Media;
import tables.Mediacontent;
import tables.Userinfo;

public class Album extends BaseAction implements ServletContextAware {

    private ServletContext sc;
    @Override
    public void setServletContext(ServletContext sc) {
        this.sc = sc;
    }
	
    public static class GetAlbumsByUserIdParam{
            int uId;
    }

    public String getAlbumsByUserId(){
            GetAlbumsByUserIdParam param = (GetAlbumsByUserIdParam) getParam(GetAlbumsByUserIdParam.class);
            MediaDao md = new MediaDao();
            ArrayList<Media> m = md.getMediaByUid(param.uId);

            return jsonResult(m);
    }

    
    public String getMyAlbums(){
        MediaDao md = new MediaDao();
        Userinfo userinfo = (Userinfo)session("myUserinfo");
        ArrayList<Media> m = md.getMediaByUid(userinfo.getUid());
        return jsonResult(m);
    }
    
    
    public String getResortFromAttention(){
    	AttentionDao atd = new AttentionDao();
    	MediaDao md = new MediaDao();
    	Userinfo u = (Userinfo)session("myUserinfo");
    	ArrayList<Attention> at = atd.GetAttentionsByUserId(u.getUid());
    	ArrayList<tables.Media> rList = null;
    	
    	if(at.size() > 0){
    		int userIds[] = new int[at.size()];
    		for(int i = 0; i < at.size(); i++){
    			userIds[i] = at.get(i).getAttedUser();
    		}
    		rList = md.getMediasByUserIds(userIds);
    	}
    	return jsonResult(rList);
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
            int mediaId;
            String mediaName;
            String mediaCover;
    }

    public String editAlbum(){
            EditAlbumParam param = (EditAlbumParam) getParam(EditAlbumParam.class);
            Media m;


            if (param.mediaName == null || param.mediaName.equals("")){
                    return jsonResult("mediaName");
            }
            if (param.mediaCover == null || param.mediaCover.equals("")){
                    return jsonResult("mediaCoverd");
            }

            MediaDao md = new MediaDao();
            m = md.getMedia(param.mediaId);
            if (m == null){
                    return jsonResult("albumId");
            }
            m.setMediaName(param.mediaName);
            m.setMediaCover(param.mediaCover);
            md.updateMedia(m);
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
            Userinfo userinfo = (Userinfo)session("myUserinfo");
            MediacontentDao mcd = new MediacontentDao();
            MediaDao md = new MediaDao();
            Media m = md.getMedia(param.albumId);
            if(m == null){
            	return jsonResult("albumId");
            }
            if(m.getUid() != userinfo.getUid()){
            	return jsonResult("albumId");
            }
            ArrayList<Mediacontent> list = null;
            list = md.getMediacontent(param.albumId);
            if(list != null){
                for(Mediacontent i : list) {
                    new File(sc.getRealPath("/" + i.getAddress())).delete();
                    mcd.deleteMediacontent(i);
                }
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
            Userinfo userinfo = (Userinfo)session("myUserinfo");
            Mediacontent m = mcd.findMediacontentbyid(param.mediaId);
            if(m == null){
                    return jsonResult("mediaId");
            }
            if(m.getUid() != userinfo.getUid()){
            	return jsonResult("mediaId");
            }
            mcd.deleteMediacontent(m);
            return jsonResult("ok");
    }


    public static class AddAlbumParam{
            String albumName;
    }

    public String addAlbum(){
            AddAlbumParam param = (AddAlbumParam) getParam(AddAlbumParam.class);
            if (param.albumName == null || param.albumName.equals("")){
                    return jsonResult("albumName");
            }
            Media media = new Media();
            media.setMediaId(null);
            Userinfo myUserinfo = (Userinfo)session("myUserinfo");
            media.setUid(myUserinfo.getUid());
            media.setDate(new Date());
            media.setMediaName(param.albumName);
            media.setMediaCover(null);
            MediaDao md = new MediaDao();
            md.addMedia(media);

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


    public static class MoveMediaParam{
            int mediaId;
            int albumId;
    }

    public String MoveMedia(){
            MoveMediaParam param = (MoveMediaParam) getParam(MoveMediaParam.class);
            MediacontentDao mcd = new MediacontentDao();
            Mediacontent mc = mcd.findMediacontentbyid(param.mediaId);
            if(mc == null){
                    return jsonResult("mediaId");
            }
            MediaDao md = new MediaDao();
            Media m = md.getMedia(param.albumId);
            if(m == null){
                    return jsonResult("albumId");
            }

            mc.setMediaId(param.albumId);

            return jsonResult("ok");
    }
}
