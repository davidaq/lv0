package dao;

import java.util.ArrayList;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import tables.Comment;

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
	public void findComment(Comment ad){

		
		Transaction tran=session.beginTransaction();
		
	    tran.commit();
		
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
	           
	            //String hql = "from Employee emp";// 查询HQL语句
	           String HQL = "from Comment";// 条件查询HQL语句
	            Query q = session.createQuery(HQL);// 执行查询操作
	            q.setFirstResult(pageSize * (pageNow - 1));
	            q.setMaxResults(pageSize);
	            sftlist = (ArrayList<Comment>)q.list();
	        } catch (HibernateException e) {
	            e.printStackTrace();
	            System.out.println("查询失败");
	        } finally {
	        	HibernateSessionFactory.closeSession();// 关闭session
	        }
	        return sftlist;
	    }

}
