package dao;

import java.util.ArrayList;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import tables.Resortremark;
import tables.Ressupplement;

public class RessupplementDao {
	//根据景点ID查询
	Session session=HibernateSessionFactory.currentSession();
	public void close(){
		session.close();
	}
	public void addRessupplement(Ressupplement ad){

		
		Transaction tran=session.beginTransaction();
		session.save(ad);
	    tran.commit();
		
	} 
	public Ressupplement findRessupplementbyid(int id){

		Transaction tran=session.beginTransaction();
		String hql="from Ressupplement where resSupplementId=' "+id+"'";
		Query query =session.createQuery(hql);
		Ressupplement ad=(Ressupplement)query.uniqueResult();
	    tran.commit();
		return ad;
		
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
	           
	            //String hql = "from Employee emp";// 查询HQL语句
	           String HQL = "from Ressupplement";// 条件查询HQL语句
	            Query q = session.createQuery(HQL);// 执行查询操作
	            q.setFirstResult(pageSize * (pageNow - 1));
	            q.setMaxResults(pageSize);
	            sftlist = (ArrayList<Ressupplement>)q.list();
	        } catch (HibernateException e) {
	            e.printStackTrace();
	            System.out.println("查询失败");
	        } finally {
	        	HibernateSessionFactory.closeSession();// 关闭session
	        }
	        return sftlist;
	    }

}
