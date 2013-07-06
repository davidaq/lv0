
package actions;

import com.google.gson.Gson;

public class BaseAction {
    
    public static void validate(Object object) {
        
    }
    
    public final String jsonResult(Object o) {
        Gson gson = new Gson();
        return gson.toJson(o);
    }
}
