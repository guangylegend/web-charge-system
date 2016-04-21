package Common;

public class Response {
	public String status = "";
	public String content = "";
	
	public Response setContent(String s) {
		status = "Fail";
		content = s;
		return this;
	}
	
	@Override
	public String toString() {
		return null;
	}
}
