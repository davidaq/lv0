package dao;

import java.util.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import tables.Good;
import tables.Tourlog;

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
	public ArrayList <Tourlog> getTop10(){

		Session session=HibernateSessionFactory.currentSession();
		Date now=new Date();
		long nw=now.getTime();
		long start=nw-48*60*60*1000;
		java.sql.Date startDate= new java.sql.Date(start);
		java.sql.Date nowtDate= new java.sql.Date(nw);
		Transaction tran=session.beginTransaction();
		String strSql="from Tourlog where tourLogId in (select one.tourLogId from Good one  where one.date > '"+startDate+"' and one.date <'" +nowtDate
				+"' group by one.tourLogId order by count(*) desc)";
		Query query=session.createQuery(strSql);
		query.setFirstResult(0);
		query.setMaxResults(10);
		
		System.out.println(strSql);
		ArrayList <Tourlog> rs=(ArrayList<Tourlog>) query.list();
//		List resultStu = query.list();
//		Iterator it=resultStu.iterator();
		
//		while(it.hasNext())
//		{
//		
//			Object o[]=(Object[])it.next();
//			for(int i=0;i<o.length;i++)
//			{
//				System.out.print("uid "+o[i].toString()+"\t");
//			}
//			System.out.println();
//		}
//		while(it.hasNext())
//		{
//		
//		
//				System.out.print("uid "+it.next().toString()+"\n");
//			
//			
//		}
		
		
		
	    tran.commit();
		//.close();
		return rs;
	} 
	public int getCountGoodByid(int logid){
		int count;
		Session session=HibernateSessionFactory.currentSession();
		
		Transaction tran=session.beginTransaction();
		String strSql="select count(*)  from Good one where tourLogId='"+logid+"'";
		Query query=session.createQuery(strSql);
		Object temp=query.uniqueResult();
		if(temp!=null){
			 count=Integer.parseInt(temp.toString());
		}
		else  count=0;
	    
	    tran.commit();
		//.close();
		return count;
	} 
	 public ArrayList<Good> queryByPage(int pageSize, int pageNow) {
	        Session session = null;
	        ArrayList<Good> sftlist = new ArrayList();
	        try {
	            session = HibernateSessionFactory.currentSession();// 获得session对象
	            //String hql = "from Employee emp";// 查询HQL语句
	           String HQL = "from Good";// 条件查询HQL语句
	            Query q = session.createQuery(HQL);// 执行查询操作
	            q.setFirstResult(pageSize * (pageNow - 1));
	            q.setMaxResults(pageSize);
	            sftlist = (ArrayList<Good>)q.list();
	        } catch (HibernateException e) {
	            e.printStackTrace();
	            System.out.println("查询失败");
	        } finally {
	        	HibernateSessionFactory.closeSession();// 关闭session
	        }
	        return sftlist;
	    }

}
