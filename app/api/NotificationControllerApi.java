package api;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;

import models.Message;
import models.Notification;
import models.User;
import play.mvc.Controller;
import play.mvc.Result;
import flexjson.JSONSerializer;

public class NotificationControllerApi extends Controller{
	
	/**
	 * Method used to get list user messages
	 * When client send the request, server know whom the client talk about by retrieving the user login in session
	 * @return list user messages
	 */
	public static Result findMessagesByUser(){

		System.out.println(session("userNameMobile"));
		String userName = session("userNameMobile");
		List<Message> messages = Message.findMessagesToByUser(userName);
		JSONSerializer serializer = new JSONSerializer();
		String json = serializer.serialize(messages);
		return ok(json);
	}
	
	/**
	 * Method used to get list user messages
	 * When client send the request, server know whom the client talk about by retrieving the user login in session
	 * @return list user messages
	 */
	public static Result findNotisByUser(){
		String userName = session("userNameMobile");
		List<Notification> notis = Notification.findNotificationsByUser(userName);
		JSONSerializer serializer = new JSONSerializer();
		String json = serializer.serialize(notis);
		return ok(json);
	}
	
	public static Result sendMessage(){
		JsonNode json = request().body().asJson();
		if(json == null){
			return badRequest();
		}else{
			String content = json.get("content").asText();
			String receiverString = json.get("receiver").asText();
			User sender = User.findById(session("userNameMobile"));
			User receiver = User.findById(receiverString);
			if(receiver == null){
				return badRequest("Utilisateur introuvable");
			}else{
				Message message = new Message();
				message.userNameFrom = sender;
				message.userNameTo = receiverString;
				message.content = content;
				message.save();
			}
			
		}
		
		return ok("Success");
	}
	
	
}
