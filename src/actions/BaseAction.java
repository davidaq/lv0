
package actions;

import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;

public class BaseAction extends ActionSupport {
    
    private Gson gson = new Gson();
    private String param;
    private String callback;
    private String resultStream;
    
    public InputStream getResultStream() {
        return new ByteArrayInputStream(resultStream.getBytes());
    }

    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }
    
    public void setD(String d) {}
    
    public final void setParam(String param) {
        this.param = param;
    }
    
    public final Object getParam(Class<? extends Object> clazz) {
        return gson.fromJson(param, clazz);
    }
    
    public final String jsonResult(Object o) {
        String ret = gson.toJson(o);
        if(callback != null) {
            ret = callback + "(" + ret + ");";
        }
        resultStream = ret;
        return "success";
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
