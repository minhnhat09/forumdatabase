package api;

import java.util.List;

import models.Contact;
import models.Gift;
import models.Message;
import models.User;
import models.UserAppreciation;
import play.mvc.Controller;
import play.mvc.Result;
import flexjson.JSONSerializer;

public class UserControllerApi extends Controller{
	
	/**
	 * Method used to get user information current user
	 * When client send the request, server know whom the client talk about by retrieving the user login in session
	 * @return user info
	 */
	public static Result findUserInfo(){
		User user = User.findById(session("userNameMobile"));
		JSONSerializer serializer = new JSONSerializer();
		String json = serializer.serialize(user);
		return ok(json);
	}
	/**
	 * Method used to get list user gifts
	 * When client send the request, server know whom the client talk about by retrieving the user login in session
	 * @return list user gifts
	 */
	public static Result findGiftsByUser(){
		String userName = session("userNameMobile");
		List<Gift> gifts = Gift.findByUser(userName);
		JSONSerializer serializer = new JSONSerializer();
		String json = serializer.serialize(gifts);
		return ok(json);
	}
	
	/**
	 * Method used to get list user contacts
	 * When client send the request, server know whom the client talk about by retrieving the user login in session
	 * @return list user contacts
	 */
	public static Result findContactsByUser(){
		User user = User.findById(session("userNameMobile"));
		List<Contact> contacts = Contact.findContacts(user);
		JSONSerializer serializer = new JSONSerializer();
		String json = serializer.serialize(contacts);
		return ok(json);
	}
	
	/**
	 * Method used to get list user bookmarks
	 * When client send the request, server know whom the client talk about by retrieving the user login in session
	 * @return list user bookmarks
	 */
	public static Result findBookmarksByUser(){
		User user = User.findById(session("userNameMobile"));
		List<UserAppreciation> uas = UserAppreciation.findBookmark(user);
		JSONSerializer serializer = new JSONSerializer();
		String json = serializer.serialize(uas);
		return ok(json);
	}
	
	
	
	
}
