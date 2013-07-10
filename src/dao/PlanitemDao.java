package dao;

import java.util.ArrayList;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import tables.Planitem;
import tables.Resort;

public class PlanitemDao {
	Session session=HibernateSessionFactory.currentSession();
	public void close(){
		session.close();
	}
	public void addPlanitem(Planitem ad){

	
	Transaction tran=session.beginTransaction();
	session.save(ad);
    tran.commit();
	
} 
public ArrayList<Planitem> findPlanitemDaoByplanId(int  planid){

		
		Transaction tran=session.beginTransaction();
		String hql="from Planitem where planId ='"+planid+"'";
		Query query =session.createQuery(hql);
		
		 ArrayList<Planitem> resultStu = (ArrayList<Planitem>)query.list();
	    tran.commit();
		return resultStu;
			
		} 
public void findPlanitem(Planitem ad){

	
	Transaction tran=session.beginTransaction();
	
    tran.commit();

} 
public void updatePlanitem(Planitem ad){


	Transaction tran=session.beginTransaction();
	session.update(ad);
    tran.commit();
	
} 
public void deletePlanitem(Planitem ad){

	
	Transaction tran=session.beginTransaction();
	session.delete(ad);
    tran.commit();
	
} 
public ArrayList<Planitem> getPlanitemAll(){


	Transaction tran=session.beginTransaction();
	String hqlsql = "from Planitem";
	 Query query = session.createQuery(hqlsql);
	 ArrayList<Planitem> resultStu = (ArrayList<Planitem>)query.list();
    tran.commit();
	
	return resultStu;
} 
 public ArrayList<Planitem> queryByPage(int pageSize, int pageNow) {
       
        ArrayList<Planitem> sftlist = new ArrayList();
        try {
           
            //String hql = "from Employee emp";// 查询HQL语句
           String HQL = "from Planitem";// 条件查询HQL语句
            Query q = session.createQuery(HQL);// 执行查询操作
            q.setFirstResult(pageSize * (pageNow - 1));
            q.setMaxResults(pageSize);
            sftlist = (ArrayList<Planitem>)q.list();
        } catch (HibernateException e) {
            e.printStackTrace();
            System.out.println("查询失败");
        } finally {
        	HibernateSessionFactory.closeSession();// 关闭session
        }
        return sftlist;
    }

}
