package api;

import java.util.List;

import models.Thread;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import com.fasterxml.jackson.databind.JsonNode;

import controllers.SecuredMobile;
import controllers.ThreadController;
import flexjson.JSONSerializer;

/**
 * RestController used to control {@link Thread}
 * @author NGUYEN Nhat Minh
 * @version 1.0.0
 * @category Controller
 * @see {@link ThreadController}
 */

@Security.Authenticated(SecuredMobile.class)
public class ThreadControllerApi extends Controller {
	
	/**
	 * 
	 * @return list of threads in json format
	 */
	public static Result threadJson(){
		List<Thread> threads = Thread.findByApp(1);
		JSONSerializer serializer = new JSONSerializer().include("threadName");
		String json = serializer.serialize(threads);
		return ok(json);
	}
	
	/**
	 * 
	 * @return thread by id
	 */
	public static Result threadById(){
		Thread thread = Thread.findById(1);
		JSONSerializer serializer = new JSONSerializer().prettyPrint(true);
		String json = serializer.serialize(thread);
		return ok(json);
	}
	
	@BodyParser.Of(BodyParser.Json.class)
	public static Result testPost(){
		JsonNode json = request().body().asJson();
		if(json == null){
			return badRequest();
		}else{
			System.out.println(json);
			return ok();
		}
	}
	
}
