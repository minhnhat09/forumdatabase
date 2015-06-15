package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Page;

import play.data.validation.Constraints;
import play.db.ebean.Model;
import play.mvc.PathBindable;
@Entity
public class Title extends Model implements PathBindable<Title>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	public int idTitle;
	@Constraints.Required
	public String titleName;
	@Constraints.Required
	public int exp;
	
	public static Finder<Integer, Title> find = new Finder<Integer, Title>(Integer.class, Title.class);
	
	public static List<Title> findListTitles(){
		return find.orderBy("exp desc")
				.findList();
	}
	
	public static Title findById(String idTitle){
		return find.where()
				   .eq("id_title", idTitle)
				   .findUnique();
	}
	
	/**
	 * Return a page of titles
	 * @param page Page to display
	 * */
	
	public static Page<Title> find(int page){
		return find.where()
				.orderBy("id_title asc")
				.findPagingList(10)
				.setFetchAhead(false)
				.getPage(page);
	}
	

	@Override
	public Title bind(String key, String value) {
		return find.byId(Integer.parseInt(value));
	}

	@Override
	public String javascriptUnbind() {
		return String.valueOf(this.idTitle);
	}

	@Override
	public String unbind(String arg0) {
		return String.valueOf(this.idTitle);
	}
	
	public static void deleteTitle(String idTitle){
		final Title title = findById(idTitle);
		if(title != null){
			Ebean.delete(title);
		}
	}
	
}
