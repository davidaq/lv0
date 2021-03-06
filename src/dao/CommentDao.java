package dao;

import java.util.ArrayList;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import tables.Comment;
import tables.Good;
import tables.Mediacontent;

public class CommentDao {
	Session session=HibernateSessionFactory.currentSession();
	public void close(){
		session.close();
	}
	public void addComment(Comment ad){

		
		Transaction tran=session.beginTransaction();
		session.save(ad);
	    tran.commit();
		
	} 
	public Comment findCommentbyid(int id){

		Transaction tran=session.beginTransaction();
		String hql="from Comment where comId=' "+id+"'";
		Query query =session.createQuery(hql);
		Comment ad=(Comment)query.uniqueResult();
	    tran.commit();
		return ad;
		
	} 
	public ArrayList<Comment> getCommentByLogId(int id){

		Session session=HibernateSessionFactory.currentSession();
		Transaction tran=session.beginTransaction();
		String hqlsql = "from Comment where tourLogId='"+id+"'";
		 Query query = session.createQuery(hqlsql);
		 ArrayList<Comment> resultStu = (ArrayList<Comment>)query.list();
	    tran.commit();
		//.close();
		return resultStu;
	} 
	public void updateComment(Comment ad){

		
		Transaction tran=session.beginTransaction();
		session.update(ad);
	    tran.commit();
		
	} 
	public void deleteComment(Comment ad){

		
		Transaction tran=session.beginTransaction();
		session.delete(ad);
	    tran.commit();
		
	} 
	public ArrayList<Comment> getCommentAll(){

		
		Transaction tran=session.beginTransaction();
		String hqlsql = "from Comment";
		 Query query = session.createQuery(hqlsql);
		 ArrayList<Comment> resultStu = (ArrayList<Comment>)query.list();
	    tran.commit();
		
		return resultStu;
	} 
	 public ArrayList<Comment> queryByPage(int pageSize, int pageNow) {
	        Session session = null;
	        ArrayList<Comment> sftlist = new ArrayList();
	        try {
	           
	            //String hql = "from Employee emp";// ��ѯHQL���
	           String HQL = "from Comment";// ������ѯHQL���
	            Query q = session.createQuery(HQL);// ִ�в�ѯ����
	            q.setFirstResult(pageSize * (pageNow - 1));
	            q.setMaxResults(pageSize);
	            sftlist = (ArrayList<Comment>)q.list();
	        } catch (HibernateException e) {
	            e.printStackTrace();
	            System.out.println("��ѯʧ��");
	        } finally {
	        	HibernateSessionFactory.closeSession();// �ر�session
	        }
	        return sftlist;
	    }

}
