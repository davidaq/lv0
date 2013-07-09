
package languages;

import com.google.gson.Gson;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;


public abstract class Language {
    
    public abstract String getName();
    
    private static HashMap<String, String> cache = new HashMap<String, String>();
    
    public static String get(String language) {
        if(cache.containsKey(language)) {
            return cache.get(language);
        }
        Class<? extends Object> clz = null;
        boolean saveCache = false;
        try {
            clz = Class.forName(Language.class.getPackage().getName() + "." + language);
            saveCache = true;
        } catch (ClassNotFoundException ex) {
            try {
                clz = Class.forName(Language.class.getPackage().getName() + ".Chinese");
            } catch (ClassNotFoundException ex1) {
                Logger.getLogger(Language.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        if(clz != null) {
            try {
                String ret = new Gson().toJson(clz.newInstance());
                if(saveCache) {
                    cache.put(language, ret);
                }
                return ret;
            } catch (InstantiationException ex) {
                Logger.getLogger(Language.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(Language.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
    
    public static String lanList;
    public static class LanguageItem {
        public String name, title;
    }
    public static String getList() {
        if(lanList != null) {
            return lanList;
        }
        Class[] languages = new Class[] {
            Chinese.class,
            English.class
        };
        LinkedList<LanguageItem> list = new LinkedList<LanguageItem>();
        for(Class clz : languages) {
            LanguageItem item = new LanguageItem();
            item.name = clz.getSimpleName();
            try {
                item.title = ((Language)clz.newInstance()).getName();
            } catch (InstantiationException ex) {
                Logger.getLogger(Language.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(Language.class.getName()).log(Level.SEVERE, null, ex);
            }
            list.add(item);
        }
        lanList = new Gson().toJson(list);
        return lanList;
    }
}
