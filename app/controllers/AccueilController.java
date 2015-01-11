package controllers;

import controllers.SearchController.Search;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

public class AccueilController extends Controller {
	public static final Form<Search> searchForm = Form.form(Search.class);
	
	public static Result accueil(){
		return ok(views.html.accueil.render(searchForm));
	}
	
	
}
