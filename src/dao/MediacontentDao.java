package dao;

import java.util.ArrayList;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import tables.Mediacontent;

public class MediacontentDao {
	Session session=HibernateSessionFactory.currentSession();
	public void close(){
		session.close();
	}
	public void addMediacontent(Mediacontent ad){

		
		Transaction tran=session.beginTransaction();
		session.save(ad);
	    tran.commit();
		
	} 
	public void findMediacontent(Mediacontent ad){

		
		Transaction tran=session.beginTransaction();
		
	    tran.commit();
		
	} 
	public void updateMediacontent(Mediacontent ad){

		
		Transaction tran=session.beginTransaction();
		session.update(ad);
	    tran.commit();
		
	} 
	public void deleteMediacontent(Mediacontent ad){

		
		Transaction tran=session.beginTransaction();
		session.delete(ad);
	    tran.commit();
		
	} 
	public ArrayList<Mediacontent> getMediacontentAll(){

		
		Transaction tran=session.beginTransaction();
		String hqlsql = "from Mediacontent";
		 Query query = session.createQuery(hqlsql);
		 ArrayList<Mediacontent> resultStu = (ArrayList<Mediacontent>)query.list();
	    tran.commit();
		
		return resultStu;
	} 
	 public ArrayList<Mediacontent> queryByPage(int pageSize, int pageNow) {
	        Session session = null;
	        ArrayList<Mediacontent> sftlist = new ArrayList();
	        try {
	           // 获得session对象
	            //String hql = "from Employee emp";// 查询HQL语句
	           String HQL = "from Mediacontent";// 条件查询HQL语句
	            Query q = session.createQuery(HQL);// 执行查询操作
	            q.setFirstResult(pageSize * (pageNow - 1));
	            q.setMaxResults(pageSize);
	            sftlist = (ArrayList<Mediacontent>)q.list();
	        } catch (HibernateException e) {
	            e.printStackTrace();
	            System.out.println("查询失败");
	        } finally {
	        	HibernateSessionFactory.closeSession();// 关闭session
	        }
	        return sftlist;
	    }

}
