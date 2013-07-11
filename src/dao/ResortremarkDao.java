package dao;

import java.util.ArrayList;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import tables.Planitem;
import tables.Resortremark;

public class ResortremarkDao {
	Session session=HibernateSessionFactory.currentSession();
	public void close(){
		session.close();
	}
	public void addResortremark(Resortremark ad){

		
		Transaction tran=session.beginTransaction();
		session.save(ad);
	    tran.commit();
		
	} 
	public Resortremark findResortremarkbyid(int id){

		Transaction tran=session.beginTransaction();
		String hql="from Resortremark where resortRemarkId=' "+id+"'";
		Query query =session.createQuery(hql);
		Resortremark ad=(Resortremark)query.uniqueResult();
	    tran.commit();
		return ad;
		
	} 
	public void updateResortremark(Resortremark ad){

		
		Transaction tran=session.beginTransaction();
		session.update(ad);
	    tran.commit();
		
	} 
	public void deleteResortremark(Resortremark ad){

		
		Transaction tran=session.beginTransaction();
		session.delete(ad);
	    tran.commit();
		
	} 
	public ArrayList<Resortremark> getResortremarkAll(){

		
		Transaction tran=session.beginTransaction();
		String hqlsql = "from Resortremark";
		 Query query = session.createQuery(hqlsql);
		 ArrayList<Resortremark> resultStu = (ArrayList<Resortremark>)query.list();
	    tran.commit();
		
		return resultStu;
	} 
	 public ArrayList<Resortremark> queryByPage(int pageSize, int pageNow) {
	      
	        ArrayList<Resortremark> sftlist = new ArrayList();
	        try {
	           
	            //String hql = "from Employee emp";// ��ѯHQL���
	           String HQL = "from Resortremark";// ������ѯHQL���
	            Query q = session.createQuery(HQL);// ִ�в�ѯ����
	            q.setFirstResult(pageSize * (pageNow - 1));
	            q.setMaxResults(pageSize);
	            sftlist = (ArrayList<Resortremark>)q.list();
	        } catch (HibernateException e) {
	            e.printStackTrace();
	            System.out.println("��ѯʧ��");
	        } finally {
	        	HibernateSessionFactory.closeSession();// �ر�session
	        }
	        return sftlist;
	    }
	 
	 public ArrayList<Resortremark> findResortremarkByid(int id){
		Transaction tran=session.beginTransaction();
		String hql="from Resortremark where resortId='"+id+"'";
		Query query =session.createQuery(hql);
		
		 ArrayList<Resortremark> resultStu = (ArrayList<Resortremark>)query.list();
	    tran.commit();
		return resultStu;
	}
}
