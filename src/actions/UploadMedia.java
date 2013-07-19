
package actions;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.util.ServletContextAware;
import tables.Mediacontent;
import tables.Userinfo;

public class UploadMedia extends BaseAction implements ServletContextAware {

    private ServletContext sc;
    @Override
    public void setServletContext(ServletContext sc) {
        this.sc = sc;
    }
    
    private File attachment;
    private String attachmentContentType;
    private String attachmentFileName;

    public void setAttachment(File attachment) {
        this.attachment = attachment;
    }

    public void setAttachmentContentType(String attachmentContentType) {
        this.attachmentContentType = attachmentContentType;
    }

    public void setAttachmentFileName(String attachmentFileName) {
        this.attachmentFileName = attachmentFileName;
    }
    
    private Integer albumId;
    private String title, description;

    public void setAlbumId(Integer albumId) {
        this.albumId = albumId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    @Override
    public String execute() throws IOException {
        String[] parts = attachmentFileName.split("\\.");
        String ext;
        if(parts.length <= 1) {
            ext = "";
        } else {
            ext = "." + parts[parts.length - 1];
        }
    	Userinfo ui = (Userinfo)session("myUserinfo");
        String fname = "upload/" + ui.getUid() + "_" + new Date().getTime() + ext;
        Album.AddMediaParam o = new Album.AddMediaParam();
        o.media = new Mediacontent();
        o.media.setAddress(fname);
        o.media.setDate(new Date());
        o.media.setMediaId(albumId);
        o.media.setHeadline(toAbstract(title));
        o.media.setMediaAbstract(toAbstract(description));
        o.media.setUid(ui.getUid());
        o.media.setType("image");
        Album action = new Album();
        action.setParamObject(o);
        if(action.addMedia().equals(jsonResult("ok"))) {
            File full = new File(sc.getRealPath("/") + fname);
            FileUtils.copyFile(attachment, full);
            BufferedImage img = new BufferedImage(310, 200, BufferedImage.TYPE_INT_RGB);
            img.createGraphics().drawImage(ImageIO.read(full).getScaledInstance(310, 200, Image.SCALE_SMOOTH),0,0,null);
            ImageIO.write(img, "jpg", new File(sc.getRealPath("/") + fname + "_thumb.jpg"));
        }
        return "success";
    }
}
