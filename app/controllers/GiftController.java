package controllers;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import models.Gift;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;

import com.avaje.ebean.Ebean;
import com.google.common.io.Files;

import controllers.SearchController.Search;

@Security.Authenticated(Secured.class)
public class GiftController extends Controller {
	public static final Form<Search> searchForm = Form.form(Search.class);
	
	
	private static Result GIFT_HOME = redirect(routes.GiftController.giftHome());
	private static final Form<Gift> giftForm = Form.form(Gift.class);
	
	public static Result giftHome(){
		return ok(views.html.gifts.list.render("Gestion des cadeaux", searchForm));
	}
	
	public static Result newGift(){
		return ok(views.html.gifts.detailGift.render(giftForm, searchForm));
	}
	
	public static Result modifyGift(int idGift){
		final Gift gift = Gift.findById(idGift);
		Form<Gift> filledForm =  giftForm.fill(gift);
		return ok(views.html.gifts.detailGift.render(filledForm, searchForm));
	}
	
	public static Result saveGift(){
		Form<Gift> boundForm = giftForm.bindFromRequest();
		
		if(boundForm.hasErrors()){
			flash("error", String.format("Erreur"));
			return badRequest(views.html.gifts.detailGift.render(giftForm, searchForm));
		}
		Gift gift = boundForm.get();
		
		MultipartFormData body = request().body().asMultipartFormData();
		FilePart picture = body.getFile("picture");
		if (picture != null) {
			
			//String contentType = picture.getContentType();
			//String str[] = contentType.split("/");
			//String fileType = str[1];

			File content = picture.getFile();

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
				String fileName = "avatarGift";
				
				file = new File(path +  fileName);
				fop = new FileOutputStream(file);

				// if file doesnt exists, then create it
				if (!file.exists()) {
					file.createNewFile();
				}

				// get the content in bytes
				byte[] contentInBytes = Files.toByteArray(content);
				gift.imgUrl = fileName;
				
				gift.update();
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
		flash("success", String.format("Successfully added gift"));
		return GIFT_HOME;
	}
	
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
