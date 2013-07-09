
package actions;

import dao.ResortDao;


public class Resort extends BaseAction {
    
    static final int pageSize = 3;
    
    static class ResortPage {
        public int page;
    }
    public String list() {
        ResortPage param = (ResortPage) getParam(ResortPage.class);
        ResortDao dao = new ResortDao();
        return jsonResult(dao.queryByPage(pageSize, param.page));
    }
    
}
