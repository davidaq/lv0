package actions;

public class Test extends BaseAction {
	
	public static class HelloParam {
		public String hehe, haha;
	}
	public String hello() {
		HelloParam param = (HelloParam) getParam(HelloParam.class);
		
		param.haha = param.hehe + " fuck" + param.haha;
		
		return jsonResult(new String[]{"fuck","you"});
	}
}
