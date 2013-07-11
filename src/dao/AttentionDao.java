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
		
	}//���û������������Ŀ����û���Ϣ
	public ArrayList<Userinfo> GetAttentionedByUserId(int attedUser){//���û�������������ҵķ�˿���û���Ϣ��
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
    	 Query q = session.createQuery(Hl);// ִ�в�ѯ����
        temp=(ArrayList<Userinfo>) q.list();
		 
		return temp;
		
	}
	public ArrayList<Userinfo> GetAttentionByUserIdFriend(int attUser)//���û�������������ҵĺ��ѵ���Ϣ,����ע ����  ���ҵĺ�����Ϣ
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
    	Query q = session.createQuery(Hl);// ִ�в�ѯ����
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
public Attention findAttentionByAttid(int attid,int attedid){

	
	Transaction tran=session.beginTransaction();
	String hql="from Attention where attUser=' "+attid+"' and attedUser='"+attedid+"'" ;
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
	            
	            //String hql = "from Employee emp";// ��ѯHQL���
	           String HQL = "from Attention";// ������ѯHQL���
	            Query q = session.createQuery(HQL);// ִ�в�ѯ����
	            q.setFirstResult(pageSize * (pageNow - 1));
	            q.setMaxResults(pageSize);
	            sftlist = (ArrayList<Attention>)q.list();
	        } catch (HibernateException e) {
	            e.printStackTrace();
	            System.out.println("��ѯʧ��");
	        } finally {
	        	HibernateSessionFactory.closeSession();// �ر�session
	        }
	        return sftlist;
	    }

	 
	 
	 public ArrayList<Attention> GetAttentionsByUserIdFriend(int attUser)
	{
		ArrayList<Attention> temp=new ArrayList<Attention>();
		String  Hql="select d from Attention d where d.attUser='"+attUser+"'";
		ArrayList<Attention> list=(ArrayList<Attention>) session.createQuery(Hql).list();
		return list;
			
	}
}
