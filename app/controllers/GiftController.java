package controllers;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import models.Gift;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Result;
import play.mvc.Security;

import com.avaje.ebean.Ebean;
import com.google.common.io.Files;

import controllers.SearchController.Search;
/**
 * Gift controller manage gifts
 * @author NGUYEN Nhat Minh
 * @version 1.0.0
 * @category Controller
 * @see {@link Object}
 */
@Security.Authenticated(Secured.class)
public class GiftController extends Controller {
	public static final Form<Search> searchForm = Form.form(Search.class);
	private static Result GIFT_HOME 			= redirect(routes.GiftController.giftHome());
	private static Result GIFT_HOME_ADMIN       = redirect(routes.GiftController.giftHomeAdmin());
	
	private static final Form<Gift> giftForm = Form.form(Gift.class);
	/**
	 * 
	 * @return
	 */
	public static Result giftHome(){
		return ok(views.html.gifts.list.render("Achat des cadeaux", searchForm));
	}
	/**
	 * 
	 * @return
	 */
	public static Result giftHomeAdmin(){
		/**
		 * Check if the user is admin or not
		 * if he is not admin, he dont have permission to view this page
		 */
		
		if(controllers.Application.isAdmin()){
			return ok(views.html.gifts.listGiftsAdmin.render("Gestion des cadeaux", searchForm));
		}else{
			flash("error", String.format("Vous n'avez pas le droit de consulter cette page"));
			return redirect(routes.AccueilController.accueil());
		}
	}
	/**
	 * 
	 * @return
	 */
	public static Result newGift(){
		return ok(views.html.gifts.detailGift.render(giftForm, searchForm));
	}
	/**
	 * 
	 * @param idGift
	 * @return
	 */
	public static Result modifyGift(int idGift){
		final Gift gift = Gift.findById(idGift);
		Form<Gift> filledForm =  giftForm.fill(gift);
		return ok(views.html.gifts.detailGift.render(filledForm, searchForm));
	}
	/**
	 * 
	 * @return
	 */
	public static Result saveGift(){
		Form<Gift> boundForm = giftForm.bindFromRequest();
		
		if(boundForm.hasErrors()){
			flash("error", String.format("Erreur"));
			return ok(views.html.gifts.detailGift.render(boundForm, searchForm));
		}
		Gift gift = boundForm.get();
		MultipartFormData body = request().body().asMultipartFormData();
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
				return ok(views.html.gifts.detailGift.render(boundForm, searchForm));
			}
			
			
			File content = picture.getFile();
			if(content == null){
				flash("error", String.format("Aucun fichier selectionné"));
				return ok(views.html.gifts.detailGift.render(boundForm, searchForm));
			}
			
			FileOutputStream fop = null;
			File file;
			String path = "public\\imgs\\gifts\\" + gift.idGift + "\\avatar\\";
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
				String fileName = "avatarGift" + "." + fileType;
				
				file = new File(path +  fileName);
				fop = new FileOutputStream(file);

				// if file doesnt exists, then create it
				if (!file.exists()) {
					file.createNewFile();
				}

				// get the content in bytes
				byte[] contentInBytes = Files.toByteArray(content);
				gift.imgUrl = fileName;
				
				
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
		
		if(gift.idGift == 0){
			Ebean.save(gift);
		}else{
			Ebean.update(gift);
		}
		
		
		flash("success", String.format("Le cadeau a bien été enregistré"));
		return GIFT_HOME_ADMIN;
	}
	/**
	 * 
	 * @param str
	 * @return
	 */
	public static Result buyListGift(String str){
		if(str != null){
			if(Gift.buyListGift(str))
				flash("success", String.format("Succès"));
			else
				flash("error", String.format("Erreur"));
			return ok();
		}
		else return forbidden();
	}
	/**
	 * 
	 * @param str
	 * @return
	 */
	public static Result deleteListGift(String str){
		
		if(str != null){
			if(Gift.deleteListGift(str))
				flash("success", String.format("Supprimer la liste des cadeaux ok"));
			else
				flash("error", String.format("Supprimer la liste des cadeaux not ok"));
			return ok();
		}
		else return forbidden();
	}
	
		
}
