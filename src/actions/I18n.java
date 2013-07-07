
package actions;

import java.util.HashMap;

public class I18n extends BaseAction {
    
    public static class RequestLang {
        public String lang;
    }
    
    public String dictionary() {
        RequestLang lang = (RequestLang) getParam(RequestLang.class);
        HashMap<String, String> result = new HashMap<String, String>();
        result.put("sitename", "驴圈");
        return jsonResult(result);
    }
    
}
