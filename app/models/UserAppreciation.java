package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

import play.db.ebean.Model;
@Entity
public class UserAppreciation extends Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	public int idAppreciation;
	@ManyToOne
	public User user;
	@ManyToOne
	public Thread thread;
	//3 stages: 0, 2:dislike, 1: like
	
	public int status;
	@Size(min = 0, max = 5)
	public int note;
	public boolean isFavorited;
	
	public static Finder<Integer, UserAppreciation> find =  new Finder<Integer, UserAppreciation>(Integer.class, UserAppreciation.class);

	
	
	public static UserAppreciation findById(int idAppre){
		return find.byId(idAppre);
	}
	
	public static List<UserAppreciation> findBookmark(User user){
		return find.where()
				   .eq("user_user_name", user.userName)
				   .eq("is_favorited", true)
				   .findList();
	}
	
	public static UserAppreciation findByUserThread(User user, Thread thread){
		return find.where()
				   .eq("user_user_name", user.userName)
				   .eq("thread_id_thread", thread.idThread)
				   .findUnique();
	}
	
	public static boolean likeThread(String user, String thread){
		return UserAppreciation.actionThread(user, thread, 1);
	}
	
	public static boolean dislikeThread(String user, String thread){
		return UserAppreciation.actionThread(user, thread, 2);
	}
	
	public static boolean actionThread(String userName, String idThread, int status){
		User user   = User.findById(userName);
		Thread thread = Thread.findById(Integer.parseInt(idThread));
		if(user == null || thread == null) return false;
		else{
			UserAppreciation ua = findByUserThread(user, thread);
			//Case new
			if(ua == null){
				UserAppreciation uaNew = new UserAppreciation();
				uaNew.thread = thread;
				uaNew.user   = user;
				uaNew.status = status;
				uaNew.save();
				thread.likeCount = totalLike(thread);
				thread.dislikeCount = totalDisike(thread);
				thread.update();
				return true;
				
			}
			//Case update
			else{
				ua.status = status;
				ua.update();
				thread.likeCount = totalLike(thread);
				thread.dislikeCount = totalDisike(thread);
				thread.update();
				return true;
			}
		}
	}
	
	public static boolean noteThread(String userName, String idThread, int note){
		
		User user   = User.findById(userName);
		Thread thread = Thread.findById(Integer.parseInt(idThread));
		if(user == null || thread == null) return false;
		else{
			UserAppreciation ua = findByUserThread(user, thread);
			//Case new
			if(ua == null){
				UserAppreciation uaNew = new UserAppreciation();
				uaNew.thread = thread;
				uaNew.user   = user;
				uaNew.note = note;
				uaNew.save();
				thread.rating = averageThread(thread);
				thread.update();
				return true;
			}
			//Case update
			else{
				ua.note = note;
				ua.update();
				thread.rating = averageThread(thread);
				thread.update();
				return true;
			}
		}
		
	}
	
	public static boolean bookmarkThread(String userName, String idThread){
		User user   = User.findById(userName);
		Thread thread = Thread.findById(Integer.parseInt(idThread));
		if(user == null || thread == null) return false;
		else{
			UserAppreciation ua = findByUserThread(user, thread);
			//Case new
			if(ua == null){
				UserAppreciation uaNew = new UserAppreciation();
				uaNew.thread = thread;
				uaNew.user   = user;
				uaNew.isFavorited = true;
				uaNew.save();
				thread.favoriteCount = totalFavorit(thread);
				thread.update();
				return true;
			}
			//Case update
			else{
				ua.isFavorited = true;
				ua.update();
				thread.favoriteCount = totalFavorit(thread);
				thread.update();
				return true;
			}
		}
		
	}
	
	public static void deleteBookMarkThread(UserAppreciation ua){
		ua.isFavorited = false;
		ua.update();
	}
	
	public static List<UserAppreciation> findListByThread(Thread thread){
		return  find.where()
				.eq("thread_id_thread", thread.idThread)
				.findList();
	}
	
	public static int totalPoints(Thread thread, int status){
		List<UserAppreciation> listUA = findListByThread(thread);
		int total = 0;
		//1:like, 2 dislike
		for (UserAppreciation userAppreciation : listUA) {
			if(userAppreciation.status == status) total += 1;
		}
		return total;
	}
	
	public static int totalFavorit(Thread thread){
		List<UserAppreciation> listUA = findListByThread(thread);
		int total = 0;
		//1:like, 2 dislike
		for (UserAppreciation userAppreciation : listUA) {
			if(userAppreciation.isFavorited == true) total += 1;
		}
		return total;
	}
	
	public static int averageThread(Thread thread){
		List<UserAppreciation> listUA = findListByThread(thread);
		int total = 0;
		for (UserAppreciation userAppreciation : listUA) {
			total += userAppreciation.note;
		}
		return total / listUA.size();
	}
	
	public static int totalLike(Thread thread){
		return totalPoints(thread, 1);
	}
	
	public static int totalDisike(Thread thread){
		return totalPoints(thread, 2);
	}
	
	
	
	
	
	
	
	

}
