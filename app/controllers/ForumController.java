package controllers;

import models.Tag;
import models.Thread;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import com.avaje.ebean.Page;

import controllers.SearchController.Search;
@Security.Authenticated(Secured.class)
public class ForumController extends Controller {
	public static final Form<Search> searchForm = Form.form(Search.class);
	
	
	
	public static Result forumHome(models.Application app, Integer page, String sortBy, String order){
		Page<Thread> pageThreads = Thread.findPage(page, app, sortBy, order); 
		return ok(views.html.forumPage.mainPage.render(app, pageThreads, searchForm, sortBy, order));
		//return TODO;
	}
	
	public static Result forumTagCountry(models.Application app, Integer page, String sortBy, String order){
		Page<Thread> pageThreads = Thread.findPageAllTagCountry(page, app, sortBy, order); 
		return ok(views.html.forumPage.mainPage.render(app, pageThreads, searchForm, sortBy, order));
		//return TODO;
	}
	
	public static Result forumTagModule(models.Application app, Integer page, String sortBy, String order){
		Page<Thread> pageThreads = Thread.findPageAllTagCountry(page, app, sortBy, order); 
		return ok(views.html.forumPage.mainPage.render(app, pageThreads, searchForm, sortBy, order));
		//return TODO;
	}
	
	public static Result forumTag(models.Application app, Tag tag, Integer page, String sortBy, String order){
		Page<Thread> threads = Thread.findPageByTag(page, app, tag, sortBy, order);
		
		return ok(views.html.forumPage.mainPageByTag.render(app, threads, searchForm, sortBy, order));
		//return TODO;
	}
}
