package tables;
// Generated Jul 7, 2013 11:43:38 AM by Hibernate Tools 3.2.1.GA



/**
 * Sensitive generated by hbm2java
 */
public class Sensitive  implements java.io.Serializable {


     private Integer sensitiveId;
     private String sensitiveWords;

    public Sensitive() {
    }

    public Sensitive(String sensitiveWords) {
       this.sensitiveWords = sensitiveWords;
    }
   
    public Integer getSensitiveId() {
        return this.sensitiveId;
    }
    
    public void setSensitiveId(Integer sensitiveId) {
        this.sensitiveId = sensitiveId;
    }
    public String getSensitiveWords() {
        return this.sensitiveWords;
    }
    
    public void setSensitiveWords(String sensitiveWords) {
        this.sensitiveWords = sensitiveWords;
    }




}


