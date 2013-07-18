package dao;

import java.util.ArrayList;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import tables.Media;
import tables.Mediacontent;
import tables.Userinfo;

public class MediaDao {
    Session session=HibernateSessionFactory.currentSession();
    public void close(){
        session.close();
    }
public ArrayList<Mediacontent>getMediacontent(int Med){

        
        Transaction tran=session.beginTransaction();
        String hqlsql = "from Mediacontent where mediaId='"+Med+"' order by date desc";
        Query query = session.createQuery(hqlsql);
         ArrayList<Mediacontent> resultStu = (ArrayList<Mediacontent>)query.list();
        tran.commit();
        
        return resultStu;
    } 
public Media getMedia(int mediaid){

    
    Transaction tran=session.beginTransaction();
    String hqlsql = "from Media where mediaId='"+mediaid+"'";
    Query query = session.createQuery(hqlsql);
    Media resultStu = (Media) query.uniqueResult();
    tran.commit();
    
    return resultStu;
} 
public ArrayList<Media>getMediaByUid(int uid){

    
    Transaction tran=session.beginTransaction();
    String hqlsql = "from Media where uid='"+uid+"' order by mediaName";
    Query query = session.createQuery(hqlsql);
     ArrayList<Media> resultStu = (ArrayList<Media>)query.list();
    tran.commit();
    
    return resultStu;
} 
    public void addMedia(Media ad){

        
        Transaction tran=session.beginTransaction();
        session.save(ad);
        tran.commit();
    
    } 
    public void findMedia(Media ad){

        
        Transaction tran=session.beginTransaction();
        
        tran.commit();
        
    } 
    public void updateMedia(Media ad){

    
        Transaction tran=session.beginTransaction();
        session.update(ad);
        tran.commit();
    
    } 
    public void deleteMedia(Media ad){

    
        Transaction tran=session.beginTransaction();
        session.delete(ad);
        tran.commit();
        
    } 
    public ArrayList<Media> getMediaAll(){

        
        Transaction tran=session.beginTransaction();
        String hqlsql = "from Media order by mediaName";
         Query query = session.createQuery(hqlsql);
         ArrayList<Media> resultStu = (ArrayList<Media>)query.list();
        tran.commit();
        
        return resultStu;
    } 
     public ArrayList<Media> queryByPage(int pageSize, int pageNow) {
          
            ArrayList<Media> sftlist = new ArrayList();
            try {
               
                //String hql = "from Employee emp";// ��ѯHQL���
               String HQL = "from Media order by mediaName";// ������ѯHQL���
                Query q = session.createQuery(HQL);// ִ�в�ѯ����
                q.setFirstResult(pageSize * (pageNow - 1));
                q.setMaxResults(pageSize);
                sftlist = (ArrayList<Media>)q.list();
            } catch (HibernateException e) {
                e.printStackTrace();
                System.out.println("��ѯʧ��");
            } finally {
                HibernateSessionFactory.closeSession();// �ر�session
            }
            return sftlist;
        }

     public ArrayList<Media> getMediasByUserIds(int userIds[]){
    	MediacontentDao mcd = new MediacontentDao();
    	ArrayList<Mediacontent> mcList = mcd.getMediasByUserIds(userIds);
    	ArrayList<Media> result1 = null;
		if(mcList.size() > 0){
			result1 = new ArrayList<Media>();
			for(Mediacontent m : mcList){
				
				String hql = "from Media where mediaId=" + m.getMediaId();
				Transaction tran=session.beginTransaction();
				Query query =session.createQuery(hql);
				Media result2 = (Media)query.uniqueResult();
				tran.commit();
				System.out.println("result2=" + result2);
				if(!result1.contains(result2)){
					result1.add(result2);
				}
			}
		}
    	return result1;
     }
}
