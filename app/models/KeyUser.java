package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.db.ebean.Model;

@Entity
public class KeyUser extends Model{

	private static final long serialVersionUID = 1L;

	@Id
	public int idKeyUser;
	@ManyToOne
	public Application app;
	@ManyToOne
	public User user;
	
	public static Finder<Integer, KeyUser> find = new Finder<Integer, KeyUser>(Integer.class, KeyUser.class);
	
	public static List<KeyUser> findKeyUsersByApp(Application app){
		return find.where()
				.eq("app_id_app", app.idApp)
				.findList();
	}
	
	public static List<KeyUser> findKeyUsersByUser(String userName){
		return find.where()
				.eq("user_user_name", userName)
				.findList();
	}
	
}
