package api;

import java.util.Date;
import java.util.List;

import models.Post;
import models.Thread;
import models.User;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

import com.fasterxml.jackson.databind.JsonNode;

import controllers.ThreadController;
import flexjson.JSONSerializer;

/**
 * RestController used to control {@link Thread}
 * @author NGUYEN Nhat Minh
 * @version 1.0.0
 * @category Controller
 * @see {@link ThreadController}
 */


public class ThreadControllerApi extends Controller {
	
	
	
	/**
	 * 
	 * @return thread by id
	 */
	public static Result findThreadById(int idThread){
		Thread thread = Thread.findById(idThread);
		JSONSerializer serializer = new JSONSerializer().include("posts", "biblios");
		String json = serializer.serialize(thread);
		return ok(json);
	}
	
	public static Result commentThread(){
		JsonNode json = request().body().asJson();
		if(json == null){
			return badRequest();
		}else{
			
			String content = json.get("content").asText();
			String idThread = json.get("currentThreadId").asText();
			
			Post comment = new Post();
			User user = User.findById(session("userNameMobile"));
			Thread thread = Thread.findById(Integer.parseInt(idThread));
			
			comment.user = user;
			comment.postContent = content;
			comment.postTime = new Date();
			comment.thread = thread;
			comment.save();
			ThreadController.increaseBonus(comment.user);
					
			
			return ok("Success");
		}
	}
	
	
	
	
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
		Thread thread = Thread.findById(2);
		JSONSerializer serializer = new JSONSerializer().include("posts", "biblios");
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
