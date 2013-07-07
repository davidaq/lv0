
package actions;

public class I18n extends BaseAction {
    
    public static class RequestLang {
        public String lang;
    }
    public String dictionary() {
        RequestLang lang = (RequestLang) getParam(RequestLang.class);
        return jsonResult(lang);
    }
    
}
