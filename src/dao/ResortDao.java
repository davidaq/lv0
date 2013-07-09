package dao;

import java.util.ArrayList;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import tables.Resort;

public class ResortDao {
	Session session=HibernateSessionFactory.currentSession();
	public void close(){
		session.close();
	}
	public void addResort(Resort ad){

		
		Transaction tran=session.beginTransaction();
		session.save(ad);
	    tran.commit();
		
	} 
	public void findResort(Resort ad){

		
		Transaction tran=session.beginTransaction();
		
	    tran.commit();
		
	} 
	public void updateResort(Resort ad){

		
		Transaction tran=session.beginTransaction();
		session.update(ad);
	    tran.commit();
		
	} 
	public void deleteResort(Resort ad){

		
		Transaction tran=session.beginTransaction();
		session.delete(ad);
	    tran.commit();
		
	} 
	public ArrayList<Resort> getResortAll(){

		
		Transaction tran=session.beginTransaction();
		String hqlsql = "from Resort";
		 Query query = session.createQuery(hqlsql);
		 ArrayList<Resort> resultStu = (ArrayList<Resort>)query.list();
	    tran.commit();
		
		return resultStu;
	} 
	 public ArrayList<Resort> queryByPage(int pageSize, int pageNow) {
	       
	        ArrayList<Resort> sftlist = new ArrayList();
	        try {
	         
	            //String hql = "from Employee emp";// ��ѯHQL���
	           String HQL = "from Resort";// ������ѯHQL���
	            Query q = session.createQuery(HQL);// ִ�в�ѯ����
	            q.setFirstResult(pageSize * (pageNow - 1));
	            q.setMaxResults(pageSize);
	            sftlist = (ArrayList<Resort>)q.list();
	        } catch (HibernateException e) {
	            e.printStackTrace();
	            System.out.println("��ѯʧ��");
	        } finally {
	        	HibernateSessionFactory.closeSession();// �ر�session
	        }
	        return sftlist;
	    }

}
