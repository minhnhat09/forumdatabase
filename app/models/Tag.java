package models;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.avaje.ebean.Page;

import play.db.ebean.Model;
import play.mvc.PathBindable;

@Entity
public class Tag extends Model implements PathBindable<Tag>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	public int idTag;
	public String tagTitle;
	public String category;
	
	
	
	
	@ManyToMany(mappedBy = "tags")
	List<Thread> threads;
	
	
	public static Finder<Integer, Tag> find  = new Finder<Integer, Tag>(Integer.class, Tag.class);
	
	public static Tag findById(int id){
		return find.byId(id);
	}
	
	public static List<Tag> findListTag(){
		return find.orderBy("category asc")
				.findList();
	}
	
	public static List<Tag> findByCat(){
		return find.setDistinct(true)
				.findList();
	}
	
	public static List<Tag> findByCountry(){
		String category = "pays";
		return find
				.where()
				.eq("category", category)
				.findList();
	}
	
	public static List<Tag> findByModule(){
		String category = "modules";
		return find
				.where()
				.eq("category", category)
				.findList();
	}
	
	public static Map<String,String> countryOptions() {
        LinkedHashMap<String,String> options = new LinkedHashMap<String,String>();
        for(Tag t: Tag.findByCountry()) {
            options.put(String.valueOf(t.idTag), t.tagTitle);
        }
        return options;
    }
	
	public static Map<String,String> moduleOptions() {
        LinkedHashMap<String,String> options = new LinkedHashMap<String,String>();
        for(Tag t: Tag.findByModule()) {
            options.put(String.valueOf(t.idTag), t.tagTitle);
        }
        return options;
    }
	
	public static Map<String,String> categoryOptions() {
        LinkedHashMap<String,String> options = new LinkedHashMap<String,String>();
        for (int i = 0; i < Tag.findByCat().size(); i++) {
			options.put(String.valueOf(i), Tag.findByCat().get(i).category);
		}
        return options;
    }
	
	public Tag(){
		
	}



	@Override
	public Tag bind(String key, String value) {
		return findById(Integer.parseInt(value));
	}



	@Override
	public String javascriptUnbind() {
		return String.valueOf(this.idTag);
	}



	@Override
	public String unbind(String arg0) {
		return String.valueOf(this.idTag);
	}
	
}
