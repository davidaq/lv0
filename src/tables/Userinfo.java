package tables;

import java.util.Date;



public class Userinfo  implements java.io.Serializable {


     private int uid;
     private String uname;
     private String upassword;
     private String umail;
     private String uphone;
     private String uportrait;
     private String usketch;
     private Date ustate;
     private String ulabel;
     private Integer uscore;

    public Userinfo() {
    }

	
    public Userinfo(int uid) {
        this.uid = uid;
    }
    public Userinfo(int uid, String uname, String upassword, String umail, String uphone, String uportrait, String usketch, Date ustate, String ulabel, Integer uscore) {
       this.uid = uid;
       this.uname = uname;
       this.upassword = upassword;
       this.umail = umail;
       this.uphone = uphone;
       this.uportrait = uportrait;
       this.usketch = usketch;
       this.ustate = ustate;
       this.ulabel = ulabel;
       this.uscore = uscore;
    }
   
    public int getUid() {
        return this.uid;
    }
    
    public void setUid(int uid) {
        this.uid = uid;
    }
    public String getUname() {
        return this.uname;
    }
    
    public void setUname(String uname) {
        this.uname = uname;
    }
    public String getUpassword() {
        return this.upassword;
    }
    
    public void setUpassword(String upassword) {
        this.upassword = upassword;
    }
    public String getUmail() {
        return this.umail;
    }
    
    public void setUmail(String umail) {
        this.umail = umail;
    }
    public String getUphone() {
        return this.uphone;
    }
    
    public void setUphone(String uphone) {
        this.uphone = uphone;
    }
    public String getUportrait() {
        return this.uportrait;
    }
    
    public void setUportrait(String uportrait) {
        this.uportrait = uportrait;
    }
    public String getUsketch() {
        return this.usketch;
    }
    
    public void setUsketch(String usketch) {
        this.usketch = usketch;
    }
    public Date getUstate() {
        return this.ustate;
    }
    
    public void setUstate(Date ustate) {
        this.ustate = ustate;
    }
    public String getUlabel() {
        return this.ulabel;
    }
    
    public void setUlabel(String ulabel) {
        this.ulabel = ulabel;
    }
    public Integer getUscore() {
        return this.uscore;
    }
    
    public void setUscore(Integer uscore) {
        this.uscore = uscore;
    }




}


