package models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class UserPermissionId implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(nullable = false)
	public String user_user_name;
	@Column(nullable = false)
	public int permission_id_permission;
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + permission_id_permission;
		result = prime * result
				+ ((user_user_name == null) ? 0 : user_user_name.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserPermissionId other = (UserPermissionId) obj;
		if (permission_id_permission != other.permission_id_permission)
			return false;
		if (user_user_name == null) {
			if (other.user_user_name != null)
				return false;
		} else if (!user_user_name.equals(other.user_user_name))
			return false;
		return true;
	}
	
	
	
}
