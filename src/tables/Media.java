package tables;
// Generated Jul 7, 2013 11:43:38 AM by Hibernate Tools 3.2.1.GA


import java.util.Date;

/**
 * Media generated by hbm2java
 */
public class Media  implements java.io.Serializable {


     private Integer mediaId;
     private Integer uid;
     private Date date;
     private String mediaCover;
     private String mediaName;

    public Media() {
    }

    public Media(Integer uid, Date date, String mediaCover, String mediaName) {
       this.uid = uid;
       this.date = date;
       this.mediaCover = mediaCover;
       this.mediaName = mediaName;
    }
   
    public Integer getMediaId() {
        return this.mediaId;
    }
    
    public void setMediaId(Integer mediaId) {
        this.mediaId = mediaId;
    }
    public Integer getUid() {
        return this.uid;
    }
    
    public void setUid(Integer uid) {
        this.uid = uid;
    }
    public Date getDate() {
        return this.date;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }
    public String getMediaCover() {
        return this.mediaCover;
    }
    
    public void setMediaCover(String mediaCover) {
        this.mediaCover = mediaCover;
    }
    public String getMediaName() {
        return this.mediaName;
    }
    
    public void setMediaName(String mediaName) {
        this.mediaName = mediaName;
    }




}

