package dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import tables.*;

public class AttentionDao {
	Session session=HibernateSessionFactory.currentSession();
	public void close(){
		session.close();
	}
	public void addAttention(Attention ad){

		
		Transaction tran=session.beginTransaction();
		session.save(ad);
	    tran.commit();
		
	} 
	public Userinfo GetAttentionByUserId(int attUser){
		Userinfo u;
		UserinfoDao te=new UserinfoDao();
		u=te.findUserinfoByid(attUser);
		
		return u;
		
	}//与用户表关联，返回目标的用户信息
	public ArrayList<Userinfo> GetAttentionedByUserId(int attedUser){//与用户表关联，返回我的粉丝的用户信息）
		 ArrayList<Userinfo> temp=new ArrayList<Userinfo>();
		 String sql="";
		String  Hql="select d from Attention d where d.attedUser='"+attedUser+"'";
		ArrayList<Attention> list=(ArrayList<Attention>) session.createQuery(Hql).list();
		if(list.isEmpty()){
			return temp;
		}
		int i;
		
		for(i=0;i<list.size()-1;i++){
    		sql+=list.get(i).getAttUser()+",";
    	}
    	sql+=list.get(i).getAttUser();    
    	String Hl="select d from Userinfo d where d.uid in ("+sql+")";
    	System.out.println(Hl);
    	 Query q = session.createQuery(Hl);// 执行查询操作
        temp=(ArrayList<Userinfo>) q.list();
		 
		return temp;
		
	}
	public ArrayList<Userinfo> GetAttentionByUserIdFriend(int attUser)//与用户表关联，返回我的好友的信息,被关注 的人  既我的好友信息
	{
		 ArrayList<Userinfo> temp=new ArrayList<Userinfo>();
		 String sql="";
		String  Hql="select d from Attention d where d.attUser='"+attUser+"'";
		
		ArrayList<Attention> list=(ArrayList<Attention>) session.createQuery(Hql).list();
		if(list.isEmpty()){
			return temp;
		}
		int i;
		
		for(i=0;i<list.size()-1;i++){
    		sql+=list.get(i).getAttedUser() +",";
    		System.out.println(sql+"\n");
    	}
    	sql+=list.get(i).getAttedUser();    
    	String Hl="select d from Userinfo d where d.uid in ("+sql+")";
    	System.out.println(Hl);
    	Query q = session.createQuery(Hl);// 执行查询操作
    	temp=(ArrayList<Userinfo>) q.list();
		 
		return temp;
		
	}
	boolean IsFriend(int attUser, int attedUser){
		return false;
		
	}
public Attention findAttentionByid(int id){

		
		Transaction tran=session.beginTransaction();
		String hql="from Attention where attentionId=' "+id+"'";
		Query query =session.createQuery(hql);
		Attention ad=(Attention)query.uniqueResult();
	    tran.commit();
		return ad;
	} 
	public void findAttention(Attention ad){

		
		Transaction tran=session.beginTransaction();
		
	    tran.commit();
	} 
	public void updateAttention(Attention ad){

		
		Transaction tran=session.beginTransaction();
		session.update(ad);
	    tran.commit();
		
	} 
	public void deleteAttention(Attention ad){

		
		Transaction tran=session.beginTransaction();
		session.delete(ad);
	    tran.commit();
		
	} 
	public ArrayList<Attention> getAttentionAll(){

		
		Transaction tran=session.beginTransaction();
		String hqlsql = "from Attention";
		 Query query = session.createQuery(hqlsql);
		 ArrayList<Attention> resultStu = (ArrayList<Attention>)query.list();
	    tran.commit();
		
		return resultStu;
	} 
	 public ArrayList<Attention> queryByPage(int pageSize, int pageNow) {
	    
	        ArrayList<Attention> sftlist = new ArrayList();
	        try {
	            
	            //String hql = "from Employee emp";// 查询HQL语句
	           String HQL = "from Attention";// 条件查询HQL语句
	            Query q = session.createQuery(HQL);// 执行查询操作
	            q.setFirstResult(pageSize * (pageNow - 1));
	            q.setMaxResults(pageSize);
	            sftlist = (ArrayList<Attention>)q.list();
	        } catch (HibernateException e) {
	            e.printStackTrace();
	            System.out.println("查询失败");
	        } finally {
	        	HibernateSessionFactory.closeSession();// 关闭session
	        }
	        return sftlist;
	    }

}
