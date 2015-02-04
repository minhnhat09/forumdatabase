package controllers;

import models.User;
import play.Routes;
import play.data.DynamicForm;
import play.data.Form;
import play.data.validation.Constraints;
import play.mvc.Controller;
import play.mvc.Result;
import controllers.SearchController.Search;

/**
 * Main controller manage authentication, login, forget password
 * @author NGUYEN Nhat Minh
 * @version 1.0.0
 * @category Controller
 * @see {@link Object}
 */
public class Application extends Controller {
	
	public static String userName;
	public static User user;
	public static final Form<Search> searchForm = Form.form(Search.class);
	public static Result FORGET_PASS_HOME = redirect(routes.Application
			.forgetPassPage());

	public static Result index() {
		return ok(views.html.index.render("Your new application is ready.",
				searchForm));
	}

	public static Result displayInterface() {
		return ok(views.html.displayInterface.render());
	}

	public static Result forgetPassword() {
		DynamicForm form = Form.form().bindFromRequest();
		String userName = form.get("userName");

		final User user = User.findById(userName);
		if (user == null) {
			flash("error", String.format("Utilisateur introuvable"));
			return notFound(views.html.forgetPassword.render(searchForm));
		}
		String code = user.password;
		Global.sendMail("Mot de passe", user.email, code);
		flash("success",
				String.format("Nous avons envoyé votre mot de pass à l'adresse mail : "
						+ user.email));
		return FORGET_PASS_HOME;
	}

	public static Result forgetPassPage() {
		return ok(views.html.forgetPassword.render(searchForm));
	}

	// -- Authentication

	public static class Login {
		@Constraints.Required
		public String userName;
		@Constraints.Required
		public String password;

		public String validate() {
			if (User.authenticate(userName, password) == null) {
				return "Identifiant ou mot de passe non valide";

			}
			return null;
		}

	}

	/**
	 * 
	 * @return Login page
	 */
	public static Result login() {
		return ok(views.html.login.render(Form.form(Login.class), searchForm));
	}

	
	/**
	 * Method handle login form submission.
	 * @return
	 */
	public static Result authenticate() {
		Form<Login> loginForm = Form.form(Login.class).bindFromRequest();
		if (loginForm.hasErrors()) {
			System.out.println(loginForm.error("userName"));
			return badRequest(views.html.login.render(loginForm, searchForm));
		} else {
			
			session("userName", loginForm.get().userName);
			User user = User.findById(loginForm.get().userName);
			session("permissionUser",
					String.valueOf(user.permission.idPermission));
			session("adminMode", "off");
			user.title = User.showTitle(user);
			user.update();
			return redirect(routes.AccueilController.accueil());
			// return redirect(
			// routes.Application.index()
			// );
		}
	}

	public static String getSessionUser() {
		return session("userName");
	}

	public static void turnOnAdminMode() {
		session("adminMode", "on");
	}

	public static void turnOffAdminMode() {
		session("adminMode", "off");

	}

	public static String getAdminMode() {
		return session("adminMode");
	}

	public static int getPermissionUser() {
		return Integer.parseInt(session("permissionUser"));
	}

	public static User getUser() {
		return User.findById(session("userName"));
	}

	public static boolean isAdmin() {
		int permission = Application.getPermissionUser();
		if (permission == 1)
			return true;
		else
			return false;
	}

	/**
	 * Logout and clean the session.
	 */
	public static Result logout() {
		session().clear();
		flash("success", "You've been logged out");
		return redirect(routes.Application.login());
	}

	public static String clarifyText(String text) {
		return text.replace("\"", "");
	}

	public static boolean isConnected() {
		if (session("userName") != null)
			return true;
		else
			return false;
	}
	/**
	 * 
	 * @return
	 */
	public static Result jsRoutes() {
		response().setContentType("text/javascript");
		return ok(Routes.javascriptRouter(
				"appRoutes", // appRoutes will be the JS object available in our
								// view
				routes.javascript.ThreadController.noteThread(),
				routes.javascript.ThreadController.bookmarkThread(),
				routes.javascript.ThreadController.likeThread(),
				routes.javascript.ThreadController.dislikeThread(),

				routes.javascript.PersonController.postCode(),
				routes.javascript.PersonController.sendRequest(),
				routes.javascript.PersonController.confirmContact(),

				routes.javascript.MessageController.deleteListMess(),
				routes.javascript.MessageController.deleteListNotis(),

				routes.javascript.AdminController.getAppsByService(),

				routes.javascript.GiftController.deleteListGift(),
				routes.javascript.GiftController.buyListGift()));

	}

}
