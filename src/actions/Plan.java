package actions;

import java.util.ArrayList;

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
//		if(param.planFavor)
//		p.setPlanFavor(1);
		pd.updatePlan(p);
		return jsonResult("");
	}
}
