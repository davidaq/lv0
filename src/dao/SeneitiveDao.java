package dao;

import java.util.ArrayList;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import tables.Sensitive;

public class SeneitiveDao {
	Session session=HibernateSessionFactory.currentSession();
	public void close(){
		session.close();
	}
	public void addSensitive(Sensitive ad){

		
		Transaction tran=session.beginTransaction();
		session.save(ad);
	    tran.commit();
		
	} 
	public void findSensitive(Sensitive ad){

		
		Transaction tran=session.beginTransaction();
		
	    tran.commit();
		
	} 
	public void updateSensitive(Sensitive ad){

		
		Transaction tran=session.beginTransaction();
		session.update(ad);
	    tran.commit();
		
	} 
	public void deleteSensitive(Sensitive ad){

		
		Transaction tran=session.beginTransaction();
		session.delete(ad);
	    tran.commit();
		
	} 
	public ArrayList<Sensitive> getSensitiveAll(){

		
		Transaction tran=session.beginTransaction();
		String hqlsql = "from Sensitive";
		 Query query = session.createQuery(hqlsql);
		 ArrayList<Sensitive> resultStu = (ArrayList<Sensitive>)query.list();
	    tran.commit();
		
		return resultStu;
	} 
	 public ArrayList<Sensitive> queryByPage(int pageSize, int pageNow) {
	      
	        ArrayList<Sensitive> sftlist = new ArrayList();
	        try {
	          
	            //String hql = "from Employee emp";// 查询HQL语句
	           String HQL = "from Sensitive";// 条件查询HQL语句
	            Query q = session.createQuery(HQL);// 执行查询操作
	            q.setFirstResult(pageSize * (pageNow - 1));
	            q.setMaxResults(pageSize);
	            sftlist = (ArrayList<Sensitive>)q.list();
	        } catch (HibernateException e) {
	            e.printStackTrace();
	            System.out.println("查询失败");
	        } finally {
	        	HibernateSessionFactory.closeSession();// 关闭session
	        }
	        return sftlist;
	    }
}
