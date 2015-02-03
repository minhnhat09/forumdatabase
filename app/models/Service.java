package models;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import play.data.validation.Constraints;
import play.db.ebean.Model;
import play.mvc.PathBindable;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Page;

@Entity
public class Service extends Model implements PathBindable<Service>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int idService;
	@Constraints.Required
	public String serviceName;
	public Date createDate;
	public String avatarService;
	public String idServiceMapped;
	public boolean hidden;
	
	
	
	@OneToMany(mappedBy = "service")
	public List<Application> apps;
	
	@OneToMany(mappedBy = "service")
	public List<User> users;
	
	public static Finder<Integer, Service> find = new Finder<Integer, Service>(Integer.class, Service.class);
	
	public static void delService(Service service){
		for(Application app: service.apps){
			Application.delApp(app);
		}
		service.apps.clear();
		
		for(User user: service.users){
			Service serviceHideen = Service.findById("5");
			user.service = serviceHideen;
			user.update();
		}
		
		service.users.clear();
		
		Ebean.update(service);
		Ebean.delete(service);
	}
	
	public static Page<Service> find(int page){
		return 
				find.where()
				.eq("hidden", false)
				.orderBy("id_service asc")
				.findPagingList(10)
				.setFetchAhead(false)
				.getPage(page);
				
	}
	
	public static Map<String, String> options(){
		LinkedHashMap<String, String> options = new LinkedHashMap<String, String>();
		for(Service s: find.where().eq("hidden", false)
				.orderBy("id_service").findList()){
			options.put(String.valueOf(s.idService), s.serviceName);
		}
		return options;
	}
	
	public static  List<Service> findAllServices(){
		return find.where()
				   .eq("hidden", false)
				   .findList();
	}
	
	public static String findNameService(int idService){
		if(idService == 5)return "pas de service";
		return find.byId(idService).serviceName;
	}
	
	public static Service findById(String idService){
		return find.where().eq("id_service", idService).findUnique();
	}
	
	@Override
	public Service bind(String key, String value) {
		return findById(value);
	}

	@Override
	public String javascriptUnbind() {
		return String.valueOf(this.idService);
	}

	@Override
	public String unbind(String arg0) {
		return String.valueOf(this.idService);
	}
	
}
