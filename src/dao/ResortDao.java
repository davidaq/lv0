package dao;

import java.util.ArrayList;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import tables.Resort;
import tables.Userinfo;

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
	public Resort findResortByname(String name){

		Transaction tran=session.beginTransaction();
		String hql="from Resort where resName='"+name+"'";
		Query query =session.createQuery(hql);
		
		Resort te=(Resort) query.uniqueResult();
	    tran.commit();
		return te;
		
		
	} 
	public Resort findResortById(int id){

		Transaction tran=session.beginTransaction();
		String hql="from Resort where resortId='"+id+"'";
		Query query =session.createQuery(hql);
		
		Resort te=(Resort) query.uniqueResult();
	    tran.commit();
		return te;
		
		
	} 
	public ArrayList<Resort> findResortLikename(String name){

		
		Transaction tran=session.beginTransaction();
		String hql="from Resort where resName like '%"+name+"%'";
		Query query =session.createQuery(hql);
		
		 ArrayList<Resort> resultStu = (ArrayList<Resort>)query.list();
	    tran.commit();
		return resultStu;
			
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
	         
	            //String hql = "from Employee emp";// 查询HQL语句
	           String HQL = "from Resort";// 条件查询HQL语句
	            Query q = session.createQuery(HQL);// 执行查询操作
	            q.setFirstResult(pageSize * (pageNow - 1));
	            q.setMaxResults(pageSize);
	            sftlist = (ArrayList<Resort>)q.list();
	        } catch (HibernateException e) {
	            e.printStackTrace();
	            System.out.println("查询失败");
	        } finally {
	        	HibernateSessionFactory.closeSession();// 关闭session
	        }
	        return sftlist;
	    }

}
