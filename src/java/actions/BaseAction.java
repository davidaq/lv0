
package actions;

import com.google.gson.Gson;
//FBS
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
}
