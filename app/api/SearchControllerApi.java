package api;

import java.util.List;

import models.Thread;
import flexjson.JSONSerializer;
import play.mvc.Controller;
import play.mvc.Result;

public class SearchControllerApi extends Controller {
	
	public static Result searchInForumByName(Integer idForum, String content){
		
		List<Thread> threads = models.Thread.findInForumByName(content, idForum);
		JSONSerializer serializer = new JSONSerializer();
		String json               = serializer.serialize(threads);
		return ok(json);
	}

	public static Result searchInForumByAuthor(Integer idForum, String content){
		
		List<Thread> threads = models.Thread.findInForumByAuthor(content, idForum);
		JSONSerializer serializer = new JSONSerializer();
		String json               = serializer.serialize(threads);
		return ok(json);
	}
}
