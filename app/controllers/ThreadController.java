package controllers;

import java.util.Date;

import models.BonusRule;
import models.Notification;
import models.Post;
import models.PostQuote;
import models.Thread;
import models.User;
import models.UserAppreciation;
import play.data.Form;
import play.i18n.Messages;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import com.avaje.ebean.Page;
import com.fasterxml.jackson.databind.JsonNode;

import controllers.SearchController.Search;
@Security.Authenticated(Secured.class)
public class ThreadController extends Controller {
	private static final Form<Thread>		threadForm = Form.form(Thread.class);
	private static final Form<Post> 		postForm   = Form.form(Post.class);
	public static final Form<Search> searchForm = Form.form(Search.class);
	
	
//	public static Result threadJson(){
//		Thread thread = Thread.findById(1);
//		JSONSerializer serializer = new JSONSerializer().include("threadName").exclude("*").prettyPrint(true);
//		String json = serializer.serialize(thread);
//		return ok(json);
//	}
	
	
	
	
	
	public static Result threadHome(Thread thread, Integer page){
		Page<Post> posts = Post.find(thread, page);
		thread.viewCount += 1;
		thread.update();
		return ok(views.html.thread.mainPage.render(thread, posts, searchForm));
	}
	
	public static Result newComment(Thread thread){
		return ok(views.html.thread.commentPage.render(thread, postForm, searchForm));
	}
	
	public static Result editComment(Thread thread, String idPost){
		
		final Post post = Post.findById(Integer.parseInt(idPost));
		if(post == null){
			flash("error", String.format("Commentaire n'existe pas"));
			return redirect(routes.ThreadController.threadHome(thread, 0));
		}
		if(!Post.isPostOwner(post, Application.getSessionUser())){
			flash("error", String.format("Permission error"));
			return redirect(routes.ThreadController.threadHome(thread, 0));
		}
		Form<Post> filledForm =  postForm.fill(post);
		return ok(views.html.thread.editCommentPage.render(thread, post, postForm, searchForm));
	}
	

	public static Result editThread(String idThread){
		
		final Thread thread = Thread.findById(Integer.parseInt(idThread));
		if(thread == null){
			flash("error", String.format("Article n'existe pas"));
			return redirect(routes.ThreadController.threadHome(thread, 0));
		}
		if(!Thread.isThreadOwner(thread, Application.getSessionUser())){
			flash("error", String.format("Permission error"));
			return redirect(routes.ThreadController.threadHome(thread, 0));
		}
		Form<Thread> filledForm =  threadForm.fill(thread);
		return ok(views.html.threadeditor.editPage.render(thread.application, thread, filledForm, searchForm));
		
	}
	
	
	public static Result newCommentWithQuote(Thread thread, Post post){
		return ok(views.html.thread.commentWithQuotePage.render(thread, post, postForm, searchForm));
	}
	
	public static Result postComment(Thread thread){
		
		Form<Post> boundForm = postForm.bindFromRequest();
		if(boundForm.hasErrors()){
			flash("error", String.format("Erreur"));
			return badRequest(views.html.thread.commentPage.render(thread, postForm, searchForm));
		}
		
		Post comment = boundForm.get();
		if(comment.idPost == 0){
			comment.postContent = comment.postContent.replace("<img", "<img class='img-thumbnail' ");
			
			User user = User.findById(Application.getSessionUser());
			comment.user = user;
			comment.postTime = new Date();
			comment.thread = thread;
			comment.save();
			
			increaseBonus(comment.user);
			
		}else{
			comment.postContent = comment.postContent.replace("<img", "<img class='img-thumbnail' ");
			comment.lastUpdate = new Date();
			comment.update();
		}
		flash("success", String.format("Poster le commentaire avec succès"));
		return redirect(routes.ThreadController.threadHome(thread, 0));
	}

	public static void increaseBonus(User user){
		user.exp += BonusRule.findByID("3").xp;
		user.bonus += BonusRule.findByID("3").bonus;
		user.update();
		
	}
	
	public static Result postCommentWithQuote(Thread thread, Post post){
	
		Form<Post> boundForm = postForm.bindFromRequest();
		if(boundForm.hasErrors()){
			flash("error", String.format("Erreur"));
			return badRequest(views.html.thread.commentPage.render(thread, postForm, searchForm));
		}
		
		Post comment = boundForm.get();
		comment.postContent = comment.postContent.replace("<img", "<img class='img-thumbnail' ");
		User user = User.findById(Application.getSessionUser());
		
		comment.user = user;
		comment.postTime = new Date();
		comment.thread = thread;
		comment.save();
		
		PostQuote pq = new PostQuote();
		
		//create a notification
		Notification noti = new Notification();
		noti.user = post.user;
		noti.noteDate = new Date();
		noti.content = comment.user.firstName + " " + comment.user.lastName +  Messages.get("notiwithquote");
		noti.save();
		pq.post = comment.idPost;
		pq.quotes = post.idPost;
		pq.save();
		
		increaseBonus(comment.user);
		
		flash("success", String.format("Poster le commentaire avec succès"));
		return redirect(routes.ThreadController.threadHome(thread, 0));
	}
	
	@BodyParser.Of(BodyParser.Json.class)
	public static Result noteThread(){
		JsonNode json = request().body().asJson();
		
		String idUser   = json.get("idUser").textValue();
		String idThread = json.get("idThread").textValue();
		String note 	= json.get("note").textValue();
		
		if(UserAppreciation.noteThread(idUser, idThread,Integer.parseInt(note)))
			return ok();
		else return forbidden();
	}
	
	@BodyParser.Of(BodyParser.Json.class)
	public static Result bookmarkThread(){
		JsonNode json = request().body().asJson();
		
		String idUser   = json.get("idUser").textValue();
		String idThread = json.get("idThread").textValue();
		
		if(UserAppreciation.bookmarkThread(idUser, idThread))
			return ok();
		else return forbidden();
	}
	
	
	@BodyParser.Of(BodyParser.Json.class)
	public static Result likeThread(){
		JsonNode json = request().body().asJson();
		
		String idUser   = json.get("idUser").textValue();
		String idThread = json.get("idThread").textValue();
		
		if(UserAppreciation.likeThread(idUser, idThread))
			return ok();
		else return forbidden();
	}
	
	@BodyParser.Of(BodyParser.Json.class)
	public static Result dislikeThread(){
		JsonNode json = request().body().asJson();
		
		String idUser   = json.get("idUser").textValue();
		String idThread = json.get("idThread").textValue();
		
		if(UserAppreciation.dislikeThread(idUser, idThread))
			return ok();
		else return forbidden();
	}
	
	public static Result spinThread(models.Application app, String idThread){
		final Thread thread = Thread.findById(Integer.parseInt(idThread));
		
		if(thread == null){
			
			return notFound(String.format("Article % n'existe pas", idThread));
		}
		System.out.println("idThread" + thread.idThread);
		thread.isSpined = true;
		thread.update();
		return ok();
	}
	
	public static Result unSpinThread(models.Application app, String idThread){
		final Thread thread = Thread.findById(Integer.parseInt(idThread));
		
		if(thread == null){
			
			return notFound(String.format("Article % n'existe pas", idThread));
		}
		System.out.println("idThread" + thread.idThread);
		thread.isSpined = false;
		thread.update();
		return ok();
	}
	
	public static Result blockUnblockThread(models.Application app, String idThread){
		final Thread thread = Thread.findById(Integer.parseInt(idThread));
		if(thread == null){
			
			return notFound(String.format("Article % n'existe pas", idThread));
		}
		System.out.println("idThread" + thread.idThread);
		if(thread.isBlocked == false){
			thread.isBlocked = true;
			thread.category = "locked";
		}else{
			thread.isBlocked = false;
			thread.category = "normal";
		}
		
		thread.update();
		return ok();
	}
	
//	public static Result unblockThread(models.Application app, String idThread){
//		final Thread thread = Thread.findById(Integer.parseInt(idThread));
//		if(thread == null){
//			
//			return notFound(String.format("Article % n'existe pas", idThread));
//		}
//		System.out.println("idThread" + thread.idThread);
//		thread.isBlocked = false;
//		thread.category = "normal";
//		thread.update();
//		return ok();
//	}
	
	public static Result hotThread(models.Application app, String idThread){
		final Thread thread = Thread.findById(Integer.parseInt(idThread));
		if(thread == null){
			
			return notFound(String.format("Article % n'existe pas", idThread));
		}
		if(thread.isHot){
			thread.isHot    = false;
			thread.category = "normal";
		}else{
			thread.isHot    = true;
			thread.category = "hot";
		}
		
		
		thread.update();
		thread.author.exp = BonusRule.findByID("4").xp;
		thread.author.bonus = BonusRule.findByID("4").bonus;
		thread.author.update();
		return ok();
	}
	
	public static Result deleteThread(models.Application app, String idThread){
		final Thread thread = Thread.findById(Integer.parseInt(idThread));
		if(thread == null){
			
			return notFound(String.format("Article % n'existe pas", idThread));
		}
		System.out.println("xoa thread------------------------------------------------------------" + thread.idThread);
		Thread.delThread(thread);
		
		return ok();
	}
	
	
	public static Result deletePost(Thread thread , String idPost){
		final Post post = Post.findById(Integer.parseInt(idPost));
		if(post == null){
			return notFound(String.format("Commentaire % n'existe pas", idPost));
		}
		Post.delPost(post);
		return ok();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}

