package dao;

import java.util.ArrayList;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import tables.Message;

public class MessageDao {
	Session session=HibernateSessionFactory.currentSession();
	public void close(){
		session.close();
	}
	public void addMessage(Message ad){

		
		Transaction tran=session.beginTransaction();
		session.save(ad);
	    tran.commit();
		
	} 
	public void findMessage(Message ad){

	
		Transaction tran=session.beginTransaction();
		
	    tran.commit();
		
	} 
	public void updateMessage(Message ad){

		
		Transaction tran=session.beginTransaction();
		session.update(ad);
	    tran.commit();
		
	} 
	public void deleteMessage(Message ad){

		
		Transaction tran=session.beginTransaction();
		session.delete(ad);
	    tran.commit();
		
	} 
	public ArrayList<Message> getMessageAll(){

		
		Transaction tran=session.beginTransaction();
		String hqlsql = "from Message";
		 Query query = session.createQuery(hqlsql);
		 ArrayList<Message> resultStu = (ArrayList<Message>)query.list();
	    tran.commit();
		
		return resultStu;
	} 
	 public ArrayList<Message> queryByPage(int pageSize, int pageNow) {
	       
	        ArrayList<Message> sftlist = new ArrayList();
	        try {  //String hql = "from Employee emp";// 查询HQL语句
	           String HQL = "from Message";// 条件查询HQL语句
	            Query q = session.createQuery(HQL);// 执行查询操作
	            q.setFirstResult(pageSize * (pageNow - 1));
	            q.setMaxResults(pageSize);
	            sftlist = (ArrayList<Message>)q.list();
	        } catch (HibernateException e) {
	            e.printStackTrace();
	            System.out.println("查询失败");
	        } finally {
	        	HibernateSessionFactory.closeSession();// 关闭session
	        }
	        return sftlist;
	    }

}
