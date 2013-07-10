package dao;

import java.util.ArrayList;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import tables.Comment;
import tables.Landmark;

public class LandmarkDao {	
	Session session=HibernateSessionFactory.currentSession();
	public void close(){
		session.close();
	}
	public void addLandmark(Landmark ad){

	
	Transaction tran=session.beginTransaction();
	session.save(ad);
    tran.commit();
	
} 
	public Landmark findLandmarkbyid(int id){

		Transaction tran=session.beginTransaction();
		String hql="from Landmark where landmarkId=' "+id+"'";
		Query query =session.createQuery(hql);
		Landmark ad=(Landmark)query.uniqueResult();
	    tran.commit();
		return ad;
		
	} 
public void updateLandmark(Landmark ad){

	
	Transaction tran=session.beginTransaction();
	session.update(ad);
    tran.commit();
	
} 
public void deleteLandmark(Landmark ad){

	
	Transaction tran=session.beginTransaction();
	session.delete(ad);
    tran.commit();
	
} 
public ArrayList<Landmark> getLandmarkAll(){

	
	Transaction tran=session.beginTransaction();
	String hqlsql = "from Landmark";
	 Query query = session.createQuery(hqlsql);
	 ArrayList<Landmark> resultStu = (ArrayList<Landmark>)query.list();
    tran.commit();
	
	return resultStu;
} 
 public ArrayList<Landmark> queryByPage(int pageSize, int pageNow) {
        Session session = null;
        ArrayList<Landmark> sftlist = new ArrayList();
        try {
           
            //String hql = "from Employee emp";// ��ѯHQL���
           String HQL = "from Landmark";// ������ѯHQL���
            Query q = session.createQuery(HQL);// ִ�в�ѯ����
            q.setFirstResult(pageSize * (pageNow - 1));
            q.setMaxResults(pageSize);
            sftlist = (ArrayList<Landmark>)q.list();
        } catch (HibernateException e) {
            e.printStackTrace();
            System.out.println("��ѯʧ��");
        } finally {
        	HibernateSessionFactory.closeSession();// �ر�session
        }
        return sftlist;
    }}
