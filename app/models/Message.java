package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.data.validation.Constraints;
import play.db.ebean.Model;
@Entity
public class Message extends Model {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	public int idMessage;
	@ManyToOne
	public User userNameFrom;
	
	@Constraints.Required
	public String userNameTo;
	
	public Date sendDate = new Date();
	@Column(columnDefinition = "LONGTEXT")
	@Constraints.Required
	public String content;
	public boolean unRead;
	
	
	public static Finder<Integer, Message> find = new Finder<Integer, Message>(Integer.class, Message.class);
	
	public static List<Message> findMessagesToByUser(String userName){
		return find.where()
				.ilike("user_name_to", "%" + userName + "%")
				.orderBy().desc("sendDate")
				.findList();
	}
	
	public static int messagesUnread(String userName){
		return find.where().eq("un_read", 0)
				  .eq("user_name_to", userName)
				  
				.findRowCount();
	}
	
	public static Message findById(int idMess){
		return find.byId(idMess);
	}
	
	public static void deleteMessage(String idMess){
		int messString = Integer.parseInt(idMess);
		Message mess = Message.findById(messString);
		if(mess != null){
			mess.delete();
			 
		}
			
		
	}
}
