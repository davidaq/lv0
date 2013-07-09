package dao;

import java.util.ArrayList;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import tables.Media;

public class MediaDao {
	Session session=HibernateSessionFactory.currentSession();
	public void close(){
		session.close();
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
		String hqlsql = "from Media";
		 Query query = session.createQuery(hqlsql);
		 ArrayList<Media> resultStu = (ArrayList<Media>)query.list();
	    tran.commit();
		
		return resultStu;
	} 
	 public ArrayList<Media> queryByPage(int pageSize, int pageNow) {
	      
	        ArrayList<Media> sftlist = new ArrayList();
	        try {
	           
	            //String hql = "from Employee emp";// ��ѯHQL���
	           String HQL = "from Media";// ������ѯHQL���
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

}
