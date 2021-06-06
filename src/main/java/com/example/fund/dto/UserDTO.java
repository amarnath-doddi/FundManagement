package com.example.fund.dto;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

import com.example.fund.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserDTO {
	private Long id;
	private String loginId;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private LocalDate lastUpdated;
	private String phone;
	
	public UserDTO() {
	}
	public UserDTO(Optional<User> userOptional) {
		if(userOptional.isPresent()) {
			User user = userOptional.get();
			this.id = user.getId();
			this.email = user.getEmail();
			this.firstName = user.getFirstName();
			this.lastName = user.getLastName();
			this.lastUpdated = user.getLastUpdated();
			this.loginId = user.getLoginId();
			this.password = user.getPassword();
			this.phone = user.getPhone();
		}
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public LocalDate getLastUpdated() {
		return lastUpdated;
	}
	public void setLastUpdated(LocalDate lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserDTO getUserDTO(User user) {
		this.id = user.getId();
		this.email = user.getEmail();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.lastUpdated = user.getLastUpdated();
		this.loginId = user.getLoginId();
		this.password = user.getPassword();
		this.phone = user.getPhone();
		return this;
	}
	
	@JsonIgnore
	public User getUser() {
		User user = new User();
		user.setId(id);
		user.setEmail(email);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setLastUpdated(lastUpdated);
		user.setLoginId(loginId);
		user.setPassword(password);
		user.setPhone(phone);
		return user;
	}
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO user = (UserDTO) o;
        return id == user.id 
        		&& Objects.equals(lastName, user.lastName)
        		&& Objects.equals(firstName, user.firstName)
        		&& Objects.equals(loginId, user.loginId)
        		&& Objects.equals(password, user.password)
        		&& Objects.equals(email, user.email)
        		&& Objects.equals(lastUpdated, user.lastUpdated)
        		&& Objects.equals(phone, user.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lastName, firstName, loginId, password, email, phone, lastUpdated);
    }
}
