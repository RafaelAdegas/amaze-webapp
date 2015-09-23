package io.redshoes.zoone.admin.eao.user;

import java.util.List;

import io.redshoes.zoone.admin.eao.GenericEao;
import io.redshoes.zoone.admin.entity.Establishment;
import io.redshoes.zoone.admin.entity.User;

import org.springframework.security.core.userdetails.UserDetailsService;


public interface UserEao extends GenericEao<User, Long>, UserDetailsService
{
	/**
	 * Method used to retrieve all users from current establishment.
	 * @param establishment
	 * @return List of users
	 */
	List<User> findAllFromEstablishment(Establishment establishment, User currentUser);
	
	User findByName(String name);

}