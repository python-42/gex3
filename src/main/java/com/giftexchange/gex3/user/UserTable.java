package com.giftexchange.gex3.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="user")
public class UserTable {

	public UserTable(){}

	public UserTable(String username, String password){
		this.username = username;
		this.password = password;
		this.enabled = true;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	@Column(nullable = false, unique = true, length=8)
	private String username;

	@Column(nullable = false, length=60)//https://stackoverflow.com/questions/5881169/what-column-type-length-should-i-use-for-storing-a-bcrypt-hashed-password-in-a-d
	private String password;

	@Column(nullable = false)
	private boolean enabled;

	//getters and setters
	public Integer getId() {
		return id;
	  }
	
	  public void setId(Integer id) {
		this.id = id;
	  }

	public String getUsername(){
		return password;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public String getPassword(){
		return password;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public boolean getEnabled(){
		return enabled;
	}

	public void setEnabled(boolean enabled){
		this.enabled = enabled;
	}
	
}