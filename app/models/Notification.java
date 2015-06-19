package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.db.ebean.Model;

import com.avaje.ebean.Page;

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
	public boolean viewed;
	public int idThreadResponse;
	
	
	public static Finder<Integer, Notification> find = new Finder<Integer, Notification>(Integer.class, Notification.class);
	
	public static Notification findById(int id){
		return find.byId(id);
	}
	


	
	public static int notificationsUnread(String userName){
		return find.where().eq("viewed", 0)
				  .eq("user_user_name", userName)
				.findRowCount();
	}
	
	public static List<Notification> findListNotisByThread(String idThread){
		return find.where().eq("viewed", 0)
				  .eq("id_thread_response", idThread)
				.findList();
	}
	
	public static List<Notification> listNotificationsUnread(String userName){
		return find.where().eq("viewed", 0)
				  .eq("user_user_name", userName)
				.findList();
	}
	
	
	
	public static Page<Notification> findNotificationsByUser(String userName, int page){
		return find.where()
				.ilike("user_user_name", "%" + userName + "%")
				.orderBy().desc("noteDate")
				.findPagingList(10)
				.setFetchAhead(false)
				.getPage(page);
	}
	
	public static List<Notification> findNotificationsByUserApi(String userName){
		return find.where()
				.ilike("user_user_name", "%" + userName + "%")
				.orderBy().desc("noteDate")
				.findList();
	}
	
	

	public static void deleteNotification(String idNoti){
		int notiString = Integer.parseInt(idNoti);
		final Notification noti = Notification.findById(notiString);
		if(noti != null){
			noti.delete();
			 System.out.println("Delete noti: " + idNoti + " ok");
		}else System.out.println("Delete noti: " + idNoti + " not ok");
			
		
	}
	
}
