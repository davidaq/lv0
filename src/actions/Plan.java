package actions;

import java.util.ArrayList;
import java.util.Date;

import dao.PlanDao;
import dao.PlanitemDao;
import dao.ResortDao;


import tables.Planitem;
import tables.Resort;
import tables.Userinfo;

public class Plan extends BaseAction {
	public static class AddPlanParam{
		tables.Plan plan;
		ArrayList<Planitem> planitems;
	}
	
	public String addPlan(){
		AddPlanParam param = (AddPlanParam) getParam(AddPlanParam.class);
		if(param.plan == null){
			return jsonResult("plan");
		}
		if(param.planitems == null){
			return jsonResult("planitem");
		}
		
		param.plan.setPlanId(null);
		if(param.plan.getPlanHeadline() == null || param.plan.getPlanHeadline().equals("")){
			return jsonResult("headline");
		}
		if(param.plan.getPlanContent() == null || param.plan.getPlanContent().equals("")){
			return jsonResult("planContent");
		}
		
		Userinfo ui = (Userinfo)session("myUserinfo");
		if(param.plan.getAuthorId() == null){
			param.plan.setAuthorId(ui.getUid());
		}
		
		PlanDao pd = new PlanDao();
		pd.addPlan(param.plan);
		
		PlanitemDao pid = new PlanitemDao();
		ResortDao rd = new ResortDao();
		if(param.planitems.size() > 1){
			for(tables.Planitem planitem : param.planitems){
				planitem.setPlanItemId(null);
				planitem.setPlanId(param.plan.getPlanId());
				if(planitem.getStartdate() == null || planitem.getStartdate().equals("")){
					continue;
				}
				if(planitem.getEnddate() == null || planitem.getEnddate().equals("")){
					continue;
				}
				Resort r = rd.findResortById(planitem.getResortId());
				if(r == null || r.getResortId() == null ){
					planitem.setResortId(null);
				}
				pid.addPlanitem(planitem);
			}
		}
		return jsonResult("ok");
	}
	
	
	public static class EditPlanParam{
		int planId;
		String planHeadline;
		String planContent;
		boolean planFavor;
	}
	
	public String editPlan(){
		EditPlanParam param = (EditPlanParam) getParam(EditPlanParam.class);
		if(param.planContent == null || param.planContent.equals("")){
			return jsonResult("planContent");
		}
		if(param.planHeadline == null || param.planContent.equals("")){
			return jsonResult("planHeadline");
		}
		
		PlanDao pd = new PlanDao();
		tables.Plan p = pd.findPlanbyid(param.planId);
		if(p == null){
			return jsonResult("planId");
		}
		
		p.setPlanHeadline(param.planHeadline);
		p.setPlanContent(param.planContent);
		if(param.planFavor){
			Byte b = 1;
			p.setPlanFavor(b);
		}
		else {
			Byte b = 0;
			p.setPlanFavor(b);
		}
		pd.updatePlan(p);
		return jsonResult("");
	}
	
	
	public static class AddPlanItemParam{
		Planitem planItem;
	}
	
	public String addPlanItem(){
		AddPlanItemParam param = (AddPlanItemParam) getParam(AddPlanItemParam.class);
		if(param.planItem == null){
			return jsonResult("planItem");
		}
		if(param.planItem.getPlanId() == null){
			return jsonResult("planItem");
		}
		if(param.planItem.getStartdate() == null || param.planItem.getStartdate().equals("")){
			return jsonResult("startDate");
		}
		if(param.planItem.getEnddate() == null || param.planItem.getEnddate().equals("")){
			return jsonResult("endDate");
		}
		param.planItem.setPlanItemId(null);
		PlanDao pd = new PlanDao();
		tables.Plan p = pd.findPlanbyid(param.planItem.getPlanId());
		
		if(p == null){
			return jsonResult("planId");
		}
		PlanitemDao pid = new PlanitemDao();
		pid.addPlanitem(param.planItem);
		
		return jsonResult("ok");
	}
	
	
	public static class EditPlanItemParam{
		int planItemId;
		int resortId;
		String resName;
		Date startDate;
		Date endDate;
	}
	
	public String editPlanItem(){
		EditPlanItemParam param = (EditPlanItemParam) getParam(AddPlanItemParam.class);
		if(param.startDate == null){
			return jsonResult("startDate");
		}
		if(param.endDate == null){
			return jsonResult("endDate");
		}
		PlanitemDao pid = new PlanitemDao();
		Planitem p = pid.findPlanitembyid(param.planItemId);
		if(p == null){
			return jsonResult("planItemId");
		}
		
		ResortDao rd = new ResortDao();
		Resort r = rd.findResortById(param.resortId);
		if(r == null){
			p.setResortId(null);
		}
		
		p.setResName(param.resName);
		p.setStartdate(param.startDate);
		p.setEnddate(param.endDate);
		pid.updatePlanitem(p);
		
		return jsonResult("ok");
	}
	
	
	
	public static class DeletePlanParam{
		int planId;
	}
	
	public String deletePlan(){
		DeletePlanParam param = (DeletePlanParam) getParam(DeletePlanParam.class);
		PlanDao pd = new PlanDao();
		tables.Plan p = pd.findPlanbyid(param.planId);
		if(p == null){
			return jsonResult("planId");
		}
		PlanitemDao pid = new PlanitemDao();
		ArrayList<Planitem> piList = pid.findPlanitemDaoByplanId(param.planId);
		if(piList != null && piList.size() > 0){
			for(Planitem planitem : piList){
				pid.deletePlanitem(planitem);
			}
		}
		pd.deletePlan(p);		
		return jsonResult("ok");
	}
	
	
	
	public static class DeletePlanitemParam{
		int planitemId;
	}
	
	public String deletePlanitem(){
		DeletePlanitemParam param = (DeletePlanitemParam) getParam(DeletePlanitemParam.class);
		PlanitemDao pid = new PlanitemDao();
		Planitem pi = pid.findPlanitembyid(param.planitemId);
		if(pi == null){
			return jsonResult("planitemId");
		}
		pid.deletePlanitem(pi);
		return jsonResult("ok");
	}

}
