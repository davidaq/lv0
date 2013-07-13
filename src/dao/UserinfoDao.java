package dao;

import java.util.ArrayList;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import tables.Admininfo;
import tables.Userinfo;

public class UserinfoDao {
	Session session=HibernateSessionFactory.currentSession();
	public void close(){
		session.close();
	}
public Userinfo findUserinfoByid(int id){

		
		Transaction tran=session.beginTransaction();
		String hql="from Userinfo where uid=' "+id+"'";
		Query query =session.createQuery(hql);
		Userinfo ad=(Userinfo)query.uniqueResult();
	    tran.commit();
		return ad;
	} 
	public void addUserinfo(Userinfo ad){

		
		Transaction tran=session.beginTransaction();
		session.save(ad);
	    tran.commit();
		
	} 
	
public Userinfo findUserinfoByname(String name){

		
	Transaction tran=session.beginTransaction();
	String hql="from Userinfo where uname='"+name+"'";
	Query query =session.createQuery(hql);
	Userinfo ad=(Userinfo)query.uniqueResult();
    tran.commit();
	return ad;
		
	} 

public Userinfo findUserinfoByMail(String email){

	Transaction tran=session.beginTransaction();
	String hql="from Userinfo where umail='"+email+"'";
	Query query =session.createQuery(hql);
	Userinfo ad=(Userinfo)query.uniqueResult();
    tran.commit();
	return ad;
	
} 
	public void updateUserinfo(Userinfo ad){

		
		Transaction tran=session.beginTransaction();
		session.update(ad);
	    tran.commit();
		
	} 
	public void deleteUserinfo(Userinfo ad){

		
		Transaction tran=session.beginTransaction();
		session.delete(ad);
	    tran.commit();
		
	} 
	public ArrayList<Userinfo> getUserinfoAll(){

		
		Transaction tran=session.beginTransaction();
		String hqlsql = "from Userinfo";
		 Query query = session.createQuery(hqlsql);
		 ArrayList<Userinfo> resultStu = (ArrayList<Userinfo>)query.list();
	    tran.commit();
		
		return resultStu;
	} 
	 public ArrayList<Userinfo> queryByPage(int pageSize, int pageNow) {
	      
	        ArrayList<Userinfo> sftlist = new ArrayList();
	        try {
	            
	            //String hql = "from Employee emp";// ��ѯHQL���
	           String HQL = "from Userinfo";// ������ѯHQL���
	            Query q = session.createQuery(HQL);// ִ�в�ѯ����
	            q.setFirstResult(pageSize * (pageNow - 1));
	            q.setMaxResults(pageSize);
	            sftlist = (ArrayList<Userinfo>)q.list();
	        } catch (HibernateException e) {
	            e.printStackTrace();
	            System.out.println("��ѯʧ��");
	        } finally {
	        	HibernateSessionFactory.closeSession();// �ر�session
	        }
	        return sftlist;
	    }
	 
	public ArrayList<Userinfo> findUserinfosLikeumail(String mail){
		mail = mail.trim();
		Transaction tran=session.beginTransaction();
		String hql="from Userinfo where uMail like '%"+mail+"%'";
		Query query =session.createQuery(hql);
		ArrayList<Userinfo> result1 = (ArrayList<Userinfo>)query.list();
		tran.commit();
		if(mail.indexOf("@") > -1){
			String s[] = mail.split("@");
			if(s[0] != null && !s[0].equals("")){
				tran=session.beginTransaction();
				hql="from Userinfo where uMail like '%"+s[0]+"%'";
				query =session.createQuery(hql);
				ArrayList<Userinfo> result2 = (ArrayList<Userinfo>)query.list();
				tran.commit();
				if(result2.size() > 0){
					ArrayList<Userinfo> temp = new ArrayList<Userinfo>(result1);
					temp.retainAll(result2);
					result2.removeAll(temp);
					result1.addAll(result2);
				}
			}
		}
		return result1;
	}
	
	public ArrayList<Userinfo> findUserinfosLikeName(String name){
		name = name.trim();
		Transaction tran=session.beginTransaction();
		String hql="from Userinfo where uName like '%"+name+"%'";
		Query query =session.createQuery(hql);
		ArrayList<Userinfo> result1 = (ArrayList<Userinfo>)query.list();
		tran.commit();
		if(name.indexOf(" ") > -1){
			String s[] = name.split(" ");
			if(s.length > 1){
				tran=session.beginTransaction();
				hql="from Userinfo where uName like '%"+s[0]+"%'";
				for(int i = 1;i < s.length && !s[i].equals("");i++){
					if(!s[i].equals("")){
						hql+=" and uName like '%"+s[i]+"%'";
					}
				}
				query =session.createQuery(hql);
				ArrayList<Userinfo> result2 = (ArrayList<Userinfo>)query.list();
				tran.commit();
				if(result2.size() > 0){
					ArrayList<Userinfo> temp = new ArrayList<Userinfo>(result1);
					temp.retainAll(result2);
					result2.removeAll(temp);
					result1.addAll(result2);
				}
				
				
				tran=session.beginTransaction();
				hql="from Userinfo where uName like '%"+s[0]+"%'";
				for(int i = 1;i < s.length && !s[i].equals("");i++){
					if(!s[i].equals("")){
						hql+=" or uName like '%"+s[i]+"%'";
					}
				}
				query =session.createQuery(hql);
				ArrayList<Userinfo> result3 = (ArrayList<Userinfo>)query.list();
				tran.commit();
				if(result3.size() > 0){
					ArrayList<Userinfo> temp = new ArrayList<Userinfo>(result1);
					temp.retainAll(result3);
					result3.removeAll(temp);
					result1.addAll(result3);
				}
			}
		}
		return result1;
	}
	
	
	public ArrayList<Userinfo> findUserinfosLikeuSketch(String sketch){
		sketch = sketch.trim();
		Transaction tran=session.beginTransaction();
		String hql="from Userinfo where uName like '%"+sketch+"%'";
		Query query =session.createQuery(hql);
		ArrayList<Userinfo> result1 = (ArrayList<Userinfo>)query.list();
		tran.commit();
		if(sketch.indexOf(" ") > -1){
			String s[] = sketch.split(" ");
			if(s.length > 1){
				tran=session.beginTransaction();
				hql="from Userinfo where uName like '%"+s[0]+"%'";
				for(int i = 1;i < s.length && !s[i].equals("");i++){
					if(!s[i].equals("")){
						hql+=" and uName like '%"+s[i]+"%'";
					}
				}
				query =session.createQuery(hql);
				ArrayList<Userinfo> result2 = (ArrayList<Userinfo>)query.list();
				tran.commit();
				if(result2.size() > 0){
					ArrayList<Userinfo> temp = new ArrayList<Userinfo>(result1);
					temp.retainAll(result2);
					result2.removeAll(temp);
					result1.addAll(result2);
				}
				
				
				tran=session.beginTransaction();
				hql="from Userinfo where uName like '%"+s[0]+"%'";
				for(int i = 1;i < s.length && !s[i].equals("");i++){
					if(!s[i].equals("")){
						hql+=" or uName like '%"+s[i]+"%'";
					}
				}
				query =session.createQuery(hql);
				ArrayList<Userinfo> result3 = (ArrayList<Userinfo>)query.list();
				tran.commit();
				if(result3.size() > 0){
					ArrayList<Userinfo> temp = new ArrayList<Userinfo>(result1);
					temp.retainAll(result3);
					result3.removeAll(temp);
					result1.addAll(result3);
				}
			}
		}
		return result1;
	} 
}
