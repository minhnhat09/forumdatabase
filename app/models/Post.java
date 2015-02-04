package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.data.validation.Constraints;
import play.db.ebean.Model;
import play.mvc.PathBindable;

import com.avaje.ebean.Page;
@Entity
public class Post extends Model implements PathBindable<Post>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	public int idPost;
	@Constraints.Required
	@Constraints.MinLength(20)
	@Column(columnDefinition = "LONGTEXT")
	public String postContent;
	
	public Date postTime;
	public Date lastUpdate;
	
	@ManyToOne
	public User user;
	
	@ManyToOne
	public Thread thread;
	
	
	public static Finder<Integer, Post> find = new Finder<Integer, Post>(Integer.class, Post.class);
	
	public static List<Post> findPostsByThread(int idThread){
		
		return find.where().eq("thread_id_thread", idThread).findList();
	}
	
	public static void delPost(Post post){
		post.delete();
	}
	
	public static boolean isPostOwner(Post post, String userName){
		return post.user.userName.equals(userName);
	}
	
	public static Post findLastPostByThread(int idThread){
		return find.where()
				   .eq("thread_id_thread", idThread)
				   .orderBy("id_post desc")
				   .setMaxRows(1)
				   .findUnique()
				   ;
				   
	}
	
	public static String printAuthor(Post post){
		return post.user.firstName + " " + post.user.lastName;
	}
	public static int countPostsByUser(User user){
		return find.where()
				   .eq("user_user_name", user.userName)
				   .findRowCount();
	}
	
	public static Page<Post> find(Thread thread, int page){
		thread.responseCount = find.where()
								   .eq("thread_id_thread", thread.idThread)
								   .findRowCount();
		thread.update();
		return find.where()
				.eq("thread_id_thread", thread.idThread)
				.orderBy("id_post asc")
				.findPagingList(10)
				.setFetchAhead(false)
				.getPage(page);
	}
	
	public static Post findById(int idPost){
		return find.where().eq("id_post", idPost).findUnique();
	}
	
	public static List<Post> findPosts(){
		return find
				   .findList();
	}
	
	
	
	@Override
	public Post bind(String key, String value) {
		return findById(Integer.parseInt(value));
	}

	@Override
	public String javascriptUnbind() {
		return String.valueOf(this.idPost);
	}

	@Override
	public String unbind(String arg0) {
		return String.valueOf(this.idPost);
	}
}
