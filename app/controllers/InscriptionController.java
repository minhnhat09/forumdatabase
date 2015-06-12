package controllers;


import java.util.Date;

import models.AccountValidation;
import models.Demand;
import models.DemandPremium;
import models.Permission;
import models.Service;
import models.User;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

import com.avaje.ebean.Ebean;

import controllers.SearchController.Search;

public class InscriptionController extends Controller {
	
	public static final Form<User>            	userForm   		  = Form.form(User.class);
	public static final Form<Search> 			searchForm 		  = Form.form(Search.class);
	public static final Form<Demand> 			demandForm 		  = Form.form(Demand.class);
	public static final Form<DemandPremium>     demandPremiumForm = Form.form(DemandPremium.class);
	
	
	/**
	 * 
	 * @return
	 */
	public static Result saveDemand(){
		Form<Demand> boundForm = demandForm.bindFromRequest();
		if(boundForm.hasErrors()){
			flash("error", String.format("Formulaire de la demande a des erreurs"));
			return badRequest(views.html.person.detailDemand.render(demandForm, searchForm));
		}
		Demand demand = boundForm.get();
		
		if(demand.idDemand == 0){
			demand.dateApply = new Date();
			Ebean.save(demand);
			
		}else{
			demand.dateApply = new Date();
			Ebean.update(demand);
		}
		
		flash("success", String.format("La demande a été envoyé"));
		return redirect(routes.InscriptionController.inscription());
	}
	
	public static Result saveDemandPremium(){
		Form<DemandPremium> boundForm = demandPremiumForm.bindFromRequest();
		if(boundForm.hasErrors()){
			flash("error", String.format("Tous les champs marqués d'un astérique sont obligatoires"));
			return badRequest(views.html.person.premiumDemand.render(demandPremiumForm, searchForm));
		}
		DemandPremium demandPremium = boundForm.get();
		
		if(demandPremium.idDemandPremium == 0){
			demandPremium.dateApply = new Date();
			demandPremium.user = Application.getUser();
			Ebean.save(demandPremium);
			
		}else{
			demandPremium.dateApply = new Date();
			Ebean.update(demandPremium);
		}
		
		flash("success", String.format("La demande a été envoyé"));
		return redirect(routes.AccueilController.accueil());
	}
	
	/**
	 * 
	 * @return
	 */
	public static Result newDemand(){
		return ok(views.html.person.detailDemand.render(demandForm, searchForm));
	}
	
	public static Result demandPremium(){
		return ok(views.html.person.premiumDemand.render(demandPremiumForm, searchForm));
	}
	/**
	 * 
	 * @return
	 */
	public static Result inscription(){
		return ok(views.html.person.inscriptionPage.render(userForm, searchForm));
	}
	/**
	 * 
	 * @return
	 */
	
	public static Result saveUser(){
		Form<User> boundForm = userForm.bindFromRequest();
		
		if(boundForm.hasErrors()){
			String error = "";
			error += "* Tous les champs marqués d'un astérique sont obligatoires";
			flash("error", String.format(error));
			return badRequest(views.html.person.inscriptionPage.render(boundForm, searchForm));
		}
		
		User user = boundForm.get();
		boundForm.fill(user);
		String signInCode = user.signinCode;
		
		if(!user.email.equals(user.confirmEmail)){
			flash("error", String.format("Email doit être identique"));
			return ok(views.html.person.inscriptionPage.render(boundForm, searchForm));
		}
		
		if(!user.password.equals(user.confirmPassword)){
			flash("error", String.format("Mot de passe doit être identique"));
			return ok(views.html.person.inscriptionPage.render(boundForm, searchForm));
		}
		
		
		if(User.findById(user.userName) != null){
			flash("error", String.format("Nom d'utilisateur existe déjà"));
			return badRequest(views.html.person.inscriptionPage.render(boundForm, searchForm));
		}
		
		if(user.userName.contains(" ")){
			flash("error", String.format("Nom d'utilisateur ne contient pas d'espace"));
			return badRequest(views.html.person.inscriptionPage.render(boundForm, searchForm));
		}
		
		if(User.checkEmail(user.email)){
			;
			flash("error", String.format("Email existe déjà"));
			return badRequest(views.html.person.inscriptionPage.render(boundForm, searchForm));
		}
		
		if(!AccountValidation.confirmCode(signInCode)){
			flash("error", String.format("Code Parrainage Invalide"));
			return badRequest(views.html.person.inscriptionPage.render(boundForm, searchForm));
		}
		
		
		Permission permission = Permission.findById(3);
		//Get idService from form and process to affect service to user
		user.service = Service.findById(String.valueOf(user.idServiceSubscribe));
		
		
		user.permission = permission;
		user.dateInscription = new Date();
		user.threadCountViews = 0;
		user.isPremium = false;
		user.isBlock = false;
		user.isExpert = false;
		user.save();
		
		flash("success", String.format("L'utilisateur a bien été créé"));
		return redirect(routes.AccueilController.accueil());
	}
	
}
