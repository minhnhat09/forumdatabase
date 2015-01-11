package test;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import play.db.ebean.Model;

@Entity
@Table(name="PROJ_EMP")

public class ProjectAssociation extends Model{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	ProjectAssociationId projectID;
//	@Id
//	private long employeeId;
//	 @Id
//	  private long projectId;
	  @Column(name="IS_PROJECT_LEAD")
	  private boolean isProjectLead;
	  @ManyToOne
	  @PrimaryKeyJoinColumn(name="EMPLOYEEID", referencedColumnName="ID")
	  /* if this JPA model doesn't create a table for the "PROJ_EMP" entity,
	  *  please comment out the @PrimaryKeyJoinColumn, and use the ff:
	  *  @JoinColumn(name = "employeeId", updatable = false, insertable = false)
	  * or @JoinColumn(name = "employeeId", updatable = false, insertable = false, referencedColumnName = "id")
	  */
	  private Employee employee;
	  @ManyToOne
	  @PrimaryKeyJoinColumn(name="PROJECTID", referencedColumnName="ID")
	  private Project project;
}
