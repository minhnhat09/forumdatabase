package api;

import java.util.Date;
import java.util.List;

import models.Application;
import models.BonusRule;
import models.Post;
import models.Tag;
import models.Thread;
import models.User;
import models.UserAppreciation;
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
	public static final String THREAD_CAT_NORMAL  = "normal";
	
	
	/**
	 * 
	 * @return thread by id
	 */
	public static Result findThreadById(String idThread){
		Thread thread = Thread.findById(Integer.parseInt(idThread));
		JSONSerializer serializer = new JSONSerializer().include("posts", "biblios");
		String json = serializer.serialize(thread);
		return ok(json);
	}
	
	public static Result commentThread(){
		JsonNode json = request().body().asJson();
		if(json == null){
			return badRequest();
		}else{
			
			String content  = json.get("content").asText();
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
			Post.updateCommentCount(thread);
			return ok("Success");
		}
	}
	
	public static Result likeThread(){
		JsonNode json = request().body().asJson();
		if(json == null){
			return badRequest();
		}else{
			String idThread = json.get("currentThreadId").asText();
			String idUser = session("userNameMobile");
			
			System.out.println("user" + idUser);
			System.out.println("thread " + idThread);
			
			if(UserAppreciation.likeThread(idUser, idThread))
				return ok("Success");
			else return badRequest();
	
		}
	}
		
		
	public static Result dislikeThread(){
		JsonNode json = request().body().asJson();
		if(json == null){
			return badRequest();
		}else{
			String idThread = json.get("currentThreadId").asText();
			String idUser   = session("userNameMobile");
			if(UserAppreciation.dislikeThread(idUser, idThread))
				return ok();
			else return forbidden();
	
		}
	}
	
	public static Result bookmarkThread(){
		JsonNode json = request().body().asJson();
		if(json == null){
			return badRequest();
		}else{
			String idThread = json.get("currentThreadId").asText();
			String idUser   = session("userNameMobile");
			if(UserAppreciation.bookmarkThread(idUser, idThread))
				return ok();
			else return forbidden();
		}
	}
	
	public static Result noteThread(){
		JsonNode json = request().body().asJson();
		if(json == null){
			return badRequest();
		}else{
			String idThread = json.get("currentThreadId").asText();
			String idUser 	= session("userNameMobile");
			String note 	= json.get("note").asText();
			
			System.out.println(idThread + " "  + idUser + " " + note);
			
			
			
			if(UserAppreciation.noteThread(idUser, idThread,Integer.parseInt(note)))
				return ok();
			else return forbidden();
		}
	}
	
		
		
	
	public static Result createThread(){
		JsonNode json = request().body().asJson();
		if(json == null){
			return badRequest();
		}else{
			String content 	  = json.get("content").asText();
			String idApp      = json.get("currentForumId").asText();
			String title      = json.get("title").asText();
			String tagCountry = json.get("tagCountry").asText();
			String tagModule  = json.get("tagModule").asText();
			
			System.out.println(content + " " + idApp + " " + title + " " + tagCountry + " " + tagModule);
			
			Thread thread  = new Thread();
			User author = User.findById(session("userNameMobile"));
			Application app = Application.findById(idApp);
			
			//affect thread to many attributes
			
			thread.author = author;
			thread.application  = app;
			thread.publicDate = new Date();
			thread.lastUpdate = new Date();
			thread.category = THREAD_CAT_NORMAL;
			thread.threadName = title;
			thread.content = content;
			
			
			//add tag to thread tag list
			Tag tagC = Tag.findById(Integer.parseInt(tagCountry));
			Tag tagM = Tag.findById(Integer.parseInt(tagModule));
			
			thread.tags.add(tagC);
			thread.tags.add(tagM);
			thread.save();
			
			//update bonus and exp for thread author
			thread.author.exp   += BonusRule.findByID("2").xp;
			thread.author.bonus += BonusRule.findByID("2").bonus;
			thread.author.update();
			
		}
		return ok("Success");
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
