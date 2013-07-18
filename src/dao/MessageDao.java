package dao;

import java.util.ArrayList;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import tables.Landmark;
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
	public Message findMessagebyid(int id){

		Transaction tran=session.beginTransaction();
		String hql="from Message where messageId=' "+id+"'";
		Query query =session.createQuery(hql);
		Message ad=(Message)query.uniqueResult();
	    tran.commit();
		return ad;
		
	} 
	public ArrayList<Message> findMessagebyuid(int id, int least){

		Transaction tran=session.beginTransaction();
		String hql="from Message where targetId=' "+id+"' and messageId > " + least + " order by messageId asc";
		Query query =session.createQuery(hql);
		ArrayList<Message> ad=(ArrayList<Message>)query.list();
	    tran.commit();
		return ad;
		
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
	        try {  //String hql = "from Employee emp";// ��ѯHQL���
	           String HQL = "from Message";// ������ѯHQL���
	            Query q = session.createQuery(HQL);// ִ�в�ѯ����
	            q.setFirstResult(pageSize * (pageNow - 1));
	            q.setMaxResults(pageSize);
	            sftlist = (ArrayList<Message>)q.list();
	        } catch (HibernateException e) {
	            e.printStackTrace();
	            System.out.println("��ѯʧ��");
	        } finally {
	        	HibernateSessionFactory.closeSession();// �ر�session
	        }
	        return sftlist;
	    }

}
