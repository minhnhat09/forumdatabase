package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.db.ebean.Model;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Query;
import com.avaje.ebean.RawSql;
import com.avaje.ebean.RawSqlBuilder;

@Entity
public class GiftUser extends Model {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	public int idGiftUser;
	public Date buyDate;
	@ManyToOne
	public User user;
	@ManyToOne
	public Gift gift;
	public int amount;
	
	
	public static Finder<Integer, GiftUser> find = new Finder<Integer, GiftUser>(Integer.class,
			GiftUser.class);
	
	public static GiftUser findByGiftId(int id){
		return find.where()
				.eq("gift_id_gift", id)
				.findUnique();
	}
	
	public static List<GiftUser> findByUser(String userName){
		
		return find.where()
				.eq("user_user_name", userName)
				.findList();
	}
	
	public static List<Gift> findByUser(){
		String sql = "select name, idgift from gift, gift_user";
		RawSql rawSQL = RawSqlBuilder.parse(sql)
				.columnMapping("name", "name")
				.columnMapping("idgift", "idGift")
				.create();
		System.out.println("raw sql la: " + rawSQL.toString());
		Query<Gift> query = Ebean.find(Gift.class);
		query.setRawSql(rawSQL);
		return query.findList();
	}
	
	public static GiftUser findByUserGift(User user, Gift gift){
		return find.where()
				   .eq("user_user_name", user.userName)
				   .eq("gift_id_gift", gift.idGift)
				   .findUnique();
	}
	
	public static void deleteGiftUserByGift(int idGift){
		GiftUser gu = GiftUser.findByGiftId(idGift);
		if(gu != null) gu.delete();
	}
	
	public static GiftUser isExist(User user, Gift gift){
		GiftUser gu = find.where()
						  .eq("user_user_name", user.userName)
						  .eq("gift_id_gift", gift.idGift)
						  .findUnique();
		if(gu != null) return gu;
		else return null;
	}
}
