package dao;

import java.util.ArrayList;
import java.util.Date;
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
	public ArrayList<Good> getGoodByLogId(int id){

		Session session=HibernateSessionFactory.currentSession();
		Transaction tran=session.beginTransaction();
		String hqlsql = "from Good where tourLogId='"+id+"'";
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
		String strSql="select t, (select count(*) from Good g where g.tourLogId = t.tourLogId and g.date > '"+startDate+"' group by g.tourLogId) as _good from Tourlog t order by _good desc";
		Query query=session.createQuery(strSql);
                System.out.println(query.getQueryString());
		query.setFirstResult(0);
		query.setMaxResults(10);
		
		System.out.println(strSql);
		ArrayList <Tourlog> rs= new ArrayList<Tourlog>();
                for(Object o : query.list()) {
                    Object[] arr = (Object[]) o;
                    rs.add((Tourlog) arr[0]);
                }
		
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

	 public Good getGoodByLogIdAndUserId(int tourLogid,int userId){

			Session session=HibernateSessionFactory.currentSession();
			Transaction tran=session.beginTransaction();
			String hqlsql = "from Good where tourLogId='"+tourLogid+"' and uId='"+ userId + "'";
			 Query query = session.createQuery(hqlsql);
			 Good resultStu = (Good)query.uniqueResult();
		    tran.commit();
			//.close();
			return resultStu;
		} 
}
