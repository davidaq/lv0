package dao;

import java.util.ArrayList;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import tables.Plan;

public class PlanDao {

    Session session = HibernateSessionFactory.currentSession();

    public void close() {
        session.close();
    }

    public void addPlan(Plan ad) {


        Transaction tran = session.beginTransaction();
        session.save(ad);
        tran.commit();

    }

    public Plan findPlanbyid(int id) {

        Transaction tran = session.beginTransaction();
        String hql = "from Plan where planId=' " + id + "'";
        Query query = session.createQuery(hql);
        Plan ad = (Plan) query.uniqueResult();
        tran.commit();
        return ad;

    }

    public void updatePlan(Plan ad) {
        Transaction tran = session.beginTransaction();
        session.update(ad);
        tran.commit();

    }

    public void deletePlan(Plan ad) {


        Transaction tran = session.beginTransaction();
        session.delete(ad);
        tran.commit();

    }

    public ArrayList<Plan> getPlanAll() {


        Transaction tran = session.beginTransaction();
        String hqlsql = "from Plan";
        Query query = session.createQuery(hqlsql);
        ArrayList<Plan> resultStu = (ArrayList<Plan>) query.list();
        tran.commit();

        return resultStu;
    }

    public ArrayList<Plan> queryByPage(int pageSize, int pageNow) {

        ArrayList<Plan> sftlist = new ArrayList();
        try {

            //String hql = "from Employee emp";// ��ѯHQL���
            String HQL = "from Plan";// ������ѯHQL���
            Query q = session.createQuery(HQL);// ִ�в�ѯ����
            q.setFirstResult(pageSize * (pageNow - 1));
            q.setMaxResults(pageSize);
            sftlist = (ArrayList<Plan>) q.list();
        } catch (HibernateException e) {
            e.printStackTrace();
            System.out.println("��ѯʧ��");
        } finally {
            HibernateSessionFactory.closeSession();// �ر�session
        }
        return sftlist;
    }

    public ArrayList<Plan> queryByPageAuthor(int uid, int pageSize, int pageNow) {

        ArrayList<Plan> sftlist = new ArrayList();
        try {

            //String hql = "from Employee emp";// ��ѯHQL���
            String HQL = "from Plan where authorId='" + uid + "' order by planId desc";// ������ѯHQL���
            Query q = session.createQuery(HQL);// ִ�в�ѯ����
            q.setFirstResult(pageSize * (pageNow - 1));
            q.setMaxResults(pageSize);
            sftlist = (ArrayList<Plan>) q.list();
        } catch (HibernateException e) {
            e.printStackTrace();
            System.out.println("��ѯʧ��");
        } finally {
            HibernateSessionFactory.closeSession();// �ر�session
        }
        return sftlist;
    }
}
