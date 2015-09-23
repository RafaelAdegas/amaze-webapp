package io.redshoes.zoone.web.rest.resources;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.redshoes.zoone.admin.eao.establishment.EstablishmentEao;
import io.redshoes.zoone.admin.eao.user.UserEao;
import io.redshoes.zoone.admin.entity.Beacon;
import io.redshoes.zoone.admin.entity.Establishment;
import io.redshoes.zoone.admin.entity.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@Path("/users")
public class UsersResource extends GenericResource<User>
{
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserEao userDao;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private EstablishmentEao establishmentEao;

	/**
	 * Retrieves the currently logged in user.
	 * 
	 * @return A transfer containing the username and the roles.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> list() 
	{
		List<User> users = this.userDao.findAll();
		
		return users;
	}

	
	/**
	 * validate user
	 */
	@Path("validate")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public boolean validate(@FormParam("username") String username, @FormParam("password") String password) {
		User user = new User();
		user = this.userDao.findByName(username);
		
		if(user == null) {
			return false;
		}
		
		if(user.getPassword() != this.passwordEncoder.encode(password)) {
			return false;
		}
		
		return true;
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public User create(User user)
	{
		this.logger.info("create(): " + user);
		user.addRole("admin");
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		
		return this.userDao.save(user);
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{idUser}")
	public User update(@PathParam("idUser") Long id, User user)
	{
		this.logger.info("update(): " + user);
		if(user.getRoles().isEmpty()) {
			user.addRole("admin");
		}
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));

		return this.userDao.save(user);
	}

}