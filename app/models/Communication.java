package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.Constraint;

import play.data.validation.Constraints;
import play.db.ebean.Model.Finder;
import play.mvc.PathBindable;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Page;

@Entity
public class Communication implements PathBindable<Communication>{
	
	@Id
	public int idCommunication;
	@Constraints.Required
	public String nameCommunication;
	@Column(columnDefinition = "LONGTEXT")
	@Constraints.Required
	public String content;
	public Date lastDateUpdate;
	public Date createDate;
	
	
	/**
	 * Generic query helper for entity Communication with id Integer
	 * */
	public static Finder<Integer, Communication> find = new Finder<Integer, Communication>(Integer.class, Communication.class);
	
	/**
	 * Return a page of Communication
	 * @param page Page to display
	 * */
	
	public static Page<Communication> find(int page){
		return find.where()
				.orderBy("idCommunication asc")
				.findPagingList(10)
				.setFetchAhead(false)
				.getPage(page);
	}
	
	public static List<Communication> findList(){
		return find.all();
	}
	
	/**
	 * Return bonus by its Id
	 * */
	
	public static Communication findByID(String idCommunication){
		return find.where().eq("id_communication", idCommunication).findUnique();
	}

	@Override
	public Communication bind(String key, String value) {
		return findByID(value);
	}

	@Override
	public String javascriptUnbind() {
		return String.valueOf(this.idCommunication);
	}

	@Override
	public String unbind(String arg0) {
		return String.valueOf(this.idCommunication);
	}
	
	public static void deleteCommunication(String idCom){
		final Communication com = Communication.findByID(idCom);
		if(com != null){
			Ebean.delete(com);
		}
		
	}
}



















