package api;

import java.util.List;

import models.Message;
import models.Notification;
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
	
	
}
