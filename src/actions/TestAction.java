
package actions;

public class TestAction extends BaseAction {
    private String text = "xx";
    //
    public String test() {
        return jsonResult(new TestAction());
    }
    
}
