package tables;
// Generated Jul 7, 2013 11:43:38 AM by Hibernate Tools 3.2.1.GA



/**
 * Resortremark generated by hbm2java
 */
public class Resortremark  implements java.io.Serializable {


     private Integer resortRemarkId;
     private Integer authorId;
     private Integer resortId;
     private Integer resRemark;

    public Resortremark() {
    }

    public Resortremark(Integer authorId, Integer resortId, Integer resRemark) {
       this.authorId = authorId;
       this.resortId = resortId;
       this.resRemark = resRemark;
    }
   
    public Integer getResortRemarkId() {
        return this.resortRemarkId;
    }
    
    public void setResortRemarkId(Integer resortRemarkId) {
        this.resortRemarkId = resortRemarkId;
    }
    public Integer getAuthorId() {
        return this.authorId;
    }
    
    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }
    public Integer getResortId() {
        return this.resortId;
    }
    
    public void setResortId(Integer resortId) {
        this.resortId = resortId;
    }
    public Integer getResRemark() {
        return this.resRemark;
    }
    
    public void setResRemark(Integer resRemark) {
        this.resRemark = resRemark;
    }




}

