package actions;

import dao.PlanDao;
import dao.PlanitemDao;
import dao.ResortDao;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import tables.Planitem;
import tables.Resort;
import tables.Userinfo;

public class Plan extends BaseAction {

    public static class AddPlanParam {

        tables.Plan plan;
        Planitem[] planitems;
    }

    public String addPlan() {
        AddPlanParam param = (AddPlanParam) getParam(AddPlanParam.class);
        if (param.plan == null) {
            return jsonResult("plan");
        }
        if (param.planitems == null) {
            return jsonResult("planitem");
        }

        param.plan.setPlanId(null);
        if (param.plan.getPlanHeadline() == null || param.plan.getPlanHeadline().equals("")) {
            return jsonResult("headline");
        }
        if (param.plan.getPlanContent() == null || param.plan.getPlanContent().equals("")) {
            return jsonResult("planContent");
        }

        Userinfo ui = (Userinfo) session("myUserinfo");
        param.plan.setAuthorId(ui.getUid());
        param.plan.setPlanFavor(Byte.MIN_VALUE);

        PlanDao pd = new PlanDao();
        pd.addPlan(param.plan);

        PlanitemDao pid = new PlanitemDao();
        ResortDao rd = new ResortDao();
        for (tables.Planitem planitem : param.planitems) {
            planitem.setPlanItemId(null);
            planitem.setPlanId(param.plan.getPlanId());
            if (planitem.getStartdate() == null || planitem.getStartdate().equals("")) {
                continue;
            }
            if (planitem.getEnddate() == null || planitem.getEnddate().equals("")) {
                continue;
            }
            Resort r = rd.findResortByname(planitem.getResName());
            if (r != null) {
                planitem.setResortId(r.getResortId());
            } else {
                planitem.setResortId(null);
            }
            pid.addPlanitem(planitem);
        }
        return jsonResult("ok");
    }

    public static class PlanPack {
        public tables.Plan plan;
        public tables.Planitem[] items;
    }

    public static class Page {
        public int page;
    }

    public String getMyPlans() {
        Page param = (Page) getParam(Page.class);
        PlanDao pd = new PlanDao();
        PlanitemDao pid = new PlanitemDao();
        Userinfo ui = (Userinfo) session("myUserinfo");
        List<tables.Plan> list = pd.queryByPageAuthor(ui.getUid(), 12, param.page);
        PlanPack[] ret = new PlanPack[list.size()];
        int i = 0;
        for (tables.Plan plan : list) {
            ret[i] = new PlanPack();
            ret[i].plan = plan;
            ret[i].items = pid.findPlanitemByplanId(plan.getPlanId()).toArray(new tables.Planitem[0]);
            i++;
        }

        return jsonResult(ret);
    }

    public static class EditPlanParam {

        int planId;
        String planHeadline;
        String planContent;
        boolean planFavor;
    }

    public String editPlan() {
        EditPlanParam param = (EditPlanParam) getParam(EditPlanParam.class);
        if (param.planContent == null || param.planContent.equals("")) {
            return jsonResult("planContent");
        }
        if (param.planHeadline == null || param.planContent.equals("")) {
            return jsonResult("planHeadline");
        }

        PlanDao pd = new PlanDao();
        tables.Plan p = pd.findPlanbyid(param.planId);
        if (p == null) {
            return jsonResult("planId");
        }

        p.setPlanHeadline(param.planHeadline);
        p.setPlanContent(param.planContent);
        if (param.planFavor) {
            Byte b = 1;
            p.setPlanFavor(b);
        } else {
            Byte b = 0;
            p.setPlanFavor(b);
        }
        pd.updatePlan(p);
        return jsonResult("");
    }
    
    public String findPartner() {
        PlanIdParam param = (PlanIdParam) getParam(PlanIdParam.class);
        PlanDao pd = new PlanDao();
        tables.Plan plan = pd.findPlanbyid(param.planId);
        PlanitemDao pid = new PlanitemDao();
        pd.findPlanbyid(param.planId);
        final HashMap<Integer, Integer> counter = new HashMap<Integer, Integer>();
        HashSet<tables.Plan> matched = new HashSet<tables.Plan>();
        List<Planitem> planitems = pid.findPlanitemByplanId(param.planId);
        int sz = planitems.size();
        for(Planitem item : planitems) {
            for(tables.Plan p : pid.findGatherdPlanitem(item, plan.getAuthorId())) {
                int planId = p.getPlanId();
                if(!counter.containsKey(planId)) {
                    counter.put(planId, 1);
                } else{
                    counter.put(planId, counter.get(planId) + 1);
                }
                matched.add(p);
            }
        }
        LinkedList<tables.Plan> list = new LinkedList<tables.Plan>();
        sz /= 2;
        if(sz == 0) {
            sz = 1;
        }
        for(tables.Plan p : matched) {
            if(counter.get(p.getPlanId()) >= sz) {
                list.add(p);
            }
        }
        Collections.sort(list, new Comparator<tables.Plan>() {
            @Override
            public int compare(tables.Plan t, tables.Plan t1) {
                return counter.get(t1.getPlanId()).compareTo(counter.get(t.getPlanId()));
            }
        });
        list.addFirst(plan);
        PlanPack[] ret = new PlanPack[list.size()];
        int i = 0;
        for (tables.Plan p : list) {
            ret[i] = new PlanPack();
            ret[i].plan = p;
            ret[i].items = pid.findPlanitemByplanId(p.getPlanId()).toArray(new tables.Planitem[0]);
            i++;
        }
        
        return jsonResult(ret);
    }

    public static class AddPlanItemParam {

        Planitem planItem;
    }

    public String addPlanItem() {
        AddPlanItemParam param = (AddPlanItemParam) getParam(AddPlanItemParam.class);
        if (param.planItem == null) {
            return jsonResult("planItem");
        }
        if (param.planItem.getPlanId() == null) {
            return jsonResult("planItem");
        }
        if (param.planItem.getStartdate() == null || param.planItem.getStartdate().equals("")) {
            return jsonResult("startDate");
        }
        if (param.planItem.getEnddate() == null || param.planItem.getEnddate().equals("")) {
            return jsonResult("endDate");
        }
        param.planItem.setPlanItemId(null);
        PlanDao pd = new PlanDao();
        tables.Plan p = pd.findPlanbyid(param.planItem.getPlanId());

        if (p == null) {
            return jsonResult("planId");
        }
        PlanitemDao pid = new PlanitemDao();
        pid.addPlanitem(param.planItem);

        return jsonResult("ok");
    }

    public static class EditPlanItemParam {

        int planItemId;
        int resortId;
        String resName;
        Integer startDate;
        Integer endDate;
    }

    public String editPlanItem() {
        EditPlanItemParam param = (EditPlanItemParam) getParam(AddPlanItemParam.class);
        if (param.startDate == null) {
            return jsonResult("startDate");
        }
        if (param.endDate == null) {
            return jsonResult("endDate");
        }
        PlanitemDao pid = new PlanitemDao();
        Planitem p = pid.findPlanitembyid(param.planItemId);
        if (p == null) {
            return jsonResult("planItemId");
        }

        ResortDao rd = new ResortDao();
        Resort r = rd.findResortById(param.resortId);
        if (r == null) {
            p.setResortId(null);
        }

        p.setResName(param.resName);
        p.setStartdate(param.startDate);
        p.setEnddate(param.endDate);
        pid.updatePlanitem(p);

        return jsonResult("ok");
    }

    public static class PlanIdParam {

        int planId;
    }

    public String deletePlan() {
        PlanIdParam param = (PlanIdParam) getParam(PlanIdParam.class);
        PlanDao pd = new PlanDao();
        tables.Plan p = pd.findPlanbyid(param.planId);
        if (p == null) {
            return jsonResult("planId");
        }
        PlanitemDao pid = new PlanitemDao();
        ArrayList<Planitem> piList = pid.findPlanitemByplanId(param.planId);
        if (piList != null && piList.size() > 0) {
            for (Planitem planitem : piList) {
                pid.deletePlanitem(planitem);
            }
        }
        pd.deletePlan(p);
        return jsonResult("ok");
    }

    public static class DeletePlanitemParam {

        int planitemId;
    }

    public String deletePlanitem() {
        DeletePlanitemParam param = (DeletePlanitemParam) getParam(DeletePlanitemParam.class);
        PlanitemDao pid = new PlanitemDao();
        Planitem pi = pid.findPlanitembyid(param.planitemId);
        if (pi == null) {
            return jsonResult("planitemId");
        }
        pid.deletePlanitem(pi);
        return jsonResult("ok");
    }
}
