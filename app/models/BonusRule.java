package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.data.validation.Constraints;
import play.db.ebean.Model.Finder;
import play.mvc.PathBindable;

import com.avaje.ebean.Page;
@Entity
public class BonusRule implements PathBindable<BonusRule>{
	/**
	 * 
	 */

	@Id
	public int idBonus;
	@Constraints.Required
	public String name;
	@Constraints.Required
	public int xp;
	@Constraints.Required
	public int bonus;
	public Date lastUpdateDate;
	public Date createDate;
	
	
	/**
	 * Generic query helper for entity BonusRule witd id Integer
	 * */
	public static Finder<Integer, BonusRule> find = new Finder<Integer, BonusRule>(Integer.class, BonusRule.class);
	
	
	/**
	 * Return a page of BonusRule
	 * @param page Page to display
	 * */
	
	public static Page<BonusRule> find(int page){
		return 
				find.where()
				.orderBy("id_Bonus asc")
				.findPagingList(10)
				.setFetchAhead(false)
				.getPage(page);
				
	}
	
	/**
	 * Return a BonusRule by its Id
	 * @param idBonusRule Id of the BonusRule to find
	 * */
	
	public static BonusRule findByID(String idBonusRule) {
		   return find.where().eq("id_bonus", idBonusRule).findUnique();
		   
		  }

	@Override
	public BonusRule bind(String key, String value) {
		return findByID(value);
	}

	@Override
	public String javascriptUnbind() {
		return String.valueOf(this.idBonus);
	}

	@Override
	public String unbind(String arg0) {
		return String.valueOf(this.idBonus);
	}

	

	
	
	
}
