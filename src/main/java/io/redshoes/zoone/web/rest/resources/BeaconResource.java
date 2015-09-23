package io.redshoes.zoone.web.rest.resources;

import io.redshoes.zoone.admin.JsonViews;
import io.redshoes.zoone.admin.eao.beacon.BeaconEao;
import io.redshoes.zoone.admin.eao.establishment.EstablishmentEao;
import io.redshoes.zoone.admin.eao.user.UserEao;
import io.redshoes.zoone.admin.entity.Beacon;
import io.redshoes.zoone.admin.entity.Establishment;
import io.redshoes.zoone.admin.entity.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
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
@Path("/beacons")
public class BeaconResource extends GenericResource<Beacon>
{
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private BeaconEao beaconDao;
	
	@Autowired
	private UserEao userDao;

	@Autowired
	private EstablishmentEao establishmentEao;
	
	@Autowired
	private ObjectMapper mapper;
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Beacon> list() throws JsonGenerationException, JsonMappingException, IOException 
	{
		this.logger.info("list()");
		
		List<Beacon> allBeacons = this.beaconDao.findAll();
		
		return allBeacons;
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Beacon create(Beacon beacon)
	{
		this.logger.info("create(): " + beacon);
		
		this.beaconDao.save(beacon);
		
		return beacon;
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{idBeacon}")
	public Beacon update(@PathParam("idBeacon") Long id, Beacon beacon)
	{
		this.logger.info("update(): " + beacon);

		return this.beaconDao.save(beacon);
	}
}