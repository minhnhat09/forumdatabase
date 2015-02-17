package api;

import java.util.List;

import models.Service;
import models.Thread;
import play.mvc.Controller;
import play.mvc.Result;
import flexjson.JSONSerializer;


/**
 * RestController used to control {@link Service}
 * @author NGUYEN Nhat Minh
 * @version 1.0.0
 * @category Controller
 * 
 */


public class ForumControllerApi extends Controller {
	public static Result findAllForums(){
		List<Service> services = Service.findAllServices();
		JSONSerializer serializer = new JSONSerializer().include("apps");
		String json = serializer.serialize(services);
		return ok(json);
	}
	
	
	
	public static Result findForumById(String idForum){
		List<Thread> threads = Thread.findByApp(Integer.parseInt(idForum));
		JSONSerializer serializer = new JSONSerializer();
		String json = serializer.serialize(threads);
		return ok(json);
	}
}
