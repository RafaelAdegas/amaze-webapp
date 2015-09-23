package io.redshoes.zoone.admin.entity;

import io.redshoes.zoone.admin.entity.Establishment;
import io.redshoes.zoone.admin.entity.User;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


@Entity
@Table(name="USERS")
public class User implements UserDetails
{
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue
	private Long idUser;

	@Column(name="s_username")
	private String username;

	@Column(name="s_firstname")
	private String firstName;

	@Column(name="s_lastname")
	private String lastName;

	@Column(name="s_password")
	private String password;
	
	private transient String password2;
	
	@Column(name="s_email")
	private String email;
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity=Establishment.class)
	@JoinColumn(name="id_establishment")
	private Establishment establishment;

	@ElementCollection(fetch = FetchType.EAGER)
	private Set<String> roles = new HashSet<String>();

	public User()
	{
		/* Reflection instantiation */
	}


	public User(String name, String passwordHash)
	{
		this.username = name;
		this.password = passwordHash;
	}

	public Long getIdUser() {
		return this.idUser;
	}


	public void setUsername(String name) {
		this.username = name;
	}
	
	
	public String getFirstName() {
		return this.firstName;
	}
	
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	
	public String getLastName() {
		return this.lastName;
	}
	
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public Set<String> getRoles() {
		return this.roles;
	}


	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}


	public void addRole(String role) {
		this.roles.add(role);
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPassword2() {
		return password2;
	}
	
	public void setPassword2(String password2) {
		this.password2 = password2;
	}
	
	public Establishment getEstablishment() {
		return this.establishment;
	}
	
	public void setEstablishment(Establishment establishment) {
		this.establishment = establishment;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}


	@Override
	public String getPassword()
	{
		return this.password;
	}

	@JsonIgnore
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities()
	{
		Set<String> roles = this.getRoles();

		if (roles == null) {
			return Collections.emptyList();
		}

		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		for (String role : roles) {
			authorities.add(new SimpleGrantedAuthority(role));
		}

		return authorities;
	}


	@Override
	public String getUsername()
	{
		return this.username;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonExpired()
	{
		return true;
	}


	@JsonIgnore
	@Override
	public boolean isAccountNonLocked()
	{
		return true;
	}


	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired()
	{
		return true;
	}


	@JsonIgnore
	@Override
	public boolean isEnabled()
	{
		return true;
	}

}