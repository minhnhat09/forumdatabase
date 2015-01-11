package api;

import java.util.List;

import models.Thread;
import play.mvc.Controller;
import play.mvc.Result;
import flexjson.JSONSerializer;

public class ThreadControllerApi extends Controller {
	
	
	public static Result threadJson(){
		List<Thread> threads = Thread.findByApp(1);
		JSONSerializer serializer = new JSONSerializer().include("threadName");
		String json = serializer.serialize(threads);
		return ok(json);
	}
	
	public static Result threadById(){
		Thread thread = Thread.findById(1);
		JSONSerializer serializer = new JSONSerializer().prettyPrint(true);
		String json = serializer.serialize(thread);
		return ok(json);
	}
	
}
