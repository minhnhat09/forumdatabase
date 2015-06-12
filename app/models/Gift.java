package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import play.data.validation.Constraints;
import play.db.ebean.Model;
import controllers.Application;

@Entity
public class Gift extends Model {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	public int idGift;
	@Constraints.Required
	public String name;
	@Constraints.Required
	public int bonus;
	public String imgUrl;
	@Constraints.Required
	public int category;
	@Column(columnDefinition = "LONGTEXT")
	public String description;
	@OneToMany(mappedBy = "gift")
	public List<GiftUser> users;

	public static Finder<Integer, Gift> find = new Finder<Integer, Gift>(Integer.class,
			Gift.class);

	public static String getDescription(Gift gift){
		return gift.description;
	}
	/**
	 * find gift by category
	 * */
	public static List<Integer> findCategory() {

		List<Gift> listGift = find.orderBy("category").findList();
		List<Integer> listTemp = new ArrayList<Integer>();
		
		for (int i = 0; i <listGift.size(); i++) {
			boolean flag = true;
			int cat = listGift.get(i).category;
			for (Integer integer : listTemp) {
				if(integer == cat)
					flag = false;
			}
			if(flag == true)
				listTemp.add(cat);
		}
		return listTemp;
	}

	public static List<Gift> findGiftsByCategory(int category) {
		return find.where().eq("category", category).findList();
	}
	
	public static boolean deleteListGift(String listGift){
		boolean flag = false;
		StringTokenizer st = new StringTokenizer(listGift,",");
		while(st.hasMoreElements()){
			flag = true;
			int idGift = Integer.parseInt((String) st.nextElement());
			GiftUser.deleteGiftUserByGift(idGift);
			Gift.deleteGift(idGift);
		}
		
		return flag;
	}
	/**
	 * Sand box Method
	 * */
	public static void testBuyGift(){
		GiftUser gu = new GiftUser();
		gu.user = User.findById("minh");
		gu.gift = Gift.findById(1);
		gu.amount = 2;
		gu.buyDate = new Date();
		gu.save();
	}
	
	public static boolean buyListGift(String listGift){
		boolean flag 	   = false;
		StringTokenizer st = new StringTokenizer(listGift,",");
		//Find user by id
		User user = User.findById(Application.getSessionUser());
		while(st.hasMoreElements()){
			//User can buy a Gift
			flag = true;
			int idGift = Integer.parseInt((String) st.nextElement());
			//find gift by id
			Gift gift = Gift.findById(idGift);
			if(user.bonus < gift.bonus) return false;
			else{
				GiftUser gu = GiftUser.isExist(user, gift);
				if(gu != null){
					gu.amount += 1;
					gu.update();
					user.bonus -= gift.bonus;
					user.update();
				}else{
					GiftUser newGiftUser = new GiftUser();
					newGiftUser.gift = gift;
					newGiftUser.user = user;
					newGiftUser.buyDate = new Date();
					newGiftUser.amount = 1;
					newGiftUser.save();
					//decrement his bonus
					user.bonus -= gift.bonus;
					user.update();
				}
			}
			
			
		}
		
		return flag;
	}
	
	//find list gifts by GiftUser
	public static Gift findById(int idGift){
		return find.byId(idGift);
	}
	
	//find List Gifts by User
	public static List<Gift> findByUser(String userName){
		
		List<Gift> listGiftsByUser = new ArrayList<Gift>();
		List<GiftUser> listGiftUser = GiftUser.findByUser(userName);
		for (GiftUser giftUser : listGiftUser) {
			Gift giftByUser = Gift.findById(giftUser.gift.idGift);
			listGiftsByUser.add(giftByUser);
		}
		return listGiftsByUser;
	}
	
	
	
	public static void deleteGift(int id){
		final Gift gift = find.byId(id);
		gift.delete();
	}
	@Override
	public String toString() {
		return "Gift [idGift=" + idGift + ", name=" + name + ", bonus=" + bonus
				+ ", imgUrl=" + imgUrl + ", category=" + category
				+ ", description=" + description + ", users=" + users + "]";
	}
	
	

}
