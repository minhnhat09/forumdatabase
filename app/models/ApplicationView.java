package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.db.ebean.Model;

@Entity
public class ApplicationView extends Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	public int idApplicationView;
	@ManyToOne
	public User user;
	@ManyToOne
	public Application application;
	public int viewCount;
	
	public static Finder<Integer, ApplicationView> find =  new Finder<Integer, ApplicationView>(Integer.class, ApplicationView.class);

	public static ApplicationView findByUserApp(User user, Application app){
		return find.where()
				   .eq("user_user_name", user.userName)
				   .eq("application_id_app", app.idApp)
				   .findUnique();
	}
	
	public ApplicationView(User user, Application app){
		this.user = user;
		this.application = app;
		this.viewCount = 0;
	}
	
	public static boolean countView(User user, Application app){
		if(user == null || app == null) return false;
		else{
			ApplicationView av = findByUserApp(user, app);
			//case new
			if(av == null){
				ApplicationView avNew = new ApplicationView(user, app);
				avNew.viewCount = 1;
				avNew.save();
				
			}
			//case update
			else{
				av.viewCount++;
				av.update();
			}
			return true;
		}
	}
	
}
