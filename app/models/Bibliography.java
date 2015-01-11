package models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.db.ebean.Model;

@Entity
public class Bibliography extends Model{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	public int idBibliography;
	public String author;
	@Column(columnDefinition = "LONGTEXT")
	public String content;
	public String lien;
	@ManyToOne
	public Thread thread;
	
	public static Finder<Integer, Bibliography> find = new Finder<Integer, Bibliography>(Integer.class, Bibliography.class);
	
	
	public static List<Bibliography> findListByThread(int idThread){
		return find.where()
				   .eq("thread_id_thread", idThread)
				   .findList();
	}
}
