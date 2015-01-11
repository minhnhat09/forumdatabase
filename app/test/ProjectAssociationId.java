package test;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
@Embeddable
public class ProjectAssociationId implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(nullable = false)
	private long employeeId;
	 @Column(nullable = false)
	  private long projectId;
	  
	 
	  public int hashCode() {
	    return (int)(employeeId + projectId);
	  }
	 
	  public boolean equals(Object object) {
	    if (object instanceof ProjectAssociationId) {
	      ProjectAssociationId otherId = (ProjectAssociationId) object;
	      return (otherId.employeeId == this.employeeId) && (otherId.projectId == this.projectId);
	    }
	    return false;
	  }
}
