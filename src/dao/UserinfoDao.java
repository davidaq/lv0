package dao;

import java.util.ArrayList;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import tables.Admininfo;
import tables.Userinfo;

public class UserinfoDao {
	Session session=HibernateSessionFactory.currentSession();
	public void close(){
		session.close();
	}
public Userinfo findUserinfoByid(int id){

		
		Transaction tran=session.beginTransaction();
		String hql="from Userinfo where uid=' "+id+"'";
		Query query =session.createQuery(hql);
		Userinfo ad=(Userinfo)query.uniqueResult();
	    tran.commit();
		return ad;
	} 
	public void addUserinfo(Userinfo ad){

		
		Transaction tran=session.beginTransaction();
		session.save(ad);
	    tran.commit();
		
	} 
	
public Userinfo findUserinfoByname(String name){

		
	Transaction tran=session.beginTransaction();
	String hql="from Userinfo where uname='"+name+"'";
	Query query =session.createQuery(hql);
	Userinfo ad=(Userinfo)query.uniqueResult();
    tran.commit();
	return ad;
		
	} 
public ArrayList<Userinfo> findUserinfoLikename(String name){

	
	Transaction tran=session.beginTransaction();
	String hql="from Userinfo where uname like '%"+name+"%'";
	Query query =session.createQuery(hql);
	
	 ArrayList<Userinfo> resultStu = (ArrayList<Userinfo>)query.list();
    tran.commit();
	return resultStu;
		
	} 
public Userinfo findUserinfoByMail(String email){

	Transaction tran=session.beginTransaction();
	String hql="from Userinfo where umail='"+email+"'";
	Query query =session.createQuery(hql);
	Userinfo ad=(Userinfo)query.uniqueResult();
    tran.commit();
	return ad;
	
} 
	public void updateUserinfo(Userinfo ad){

		
		Transaction tran=session.beginTransaction();
		session.update(ad);
	    tran.commit();
		
	} 
	public void deleteUserinfo(Userinfo ad){

		
		Transaction tran=session.beginTransaction();
		session.delete(ad);
	    tran.commit();
		
	} 
	public ArrayList<Userinfo> getUserinfoAll(){

		
		Transaction tran=session.beginTransaction();
		String hqlsql = "from Userinfo";
		 Query query = session.createQuery(hqlsql);
		 ArrayList<Userinfo> resultStu = (ArrayList<Userinfo>)query.list();
	    tran.commit();
		
		return resultStu;
	} 
	 public ArrayList<Userinfo> queryByPage(int pageSize, int pageNow) {
	      
	        ArrayList<Userinfo> sftlist = new ArrayList();
	        try {
	            
	            //String hql = "from Employee emp";// 查询HQL语句
	           String HQL = "from Userinfo";// 条件查询HQL语句
	            Query q = session.createQuery(HQL);// 执行查询操作
	            q.setFirstResult(pageSize * (pageNow - 1));
	            q.setMaxResults(pageSize);
	            sftlist = (ArrayList<Userinfo>)q.list();
	        } catch (HibernateException e) {
	            e.printStackTrace();
	            System.out.println("查询失败");
	        } finally {
	        	HibernateSessionFactory.closeSession();// 关闭session
	        }
	        return sftlist;
	    }

}
