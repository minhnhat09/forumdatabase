package controllers;

import controllers.SearchController.Search;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * Controller used to render welcome page
 * @author NGUYEN Nhat Minh
 * @version 1.0.0
 * @category Controller
 * @see {@link Object}
 */
public class AccueilController extends Controller {
	
	public static final Form<Search> SEARCHFORM = Form.form(Search.class);
	
	/**
	 * 
	 * @return welcome page with all the services and their applications
	 */
	public static Result accueil(){
		return ok(views.html.accueil.render(SEARCHFORM));
	}	
}
