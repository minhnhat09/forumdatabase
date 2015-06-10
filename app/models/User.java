package models;



import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import play.data.validation.Constraints;
import play.data.validation.Constraints.Email;
import play.data.validation.Constraints.Min;
import play.data.validation.Constraints.MinLength;
import play.db.ebean.Model;
import play.mvc.PathBindable;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Page;
@Entity
public class User extends Model implements PathBindable<User>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@MinLength(5)
	public String userName;
	
	@MinLength(7)
	@Constraints.Required
	public String password;
	@MinLength(7)
	@Constraints.Required
	public String confirmPassword;
	
	public int bonus;
	
	public int exp;
	
	
	@Constraints.Required
	public String email;
	
	
	@Constraints.Required
	public String confirmEmail;
	
	public String title;
	
	public int postCount;
	
	@Constraints.Required
	public String firstName;
	
	@Constraints.Required
	public String lastName;
	
	@Constraints.Required
	public String address;
	
	public String homePhone;
	
	public String mobilePhone;
	
	public int idServiceSubscribe;
	
	@Constraints.Required
	public String postalCode;
	
	@Constraints.Required
	public String city;
	
	@Constraints.Required
	public String country;
	
	
	public boolean civilite;
	
	public String role;
	
	public String imgUrl;
	
	public String signature;
	
	
	
	public String ipn;
	public boolean isBlock;
	public boolean isExpert;
	public String avatar;
	public byte[] imageAvatar;
	public Date dateInscription;
	
	public Date dateFinalPost;
	@Column(columnDefinition = "LONGTEXT")
	public String presentation;
	@Column(columnDefinition = "LONGTEXT")
	public String hoppy;
	
	@Constraints.Required
	public String signinCode;
	
	
	public boolean isPremium;
	
	public int threadCountViews;
	
	@ManyToMany
	public List<Application> apps = new LinkedList<Application>();
	@ManyToOne
	public Service service;
	@OneToOne
	public Permission permission;
	
	@OneToMany(mappedBy = "user")
	List<Notification> notifications = new ArrayList<Notification>();
	@OneToMany
	public List<AccountValidation> accountValidations = new ArrayList<AccountValidation>();
	
	@OneToMany(mappedBy = "user")
	public List<GiftUser> gifts;
	@OneToMany(mappedBy = "user")
	public List<UserAppreciation> appreciations;
	@OneToMany(mappedBy = "user")
	public List<Contact> users;
	@OneToMany(mappedBy = "contact")
	public List<Contact> contacts;
	@OneToMany(mappedBy = "user")
	public List<UserPermission> permissions;
	@OneToMany(mappedBy = "userNameFrom")
	public List<Message> messagesFrom;
	@OneToMany(mappedBy = "author")
	public List<Thread> threads;
	@OneToMany
	public List<Post> posts;
	@OneToMany(mappedBy="user")
	public List<ApplicationView> appViews;
	/**
	 * one demandpremium for one user
	 */
	@OneToOne(mappedBy="user")
	public DemandPremium demandPremium;
	
	public static void delUser(User user){
		//clear app, ManytoMany
		for(Application app: user.apps){
			
		}
		user.apps.clear();
		//clear noti
		for(Notification noti: user.notifications){
			noti.delete();
		}
		user.notifications.clear();
		//clear account validation
		for(AccountValidation av: user.accountValidations){
			av.delete();
		}
		//clear giftuser
		user.accountValidations.clear();
		for(GiftUser gu: user.gifts){
			gu.delete();
		}
		user.gifts.clear();
		//clear appreciations
		for(UserAppreciation ua: user.appreciations){
			ua.delete();
		}
		user.appreciations.clear();
		//clear user
		for(Contact friend: user.users){
			friend.delete();
		}
		user.users.clear();
		//clear contact
		for(Contact contact: user.contacts){
			contact.delete();
		}
		user.contacts.clear();
		//clear userpermission
		for(UserPermission up: user.permissions){
			up.delete();
		}
		user.permissions.clear();
		//clear messagesFrom
		for(Message message: user.messagesFrom){
			message.delete();
		}
		user.messagesFrom.clear();
		//clear threads
		for(Thread thread: user.threads){
			Thread.delThread(thread);
		}
		user.threads.clear();
		//clear post
		for(Post post: user.posts){
			post.delete();
		}
		user.posts.clear();
		
		Ebean.update(user);
		Ebean.delete(user);
	}
	
	/**
	 * Generic query helper for entity User with id Integer
	 * */
	
	public static Finder<Integer, User> find = new Finder<Integer, User>(Integer.class, User.class);
	
	/**
	 * Return a User by its Id
	 * @param idUser Id of the user
	 * */
	
	public static List<User> findKeyUsersByApp(Application app){
		return find.where()
				   .eq("is_expert", true)
				   .eq("service_id_service", app.idApp)
				   .findList();
	}
	
	public static List<User> findModsByApp(Application app){
		return find.where()
				   .eq("permission_id_permission", 2)
				   .eq("service_id_service", app.idApp)
				   .findList();
	}
	
	public static User findById(String userName){
		return find.where().eq("user_name",userName).findUnique();
	}
	
	public static boolean checkEmail(String email){
		for(User user: find.findList()){
			if(email.equals(user.email)) return true;
			
		}
		
		return false;
	}
	/**
	 * Return a page of User
	 * @param page Page to display
	 * */
	public static Page<User> find(int page, int pageSize,
			String sortBy, String order, String filter){
		return find.where()
				   .ilike("user_name", "%" + filter + "%")
				   .orderBy(sortBy + " " + order)
				   .findPagingList(pageSize)
				   .setFetchAhead(false)
				   .getPage(page);
					
	}
	
	public static User findUserbyUserName(String userName){
		return find.where().ilike("user_name", userName).findUnique();
	}

	/**
	 * Check if experience point of user is greater than the experience point of the title
	 * If yes, update the user title
	 * @param user
	 * @return
	 */
	public static String showTitle(User user){
		List<Title> listTitle = Title.findListTitles();
		
		for (Title title : listTitle) {
			
			if(user.exp >= title.exp){
				
				return title.titleName;
				
			}
		}
		return "Titre inconnu";
	}
	
	
	@Override
	public User bind(String key, String value) {
		return findById(value);
	}

	@Override
	public String javascriptUnbind() {
		return this.userName;
	}

	@Override
	public String unbind(String arg0) {
		return this.userName;
	}

	 public static User authenticate(String userName, String password) {
	        return find.where()
	            .eq("user_name", userName)
	            .eq("password", password)
	            .findUnique();
	    }

}
