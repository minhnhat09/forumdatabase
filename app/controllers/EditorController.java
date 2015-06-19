package controllers;

import java.util.Date;
import java.util.List;

import models.Bibliography;
import models.BonusRule;
import models.Thread;
import models.User;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import controllers.SearchController.Search;
/**
 * Main controller manage authentication, login, forget password
 * @author NGUYEN Nhat Minh
 * @version 1.0.0
 * @category Controller
 * @see {@link Object}
 */
@Security.Authenticated(Secured.class)
public class EditorController extends Controller {
	
	private static final Form<Thread> threadForm 		= Form.form(Thread.class);
	public static final Form<Search> searchForm 		= Form.form(Search.class);
	
	public static final String THREAD_CAT_NORMAL  = "normal";
	
	/**
	 * 
	 * @param app
	 * @return
	 */
	public static Result editorHome(models.Application app){
		return ok(views.html.threadeditor.mainPage.render(app, threadForm, searchForm));
		
	}
	
	/**
	 * 
	 * @param app
	 * @return
	 */
	public static Result postThread(models.Application app){
		Form<Thread> boundThreadForm = threadForm.bindFromRequest();
		
		if(boundThreadForm.hasErrors()){
			flash("error", String.format("Erreur lors de la création de l'article"));
			return ok(views.html.threadeditor.mainPage.render(app, boundThreadForm, searchForm));
			
		}
		
		Thread thread = boundThreadForm.get();
		
		if(thread.threadName == "") thread.threadName = "Non titre";
		
		
		thread.content = thread.content.replace("<img", "<img class='img-thumbnail' ");
		//Case Create thread
		if(thread.idThread == 0){
			//attach user to thread
			User authorThread = User.findById(Application.getSessionUser());
			thread.author = authorThread;
			thread.application = app;
			thread.publicDate = new Date();
			thread.lastUpdate = new Date();
			thread.category = THREAD_CAT_NORMAL;
			thread.save();
			//b1.save();
			for (Bibliography bibli : thread.biblios) {
				bibli.thread = thread;
				bibli.save();
			}
			
			//attach app to thread
			thread.application = app;
			thread.update();
			//Experience point for posting an thread
			ThreadController.increaseBonusPostThread(thread.author);
			flash("success", String.format("L'article a bien été enregistré"));
			return redirect(routes.ForumController.forumHome(app,0, "publicDate", "desc"));
			
		}
		//Case update thread
		else{
			//b1.save();
			for (Bibliography bibli : thread.biblios) {
				System.out.println(bibli.idBibliography);
			}

			for (Bibliography bibli : thread.biblios) {
				bibli.thread = thread;
				bibli.update();
			}
			thread.lastUpdate = new Date();
			thread.update();
			
			flash("success", String.format("Votre article a bien été publié"));
			return redirect(routes.ThreadController.threadHome(thread, 0));
		}
		
	}
	/**
	 * 
	 * @param list
	 * @return
	 */
	public static String listToString(List<Bibliography> list){
		String author = "";
		for (Bibliography bibliography : list) {
			author += bibliography.author + " ";
		}
		return author;
	}
}
