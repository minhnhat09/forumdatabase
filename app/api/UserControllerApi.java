package api;

import models.User;
import play.mvc.Controller;
import play.mvc.Result;
import flexjson.JSONSerializer;

public class UserControllerApi extends Controller{
	
	
	public static Result findUserInfo(){
		User user = User.findById(session("userNameMobile"));
		JSONSerializer serializer = new JSONSerializer();
		String json = serializer.serialize(user);
		return ok(json);
	}
}
