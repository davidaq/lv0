package dao;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import tables.Plan;
import tables.Planitem;

public class PlanitemDao {

    Session session = HibernateSessionFactory.currentSession();

    public void close() {
        session.close();
    }

    public void addPlanitem(Planitem ad) {


        Transaction tran = session.beginTransaction();
        session.save(ad);
        tran.commit();

    }

    public ArrayList<Planitem> findPlanitemByplanId(int planid) {


        Transaction tran = session.beginTransaction();
        String hql = "from Planitem where planId ='" + planid + "'";
        Query query = session.createQuery(hql);

        ArrayList<Planitem> resultStu = (ArrayList<Planitem>) query.list();
        tran.commit();
        return resultStu;

    }

    public Planitem findPlanitembyid(int id) {

        Transaction tran = session.beginTransaction();
        String hql = "from Planitem where planItemId=' " + id + "'";
        Query query = session.createQuery(hql);
        Planitem ad = (Planitem) query.uniqueResult();
        tran.commit();
        return ad;

    }
    
    public List<Plan> findGatherdPlanitem(Planitem plan, int author) {

        Transaction tran = session.beginTransaction();
        String resortMatch;
        if(plan.getResortId() != null) {
            resortMatch = "(pi.resortId='" + plan.getResortId() + "' or pi.resName='" + plan.getResName() + "')";
        } else {
            resortMatch = "pi.resName='" + plan.getResName() + "'";
        }
        String dateMatch = null;
        if(plan.getEnddate() != null && plan.getEnddate() > 0) {
            dateMatch = "(pi.startdate = 0 or pi.startdate < " + plan.getEnddate() + ")";
        }
        if(plan.getStartdate() != null && plan.getStartdate() > 0) {
            if(dateMatch != null) {
                dateMatch += " and ";
            } else {
                dateMatch = "";
            }
            dateMatch += "(pi.enddate = 0 or pi.enddate > " + plan.getStartdate() + ")";
        }
        String hql = "from Plan where authorId!='" + author + "' and planId in (select pi.planId from Planitem as pi where pi.planId!='" + plan.getPlanId() + "' and " + resortMatch;
        if(dateMatch != null) {
            hql += " and " + dateMatch;
        }
        hql += ")";
        Query query = session.createQuery(hql);
        List<Plan> ad = (List<Plan>) query.list();
        tran.commit();
        return ad;
    }

    public void updatePlanitem(Planitem ad) {


        Transaction tran = session.beginTransaction();
        session.update(ad);
        tran.commit();

    }

    public void deletePlanitem(Planitem ad) {


        Transaction tran = session.beginTransaction();
        session.delete(ad);
        tran.commit();

    }

    public ArrayList<Planitem> getPlanitemAll() {


        Transaction tran = session.beginTransaction();
        String hqlsql = "from Planitem";
        Query query = session.createQuery(hqlsql);
        ArrayList<Planitem> resultStu = (ArrayList<Planitem>) query.list();
        tran.commit();

        return resultStu;
    }

    public ArrayList<Planitem> queryByPage(int pageSize, int pageNow) {

        ArrayList<Planitem> sftlist = new ArrayList();
        try {

            //String hql = "from Employee emp";// ��ѯHQL���
            String HQL = "from Planitem";// ������ѯHQL���
            Query q = session.createQuery(HQL);// ִ�в�ѯ����
            q.setFirstResult(pageSize * (pageNow - 1));
            q.setMaxResults(pageSize);
            sftlist = (ArrayList<Planitem>) q.list();
        } catch (HibernateException e) {
            e.printStackTrace();
            System.out.println("��ѯʧ��");
        } finally {
            HibernateSessionFactory.closeSession();// �ر�session
        }
        return sftlist;
    }
}
