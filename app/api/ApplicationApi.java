package api;

import models.User;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

import com.fasterxml.jackson.databind.JsonNode;

import flexjson.JSONSerializer;

public class ApplicationApi extends Controller{

	@BodyParser.Of(BodyParser.Json.class)
	public static Result authenticateMobile(){
		JsonNode json = request().body().asJson();
		if(json == null){
			return badRequest();
		}else{
			System.out.println("Authenticate");
			String userName = json.get("username").asText();
			String password = json.get("password").asText();
			
			if(User.authenticate(userName, password) == null){
				return forbidden("invalid password");
			}
			session().clear();
			session("userNameMobile", userName);
			
			User user = User.findById(userName);
			session("permissionUser",
					String.valueOf(user.permission.idPermission));
			session("adminMode", "off");
			user.title = User.showTitle(user);
			user.update();
			JSONSerializer serializer = new JSONSerializer();
			String jsonReturn = serializer.serialize(user);
			return ok(user.userName);
		}
	}
}
