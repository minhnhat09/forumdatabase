package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Page;

import play.db.ebean.Model;
import play.mvc.PathBindable;
@Entity
public class Application extends Model implements PathBindable<Application>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	public int idApp;
	public String appName;
	public String appDescription;
	public String avatarApp;
	
	@ManyToMany(mappedBy = "apps")
	List<User> listKeyUsers;
	
	@ManyToOne
	public Service service;
	
	
	@OneToMany
	public List<Thread> threads;
	
	
	@OneToMany(mappedBy = "app")
	public List<UserPermission> userPermission;
	
	
	public static Finder<Integer, Application> find = new Finder<Integer, Application>(Integer.class, Application.class);
	
	public static String presentApp(int idApp){
		return find
				.byId(idApp)
				.appDescription;
				
	}
	
	public static void delApp(Application app){
		for(Thread thread: app.threads){
			Thread.delThread(thread);
		}
		app.threads.clear();
		
		for(UserPermission up: app.userPermission){
			up.delete();
		}
		app.userPermission.clear();
		
		for(User user: app.listKeyUsers){
			
		}
		app.listKeyUsers.clear();
		
		Ebean.update(app);
		Ebean.delete(app);
	}
	
	
	/**
	 * Refresh like, dislike, nb reply, nb reader, note
	 * */
	
	
//	public static List<User> findKeyUsers(int idApp){
//		return find
//				.byId(idApp)
//				.listKeyUsers;
//	}
	
	public static Application findById(String idApp){
		return find.byId(Integer.parseInt(idApp));
	}
	
	
	public static List<Application> findByService(Service service){
		return find.where()
				   .eq("service_id_service", service.idService)
				   .findList();
	}
	
	public static List<Application> findByIdService(String idService){
		return find.where()
				   .eq("service_id_service", idService)
				   .findList();
	}
	
	public static List<Application> findList(){
		return find.where()
				   .orderBy("id_app")
				   .findList();
	}
	
	public static Page<Application> find(int page){
		return 
				find.where()
				.orderBy("id_app asc")
				.findPagingList(10)
				.setFetchAhead(false)
				.getPage(page);
				
	}
	
	
	@Override
	public Application bind(String key, String value) {
		return findById(value);
	}
	@Override
	public String javascriptUnbind() {
		return String.valueOf(this.idApp);
	}
	@Override
	public String unbind(String arg0) {
		return String.valueOf(this.idApp);
	}
	
	
}
