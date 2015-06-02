package api;

import models.User;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

import com.fasterxml.jackson.databind.JsonNode;

public class ApplicationApi extends Controller{

	@BodyParser.Of(BodyParser.Json.class)
	public static Result authenticateMobile(){
		JsonNode json = request().body().asJson();
		if(json == null){
			return badRequest();
		}else{
			if(json.hasNonNull("username") && json.hasNonNull("password")){
				String userName = json.get("username").asText();
				String password = json.get("password").asText();
				
				
				
				if(User.authenticate(userName, password) == null){
					return forbidden("invalid password");
				}
				
				//session().clear();
				session("userNameMobile", userName);
				System.out.println(session("userNameMobile"));
				
				return ok(userName);
			}
			
		}
		return badRequest();
	}
	
	
	
}
