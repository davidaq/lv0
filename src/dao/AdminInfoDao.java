package dao;
import java.util.ArrayList;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import tables.*;



public class AdminInfoDao {
	Session session=HibernateSessionFactory.currentSession();
	public void close()
	{
		session.close();
	}
	public void addAdminInfo(Admininfo ad){

		
		Transaction tran=session.beginTransaction();
		session.save(ad);
	    tran.commit();
		session.close();
	} 
	public Admininfo findAdminInfoByid(int id){

		
		Transaction tran=session.beginTransaction();
		String hql="from Admininfo where adId=' "+id+"'";
		Query query =session.createQuery(hql);
		Admininfo ad=(Admininfo)query.uniqueResult();
	    tran.commit();
		return ad;
	}
	public Admininfo findAdminInfoByAdName(String adName){

		
		Transaction tran=session.beginTransaction();
		String hql="from Admininfo where adName=' "+adName+"'";
		Query query =session.createQuery(hql);
		Admininfo ad=(Admininfo)query.uniqueResult();
	    tran.commit();
		return ad;
	} 
	public void updateAdminInfo(Admininfo ad){

		
		Transaction tran=session.beginTransaction();
		session.update(ad);
	    tran.commit();
		
	} 
	public void deleteAdminInfo(Admininfo ad){

		
		Transaction tran=session.beginTransaction();
		session.delete(ad);
	    tran.commit();
	
	} 
	public ArrayList<Admininfo> getAdminInfoAll(){

		
		Transaction tran=session.beginTransaction();
		String hqlsql = "from Admininfo";
		 Query query = session.createQuery(hqlsql);
		 ArrayList<Admininfo> resultStu = (ArrayList<Admininfo>)query.list();
	    tran.commit();
		
		return resultStu;
	} 
	 public ArrayList<Admininfo> queryByPage(int pageSize, int pageNow, String HQL) {
	      
	        ArrayList<Admininfo> sftlist = new ArrayList();
	        try {
	            // ���session����
	            //String hql = "from Employee emp";// ��ѯHQL���
	            HQL = "from Admininfo";// ������ѯHQL���
	            Query q = session.createQuery(HQL);// ִ�в�ѯ����
	            q.setFirstResult(pageSize * (pageNow - 1));
	            q.setMaxResults(pageSize);
	            sftlist = (ArrayList<Admininfo>)q.list();
	        } catch (HibernateException e) {
	            e.printStackTrace();
	            System.out.println("��ѯʧ��");
	        } finally {
	        	HibernateSessionFactory.closeSession();// �ر�session
	        }
	        return sftlist;
	    }

}
