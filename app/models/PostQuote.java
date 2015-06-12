package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;
@Entity
public class PostQuote extends Model{

	
	private static final long serialVersionUID = 1L;
	
	@Id
	public int idPostQuote;
	
	public int post;
	public int quotes;
	
	public static Finder<Integer, PostQuote> find = new Finder<Integer, PostQuote>(Integer.class, PostQuote.class);

	public static List<PostQuote> findQuotesByPost(int idPost){
		return find.where()
				   .eq("post", idPost)
				   .findList();
	}
	/**
	 * Method used to find post by quote
	 * For delete purpose
	 * 
	 * @param idPost id of the quote field in postquote table
	 * @return
	 */
	public static List<PostQuote> findQuotesByQuote(int idPost){
		return find.where()
				   .eq("quotes", idPost)
				   .findList();
	}
}
