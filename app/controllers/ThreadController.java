package controllers;

import java.util.Date;

import models.ApplicationView;
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
/**
 * Controller used to manage Thread
 * @author NGUYEN Nhat Minh
 * @version 1.0.0
 * @category Controller
 * @see {@link Object}
 */
@Security.Authenticated(Secured.class)
public class ThreadController extends Controller {
	private static final Form<Thread>		threadForm = Form.form(Thread.class);
	private static final Form<Post> 		postForm   = Form.form(Post.class);
	public static final Form<Search> 		searchForm = Form.form(Search.class);
	
	/**
	 * 
	 * @param thread
	 * @param page
	 * @return
	 */
	public static Result threadHome(Thread thread, Integer page){
		Page<Post> posts = Post.find(thread, page);
		System.out.println("thread home");
		/**
		 * Update the article view count
		 */
		thread.viewCount++;
		thread.update();
		/**
		 * Update user view count (mode classic or mode premium)
		 */
		final User user = Application.getUser();
		user.threadCountViews++;
		user.update();
		
		ApplicationView av = ApplicationView.findByUserApp(user, thread.application);
		if(!user.isPremium){
			if(av == null){
				ApplicationView avNew = new ApplicationView(user, thread.application);
				avNew.save();
			}else{
				
				if(av.viewCount > thread.application.maxViews){
					flash("error", String.format("Vous n'avez plus d'accès à ce forum"));
					return redirect(routes.ForumController.forumHome(thread.application, 0, "publicDate", "desc"));
				}
			}
			
		}
		
		ApplicationView.countView(user, thread.application);
		return ok(views.html.thread.mainPage.render(thread, posts, searchForm));
	}
	
	
	public static Result threadHomeFromNotiPage(int idNoti, Thread thread, Integer page){
		final Notification noti = Notification.findById(idNoti);
		System.out.println(noti.idNotification);
		noti.viewed = true;
		noti.update();
		
		
		Page<Post> posts = Post.find(thread, page);
		
		/**
		 * Update the article view count
		 */
		thread.viewCount++;
		thread.update();
		/**
		 * Update user view count (mode classic or mode premium)
		 */
		final User user = Application.getUser();
		user.threadCountViews++;
		user.update();
		
		
		
		
		
		
		ApplicationView av = ApplicationView.findByUserApp(user, thread.application);
		if(!user.isPremium){
			if(av == null){
				ApplicationView avNew = new ApplicationView(user, thread.application);
				avNew.save();
			}else{
				if(av.viewCount > thread.application.maxViews){
					flash("error", String.format("Vous n'avez plus d'accès à ce forum"));
					return redirect(routes.ForumController.forumHome(thread.application, 0, "publicDate", "desc"));
				}
			}
			
		}
		
		ApplicationView.countView(user, thread.application);
		return ok(views.html.thread.mainPage.render(thread, posts, searchForm));
	}
	
	
	/**
	 * 
	 * @param thread
	 * @return
	 */
	public static Result newComment(Thread thread){
		return ok(views.html.thread.commentPage.render(thread, postForm, searchForm));
	}
	/**
	 * 
	 * @param thread
	 * @param idPost
	 * @return
	 */
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
	

	/**
	 * 
	 * @param idThread
	 * @return
	 */
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
	
	/**
	 * 
	 * @param thread
	 * @param post
	 * @return
	 */
	public static Result newCommentWithQuote(Thread thread, Post post){
		return ok(views.html.thread.commentWithQuotePage.render(thread, post, postForm, searchForm));
	}
	
	
	/**
	 * 
	 * @param thread
	 * @return
	 */
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
			comment.lastUpdate = new Date();
			comment.thread = thread;
			
			comment.save();
			//create notification to thread author
			notificationForComment(thread, comment);
			//increase bonus for user who created thread
			increaseBonusPostComment(comment.user);
		}else{
			comment.postContent = comment.postContent.replace("<img", "<img class='img-thumbnail' ");
			comment.lastUpdate = new Date();
			
			comment.update();
		}
		
		
		//update lastUpdate Thread
		thread.lastUpdate = new Date();
		
		Post.updateCommentCount(thread);
		flash("success", String.format("Votre commentaire a bien été créé"));
		return redirect(routes.ThreadController.threadHome(thread, 0));
	}
	
	/**
	 * 
	 * @param thread
	 * @param post
	 * @return
	 */
	public static Result postCommentWithQuote(Thread thread, Post post){
	
		Form<Post> boundForm = postForm.bindFromRequest();
		if(boundForm.hasErrors()){
			flash("error", String.format("Erreur"));
			return badRequest(views.html.thread.commentPage.render(thread, postForm, searchForm));
		}
		
		Post comment = boundForm.get();
		comment.postContent = comment.postContent.replace("<img", "<img class='img-thumbnail' ");
		//user who post comment
		User user = User.findById(Application.getSessionUser());
		//Save comment to database
		comment.user = user;
		comment.postTime = new Date();
		comment.thread = thread;
		comment.save();
		
		PostQuote pq = new PostQuote();
		
		//create a notification
		
		notificationForComment(thread, comment);
		
		//Attach precedent post to quote for this comment
		pq.post = comment.idPost;
		pq.quotes = post.idPost;
		pq.save();
		
		//Increase bonuse for user who post comment
		increaseBonusPostComment(comment.user);
		//update lastUpdate Thread
		thread.lastUpdate = new Date();
		thread.update();
		//Send success message
		flash("success", String.format("Le commentaire a bien été ajouté"));
		return redirect(routes.ThreadController.threadHome(thread, 0));
	}

	/**
	 * Method used to increase bonus and xp of user
	 * And update the date of final activity of the user
	 * And increase postCount user 
	 * It's used when user comment on thread
	 * @param user who comment on a thread
	 */
	public static void increaseBonusPostComment(User user){
		user.exp += BonusRule.findByID("3").xp;
		user.bonus += BonusRule.findByID("3").bonus;
		user.dateFinalPost = new Date();
		user.postCount = Post.countPostsByUser(user);
		user.update();
		
	}
	
	/**
	 * Method used to increase bonus and xp of user
	 * And update the date of final activity of the user
	 * It's used when user comment on thread
	 * @param user who comment on a thread
	 */
	public static void increaseBonusPostThread(User user){
		user.exp += BonusRule.findByID("2").xp;
		user.bonus += BonusRule.findByID("2").bonus;
		user.dateFinalPost = new Date();
		user.threadCount = Thread.countThreadsByUser(user.userName);
		user.update();
		
	}
	
	/**
	 * Method used to create notification for comment use case
	 * 
	 * @param thread
	 * @param comment
	 */
	public static void notificationForComment(Thread thread, Post comment){
		Notification noti = new Notification();
		if(thread.author != comment.user){
			noti.user     = thread.author;
			noti.noteDate = new Date();
			noti.content  = comment.user.firstName + " " + comment.user.lastName +  " " + Messages.get("responseThread") + " " + comment.thread.threadName;
			noti.idThreadResponse = comment.thread.idThread;
			noti.save();
		}
	}
	
	/**
	 * 
	 * @return
	 */
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
	
	/**
	 * 
	 * @return
	 */
	@BodyParser.Of(BodyParser.Json.class)
	public static Result bookmarkThread(){
		JsonNode json = request().body().asJson();
		
		String idUser   = json.get("idUser").textValue();
		String idThread = json.get("idThread").textValue();
		
		if(UserAppreciation.bookmarkThread(idUser, idThread))
			return ok();
		else return forbidden();
	}
	
	/**
	 * 
	 * @return
	 */
	@BodyParser.Of(BodyParser.Json.class)
	public static Result likeThread(){
		JsonNode json = request().body().asJson();
		
		String idUser   = json.get("idUser").textValue();
		String idThread = json.get("idThread").textValue();
		
		if(UserAppreciation.likeThread(idUser, idThread))
			return ok();
		else return forbidden();
	}
	
	/**
	 * 
	 * @return
	 */
	@BodyParser.Of(BodyParser.Json.class)
	public static Result dislikeThread(){
		JsonNode json = request().body().asJson();
		
		String idUser   = json.get("idUser").textValue();
		String idThread = json.get("idThread").textValue();
		
		if(UserAppreciation.dislikeThread(idUser, idThread))
			return ok();
		else return forbidden();
	}
	
	/**
	 * 
	 * @param app
	 * @param idThread
	 * @return
	 */
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
	
	/**
	 * 
	 * @param app
	 * @param idThread
	 * @return
	 */
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
	
	/**
	 * 
	 * @param app
	 * @param idThread
	 * @return
	 */
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

	/**
	 * 
	 * @param app
	 * @param idThread
	 * @return
	 */
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
	
	/**
	 * 
	 * @param app
	 * @param idThread
	 * @return
	 */
	public static Result deleteThread(models.Application app, String idThread){
		final Thread thread = Thread.findById(Integer.parseInt(idThread));
		if(thread == null){
			return notFound(String.format("Article % n'existe pas", idThread));
		}
		System.out.println("xoa thread------------------------------------------------------------" + thread.idThread);
		
		
		Thread.delThread(thread);
		
		return ok();
	}
	/**
	 * 
	 * @param thread
	 * @param idPost
	 * @return
	 */
	
	public static Result deletePost(Thread thread , String idPost){
		final Post post = Post.findById(Integer.parseInt(idPost));
		if(post == null){
			return notFound(String.format("Commentaire % n'existe pas", idPost));
		}
		Post.delPost(post);
		return ok();
	}
}

