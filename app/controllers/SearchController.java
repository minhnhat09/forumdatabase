package controllers;

import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

import com.avaje.ebean.Page;

public class SearchController extends Controller{
	
	public static final Form<Search> searchForm = Form.form(Search.class);
	
	public static class Search{
		public String option;
		public String content;
	}
	
	public static Result search(){
		Form<Search> boundForm = searchForm.bindFromRequest();
		Search search = boundForm.get();
		
		String option  = search.option;
		String content = search.content;
		switch(option){
		case "0": return searchAll(0, content);
		case "1": return searchByForum(0, content);
		case "2": return searchByAuthor(0, content);
		default:return badRequest();
		}
	}
	public static Result searchAll(Integer page, String content){
		Page<models.Thread> threads = models.Thread.findByName(page, content);
		
		return ok(views.html.search.searchResultAll.render(threads, searchForm));
	}
	
	public static Result searchByAuthor(Integer page, String content){
		Page<models.Thread> threads = models.Thread.findByAuthor(page, content);
		
		return ok(views.html.search.searchResultAll.render(threads, searchForm));
	}
	
	public static Result searchByForum(Integer page, String content){
		Page<models.Thread> threads = models.Thread.findByNameForum(page, content, 1);
		return ok(views.html.search.searchResultAll.render(threads, searchForm));
	}
	
}
