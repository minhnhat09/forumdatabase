package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import play.db.ebean.Model;
@Entity
public class Permission extends Model{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	public int idPermission;
	
	public String permissionName;
	public String permissonDescription;
	
	@OneToMany(mappedBy = "permission")
	public List<UserPermission> users;
	
	@OneToOne(mappedBy = "permission")
	public User user;
	
	public static Finder<Integer, Permission> find = new Finder<Integer, Permission>(Integer.class, Permission
			.class);
	
	public static Permission findById(int idPermission){
		return find.byId(idPermission);
	}
	
}
