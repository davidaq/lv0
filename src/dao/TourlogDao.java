package dao;

import java.util.ArrayList;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import tables.*;

public class TourlogDao {
	Session session=HibernateSessionFactory.currentSession();
	public void close(){
		session.close();
	}
	public void addTourlog(Tourlog ad){

		
		Transaction tran=session.beginTransaction();
		session.save(ad);
	    tran.commit();
		
	} 
	public void findTourlog(Tourlog ad){

		
		Transaction tran=session.beginTransaction();
		
	    tran.commit();
		
	} 
	public void updateTourlog(Tourlog ad){

		
		Transaction tran=session.beginTransaction();
		session.update(ad);
	    tran.commit();
		
	} 
	public void deleteTourlog(Tourlog ad){

		
		Transaction tran=session.beginTransaction();
		session.delete(ad);
	    tran.commit();
		
	} 
	public ArrayList<Tourlog> getTourlogAll(){

		
		Transaction tran=session.beginTransaction();
		String hqlsql = "from Tourlog";
		 Query query = session.createQuery(hqlsql);
		 ArrayList<Tourlog> resultStu = (ArrayList<Tourlog>)query.list();
	    tran.commit();
	
		return resultStu;
	} 
	 public ArrayList<Tourlog> queryByPage(int pageSize, int pageNow) {
	       
	        ArrayList<Tourlog> sftlist = new ArrayList();
	        try {
	            
	            //String hql = "from Employee emp";// 查询HQL语句
	           String HQL = "from Tourlog";// 条件查询HQL语句
	            Query q = session.createQuery(HQL);// 执行查询操作
	            q.setFirstResult(pageSize * (pageNow - 1));
	            q.setMaxResults(pageSize);
	            sftlist = (ArrayList<Tourlog>)q.list();
	        } catch (HibernateException e) {
	            e.printStackTrace();
	            System.out.println("查询失败");
	        } finally {
	        	HibernateSessionFactory.closeSession();// 关闭session
	        }
	        return sftlist;
	    }
	 public ArrayList<Tourlog> queryByPageUser(ArrayList<Userinfo> ulist,int pageSize, int pageNow) {
	       
	        ArrayList<Tourlog> sftlist = new ArrayList();
	        try {
//	        	String Hql="from Tourlog where author=";
//	        	int i=0;
//	        	for(i=0;i<ulist.size()-1;i++){
//	            
//	        	Hql+="'"+ulist.get(i).getUid()+"'"+" or author=";
//	        	}
//	        	Hql+="'"+ulist.get(i).getUid()+"'";
//	        	System.out.println(Hql);
	        	String temp="";
	        	int i;
	        	for(i=0;i<ulist.size()-1;i++){
	        		temp+=ulist.get(i).getUid()+",";
	        		System.out.println(temp);
	        	}
	        	temp+=ulist.get(i).getUid();
	        	String Hql="select d from Tourlog d where d.author in ("+temp+")";
	        	System.out.println("***************************"+Hql);
	            Query q = session.createQuery(Hql);// 执行查询操作
	            q.setFirstResult(pageSize * (pageNow - 1));
	            q.setMaxResults(pageSize);
	            sftlist = (ArrayList<Tourlog>)q.list();
	        } catch (HibernateException e) {
	            e.printStackTrace();
	            System.out.println("查询失败");
	        } finally {
	        	HibernateSessionFactory.closeSession();// 关闭session
	        }
	        return sftlist;
	    }
}
