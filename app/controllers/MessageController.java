package controllers;

import java.util.Iterator;

import models.Message;
import models.Notification;
import models.User;
import play.data.Form;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import com.avaje.ebean.Ebean;
import com.fasterxml.jackson.databind.JsonNode;

import controllers.SearchController.Search;
@Security.Authenticated(Secured.class)
public class MessageController extends Controller {
	
	public static Result GO_HOME_MESSAGE = redirect(routes.MessageController.message());
    public static Form<Message> messageForm = Form.form(Message.class);
    public static final Form<Search> searchForm = Form.form(Search.class);
    
    
	public static Result message(){
		return ok(views.html.message.messageMainPage.render(searchForm));
	}
	
	public static Result writeMessageWithUser(User user){
		return ok(views.html.message.sendMessageWithUserPage.render(messageForm, user, searchForm));
	}
	
	public static Result writeMessage(){
		
		return ok(views.html.message.sendMessagePage.render(messageForm, searchForm));
	}
	
	public static Result sendMessage(){
		Form<Message> messageForm = Form.form(Message.class).bindFromRequest();
		if(messageForm.hasErrors()){
			flash("error","send Message error");
			return badRequest(views.html.message.sendMessagePage.render(messageForm, searchForm));
			
		}
		Message message = messageForm.get();
		String userTo = message.userNameTo;
		if(User.findById(userTo) == null){
			flash("error", "Utilisateur Introuvable");
			return badRequest(views.html.message.sendMessagePage.render(messageForm, searchForm));
		}
		User userFrom = User.findById(Application.getSessionUser());
		message.userNameFrom = userFrom;
		message.save();
		
		flash("success","Le message a bien été envoyé");
		return GO_HOME_MESSAGE;
	}
	
	public static Result deleteNoti(int idNoti) {
	    final Notification noti = Notification.findById(idNoti);
	    if(noti == null) {
	        return notFound(String.format("Tag %s does not exists.", idNoti));
	    }
	    Ebean.delete(noti);
	    flash("success", String.format("La notification a bien été supprimée"));
	    return GO_HOME_MESSAGE;
	  }
	
	public static Result deleteMess(int idMess) {
	    final Message mess = Message.findById(idMess);
	    if(mess == null) {
	        return notFound(String.format("Tag %s does not exists.", idMess));
	    }
	    Ebean.delete(mess);
	    return GO_HOME_MESSAGE;
	  }
	
	@BodyParser.Of(BodyParser.Json.class)
	public static Result deleteListMess(){
		JsonNode json = request().body().asJson();
		
		if(json ==null){
			return badRequest("Json data not found");
		}else{
			Iterator<JsonNode> it = json.elements();
			while(it.hasNext()){
				String idMess = it.next().textValue();
				System.out.println(idMess);
				Message.deleteMessage(idMess);
			}
		}
		
		return ok();
	}
	
	@BodyParser.Of(BodyParser.Json.class)
	public static Result deleteListNotis(){
		JsonNode json = request().body().asJson();
		
		if(json ==null){
			return badRequest("Json data not found");
		}else{
			Iterator<JsonNode> it = json.elements();
			while(it.hasNext()){
				String idMess = it.next().textValue();
				System.out.println(idMess);
				Notification.deleteNotification(idMess);
			}
		}
		
		return ok();
	}
}