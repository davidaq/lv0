package dao;

import java.util.ArrayList;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import tables.Good;

public class GoodDao {
	Session session=HibernateSessionFactory.currentSession();
	public void addGood(Good ad){

		
		Transaction tran=session.beginTransaction();
		session.save(ad);
	    tran.commit();
		//session.close();
	} 
	
	public void close()
	{
		session.close();
	}
	public void findGood(Good ad){

		Session session=HibernateSessionFactory.currentSession();
		Transaction tran=session.beginTransaction();
		
	    tran.commit();
//		session.close();
	} 
	public void updateGood(Good ad){

		Session session=HibernateSessionFactory.currentSession();
		Transaction tran=session.beginTransaction();
		session.update(ad);
	    tran.commit();
		//session.close();
	} 
	public void deleteGood(Good ad){

		Session session=HibernateSessionFactory.currentSession();
		Transaction tran=session.beginTransaction();
		session.delete(ad);
	    tran.commit();
		//session.close();
	} 
	public ArrayList<Good> getGoodAll(){

		Session session=HibernateSessionFactory.currentSession();
		Transaction tran=session.beginTransaction();
		String hqlsql = "from Good";
		 Query query = session.createQuery(hqlsql);
		 ArrayList<Good> resultStu = (ArrayList<Good>)query.list();
	    tran.commit();
		//.close();
		return resultStu;
	} 
	 public ArrayList<Good> queryByPage(int pageSize, int pageNow) {
	        Session session = null;
	        ArrayList<Good> sftlist = new ArrayList();
	        try {
	            session = HibernateSessionFactory.currentSession();// ���session����
	            //String hql = "from Employee emp";// ��ѯHQL���
	           String HQL = "from Good";// ������ѯHQL���
	            Query q = session.createQuery(HQL);// ִ�в�ѯ����
	            q.setFirstResult(pageSize * (pageNow - 1));
	            q.setMaxResults(pageSize);
	            sftlist = (ArrayList<Good>)q.list();
	        } catch (HibernateException e) {
	            e.printStackTrace();
	            System.out.println("��ѯʧ��");
	        } finally {
	        	HibernateSessionFactory.closeSession();// �ر�session
	        }
	        return sftlist;
	    }

}
