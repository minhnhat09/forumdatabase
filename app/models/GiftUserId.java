package models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class GiftUserId implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(nullable = false)
	public int gift_id_gift;
	@Column(nullable = false)
	public String user_user_name;
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + gift_id_gift;
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
		GiftUserId other = (GiftUserId) obj;
		if (gift_id_gift != other.gift_id_gift)
			return false;
		if (user_user_name == null) {
			if (other.user_user_name != null)
				return false;
		} else if (!user_user_name.equals(other.user_user_name))
			return false;
		return true;
	}
	
	
}
