package controllers;


import java.util.Date;

import models.AccountValidation;
import models.Demand;
import models.Permission;
import models.User;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

import com.avaje.ebean.Ebean;

import controllers.SearchController.Search;

public class InscriptionController extends Controller {
	
	public static final Form<User>            	userForm   = Form.form(User.class);
	public static final Form<Search> 			searchForm = Form.form(Search.class);
	public static final Form<Demand> 			demandForm = Form.form(Demand.class);
	
	
	/**
	 * Method demand form: new, save
	 * */
	public static Result saveDemand(){
		Form<Demand> boundForm = demandForm.bindFromRequest();
		if(boundForm.hasErrors()){
			flash("error", String.format("Tous les champs marqués d'un astérique sont obligatoires"));
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
	
	public static Result newDemand(){
		return ok(views.html.person.detailDemand.render(demandForm, searchForm));
	}
	
	
	
	
	public static Result inscription(){
		return ok(views.html.person.inscriptionPage.render(userForm, searchForm));
	}
	
	public static Result saveUser(){
		Form<User> boundForm = userForm.bindFromRequest();
		
		if(boundForm.hasErrors()){
			String error = "";
			error += "* Tous les champs marqués d'un astérique sont obligatoires";
			flash("error", String.format(error));
			return badRequest(views.html.person.inscriptionPage.render(userForm, searchForm));
		}
		User user = boundForm.get();
		String signInCode = user.signinCode;
		if(!user.email.equals(user.confirmEmail)  || !user.password.equals(user.confirmPassword)){
			flash("error", String.format("Email doit être identique"));
			return badRequest(views.html.person.inscriptionPage.render(userForm, searchForm));
		}
		if(!AccountValidation.confirmCode(signInCode)){
			flash("error", String.format("Code Parrainage Invalide"));
			return badRequest(views.html.person.inscriptionPage.render(userForm, searchForm));
		}
		if(User.findById(user.userName) != null){
			flash("error", String.format("Nom d'utilisateur existe déjà"));
			return badRequest(views.html.person.inscriptionPage.render(userForm, searchForm));
		}
		
		if(user.userName.contains(" ")){
			flash("error", String.format("Nom d'utilisateur ne contient pas d'espace"));
			return badRequest(views.html.person.inscriptionPage.render(userForm, searchForm));
		}
		
		if(User.checkEmail(user.email)){
			;
			flash("error", String.format("Email existe déjà"));
			return badRequest(views.html.person.inscriptionPage.render(userForm, searchForm));
		
		}
		Permission permission = Permission.findById(3);
		user.permission = permission;
		user.dateInscription = new Date();
		user.save();
		
		flash("success", String.format("Inscription avec succès"));
		return redirect(routes.AccueilController.accueil());
	}
	
}
