package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import com.avaje.ebean.Page;
@Entity
public class Message extends Model {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	public int idMessage;
	@ManyToOne
	public User userNameFrom;
	
	@Constraints.Required
	public String userNameTo;
	@Constraints.Required
	public String title;
	public Date sendDate = new Date();
	@Column(columnDefinition = "LONGTEXT")
	@Constraints.Required
	public String content;
	public boolean viewed;
	@OneToOne
	public Message previousMail;
	
	public static Finder<Integer, Message> find = new Finder<Integer, Message>(Integer.class, Message.class);
	
	public static List<Message> findMessagesToByUserApi(String userName){
		return find.where()
				.ilike("user_name_to", "%" + userName + "%")
				.orderBy().asc("viewed")
				.orderBy().desc("sendDate")
				.findList();
	}
	
	
	/**
	 * Method used to find the message sent to a user
	 * @param userName messages sent to this user
	 * @return
	 */
	public static Page<Message> findMessagesToByUser(String userName, int page){
		return find.where()
				.ilike("user_name_to", "%" + userName + "%")
				.orderBy().asc("viewed")
				.orderBy().desc("sendDate")
				.findPagingList(10)
				.setFetchAhead(false)
				.getPage(page);
	}
	/**
	 * Method used to count the unread mesasges
	 * @param userName mesasges belong to this user
	 * @return
	 */
	public static int messagesUnread(String userName){
		return find.where().eq("viewed", 0)
				   .eq("user_name_to", userName)
				   .findRowCount();
	}
	/**
	 * Method used to return 
	 * @param userName
	 * @return
	 */
	public static Page<Message> findMessagesFromByUser(String userName, int page){
		return find.where()
				.ilike("user_name_from_user_name", "%" + userName + "%")
				.orderBy().desc("sendDate")
				.findPagingList(10)
				.setFetchAhead(false)
				.getPage(page);
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
