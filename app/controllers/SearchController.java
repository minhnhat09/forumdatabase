package controllers;

import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

import com.avaje.ebean.Page;
/**
 * Controller used to manage search operation
 * @author NGUYEN Nhat Minh a073417
 * @version 1.0.0
 * @category Controller
 * @see {@link Object}
 */
public class SearchController extends Controller{
	
	public static final Form<Search> searchForm = Form.form(Search.class);
	/**
	 * 
	 * @author NGUYEN Nhat Minh a073417
	 *
	 */
	public static class Search{
		public String option;
		public String content;
	}
	/**
	 * Method used to redirect 3 search options
	 * There are 3 search options:
	 * 1. Search all articles and authors
	 * 2. Search by forum (application)
	 * 3. Search by author
	 * @return redirect to appropriate search page result
	 */
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
	/**
	 * Method used to search both application and author
	 * @param page for example there are lots of results. We have to divide it by many pages
	 * @param content search content put by user
	 * @return search page with approprite result
	 */
	public static Result searchAll(Integer page, String content){
		Page<models.Thread> threads = models.Thread.findByName(page, content);
		
		return ok(views.html.search.searchResultAll.render(threads, searchForm));
	}
	/**
	 * Method used to search content by author
	 * @param page for example there are many pages. We have to divide to many pages
	 * @param content search content
	 * @return result search page by author
	 */
	public static Result searchByAuthor(Integer page, String content){
		Page<models.Thread> threads = models.Thread.findByAuthor(page, content);
		
		return ok(views.html.search.searchResultAll.render(threads, searchForm));
	}
	/**
	 * Method used to search content by forum
	 * @param page for example there are many pages. We have to divide to many pages
	 * @param content search content put by user
	 * @return result search page by forum
	 */
	public static Result searchByForum(Integer page, String content){
		Page<models.Thread> threads = models.Thread.findByNameForum(page, content, 1);
		return ok(views.html.search.searchResultAll.render(threads, searchForm));
	}
	
}
