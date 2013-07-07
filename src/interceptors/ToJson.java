
package interceptors;

import actions.BaseAction;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.opensymphony.xwork2.interceptor.PreResultListener;
import exceptions.VException;
import java.io.ByteArrayInputStream;

public class ToJson implements Interceptor {
    @Override
    public void destroy() {
    }

    @Override
    public void init() {
    }
    
    @Override
    public String intercept(ActionInvocation ai) throws Exception {
        ai.addPreResultListener(new PreResultListener() {
            @Override
            public void beforeResult(ActionInvocation ai, String result) {
                ai.getStack().set("resultStream", new ByteArrayInputStream(result.getBytes()));
                ai.setResultCode("success");
            }
        });
        try {
            return ai.invoke();
        } catch(final VException ex) {
            return ((BaseAction) ai.getAction()).jsonResult(ex.toResult());
        }
    }
}