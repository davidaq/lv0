package dao;

import java.util.ArrayList;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import tables.Ressupplement;

public class RessupplementDao {
	Session session=HibernateSessionFactory.currentSession();
	public void close(){
		session.close();
	}
	public void addRessupplement(Ressupplement ad){

		
		Transaction tran=session.beginTransaction();
		session.save(ad);
	    tran.commit();
		
	} 
	public void findRessupplement(Ressupplement ad){

		
		Transaction tran=session.beginTransaction();
		
	    tran.commit();
	
	} 
	public void updateRessupplement(Ressupplement ad){

		
		Transaction tran=session.beginTransaction();
		session.update(ad);
	    tran.commit();
	
	} 
	public void deleteRessupplement(Ressupplement ad){

		
		Transaction tran=session.beginTransaction();
		session.delete(ad);
	    tran.commit();
		
	} 
	public ArrayList<Ressupplement> getRessupplementAll(){

		
		Transaction tran=session.beginTransaction();
		String hqlsql = "from Ressupplement";
		 Query query = session.createQuery(hqlsql);
		 ArrayList<Ressupplement> resultStu = (ArrayList<Ressupplement>)query.list();
	    tran.commit();
		
		return resultStu;
	} 
	 public ArrayList<Ressupplement> queryByPage(int pageSize, int pageNow) {
	       
	        ArrayList<Ressupplement> sftlist = new ArrayList();
	        try {
	           
	            //String hql = "from Employee emp";// ��ѯHQL���
	           String HQL = "from Ressupplement";// ������ѯHQL���
	            Query q = session.createQuery(HQL);// ִ�в�ѯ����
	            q.setFirstResult(pageSize * (pageNow - 1));
	            q.setMaxResults(pageSize);
	            sftlist = (ArrayList<Ressupplement>)q.list();
	        } catch (HibernateException e) {
	            e.printStackTrace();
	            System.out.println("��ѯʧ��");
	        } finally {
	        	HibernateSessionFactory.closeSession();// �ر�session
	        }
	        return sftlist;
	    }

}
