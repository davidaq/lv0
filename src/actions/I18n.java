
package actions;

import languages.Language;

public class I18n extends BaseAction {
    
    public static class RequestLang {
        public String lang;
    }
    public String dictionary() {
        RequestLang lang = (RequestLang) getParam(RequestLang.class);
        return Language.get(lang.lang);
    }
    
    public String list() {
        return Language.getList();
    }
}
