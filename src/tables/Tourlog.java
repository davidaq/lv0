package tables;
// Generated Jul 7, 2013 11:43:38 AM by Hibernate Tools 3.2.1.GA


import java.util.Date;

/**
 * Tourlog generated by hbm2java
 */
public class Tourlog  implements java.io.Serializable {


     private Integer tourLogId;
     private Integer author;
     private String content;
     private Integer relaySourceId;
     private Integer relayFromId;
     private String abstract_;
     private Date date;

    public Tourlog() {
    }

    public Tourlog(Integer author, String content, Integer relaySourceId, Integer relayFromId, String abstract_, Date date) {
       this.author = author;
       this.content = content;
       this.relaySourceId = relaySourceId;
       this.relayFromId = relayFromId;
       this.abstract_ = abstract_;
       this.date = date;
    }
   
    public Integer getTourLogId() {
        return this.tourLogId;
    }
    
    public void setTourLogId(Integer tourLogId) {
        this.tourLogId = tourLogId;
    }
    public Integer getAuthor() {
        return this.author;
    }
    
    public void setAuthor(Integer author) {
        this.author = author;
    }
    public String getContent() {
        return this.content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    public Integer getRelaySourceId() {
        return this.relaySourceId;
    }
    
    public void setRelaySourceId(Integer relaySourceId) {
        this.relaySourceId = relaySourceId;
    }
    public Integer getRelayFromId() {
        return this.relayFromId;
    }
    
    public void setRelayFromId(Integer relayFromId) {
        this.relayFromId = relayFromId;
    }
    public String getAbstract_() {
        return this.abstract_;
    }
    
    public void setAbstract_(String abstract_) {
        this.abstract_ = abstract_;
    }
    public Date getDate() {
        return this.date;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }




}

