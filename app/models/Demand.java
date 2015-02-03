package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.data.validation.Constraints;
import play.db.ebean.Model;
import play.mvc.PathBindable;

import com.avaje.ebean.Page;

import controllers.Global;

@Entity
public class Demand extends Model implements PathBindable<Demand>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	public int idDemand;
	@Constraints.Required
	public String name;
	@Constraints.Required
	public String ipn;
	public String idService;
	@Constraints.Required
	public String email;
	@Constraints.Required
	public String motif;
	public Date dateApply;
	public boolean isActivated;
	
	public static Finder<Integer, Demand> find = new Finder<Integer, Demand>(
			Integer.class, Demand.class);

	public static Demand findById(int id){
		return find.byId(id);
	}
	
	public static Page<Demand> find(int page){
		return find.where()
				.eq("is_activated", false)
				.orderBy("id_demand asc")
				
				.findPagingList(10)
				.setFetchAhead(false)
				.getPage(page);
	}
	
	public static boolean activateDemand(Demand demand){
		
		String mail = demand.email;
		AccountValidation av = new AccountValidation();
		av.addDate = new Date();
		av.isActivated = false;
		av.codeGenerated = AccountValidation.generateCode();
		av.personActivate = "admin";
		av.save();
		
		Global.sendMail("Code Parrainage",mail, av.codeGenerated);
		demand.isActivated = true;
		demand.update();
		return true;
	}
	
	
	@Override
	public Demand bind(String key, String value) {
		return findById(Integer.parseInt(value));
	}

	@Override
	public String javascriptUnbind() {
		return String.valueOf(this.idDemand);
	}

	@Override
	public String unbind(String arg0) {
		return String.valueOf(this.idDemand);
	}
	
}
