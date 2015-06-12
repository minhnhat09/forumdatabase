package controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import models.AccountValidation;
import models.Application;
import models.BonusRule;
import models.Communication;
import models.Demand;
import models.DemandPremium;
import models.Permission;
import models.Service;
import models.Tag;
import models.Thread;
import models.Title;
import models.User;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Result;
import play.mvc.Security;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Page;
import com.google.common.io.Files;

import controllers.SearchController.Search;
/**
 * Controller used to control {@link User Demand Service Application Title Tag Gift}
 * @author NGUYEN Nhat Minh
 * @version 1.0.0
 * @category Controller
 * @see {@link Object}
 */
@Security.Authenticated(Secured.class)
public class AdminController extends Controller {
	
	/**
	 * This result directly redirect to Admin page Home
	 * 
	 * */
	
	public static Result ADMIN_HOME 				= redirect(routes.AdminController.listUsers(0, "user_name", "asc", ""));
	public static Result GO_HOME_DEMAND				= redirect(routes.AdminController.listDemands(0));
	public static Result GO_HOME_DEMAND_PREMIUM		= redirect(routes.AdminController.listDemandsPremium(0));
	
	public static Result GO_HOME_TITLE 				= redirect(routes.AdminController.listTitles());
	public static Result GO_HOME_TAG 				= redirect(routes.AdminController.listTags());
	public static Result GO_HOME_SERVICE			= redirect(routes.AdminController.listServices(0));
	public static Result GO_HOME_APPLICATION		= redirect(routes.AdminController.listApps(0));
	
	/**
	 * Attributes represent many forms in the Admin Page
	 * */
	//private static final Form<User>					userForm	 			= Form.form(User.class);
	//private static final Form<AccountValidation>	accountValidationForm 		= Form.form(AccountValidation.class);
	private static final Form<Service>				serviceForm 				= Form.form(Service.class);
	private static final Form<Application>			appForm 					= Form.form(Application.class);
	private static final Form<Tag>					tagForm 					= Form.form(Tag.class);
	private static final Form<BonusRule> 			bonusRuleForm 				= Form.form(BonusRule.class);
	private static final Form<Communication>		communicationForm 			= Form.form(Communication.class);
	public static final Form<Search> 				searchForm 					= Form.form(Search.class);
	private static final Form<Title>				titleForm 					= Form.form(Title.class);
	
	
	
	/**
	 * 
	 * @return Adminstrateur page
	 */
	public static Result adminHome(){
		//return ok(views.html.admin.adminMain.render());
		
		if(controllers.Application.isAdmin()){
			return ADMIN_HOME;
		}else{
			flash("error", String.format("Vous n'avez pas le droit de consulter cette page"));
			return redirect(routes.AccueilController.accueil());
		}
			
	}
	
	
	
	/**
	 * method used to switch between to mode: admin mod allow to change forum's settings and normal mode
	 * @return 
	 */
	public static Result toggleAdminMode(){
		
		if(controllers.Application.getAdminMode().equals("on")){
			controllers.Application.turnOffAdminMode();
			flash("success", String.format("Admin Mode OFF"));
			return ok();
		}else{
			controllers.Application.turnOnAdminMode();
			flash("success", String.format("Admin Mode ON"));
			return ok();
		}
		
		
	}
	
	/**
	 * list of methods manage users
	 */
	
	/**
	 * 
	 * @param page of list, used for pagination
	 * @param sortBy criteria of sort
	 * @param order ascendant or descendant
	 * @param filter search
	 * @return
	 */
	public static Result listUsers(Integer page, String sortBy, String order, String filter){
		
		if(controllers.Application.isAdmin()){
			Page<User> users = User.find(page, 10, sortBy, order, filter);
			return ok(views.html.admin.listUsers.render(users, "", searchForm, sortBy, order, filter));
		}else{
			flash("error", String.format("Vous n'avez pas le droit de consulter cette page"));
			return redirect(routes.AccueilController.accueil());
		}
	}
	
	/**
	 * Method used to search an user by name
	 * @param String for the app's name
	 * @return application form
	 */
	public static Result searchUsersAdminPage(Integer page, String userName, String sortBy, String order, String filter){
		if(controllers.Application.isAdmin()){
			
			Page<User> users = User.getListUsersByName(page, userName);
			
			return ok(views.html.admin.listUsers.render(users, userName, searchForm, sortBy, order, filter));
		}else{
			flash("error", String.format("Vous n'avez pas le droit de consulter cette page"));
			return redirect(routes.AccueilController.accueil());
		}
	}
	
	
	/**
	 * 
	 * @param user will be block/unblock
	 * @return page
	 */
	public static Result blockUnblockUser(User user){
		user.isBlock = !user.isBlock;
		user.update();
		return ok();
	}
	
	/**
	 * 
	 * @param user status will be switch to expert/normal
	 * @return
	 */
	public static Result changeExpert(User user){
		user.isExpert = ! user.isExpert;
		
		user.update();
		
		return ok();
	}
	
	/**
	 * 
	 * @param user status will be switched to moderateur/normal
	 * @return
	 */
	public static Result changeStatusMod(User user){
		
		Permission statusMod  = Permission.findById(2);
		Permission statusUser = Permission.findById(3);
		if(user.permission.equals(statusMod)){
			user.permission = statusUser;
		}else{
			user.permission = statusMod;
		}
		user.update();
		return ok();
	}
	
	/**
	 * 
	 * @param user status will be switched to admin/normal
	 * @return
	 */
	public static Result changeStatusAdmin(User user){
		
		Permission statusAdmin  = Permission.findById(1);
		Permission statusUser = Permission.findById(3);
		if(user.permission.equals(statusAdmin)){
			user.permission = statusUser;
			flash("success", "Le status d'utilisateur est changé");
		}else{
			user.permission = statusAdmin;
		}
		user.update();
		return ok();
	}
	
	
	
	
	/**
	 * method delete user and all his references
	 * @param user 
	 * @return
	 */
	public static Result deleteUser(User user){
		
		if(user == null){
			
			return notFound(String.format("Utilisateur % n'existe pas", user));
		}
		System.out.println("idUser" + user.userName);
		if(user.permission.equals(Permission.findById(1))){
			return badRequest();
		}
		User.delUser(user);
		return ok();
	}
	
	/**
	 * Methods manage Demand Tab
	 * */
	/**
	 * 
	 * @param page
	 * @return list of inscription demand
	 */
	public static Result listDemands(int page){
		if(controllers.Application.isAdmin()){
			Page<Demand> demands = Demand.find(page);
			return ok(views.html.admin.listDemands.render(demands, searchForm));
		}else{
			flash("error", String.format("Vous n'avez pas le droit de consulter cette page"));
			return redirect(routes.AccueilController.accueil());
		}
		
		
		
	}
	/**
	 * Method used to delete inscription demand
	 * @param idAV id Demand deleted
	 * @return demand page
	 */
	public static Result deleteDemand(int idAV) {
	    final Demand demand = Demand.findById(idAV);
	    if(demand == null) {
	        return notFound(String.format("Demand %s n'existe pas", idAV));
	    }
	    demand.isRefused = true;
	    demand.update();
	    //Ebean.delete(demand);
	    
	    return redirect(routes.AdminController.listDemands(0));
	  }
	
	public static Result validateDemand(int idAV){
		final Demand demand = Demand.findById(idAV);
	    if(demand == null) {
	    	flash("error", String.format("Demande introuvable"));
	    	
	        return notFound(String.format("Demand %s n'existe pas", idAV));
	    }
	    
		if(Demand.activateDemand(demand))
			flash("success", String.format("La demande a bien été validée"));
		return GO_HOME_DEMAND;
	}
	
	public static Result confirmAccount(int idAV){
		final AccountValidation av = AccountValidation.findById(idAV);
	    if(av == null) {
	        return notFound(String.format("Demand %s n'existe pas", idAV));
	    }
	    
	    return redirect(routes.AdminController.listDemands(0));
	}
	
	/**
	 * Methods manage DemandPremium Tab
	 * */
	/**
	 * 
	 * @param page
	 * @return list of premium demand
	 */
	public static Result listDemandsPremium(int page){
		if(controllers.Application.isAdmin()){

			Page<DemandPremium> demands = DemandPremium.find(page);
			return ok(views.html.admin.listDemandsPremium.render(demands, searchForm));
		}else{
			flash("error", String.format("Vous n'avez pas le droit de consulter cette page"));
			return redirect(routes.AccueilController.accueil());
		}
		
		
	}
	/**
	 * Method used to delete premium demand
	 * @param idAV id Demand deleted
	 * @return demand page
	 */
	public static Result deleteDemandPremium(int idAV) {
	    final DemandPremium demandPremium = DemandPremium.findById(idAV);
	    if(demandPremium == null) {
	        return notFound(String.format("La demande premium %s n'existe pas", idAV));
	    }
	    Ebean.delete(demandPremium);
	    return redirect(routes.AdminController.listDemandsPremium(0));
	  }
	
	public static Result validateDemandPremium(int idAV){
		final DemandPremium demandPremium = DemandPremium.findById(idAV);
	    if(demandPremium == null) {
	    	flash("error", String.format("Demande Premium introuvable"));
	    	
	        return notFound(String.format("Demand Premium %s n'existe pas", idAV));
	    }
	    
		if(DemandPremium.activateDemandPremium(demandPremium))
			flash("success", String.format("La demande premium a bien été validée"));
		return GO_HOME_DEMAND_PREMIUM;
	}
	
	
	
	
	/**
	 * Methods manage Service Tab
	 * */
	/**
	 * 
	 * @param page 
	 * @return return list of services
	 */
	public static Result listServices(int page){
		if(controllers.Application.isAdmin()){
			Page<Service> services = Service.find(page);
			return ok(views.html.admin.listServices.render(services, searchForm));
		}else{
			flash("error", String.format("Vous n'avez pas le droit de consulter cette page"));
			return redirect(routes.AccueilController.accueil());
		}
		
		
		
		
	}
	/**
	 * Method used to create new service
	 * @return new service form
	 */
	public static Result newService(){
		if(controllers.Application.isAdmin()){
			return ok(views.html.admin.detailService.render(serviceForm, searchForm));
		}else{
			flash("error", String.format("Vous n'avez pas le droit de consulter cette page"));
			return redirect(routes.AccueilController.accueil());
		}
		
		
		
	}
	/**
	 * Method used to modify an exist service
	 * @param service service to be modidified
	 * @return service form
	 */
	public static Result modifyService(Service service){
		if(controllers.Application.isAdmin()){
			Form<Service> filledForm =  serviceForm.fill(service);
			return ok(views.html.admin.detailService.render(filledForm, searchForm));
		}else{
			flash("error", String.format("Vous n'avez pas le droit de consulter cette page"));
			return redirect(routes.AccueilController.accueil());
		}
		
		
		
	}
	/**
	 * Method used to save Service, it receive arguments from http post from client
	 * It used for both creation and modification service use case
	 * @return status ok or not ok, 
	 */
	public static Result saveService(){
		MultipartFormData body = request().body().asMultipartFormData();
	    Form<Service> boundForm = serviceForm.bindFromRequest();
	    
		if(boundForm.hasErrors()){
			return badRequest(views.html.admin.detailService.render(serviceForm, searchForm));
		}
		
		Service service = boundForm.get();
		
		FilePart picture = body.getFile("picture");
		if (picture != null) {
			File content = picture.getFile();
			FileOutputStream fop = null;
			File file;
			try {

				File folderUser = new File("public\\imgs\\infoservice\\avatar\\" + String.valueOf(service.idService)
						+ "\\");
				if (!folderUser.exists()) {
					if (folderUser.mkdirs()) {
						System.out.println("Multiple directories are created!");
					} else {
						System.out
								.println("Failed to create multiple directories!");
					}
				}
				String fileName = "avatarService";
				
				file = new File("public\\imgs\\infoservice\\avatar\\" + String.valueOf(service.idService)
						 + "\\" + fileName);
				fop = new FileOutputStream(file);

				// if file doesnt exists, then create it
				if (!file.exists()) {
					file.createNewFile();
				}

				// get the content in bytes
				byte[] contentInBytes = Files.toByteArray(content);
				service.avatarService = fileName;
				fop.write(contentInBytes);
				fop.flush();
				fop.close();

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (fop != null) {
						fop.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
		
		if(service.idService == 0){
			service.save();
		}else{
			service.update();
		}
		
		flash("success", String.format("Successfully added Service"));
		return GO_HOME_SERVICE;
	}

	/**
	 * Method used to delete a service
	 * @param idService id of service to be deleted
	 * @return Service admin page
	 */
	public static Result deleteService(String idService) {
	    final Service service = Service.findById(idService);
	    if(service == null) {
	        return notFound(String.format("Service %s does not exists.", idService));
	    }
	    
	    Service.delService(service);
	    return GO_HOME_SERVICE;
	  }
	
	/**
	 * Methods manage Apps 
	 * */
	
	/**
	 * Method used to render list of services
	 * @param page for pagination
	 * @return lists of applications in forum and its services
	 */
	public static Result listApps(int page){
		if(controllers.Application.isAdmin()){
			Page<Application> apps = Application.find(page);
			return ok(views.html.admin.listApps.render(apps, searchForm));
		}else{
			flash("error", String.format("Vous n'avez pas le droit de consulter cette page"));
			return redirect(routes.AccueilController.accueil());
		}
		
		
		
	}
	/**
	 * Method used to serve new application form
	 * @return application form 
	 */
	public static Result newApp(){
		if(controllers.Application.isAdmin()){
			return ok(views.html.admin.detailApp.render(appForm, searchForm));
		}else{
			flash("error", String.format("Vous n'avez pas le droit de consulter cette page"));
			return redirect(routes.AccueilController.accueil());
		}
		
		
	}
	
	/**
	 * Method used to modify application'information
	 * @param app application to be modified
	 * @return application form
	 */
	public static Result modifyApp(models.Application app){
		if(controllers.Application.isAdmin()){
			Form<Application> filledForm =  appForm.fill(app);
			return ok(views.html.admin.detailApp.render(filledForm, searchForm));
		}else{
			flash("error", String.format("Vous n'avez pas le droit de consulter cette page"));
			return redirect(routes.AccueilController.accueil());
		}
		
		
	}

	/**
	 * Method used to search an app by her name
	 * @param String for the app's name
	 * @return application form
	 */
	public static Result searchApp(Integer page, String appName){
		if(controllers.Application.isAdmin()){
			Page<Application> apps =  Application.findByAppName(page, appName);
			return ok(views.html.admin.listApps.render(apps, searchForm));
		}else{
			flash("error", String.format("Vous n'avez pas le droit de consulter cette page"));
			return redirect(routes.AccueilController.accueil());
		}
		
	}
	
	
	/**
	 * Method used to save application into database. It used for both creation and modification use case
	 * @return status ok or not ok
	 */
	public static Result saveApp(){
		Form<Application> boundForm = appForm.bindFromRequest();
		
		MultipartFormData body = request().body().asMultipartFormData();
	    
		if(boundForm.hasErrors()){
			flash("error", String.format("Erreur"));
			return badRequest(views.html.admin.detailApp.render(boundForm, searchForm));
		}
		Application app = boundForm.get();
		
		FilePart picture = body.getFile("picture");
		
		if (picture != null) {
			
			String contentType = picture.getContentType();
			
			String str[] = contentType.split("/");
			String fileType = str[1];
			
			/**
			 * if filetype not equal jpg, jpeg, bmp, gif (image format) return error
			 */
			if(!fileType.equalsIgnoreCase("jpg") && !fileType.equalsIgnoreCase("jpeg")&& !fileType.equalsIgnoreCase("bmp")
					&& !fileType.equalsIgnoreCase("gif")){
				
				flash("error", String.format("La photo doit être en format jpg, jpeg, bmp, gif"));
				return badRequest(views.html.admin.detailApp.render(boundForm, searchForm));
			}
			
			File content = picture.getFile();
			if(content == null){
				flash("error", String.format("Aucun fichier selectionné"));
				return ok(views.html.admin.detailApp.render(boundForm, searchForm));
			}
			FileOutputStream fop = null;
			File file;
			String path = "public\\imgs\\applications\\app" 
			+ String.valueOf(app.idApp) +"\\avatar\\";
			try {

				File folderUser = new File(path);
				if (!folderUser.exists()) {
					if (folderUser.mkdirs()) {
						System.out.println("Multiple directories are created!");
					} else {
						System.out
								.println("Failed to create multiple directories!");
					}
				}
				String fileName = "avatarApp" + fileType;
				
				file = new File(path + fileName);
				fop = new FileOutputStream(file);

				// if file doesnt exists, then create it
				if (!file.exists()) {
					file.createNewFile();
				}

				// get the content in bytes
				byte[] contentInBytes = Files.toByteArray(content);
				app.avatarApp = fileName;
				fop.write(contentInBytes);
				fop.flush();
				fop.close();

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (fop != null) {
						fop.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
		
		if(app.idApp == 0){
			//default value of max views is 3
			app.maxViews = 3;
			app.save();
		}else{
			app.update();
		}
		
		flash("success", String.format("L'application a bien été enregistrée"));
		return GO_HOME_APPLICATION;
	}
	
	/**
	 * Method used to delete application
	 * @param idApp Application'id
	 * @return application admin page
	 */
	public static Result deleteApp(String idApp) {
	    final Application app = Application.findById(idApp);
	    if(app == null) {
	        return notFound(String.format("Application %s n'existe pas.", idApp));
	    }
	    System.out.println(app.appName);
	    
	    Application.delApp(app);
	    
	    
	    return GO_HOME_APPLICATION;
	  }
	
	
	
	/**
	 * Methods manage Bonus Rules tab
	 * */
	
	/**
	 * Method used to show list of bonus rules admin page
	 * @param page for pagincation purpose
	 * @return bonus rule admin page
	 */
	public static Result listBonusRules(Integer page){
		if(controllers.Application.isAdmin()){
			Page<BonusRule> bonusRules = BonusRule.find(page);
			return ok(views.html.admin.listBonusRules.render(bonusRules, searchForm));
		}else{
			flash("error", String.format("Vous n'avez pas le droit de consulter cette page"));
			return redirect(routes.AccueilController.accueil());
		}
		
		
	}

	/**
	 * Method used to render bonus rule form
	 * @return form to create bonus rule
	 */
	public static Result newBonusRule(){
		if(controllers.Application.isAdmin()){
			return ok(views.html.admin.detailBonusRule.render(bonusRuleForm, searchForm));
		}else{
			flash("error", String.format("Vous n'avez pas le droit de consulter cette page"));
			return redirect(routes.AccueilController.accueil());
		}
		
		
	}
	
	/**
	 * Method used to render bonus rule form
	 * @param bonusRule it take a bonus rule as param
	 * @return render bonus rule form
	 */
	public static Result modifyBonusRule(BonusRule bonusRule){
		if(controllers.Application.isAdmin()){
			Form<BonusRule> filledForm =  bonusRuleForm.fill(bonusRule);
			return ok(views.html.admin.detailBonusRule.render(filledForm, searchForm));
		}else{
			flash("error", String.format("Vous n'avez pas le droit de consulter cette page"));
			return redirect(routes.AccueilController.accueil());
		}
		
		
		
	}
	
	/**
	 * Method used to save bonus rule into database. It used for both creation and modification use case
	 * @return status ok or not ok and redirect to bonus rule admin page
	 */
	public static Result saveBonusRule(){
		Form<BonusRule> boundForm = bonusRuleForm.bindFromRequest();
		if(boundForm.hasErrors()){
			return badRequest(views.html.admin.detailBonusRule.render(bonusRuleForm, searchForm));
		}
		BonusRule bonusRule = boundForm.get();
		
		if(bonusRule.idBonus == 0){
			Ebean.save(bonusRule);
		}else{
			Ebean.update(bonusRule);
		}
		
		flash("success", String.format("Le règlement a bien été ajouté"));
		return redirect(routes.AdminController.listBonusRules(0));
	}
	
	/**
	 * Method used to delete bonus rule
	 * @param idBonusRule it take id of bonus rule as param
	 * @return redirect to bonus rule admin page
	 */
	
	public static Result deleteBonusRule(String idBonusRule) {
	    final BonusRule bonusRule = BonusRule.findByID(idBonusRule);
	    if(bonusRule == null) {
	        return notFound(String.format("Bonus %s does not exists.", idBonusRule));
	    }
	    Ebean.delete(bonusRule);
	    return redirect(routes.AdminController.listBonusRules(0));
	  }

	/**
	 * Methods manage Tag Tab
	 * */
	
	/**
	 * Method used to render tag admin page
	 * @return tag admin page
	 */
	public static Result listTags(){
		if(controllers.Application.isAdmin()){
			return ok(views.html.admin.listTags.render(searchForm));
		}else{
			flash("error", String.format("Vous n'avez pas le droit de consulter cette page"));
			return redirect(routes.AccueilController.accueil());
		}
		
		
	}
	
	/**
	 * Method used to render tag form
	 * @return render tag form page
	 */
	public static Result newTag(){
		if(controllers.Application.isAdmin()){
			return ok(views.html.admin.detailTag.render(tagForm, searchForm));
		}else{
			flash("error", String.format("Vous n'avez pas le droit de consulter cette page"));
			return redirect(routes.AccueilController.accueil());
		}
		
		
	}
	
	/**
	 * Method used to modify tags
	 * @param tag tag to be modified
	 * @return render tag form to modify tag
	 */
	public static Result modifyTag(Tag tag){
		if(controllers.Application.isAdmin()){
			Form<Tag> filledForm =  tagForm.fill(tag);
			return ok(views.html.admin.detailTag.render(filledForm, searchForm));
		}else{
			flash("error", String.format("Vous n'avez pas le droit de consulter cette page"));
			return redirect(routes.AccueilController.accueil());
		}
		
		
	}
	
	/**
	 * Method used to save tag into database. It used for both creation and modification page
	 * @return status ok or not ok and redirect to tag admin page
	 */
	public static Result saveTag(){
		Form<Tag> boundForm = tagForm.bindFromRequest();
		if(boundForm.hasErrors()){
			return badRequest(views.html.admin.detailTag.render(tagForm, searchForm));
		}
		Tag tag = boundForm.get();
		
		
		if(tag.idTag == 0){
			String cat = tag.category;
			if(cat.equals("0")) tag.category = "pays";
			else if(cat.equals("1")) tag.category = "modules";
			else tag.category = "Catégory Inconnue";
			Ebean.save(tag);
		}else{
			String cat = tag.category;
			if(cat.equals("0")) tag.category = "pays";
			else if(cat.equals("1")) tag.category = "modules";
			else tag.category = "Catégory Inconnue";
			Ebean.update(tag);
		}
		
		flash("success", String.format("Successfully added new Tag"));
		return GO_HOME_TAG;
	}
	
	public static Result deleteTag(int idTag) {
		System.out.println("Del tag");
	    final Tag tag = Tag.findById(idTag);
	    if(tag == null) {
	        return notFound(String.format("Tag %s does not exists.", idTag));
	    }
	    Ebean.delete(tag);
	    return GO_HOME_TAG;
	  }
	
	
	
	/**
	 * Methods manage Reporting Tab
	 * */
	/**
	 * Method used to render reporting admin page
	 * @return reporting admin page
	 */
	public static Result listReports(){
		if(controllers.Application.isAdmin()){
			return ok(views.html.admin.listReports.render(searchForm));
		}else{
			flash("error", String.format("Vous n'avez pas le droit de consulter cette page"));
			return redirect(routes.AccueilController.accueil());
		}
		
		
	}
	/**
	 * Methods manage Communication Tab
	 * */
	/**
	 * Method used to render communication form page to create or modify communication
	 * @return communication form page
	 */
	public static Result newCommunication(){
		if(controllers.Application.isAdmin()){
			return ok(views.html.admin.detailCommunication.render(communicationForm, searchForm));
		}else{
			flash("error", String.format("Vous n'avez pas le droit de consulter cette page"));
			return redirect(routes.AccueilController.accueil());
		}
		
		
	}
	/**
	 * Method used to render communication form page
	 * @param com it takes a communication as param
	 * @return communication form page
	 */
	public static Result modifyCommunication(Communication com){
		if(controllers.Application.isAdmin()){
			Form<Communication> filledForm =  communicationForm.fill(com);
			return ok(views.html.admin.detailCommunication.render(filledForm, searchForm));
		}else{
			flash("error", String.format("Vous n'avez pas le droit de consulter cette page"));
			return redirect(routes.AccueilController.accueil());
		}
		
		
	}
	
	/**
	 * Method used to render list of communications
	 * @param page page for pagination purpose
	 * @return render communication page
	 */
	public static Result listCommunications(int page){
		if(controllers.Application.isAdmin()){
			Page<Communication> coms = Communication.find(page);
			return ok(views.html.admin.listCommunications.render(coms, searchForm));
		}else{
			flash("error", String.format("Vous n'avez pas le droit de consulter cette page"));
			return redirect(routes.AccueilController.accueil());
		}
		
		
	}
	/**
	 * Method used to save a communication into database. It used for both creation and modification use case
	 * @return status ok or not ok. Redirect to communication admin page
	 */
	public static Result saveCommunication(){
		Form<Communication> boundForm = communicationForm.bindFromRequest();
		if(boundForm.hasErrors()){
			return badRequest(views.html.admin.detailCommunication.render(communicationForm, searchForm));
		}
		Communication com = boundForm.get();
		
		Date date = new Date();
		com.createDate =  date;
		
		if(com.idCommunication == 0){
			Ebean.save(com);
		}else{
			Ebean.update(com);
		}
		
		flash("success", String.format("La publicité de communication a bien été ajoutée"));
		return redirect(routes.AdminController.listCommunications(0));
	}
	/**
	 * Method used to delete a communication 
	 * @param idCommunication it take a communication id 
	 * @return render communication admin page
	 */
	public static Result deleteCommunication(String idCommunication) {
	    final Communication com = Communication.findByID(idCommunication);
	    if(com == null) {
	        return notFound(String.format("Product %s does not exists.", idCommunication));
	    }
	    Ebean.delete(com);
	    return redirect(routes.AdminController.listCommunications(0));
	  }
	
	/**
	 * Methods manage Tag Tab
	 * */
	/**
	 * Method used to render list of titles
	 * @return titles admin page
	 */
	public static Result listTitles(){
		if(controllers.Application.isAdmin()){
			return ok(views.html.admin.listTitles.render(searchForm));
		}else{
			flash("error", String.format("Vous n'avez pas le droit de consulter cette page"));
			return redirect(routes.AccueilController.accueil());
		}
		
		
	}
	/**
	 * Method used to create new title
	 * @return new title form page
	 */
	public static Result newTitle(){
		if(controllers.Application.isAdmin()){
			return ok(views.html.admin.detailTitle.render(titleForm, searchForm));
		}else{
			flash("error", String.format("Vous n'avez pas le droit de consulter cette page"));
			return redirect(routes.AccueilController.accueil());
		}
		
		
	}
	/**
	 * Method used to modify title
	 * @param title it take a title as a param
	 * @return
	 */
	public static Result modifyTitle(Title title){
		if(controllers.Application.isAdmin()){
			Form<Title> filledForm =  titleForm.fill(title);
			return ok(views.html.admin.detailTitle.render(filledForm, searchForm));
		}else{
			flash("error", String.format("Vous n'avez pas le droit de consulter cette page"));
			return redirect(routes.AccueilController.accueil());
		}
		
		
	}
	/**
	 * Method used to save title into database. It used for both creation and modification use case
	 * @return status ok or not ok. It redirect to title admin page
	 */
	public static Result saveTitle(){
		Form<Title> boundForm = titleForm.bindFromRequest();
		if(boundForm.hasErrors()){
			return badRequest(views.html.admin.detailTitle.render(titleForm, searchForm));
		}
		Title title = boundForm.get();
		
		
		if(title.idTitle == 0){
			Ebean.save(title);
		}else{
			Ebean.update(title);
		}
		
		flash("success", String.format("Le titre a bien été ajouté"));
		return GO_HOME_TITLE;
	}
	/**
	 * Method used to delete title
	 * @param idTitle It take a title as a param
	 * @return redirect to title admin page
	 */
	public static Result deleteTitle(String idTitle) {
	    final Title title = Title.findById(idTitle);
	    if(title == null) {
	        return notFound(String.format("Product %s does not exists.", idTitle));
	    }
	    Ebean.delete(title);
	    return GO_HOME_TITLE;
	  }
	
	/**
	 * Ajax method used to get applications
	 * @param service It take a service as a param
	 * @return list of applications by service
	 */
	public static Result getAppsByService(String service){
		List<Application> listApps  = Application.findByIdService(service);
		String textToSend = "";
		for (Application app : listApps) {
			textToSend += "<option id=\"" + app.idApp + "\""
					    + "class=\"service\" value=\"" + app.idApp + "\">" + app.appName + "</option>";
			
		}
		return ok(textToSend);
	}
	
	
	
	/**
	 * Method used to export theme
	 * @param themes
	 * @return
	 */
	public static Result exportThemes(String themes){
		System.out.println("do export themes");
		DynamicForm form = Form.form().bindFromRequest();
		String idService = form.get("themeService");
		String idApp = form.get("themeApp");
		String idTag = form.get("themeTag");
		
		System.out.println(idService + idApp + idTag);
//		Thread.exportTest();
		
		return badRequest(views.html.admin.listReports.render(searchForm));
		
		
	}
	/**
	 * 
	 * @param option
	 * @return
	 */
	public static Result export(String option){
		if(option.equals("themes")){
			System.out.println("do export themes");
			DynamicForm form = Form.form().bindFromRequest();
			String idService = form.get("themeService");
			String idApp = form.get("themeApp");
			String idTag = form.get("themeTag");
			
			System.out.println(idService + idApp + idTag);
			Thread.exportTheme(idService, idApp, idTag);
			response().setHeader("Content-Disposition", "attachment; filename=ReportTheme.xls");
			return ok(new File("ReportTheme.xls"));
			
		}
		else if(option.equals("notes")){
			System.out.println("do export notes");
			DynamicForm form = Form.form().bindFromRequest();
			String idService = form.get("noteService");
			String idApp = form.get("noteApp");
			String idTag = form.get("noteTag");
			System.out.println(idService + idApp + idTag);
			Thread.exportNote(idService, idApp, idTag);
			response().setHeader("Content-Disposition", "attachment; filename=ReportNote.xls");
			return ok(new File("ReportNote.xls"));
		}
		else if(option.equals("likes")){
			System.out.println("do export like");
			DynamicForm form = Form.form().bindFromRequest();
			String idService = form.get("likeService");
			String idApp = form.get("likeApp");
			String idTag = form.get("likeTag");
			System.out.println(idService + idApp + idTag);
			Thread.exportLike(idService, idApp, idTag);
			response().setHeader("Content-Disposition", "attachment; filename=ReportLike.xls");
			return ok(new File("ReportLike.xls"));
		}
		else{
			return badRequest(views.html.admin.listReports.render(searchForm));
		}
	}
	
	/**
	 * 
	 * @param notes
	 * @return
	 */
	public static Result exportNotes(String notes){
		System.out.println("do export notes");
		DynamicForm form = Form.form().bindFromRequest();
		String idService = form.get("noteService");
		String idApp = form.get("noteApp");
		String idTag = form.get("noteTag");
		System.out.println(idService + idApp + idTag);
		return badRequest(views.html.admin.listReports.render(searchForm));
	}
}
