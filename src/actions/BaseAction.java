
package actions;

import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionContext;
import java.util.Map;

public class BaseAction {
    
    private Gson gson = new Gson();
    private String param;
    
    public static void validate(Object object) {
        
    }
    
    public final void setParam(String param) {
        this.param = param;
    }
    
    public final Object getParam(Class<? extends Object> clazz) {
        return gson.fromJson(param, clazz);
    }
    
    public final String jsonResult(Object o) {
        return gson.toJson(o);
    }
    
    public Map session() {
        return ActionContext.getContext().getSession();
    }
    
    public Object session(String item) {
        return session().get(item);
    }
    
    public void session(String item, Object value) {
        session().put(item, value);
    }
    
    public static String toAbstract(String content) {
        content = content.replaceAll("(\\<.+?\\>|\\n|\\r|&nbsp;|\\s)+", " ").trim();
        if(content.length() > 80) {
            content = content.substring(0, 78) + "...";
        }
        return content;
    }
}
