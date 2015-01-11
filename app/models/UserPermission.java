package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import play.db.ebean.Model;

@Entity
@Table(name = "table_mode")
public class UserPermission extends Model {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Id
	public int idUserPermission;
	
	@ManyToOne
	public Application app;
	
	@ManyToOne
	//nó sẽ lấy tên user + với id của bảng User, vd:user_user_name
	//rồi nối với user(user_name)
	public User user;
	@ManyToOne
	public Permission permission;
	
	
	
	public static Finder<Integer, UserPermission> find = new Finder<Integer, UserPermission>(Integer.class, UserPermission.class);
	
	public static List<UserPermission> findModByApp(Application app){
		return find.where()
				.eq("app_id_app", app.idApp)
				.findList();
	}
	
	public static boolean isMod(Application app, User user){
		UserPermission permission = find.where()
										.eq("app_id_app", app.idApp)
										.eq("user_user_name", user.userName)
										.findUnique();
		if(permission != null) return true;
		else return false;
	}
	
	
}
