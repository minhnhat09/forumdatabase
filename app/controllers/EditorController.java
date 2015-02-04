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

@Security.Authenticated(Secured.class)
public class EditorController extends Controller {
	
	private static final Form<Thread> threadForm 		= Form.form(Thread.class);
	public static final Form<Search> searchForm 		= Form.form(Search.class);
	
	
	public static Result editorHome(models.Application app){
		return ok(views.html.threadeditor.mainPage.render(app, threadForm, searchForm));
		
	}
	
	
	public static Result postThread(models.Application app){
		Form<Thread> boundThreadForm = threadForm.bindFromRequest();
		
		if(boundThreadForm.hasErrors()){
			flash("error", String.format("Erreur lors de la création de l'article"));
			return ok(views.html.threadeditor.mainPage.render(app, threadForm, searchForm));
			
		}
		
		Thread thread = boundThreadForm.get();
		
		if(thread.threadName == "") thread.threadName = "Non titre";
		
		
		thread.content = thread.content.replace("<img", "<img class='img-thumbnail' ");
		
		if(thread.idThread == 0){
			//attach user to thread
			User authorThread = User.findById(Application.getSessionUser());
			thread.author = authorThread;
			thread.application = app;
			thread.publicDate = new Date();
			thread.lastUpdate = new Date();
			thread.category = "normal";
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
			thread.author.exp   += BonusRule.findByID("2").xp;
			thread.author.bonus += BonusRule.findByID("2").bonus;
			thread.author.update();
			flash("success", String.format("L'article a bien été enregistré"));
			return redirect(routes.ForumController.forumHome(app,0, "publicDate", "desc"));
			
		}else{
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
			
			flash("success", String.format("Changer de l'article avec succès"));
			return redirect(routes.ThreadController.threadHome(thread, 0));
		}
		
	}
	
	public static String listToString(List<Bibliography> list){
		String author = "";
		for (Bibliography bibliography : list) {
			author += bibliography.author + " ";
		}
		return author;
	}
}
