package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.db.ebean.Model;

@Entity
public class Notification extends Model {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	public int idNotification;
	@ManyToOne
	public User user;
	public User userResponse;
	@Column(columnDefinition = "LONGTEXT")
	public String content;
	public Date noteDate;
	@Column(nullable=false, columnDefinition="boolean default true")
	public boolean unRead;
	public int idThreadResponse;
	
	
	public static Finder<Integer, Notification> find = new Finder<Integer, Notification>(Integer.class, Notification.class);
	
	public static Notification findById(int id){
		return find.byId(id);
	}
	

	public static int notificationsUnread(String userName){
		return find.where().eq("un_read", 1)
				  .eq("user_user_name", userName)
				.findRowCount();
	}
	
	public static List<Notification> listNotificationsUnread(String userName){
		return find.where().eq("un_read", 1)
				  .eq("user_user_name", userName)
				.findList();
	}
	
	
	
	public static List<Notification> findNotificationsByUser(String userName){
		return find.where()
				.ilike("user_user_name", "%" + userName + "%")
				.orderBy().desc("noteDate")
				.findList();
	}

	public static void deleteNotification(String idMess){
		int messString = Integer.parseInt(idMess);
		Notification mess = Notification.findById(messString);
		if(mess != null){
			mess.delete();
			 System.out.println("Delete noti: " + idMess + " ok");
		}else System.out.println("Delete noti: " + idMess + " not ok");
			
		
	}
	
}
