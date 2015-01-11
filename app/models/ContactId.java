package models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ContactId implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(nullable = false)
	public String user_user_name;
	@Column(nullable = false)
	public String user_user_contact;
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((user_user_contact == null) ? 0 : user_user_contact
						.hashCode());
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
		ContactId other = (ContactId) obj;
		if (user_user_contact == null) {
			if (other.user_user_contact != null)
				return false;
		} else if (!user_user_contact.equals(other.user_user_contact))
			return false;
		if (user_user_name == null) {
			if (other.user_user_name != null)
				return false;
		} else if (!user_user_name.equals(other.user_user_name))
			return false;
		return true;
	}
	
	
}
