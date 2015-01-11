package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import play.db.ebean.Model;
import play.mvc.PathBindable;

@Entity
public class Contact extends Model implements PathBindable<Contact>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	public int idContact;
	@NotNull
	public Date sendDate;
	public Date addDate;
	@NotNull
	public boolean isConfirmed;
	@NotNull
	public boolean isSender;
	@NotNull
	@ManyToOne
	public User user;
	@NotNull
	@ManyToOne
	public User contact;

	public static Finder<Integer, Contact> find = new Finder<Integer, Contact>(Integer.class, Contact.class);
	
	public static Contact findById(int idContact){
		return find.byId(idContact);
	}
	
	/**
	 * Method returns true if contact existed
	 * @param user Request sender
	 * @param ct   Request receiver
	 * 
	 * */
	
	public static boolean isContactExisted(User user, User ct){
		Contact contact = find.where()
							  .eq("user_user_name", user.userName)
							  .eq("contact_user_name", ct.userName)
							  .findUnique();
		if(contact == null)return false;
		else return true;
		
	}
	
	/**
	 * Method returns list "friends" of user
	 * @param user User connected
	 * 
	 * */
	
	public static List<Contact> findContacts(User user){
		return find.where()
				   .eq("user_user_name", user.userName)
				   .eq("is_confirmed", true)
				   .orderBy("id_contact asc")
				   .findList();
	}
	
	/**
	 * Method returns true if success of sending a request
	 * @param user Request sender
	 * @param ct   Request receiver
	 * 
	 * */
	
	public static boolean sendRequest(String idSender, String idReceiver){
		User user = User.findById(idSender);
		
		User ct   = User.findById(idReceiver);
	
		if(Contact.isContactExisted(user, ct) || user.equals(ct)){
			return false;
		}else{
			Contact sender = new Contact();
			sender.user = user;
			sender.contact = ct;
			sender.sendDate = new Date();
			sender.isConfirmed = false;
			sender.isSender = true;
			
			Contact receiver = new Contact();
			receiver.user = ct;
			receiver.contact = user;
			receiver.sendDate = new Date();
			receiver.isConfirmed = false;
			receiver.isSender = false;
			sender.save();
			receiver.save();
			return true;
		}
	}
	
	public static Contact findContact(User sender, User receiver){
		return find.where()
				   .eq("user_user_name", sender.userName)
				   .eq("contact_user_name", receiver.userName)
				   .findUnique();
	}
	
	public static List<Contact> findContactsUnconfirmed(User user){
		return find.where()
				   .eq("user_user_name", user.userName)
				   .eq("is_confirmed", false)
				   .eq("is_sender", false)
				   .orderBy("id_contact asc")
				   .findList();
	}
	
	public static boolean confirmContact(String idContact){
		Contact contact = Contact.findById(Integer.parseInt(idContact));
		Contact sender = Contact.findContact(contact.contact, contact.user);
		if(sender != null){
			sender.isConfirmed = true;
			contact.isConfirmed = true;
			sender.addDate = new Date();
			contact.addDate = new Date();
			sender.update();
			contact.update();
			
			return true;
		}else return false;
	}
	
	
	@Override
	public Contact bind(String key, String value) {
		return findById(Integer.parseInt(value));
	}
	@Override
	public String javascriptUnbind() {
		return String.valueOf(this.idContact);
	}
	@Override
	public String unbind(String arg0) {
		return String.valueOf(this.idContact);
	}
		
			
}
