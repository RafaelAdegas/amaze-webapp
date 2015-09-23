package io.redshoes.zoone.web.rest.resources;


import io.redshoes.zoone.admin.eao.user.UserEao;
import io.redshoes.zoone.admin.entity.User;
import io.redshoes.zoone.web.transfer.UserTransfer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;


public class GenericResource<T> {
	
	@Autowired
	private UserEao userDao;
	
	
	public User getCurrentUserAccount() {
		
		UserResource userResource = new UserResource();
		
		if(userResource.getUser() != null) {
			UserTransfer userTransf = userResource.getUser();
			User user = userDao.findByName(userTransf.getName());
			if(user != null) return user;
		}
		
		return null;
	}
	
	public boolean isAdmin()
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Object principal = authentication.getPrincipal();
		if (principal instanceof String && ((String) principal).equals("anonymousUser")) {
			return false;
		}
		UserDetails userDetails = (UserDetails) principal;

		for (GrantedAuthority authority : userDetails.getAuthorities()) {
			if (authority.toString().equals("admin")) {
				return true;
			}
		}

		return false;
	}
	
}
