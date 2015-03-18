package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import play.data.validation.Constraints;
import play.db.ebean.Model;
import play.mvc.PathBindable;

import com.avaje.ebean.Page;

@Entity
public class DemandPremium extends Model implements PathBindable<DemandPremium>{

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	public int idDemandPremium;
	@Constraints.Required
	public String motif;
	public Date dateApply;
	@OneToOne
	public User user;
	public boolean isActivated;

	public static Finder<Integer, DemandPremium> find = new Finder<Integer, DemandPremium>(
			Integer.class, DemandPremium.class);
	
	public static Page<DemandPremium> find(int page){
		return find.where()
				.eq("is_activated", false)
				.orderBy("id_demand_premium asc")
				
				.findPagingList(10)
				.setFetchAhead(false)
				.getPage(page);
	}
	
	public static DemandPremium findById(int id){
		return find.byId(id);
	}
	
	@Override
	public DemandPremium bind(String key, String value) {
		return findById(Integer.parseInt(value));
	}

	@Override
	public String javascriptUnbind() {
		return String.valueOf(this.idDemandPremium);
	}

	@Override
	public String unbind(String arg0) {
		return String.valueOf(this.idDemandPremium);
	}

	public static boolean activateDemandPremium(DemandPremium demandPremium) {
		demandPremium.isActivated = true;
		demandPremium.user.isPremium = true;
		demandPremium.user.update();
		demandPremium.update();
		
		return true;
	}
	
	

}
