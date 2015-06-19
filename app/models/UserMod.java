package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.db.ebean.Model;

@Entity
public class UserMod extends Model{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	@Id
	public int idUserMod;
	@ManyToOne
	public Service service;
	@ManyToOne
	public User user;
	
	public static Finder<Integer, UserMod> find = new Finder<Integer, UserMod>(Integer.class, UserMod.class);
	
	public static List<UserMod> findModsByService(Service service){
		return find.where()
				.eq("service_id_service", service.idService)
				.findList();
	}
}
