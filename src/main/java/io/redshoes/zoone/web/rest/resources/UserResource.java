package io.redshoes.zoone.web.rest.resources;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import io.redshoes.zoone.admin.eao.establishment.EstablishmentEao;
import io.redshoes.zoone.admin.eao.user.UserEao;
import io.redshoes.zoone.admin.entity.Establishment;
import io.redshoes.zoone.admin.entity.User;
import io.redshoes.zoone.web.rest.TokenUtils;
import io.redshoes.zoone.web.transfer.TokenTransfer;
import io.redshoes.zoone.web.transfer.UserTransfer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
@Path("/user")
public class UserResource extends GenericResource<User>
{

	@Autowired
	private UserDetailsService userDetailService;
	
	@Autowired
	private UserEao userDao;
	
	@Autowired
	private EstablishmentEao establishmentEao;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	@Qualifier("authenticationManager")
	private AuthenticationManager authManager;


	/**
	 * Retrieves the currently logged in user.
	 * 
	 * @return A transfer containing the username and the roles.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public UserTransfer getUser()
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Object principal = authentication.getPrincipal();
		if (principal instanceof String && ((String) principal).equals("anonymousUser")) {
			throw new WebApplicationException(401);
		}
		UserDetails userDetails = (UserDetails) principal;
		
		Establishment establishment = this.establishmentEao.findByUsername(userDetails.getUsername());

		return new UserTransfer(userDetails.getUsername(), establishment, this.createRoleMap(userDetails));
	}


	/**
	 * Authenticates a user and creates an authentication token.
	 * 
	 * @param username
	 *            The name of the user.
	 * @param password
	 *            The password of the user.
	 * @return A transfer containing the authentication token.
	 */
	@Path("authenticate")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Object authenticate(@FormParam("username") String username, @FormParam("password") String password)
	{
		UsernamePasswordAuthenticationToken authenticationToken =
				new UsernamePasswordAuthenticationToken(username, password);
		
		try {
			Authentication authentication = this.authManager.authenticate(authenticationToken);
			
			SecurityContextHolder.getContext().setAuthentication(authentication);
			/*
			 * Reload user as password of authentication principal will be null after authorization and
			 * password is needed for token generation
			 */
			UserDetails userDetails = this.userDetailService.loadUserByUsername(username);
	
			return new TokenTransfer(TokenUtils.createToken(userDetails));
		} catch (BadCredentialsException e) {
			return new TokenTransfer("status", "BAD_CREDENTIALS_EXCEPTION");
		}
	}


	private Map<String, Boolean> createRoleMap(UserDetails userDetails)
	{
		Map<String, Boolean> roles = new HashMap<String, Boolean>();
		for (GrantedAuthority authority : userDetails.getAuthorities()) {
			roles.put(authority.getAuthority(), Boolean.TRUE);
		}

		return roles;
	}

}