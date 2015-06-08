package models;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import play.data.validation.Constraints;
import play.db.ebean.Model;
import play.mvc.PathBindable;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Page;

@Entity
public class Thread extends Model implements PathBindable<Thread>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	public int idThread;
	@Constraints.Required
	public String threadName;
	public String threadType;
	public int rating;
	public String lastMessage;
	public int viewCount;
	public int responseCount;
	public int likeCount;
	public int dislikeCount;
	public int favoriteCount;
	@Column(columnDefinition = "LONGTEXT")
	@Constraints.Required
	public String content;
	public String attachFile;
	public String attachImg;
	public Date publicDate;
	public Date lastUpdate;
	public boolean isSpined;
	public boolean isBlocked;
	public boolean isHot;
	public String category;
	
	@ManyToMany
	public List<Tag> tags = new LinkedList<Tag>();
	
	
	@OneToOne
	public User author;
	@OneToMany(mappedBy = "thread")
	public List<Bibliography> biblios;
	@ManyToOne
	public Application application;
	@OneToMany(mappedBy = "thread")
	public List<Post> posts;
	
	@OneToMany(mappedBy = "thread")
	public List<UserAppreciation> users;
	
	public static Finder<Integer, Thread> find =  new Finder<Integer, Thread>(Integer.class, Thread.class);
	
	/***/
	
	public static Thread findById(int id){
		return find.byId(id);
	}
	
	public static List<Thread> findThreads(){
		return find.findList();
	}
	
	public static void delThread(Thread thread){
		
		for (Post post : thread.posts) {
			post.delete();
		}
		thread.posts.clear();
		
		//OneToMany
		for (UserAppreciation ua : thread.users) {
			ua.delete();
		}
		thread.users.clear();
		
		//ManytoMany
		for (Tag tag : thread.tags) {
		}
		thread.tags.clear();
		//OnetoMany
		for(Bibliography b: thread.biblios){
			b.delete();
		}
		thread.biblios.clear();
		
		
		Ebean.update(thread);
		Ebean.delete(thread);
		
	}
	
	public static boolean isThreadOwner(Thread thread, String userName){
		return thread.author.userName.equals(userName);
	}
	
	public static List<Thread> findThreadsByAppTag(int idForum, String idTag){
		return find.where()
				   .eq("application_id_app", idForum)
				   .eq("tags.idTag", idTag)
				   .findList();
	}
	
	public static List<Thread> findByApp(int idApp){
		return find.where()
				   .eq("application_id_app", idApp)
				   .findList();
	}
	
	public static List<Thread> exportThreads(String service, String forum, String tag){
		//find all threads in all services
		if(service.equals("-1") && forum.equals("-1") && tag.equals("-1")){
			return Thread.findThreads();
		}
		//find all threads in all services filter by tag
		else if(service.equals("-1") && forum.equals("-1") && !tag.equals("-1")){
			List<Service> listService  = Service.findAllServices();
			List<Thread> threads = new ArrayList<Thread>();
			for (Service sv : listService) {
				List<Application> listApps = Application.findByIdService(String.valueOf(sv.idService));
				for (Application application : listApps) {
					List<Thread> threadTag = Thread.findThreadsByAppTag(application.idApp, tag);
					threads.addAll(threadTag);
				}
			}
			return threads;
		}
		//find all threads in ONE service filter by tag
		else if(!service.equals("-1") && forum.equals("-1") && !tag.equals("-1")){
			List<Thread> threads = new ArrayList<Thread>();
			List<Application> listApps = Application.findByIdService(String.valueOf(service));
			for (Application application : listApps) {
				List<Thread> threadTag = Thread.findThreadsByAppTag(application.idApp, tag);
				threads.addAll(threadTag);
			}
			return threads;
		}
		//find all threads in ONE service NOT filter by tag
		else if(!service.equals("-1") && forum.equals("-1") && tag.equals("-1")){
			List<Thread> threads = new ArrayList<Thread>();
			List<Application> listApps = Application.findByIdService(String.valueOf(service));
			for (Application application : listApps) {
				List<Thread> threadTag = Thread.findByApp(application.idApp);
				threads.addAll(threadTag);
			}
			return threads;
		}
		//find all threads in ONE service, in ONE app, filter by tag
		else if(!service.equals("-1") && !forum.equals("-1") && tag.equals("-1")){
			return Thread.findByApp(Integer.parseInt(forum));
		}
		//find all threads in ONE service, in ONE app, NOT filter by tag
		else if(!service.equals("-1") && !forum.equals("-1") && !tag.equals("-1")){
			return Thread.findThreadsByAppTag(Integer.parseInt(forum), tag);
		}else return null;
		
		
	
	}
	
	public static void exportTheme(String service, String forum, String tag){
		System.out.println("do day");
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet();
		
		List<models.Thread> threads = exportThreads(service, forum, tag);
		int rowNum = 0;
		Row rowTitle = sheet.createRow(rowNum++);
		Cell cellTitle = rowTitle.createCell(0);
		cellTitle.setCellValue("Nom du post");
		Cell cellTitle1 = rowTitle.createCell(1);
		cellTitle1.setCellValue("Contenu");
				
		for (models.Thread thread: threads) {
			Row row =  sheet.createRow(rowNum++);
			int celNumm = 0;
			for (int i = 0; i < 2; i++) {
				
				Cell cell = row.createCell(celNumm++);
				
				if(celNumm == 1){
					cell.setCellValue(thread.threadName); 
				}else if(celNumm == 2){
					cell.setCellValue(thread.content);
				}
			}
		}
		
		try {
			FileOutputStream out = new FileOutputStream(new File("ReportTheme.xls"));
			workbook.write(out);
			out.close();
			System.out.println("okkkk");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public static void exportNote(String service, String forum, String tag){
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet();
		
		List<models.Thread> threads = exportThreads(service, forum, tag);
		int rowNum = 0;
		Row rowTitle = sheet.createRow(rowNum++);
		Cell cellTitle = rowTitle.createCell(1);
		cellTitle.setCellValue("Note");
		Cell cellTitle1 = rowTitle.createCell(0);
		cellTitle1.setCellValue("Nom du post");
				
		for (models.Thread thread: threads) {
			Row row =  sheet.createRow(rowNum++);
			int celNumm = 0;
			for (int i = 0; i < 2; i++) {
				
				Cell cell = row.createCell(celNumm++);
				
				if(celNumm == 1){
					cell.setCellValue(thread.threadName); 
				}else if(celNumm == 2){
					cell.setCellValue(thread.rating);
				}
			}
		}
		
		try {
			FileOutputStream out = new FileOutputStream(new File("ReportNote.xls"));
			workbook.write(out);
			out.close();
			System.out.println("okkkk");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	
	public static void exportLike(String service, String forum, String tag){
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet();
		
		List<models.Thread> threads = exportThreads(service, forum, tag);
		int rowNum = 0;
		Row rowTitle = sheet.createRow(rowNum++);
		Cell cellTitle = rowTitle.createCell(0);
		cellTitle.setCellValue("Nom du post");
		Cell cellTitle1 = rowTitle.createCell(1);
		cellTitle1.setCellValue("Like");
		Cell cellTitle2 = rowTitle.createCell(2);
		cellTitle2.setCellValue("DisLike");	
		Cell cellTitle3 = rowTitle.createCell(3);
		cellTitle3.setCellValue("Mise en favori");	
		
		for (models.Thread thread: threads) {
			Row row =  sheet.createRow(rowNum++);
			int celNumm = 0;
			for (int i = 0; i < 4; i++) {
				int num = celNumm++;
				Cell cell = row.createCell(num);
				
				if(celNumm == 1){
					cell.setCellValue(thread.threadName); 
				}else if(celNumm == 2){
					cell.setCellValue(thread.likeCount);
				}
				else if(celNumm == 3){
					cell.setCellValue(thread.dislikeCount);
				}
				else if(celNumm == 4){
					cell.setCellValue(thread.favoriteCount);
				}
			}
		}
		
		try {
			FileOutputStream out = new FileOutputStream(new File("ReportLike.xls"));
			workbook.write(out);
			out.close();
			System.out.println("okkkk");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	/**
	 * Method return page of thread by application to display in each forum
	 * @param page Page to display
	 * @param app  Application containing page of thread
	 * 
	 * */
	
	public static Page<Thread> findPage(int page, Application app, String sortBy
			, String order){
		return find.where()
				.eq("application_id_app", app.idApp)
				.eq("is_spined", 0)
				.orderBy(sortBy + " " + order)
				
				.findPagingList(10)
				.setFetchAhead(false)
				.getPage(page);
	}
	
	/***/
	public static Page<Thread> findPageByTagName(Page<Thread>threads, Tag tag){
		
		for (Thread thread : threads.getList()) {
			if(!thread.tags.contains(tag))
				threads.getList().remove(thread);
		}
		return threads;
	}
	
	public static Page<Thread> findPageAllTagCountry(int page, Application app, String sortBy
			, String order){
		return find.fetch("tags")
				   .where().eq("application_id_app", app.idApp)
				   .eq("tags.category", "pays")
				   .orderBy(sortBy + " " + order)
				   .findPagingList(10)
				   .setFetchAhead(false)
				   .getPage(page);
	}
	
	public static Page<Thread> findPageAllTagModule(int page, Application app, String sortBy
			, String order){
		return find.fetch("tags")
				   .where().eq("application_id_app", app.idApp)
				   .eq("tags.category", "modules")
				   .orderBy(sortBy + " " + order)
				   .findPagingList(10)
				   .setFetchAhead(false)
				   .getPage(page);
	}
	
	public static Page<Thread> findPageByTag(int page, Application app, Tag tag, String sortBy
			, String order){
		return find.fetch("tags")
				   .where().eq("application_id_app", app.idApp)
				   .eq("tags.tagTitle", tag.tagTitle)
				   .orderBy(sortBy + " " + order)
				   .findPagingList(10)
				   .setFetchAhead(false)
				   .getPage(page);
		
	}
	
	/**
	 * Method return list of spined thread by application
	 * @param app Application containing thread
	 * 
	 * */
	
	public static List<Thread> findSpinedThreads(Application app){
		return find.where()
				.eq("application_id_app", app.idApp)
				.eq("is_spined", 1)
				.orderBy("id_thread asc")
				.findList();
	}
	
	/**
	 * Method return number of threads written by user
	 * @param userName user to count his threads
	 * 
	 * */
	
	public static int countThreadsByUser(String userName){
		return find.where()
				.eq("author_user_name", userName)
				.findRowCount();
				   
	}
	
	/**
	 * Method find Page of thread in a specific forum (for search module)
	 * @param page 			Page to display
	 * @param nameThread    Thread to search
	 * @param idApp			App containing thread (to focus the result)
	 * 
	 * */
	
	public static Page<Thread> findByNameForum(int page, String nameThread, int idApp){
		return find.where()
				.ilike("thread_name", "%" + nameThread + "%")
				.eq("application_id_app", idApp)
				.orderBy("id_thread asc")
				.findPagingList(4)
				.setFetchAhead(false)
				.getPage(page);
				
	}
	
	/**
	 * Method find Page thread by name of thread (for search module)
	 * @param page 		 Page to display
	 * @param nameThread Thread to search
	 * 
	 * */
	
	public static List<Thread> findByName(int page, String nameThread){
		
		return find.where()
				.ilike("thread_name", "%" + nameThread + "%")
				.orderBy("id_thread asc")
				.findList();
	}
	
	/**
	 * Method find pages of thread by author (for search module)
	 * @param page 		 Page to display
	 * @param authorName Author to search
	 * */
	
	public static Page<Thread> findByAuthor(int page, String authorName){
		return find.where()
				.ilike("author_user_name", "%" + authorName + "%")
				.orderBy("id_thread asc")
				.findPagingList(4)
				.setFetchAhead(false)
				.getPage(page);
	}

	
	
	/**
	 * Method find a List of thread in a specific forum by name
	 * @param nameThread    Thread to search
	 * @param idApp			App containing thread (to focus the result)
	 * 
	 * */
	public static List<Thread> findInForumByName(String nameThread, int idApp){
		return find.where()
				.ilike("thread_name", "%" + nameThread + "%")
				.eq("application_id_app", idApp)
				.orderBy("id_thread asc")
				.findList();
						
	}
	
	/**
	 * Method find pages of thread by author (for search module)
	 * @param authorName 	Author to search
	 * @param idApp			App containing thread (to focus the result)
	 *  */
	public static List<Thread> findInForumByAuthor(String authorName, int idApp){
		return find.where()
				.ilike("author_user_name", "%" + authorName + "%")
				.eq("application_id_app", idApp)
				.orderBy("id_thread asc")
				.findList();
				
	}
	
	
	
	@Override
	public Thread bind(String key, String value) {
		return findById(Integer.parseInt(value));
	}

	@Override
	public String javascriptUnbind() {
		return String.valueOf(this.idThread);
	}

	@Override
	public String unbind(String arg0) {
		return String.valueOf(this.idThread);
	}
}
