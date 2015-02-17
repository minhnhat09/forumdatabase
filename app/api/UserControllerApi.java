package api;

import models.User;
import play.mvc.Controller;
import play.mvc.Result;
import flexjson.JSONSerializer;

public class UserControllerApi extends Controller{
	
	
	public static Result findUserById(String idUser){
		User user = User.findById(idUser);
		JSONSerializer serializer = new JSONSerializer();
		String json = serializer.serialize(user);
		return ok(json);
	}
}
