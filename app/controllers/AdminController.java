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
 * Manage a database of the Admin Page
 * */
@Security.Authenticated(Secured.class)
public class AdminController extends Controller {
	
	/**
	 * This result directly redirect to Admin page Home
	 * 
	 * */
	public static Result ADMIN_HOME 		= redirect(routes.AdminController.listUsers(0, "user_name", "asc", ""));
	public static Result GO_HOME_DEMAND		= redirect(routes.AdminController.listDemands(0));
	public static Result GO_HOME_TITLE 		= redirect(routes.AdminController.listTitles());
	public static Result GO_HOME_TAG 		= redirect(routes.AdminController.listTags());
	public static Result GO_HOME_SERVICE	= redirect(routes.AdminController.listServices(0));
	public static Result GO_HOME_APPLICATION= redirect(routes.AdminController.listApps(0));
	
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
	
	
	
	
	public static Result adminHome(){
		//return ok(views.html.admin.adminMain.render());
		
		if(controllers.Application.isAdmin()){
			return ADMIN_HOME;
		}else{
			flash("error", String.format("No Permission"));
			return redirect(routes.AccueilController.accueil());
		}
			
	}
	
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
	 * Methods manage User Tab
	 * */
	public static Result listUsers(Integer page, String sortBy, String order, String filter){
		Page<User> users = User.find(page, 10, sortBy, order, filter);
		return ok(views.html.admin.listUsers.render(users, searchForm, sortBy, order, filter));
		//return TODO;
	}
	
	//Block user
//	public static Result block(User user){
//		user.isActive = false;
//		user.update();
//		return ok();
//	}
//	//Deblock user
//	public static Result deBlock(User user){
//		user.isActive = true;
//		user.update();
//		return ok();
//	}
	public static Result blockUnblockUser(User user){
		user.isBlock = !user.isBlock;
		user.update();
		return ok();
	}
	public static Result changeExpert(User user){
		user.isExpert = ! user.isExpert;
		
		user.update();
		
		return ok();
	}
	//affect mod to user
	
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
	
	public static Result changeStatusAdmin(User user){
		
		Permission statusAdmin  = Permission.findById(1);
		Permission statusUser = Permission.findById(3);
		if(user.permission.equals(statusAdmin)){
			user.permission = statusUser;
		}else{
			user.permission = statusAdmin;
		}
		user.update();
		return ok();
	}
	
	
	
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
	
	public static Result listDemands(int page){
		Page<Demand> demands = Demand.find(page);
		return ok(views.html.admin.listDemands.render(demands, searchForm));
	}
	
	public static Result deleteDemand(int idAV) {
	    final Demand demand = Demand.findById(idAV);
	    if(demand == null) {
	        return notFound(String.format("Demand %s n'existe pas", idAV));
	    }
	    Ebean.delete(demand);
	    return redirect(routes.AdminController.listDemands(0));
	  }
	
	public static Result validateDemand(int idAV){
		final Demand demand = Demand.findById(idAV);
	    if(demand == null) {
	    	flash("error", String.format("Demand not found"));
	        return notFound(String.format("Demand %s n'existe pas", idAV));
	    }
	    
		if(Demand.activateDemand(demand))
			flash("success", String.format("Demande validé"));
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
	 * Methods manage Service Tab
	 * */
	public static Result listServices(int page){
		Page<Service> services = Service.find(page);
		return ok(views.html.admin.listServices.render(services, searchForm));
		//return TODO;
	}
	
	public static Result newService(){
		return ok(views.html.admin.detailService.render(serviceForm, searchForm));
	}
	
	public static Result modifyService(Service service){
		Form<Service> filledForm =  serviceForm.fill(service);
		return ok(views.html.admin.detailService.render(filledForm, searchForm));
	}
	
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

	public static Result deleteService(String idService) {
	    final Service service = Service.findById(idService);
	    if(service == null) {
	        return notFound(String.format("Service %s does not exists.", idService));
	    }
	    System.out.println(service.serviceName);
	    Service.delService(service);
	    return GO_HOME_SERVICE;
	  }
	
	/**
	 * Methods manage Apps 
	 * */
	public static Result listApps(int page){
		Page<Application> apps = Application.find(page);
		return ok(views.html.admin.listApps.render(apps, searchForm));
	}
	
	public static Result newApp(){
		return ok(views.html.admin.detailApp.render(appForm, searchForm));
	}
	
	public static Result modifyApp(models.Application app){
		Form<Application> filledForm =  appForm.fill(app);
		return ok(views.html.admin.detailApp.render(filledForm, searchForm));
	}
	
	public static Result saveApp(){
		Form<Application> boundForm = appForm.bindFromRequest();
		MultipartFormData body = request().body().asMultipartFormData();
	    
		if(boundForm.hasErrors()){
			flash("error", String.format("Erreur"));
			return badRequest(views.html.admin.detailApp.render(appForm, searchForm));
		}
		Application app = boundForm.get();
		
		FilePart picture = body.getFile("picture");
		if (picture != null) {
			File content = picture.getFile();
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
				String fileName = "avatarApp";
				
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
			app.save();
		}else{
			app.update();
		}
		
		flash("success", String.format("Successfully added new Appplication"));
		return GO_HOME_APPLICATION;
	}
	
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
	public static Result listBonusRules(Integer page){
		Page<BonusRule> bonusRules = BonusRule.find(page);
		return ok(views.html.admin.listBonusRules.render(bonusRules, searchForm));
	}

	public static Result newBonusRule(){
		return ok(views.html.admin.detailBonusRule.render(bonusRuleForm, searchForm));
	}
	
	public static Result modifyBonusRule(BonusRule bonusRule){
		Form<BonusRule> filledForm =  bonusRuleForm.fill(bonusRule);
		return ok(views.html.admin.detailBonusRule.render(filledForm, searchForm));
	}
	
	
	
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
		
		flash("success", String.format("Successfully added bonus rule"));
		return redirect(routes.AdminController.listBonusRules(0));
	}
	
	
	
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
	public static Result listTags(){
		//Page<Tag> tags = Tag.find(page);
		return ok(views.html.admin.listTags.render(searchForm));
		//return TODO;
	}
	
	
	public static Result newTag(){
		return ok(views.html.admin.detailTag.render(tagForm, searchForm));
	}
	
	public static Result modifyTag(Tag tag){
		Form<Tag> filledForm =  tagForm.fill(tag);
		return ok(views.html.admin.detailTag.render(filledForm, searchForm));
	}
	
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
	public static Result listReports(){
		return ok(views.html.admin.listReports.render(searchForm));
	}
	/**
	 * Methods manage Communication Tab
	 * */
	
	public static Result newCommunication(){
		return ok(views.html.admin.detailCommunication.render(communicationForm, searchForm));
	}
	
	public static Result modifyCommunication(Communication com){
		Form<Communication> filledForm =  communicationForm.fill(com);
		return ok(views.html.admin.detailCommunication.render(filledForm, searchForm));
	}
	
	
	public static Result listCommunications(int page){
		Page<Communication> coms = Communication.find(page);
		return ok(views.html.admin.listCommunications.render(coms, searchForm));
	}
	
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
		
		flash("success", String.format("Successfully added new communication"));
		return redirect(routes.AdminController.listCommunications(0));
	}
	
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
	public static Result listTitles(){
		
		return ok(views.html.admin.listTitles.render(searchForm));
		
	}
	
	public static Result newTitle(){
		return ok(views.html.admin.detailTitle.render(titleForm, searchForm));
	}
	
	public static Result modifyTitle(Title title){
		Form<Title> filledForm =  titleForm.fill(title);
		return ok(views.html.admin.detailTitle.render(filledForm, searchForm));
	}
	
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
		
		flash("success", String.format("Successfully added new Title"));
		return GO_HOME_TITLE;
	}
	
	public static Result deleteTitle(String idTitle) {
	    final Title title = Title.findById(idTitle);
	    if(title == null) {
	        return notFound(String.format("Product %s does not exists.", idTitle));
	    }
	    Ebean.delete(title);
	    return GO_HOME_TITLE;
	  }
	
	
	public static Result getAppsByService(String service){
		List<Application> listApps  = Application.findByIdService(service);
		String textToSend = "";
		for (Application app : listApps) {
			textToSend += "<option id=\"" + app.idApp + "\""
					    + "class=\"service\" value=\"" + app.idApp + "\">" + app.appName + "</option>";
			
		}
		return ok(textToSend);
	}
	
	
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
	
	
	public static Result exportNotes(String notes){
		System.out.println("do export notes");
		DynamicForm form = Form.form().bindFromRequest();
		String idService = form.get("noteService");
		String idApp = form.get("noteApp");
		String idTag = form.get("noteTag");
		System.out.println(idService + idApp + idTag);
//		Thread.exportTest();
		
		return badRequest(views.html.admin.listReports.render(searchForm));
		
		
	}
	
	
	
	
	
}
