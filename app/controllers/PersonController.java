package controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import models.AccountValidation;
import models.Contact;
import models.Gift;
import models.GiftUser;
import models.User;
import models.UserAppreciation;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Result;
import play.mvc.Security;

import com.avaje.ebean.Ebean;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.io.Files;

import controllers.SearchController.Search;

/**
 * Controller used to manage User information
 * @author NGUYEN Nhat Minh
 * @version 1.0.0
 * @category Controller
 * @see {@link Object}
 */
@Security.Authenticated(Secured.class)
public class PersonController extends Controller {
	public static final Form<SendGift> sendGiftForm = Form.form(SendGift.class);
	public static final Form<Search>   searchForm   = Form.form(Search.class);
	public static final Form<User>     personForm   = Form.form(User.class);
	
	
	
	/**
	 * 
	 * @param user
	 * @return
	 */
	public static Result person(User user) {
		// User user = User.findUserbyUserName(userName.);
		
		if(!user.userName.equals(Application.getUser().userName)){
			flash("error", String.format("No permission"));
			return redirect(routes.AccueilController.accueil());
		}else{
			return ok(views.html.person.infoPerson.render(user, sendGiftForm,
					searchForm));
		}
		
	}
	
	/**
	 * 
	 * @param idUserAppreciation
	 * @return
	 */
	public static Result deleteBookmark(int idUserAppreciation) {
	    final UserAppreciation ua = UserAppreciation.findById(idUserAppreciation);
	    if(ua == null) {
	        return notFound(String.format("Bookmark %s does not exists.", idUserAppreciation));
	    }
	    Ebean.delete(ua);
	    flash("success", String.format("Bookmark supprimé"));
	    return ok();
	  }

	/**
	 * 
	 * @param user
	 * @return 
	 */
	public static Result changePictureUser(User user) {
		MultipartFormData body = request().body().asMultipartFormData();
		FilePart picture = body.getFile("picture");
		if (picture != null) {
			
			String contentType = picture.getContentType();
			
			String str[] = contentType.split("/");
			String fileType = str[1];
			System.out.println(fileType);
			
			/**
			 * if filetype not equal jpg, jpeg, bmp, gif (image format) return error
			 */
			if(!fileType.equalsIgnoreCase("jpg") && !fileType.equalsIgnoreCase("jpeg")&& !fileType.equalsIgnoreCase("bmp")
					&& !fileType.equalsIgnoreCase("gif")){
				
				flash("error", String.format("La photo doit être en format jpg, jpeg, bmp, gif"));
				return redirect(routes.PersonController.person(user));
			}
				
			File content = picture.getFile();
			if(content == null){
				flash("error", String.format("Aucun fichier selectionné"));
				return redirect(routes.PersonController.person(user));
			}
			FileOutputStream fop = null;
			File file;
			String path = "public\\imgs\\users\\" + user.userName + "\\avatar\\";
			System.out.println(path);
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
				String fileName = "avatarUser." + fileType;
				
				file = new File(path +  fileName);
				fop = new FileOutputStream(file);

				// if file doesnt exists, then create it
				if (!file.exists()) {
					file.createNewFile();
				}

				// get the content in bytes
				byte[] contentInBytes = Files.toByteArray(content);
				user.avatar = fileName;
				
				user.update();
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
			flash("success", String.format("L'avatar a bien été changé"));
			return redirect(routes.PersonController.person(user));
		} else {
			flash("error", "Aucun fichier a été selectionné");
			return redirect(routes.PersonController.person(user));
		}
	}

	/**
	 * 
	 * @param user
	 * @return
	 */
	public static Result changePicturePage(User user) {
		return ok(views.html.person.changePicture.render(user, searchForm));
	}

	/**
	 * 
	 * @param user
	 * @return
	 */
	public static Result modifyPerson(User user) {
		Form<User> filledForm = personForm.fill(user);
		return ok(views.html.person.detailPerson.render(user, searchForm));
	}

	/**
	 * 
	 * @param user
	 * @return
	 */
	public static Result savePerson(User user) {
		
		DynamicForm form 	= Form.form().bindFromRequest();
		String firstName 	= form.get("firstName");
		String lastName  	= form.get("lastName");
		String email 		= form.get("email");
		String address 	 	= form.get("address");
		String postalCode   = form.get("postalCode");
		String city         = form.get("city");
		String homePhone 	= form.get("homePhone");
		String mobilePhone 	= form.get("mobilePhone");
		String presentation = form.get("presentation");
		String hoppy 		= form.get("hoppy");
		String signature    = form.get("signature");
		
		
		if(firstName != ""){
			user.firstName = firstName;
		}
		if(lastName != ""){
			user.lastName = lastName;
		}
		if(email != ""){
			user.email = email;
		}
		if(address != ""){
			user.address = address;
		}
		if(postalCode != ""){
			user.postalCode = postalCode;
		}
		if(city != ""){
			user.city = city;
		}
		if(homePhone != ""){
			user.homePhone = homePhone;
		}
		if(mobilePhone != ""){
			user.mobilePhone = mobilePhone;
		}
		if(presentation != ""){
			user.presentation = presentation;
		}
		if(hoppy != ""){
			user.hoppy = hoppy;
		}
		if(signature != ""){
			user.signature = signature;
		}
		
		user.update();
		
		return redirect(routes.PersonController.person(user));

	}

	/**
	 * 
	 * 
	 *
	 */
	public static class SendGift {
		public String userName;
		public String idGift;
		public int amount;
	}

	/**
	 * 
	 * @param str
	 * @return
	 */
	public static Result postCode(String str) {
		if (str != null) {
			User user = User.findById(Application.getSessionUser());
			if (AccountValidation.saveCode(user, str)) {
				flash("success", String.format("Code généré avec succès"));
			}
			return ok();
		} else
			return forbidden();
	}

	/**
	 * 
	 * @param user
	 * @return
	 */
	public static Result sendGift(User user) {

		Form<SendGift> boundForm = sendGiftForm.bindFromRequest();
		SendGift sg = boundForm.get();
		String userName = sg.userName;
		String idGift = sg.idGift;
		int amount = sg.amount;

		System.out.println("idGift: " + idGift + " amount: " + amount);
		// find friend to send gift
		User friend = User.findById(userName);
		Gift gift = Gift.findById(Integer.parseInt(idGift));
		if (friend == null) {
			flash("error", String.format("Utilisateur introuvable"));
			return badRequest(views.html.person.infoPerson.render(user,
					sendGiftForm, searchForm));
		} else if(friend.equals(user)){
			flash("error", String.format("Utilisateur identique "));
			return badRequest(views.html.person.infoPerson.render(user,
					sendGiftForm, searchForm));
		}
		
		else if (gift == null) {
			flash("error", String.format("Cadeau introuvable"));
			return badRequest(views.html.person.infoPerson.render(user,
					sendGiftForm, searchForm));
		} else {
			if (PersonController.offerGift(user, friend, gift, amount)) {
				flash("success", String.format("Envoi cadeau réussi"));
				return ok(views.html.person.infoPerson.render(user,
						sendGiftForm, searchForm));
			} else {
				flash("error", String.format("Erreur lors envoi cadeau"));
				return badRequest(views.html.person.infoPerson.render(user,
						sendGiftForm, searchForm));
			}
		}
	}

	/**
	 * 
	 * @param user
	 * @param friend
	 * @param gift
	 * @param amount
	 * @return
	 */
	public static boolean offerGift(User user, User friend, Gift gift,
			int amount) {
		GiftUser gu = GiftUser.findByUserGift(user, gift);
		if (gu != null) {
			/* Create a Gift User with username, gift, and amount to offfer */

			GiftUser guFriend = GiftUser.findByUserGift(friend, gift);
			if (guFriend == null) {
				guFriend = new GiftUser();
				guFriend.buyDate = new Date();
				guFriend.user = friend;
				guFriend.amount = amount;
				guFriend.gift = gift;
				guFriend.save();
			} else {
				guFriend.amount += amount;
				guFriend.buyDate = new Date();
				guFriend.update();
			}
			/* update or delete Gift user of sender */
			if (gu.amount == amount) {
				gu.delete();

			} else {
				gu.amount -= amount;
				guFriend.buyDate = new Date();
				gu.update();
			}
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 
	 * @param user
	 * @return
	 */
	public static Result changePassword(User user) {
		DynamicForm form = Form.form().bindFromRequest();
		
		String actualPass = form.get("actualPass");
		String newPass = form.get("newPass");
		String confirmPass = form.get("confirmPass");

		String passFromDB = user.password;
		if (!actualPass.equals(passFromDB)) {
			flash("error", String.format("Mot de passe incorrect"));
			return ok(views.html.person.infoPerson.render(user, sendGiftForm,
					searchForm));
		} else {
			if (newPass.length() < 6) {
				flash("error",
						String.format("Mot de passe doit contenir plus de 6 charactères"));
				return ok(views.html.person.infoPerson.render(user,
						sendGiftForm, searchForm));
			} else {
				if (!newPass.equals(confirmPass)) {
					flash("error",
							String.format("Ces deux cases doivent être identiques"));
					return ok(views.html.person.infoPerson.render(user,
							sendGiftForm, searchForm));
				} else {
					user.password = newPass;
					user.update();
					flash("success",
							String.format("Votre mot de passe a bien été changé"));

					return ok(views.html.person.infoPerson.render(user,
							sendGiftForm, searchForm));
				}
			}
		}

	}

	/**
	 * 
	 * @param idContact
	 * @return
	 */
	public static Result confirmContact(String idContact) {
		System.out.println(idContact);
		if (Contact.confirmContact(idContact)) {
			flash("success", String.format("Contact confirmé"));
			return ok();
		} else
			return forbidden();
	}

	/**
	 * 
	 * @return
	 */
	@BodyParser.Of(BodyParser.Json.class)
	public static Result sendRequest() {
		JsonNode json = request().body().asJson();

		String idSender = controllers.Application.clarifyText(json.get(
				"idSender").toString());
		String idReceiver = controllers.Application.clarifyText(json.get(
				"idReceiver").toString());

		if (Contact.sendRequest(idSender, idReceiver))
			return ok();
		else
			return forbidden();
	}
}
