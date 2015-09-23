package io.redshoes.zoone.web.rest.resources;

import io.redshoes.zoone.admin.JsonViews;
import io.redshoes.zoone.admin.eao.establishment.EstablishmentEao;
import io.redshoes.zoone.admin.eao.user.UserEao;
import io.redshoes.zoone.admin.entity.Establishment;
import io.redshoes.zoone.admin.entity.User;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Path("/establishments")
public class EstablishmentResource extends GenericResource<Establishment>
{
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private EstablishmentEao establishmentDao;
	
	@Autowired
	private UserEao userDao;
	
	@Autowired
	private ObjectMapper mapper;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String list() throws JsonGenerationException, JsonMappingException, IOException 
	{
		this.logger.info("list()");
		
		ObjectWriter viewWriter;
		if (this.isAdmin()) {
			viewWriter = this.mapper.writerWithView(JsonViews.Admin.class);
		} else {
			viewWriter = this.mapper.writerWithView(JsonViews.User.class);
		}
		
		List<Establishment> allEstablishments = this.establishmentDao.findAll();
		
		if(allEstablishments != null) {
			return viewWriter.writeValueAsString(allEstablishments.get(0));
		}
		
		return null;
	}
	

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{userName}")
	public Establishment read(@PathParam("userName") String userName)
	{
		this.logger.info("read(userName)");

		Establishment establishment = this.establishmentDao.findByUsername(userName);
		if (establishment == null) {
			throw new WebApplicationException(404);
		}
		return establishment;
			
	}
	
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Establishment create(Establishment establishment)
	{
		this.logger.info("create(): " + establishment);
		
		establishment = this.establishmentDao.save(establishment);
		
		return establishment;
	}


	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{idEstablishment}")
	public Establishment update(@PathParam("idEstablishment") Long id, Establishment establishment)
	{
		this.logger.info("update(): " + establishment);

		return this.establishmentDao.save(establishment);
	}


	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{idEstablishment}")
	public void delete(@PathParam("idEstablishment") Long id)
	{
		this.logger.info("delete(id)");

		this.establishmentDao.delete(id);
	}
	
	/*
	private boolean isAdmin()
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
	}*/
}