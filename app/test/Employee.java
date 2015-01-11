package test;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import play.db.ebean.Model;
@Entity
public class Employee extends Model {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private long id;

	@OneToMany(mappedBy = "employee")
	private List<ProjectAssociation> projects;
}
