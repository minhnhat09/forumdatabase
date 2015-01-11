package controllers;

import controllers.SearchController.Search;
import models.User;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

@Security.Authenticated(Secured.class)
public class UserController extends Controller {
	public static final Form<Search> searchForm = Form.form(Search.class);
	
	public static Result userHome(User user){
		return ok(views.html.user.infoUser.render(user,searchForm));
	}
}
