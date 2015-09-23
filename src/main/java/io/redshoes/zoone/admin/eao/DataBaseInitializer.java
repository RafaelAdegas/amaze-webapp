package io.redshoes.zoone.admin.eao;


import java.util.ArrayList;
import java.util.List;

import io.redshoes.zoone.admin.eao.beacon.BeaconEao;
import io.redshoes.zoone.admin.eao.establishment.EstablishmentEao;
import io.redshoes.zoone.admin.eao.user.UserEao;
import io.redshoes.zoone.admin.entity.Beacon;
import io.redshoes.zoone.admin.entity.Establishment;
import io.redshoes.zoone.admin.entity.User;

import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * Initialize the database with some test entries.
 * 
 * @author Philip W. Sorst <philip@sorst.net>
 */
public class DataBaseInitializer
{

	private UserEao userDao;

	private PasswordEncoder passwordEncoder;
	
	private EstablishmentEao establishmentDao;

	private BeaconEao beaconDao;


	protected DataBaseInitializer()
	{
		/* Default constructor for reflection instantiation */
	}


	public DataBaseInitializer(UserEao userDao, PasswordEncoder passwordEncoder, EstablishmentEao establishmentDao, BeaconEao beaconDao)
	{
		this.userDao = userDao;
		this.passwordEncoder = passwordEncoder;
		this.establishmentDao = establishmentDao;
		this.beaconDao = beaconDao;
	}


	public void initDataBase()
	{
		boolean hasPreviousAdmin = true;
		boolean hasPreviousEstablishment = true;
		
		User user = new User();
		
		if(this.userDao.findByName("adegas_user") == null) {
			user = new User("adegas_user", this.passwordEncoder.encode("adegas_user"));
			user.addRole("user");
			user.setEmail("email@email.user.com");
			user.setFirstName("Rafael");
			user.setLastName("Pires");
			this.userDao.save(user);
		}

		User admin = new User();
		
		if(this.userDao.findByName("adegas_admin") == null) {
			admin = new User("adegas_admin", this.passwordEncoder.encode("adegas_admin"));
			admin.addRole("admin");
			admin.setEmail("admin@admin.com");
			admin.setFirstName("Rafael");
			admin.setLastName("Adegas");
			this.userDao.save(user);
			hasPreviousAdmin = false;
		}

		Establishment establishment = establishmentDao.findByFullName("New Store LTDA");
		
		if(establishment == null) {
			establishment = new Establishment();
			establishment.setCode("E_CODE_001");
			establishment.setFullName("New Store LTDA");
			establishment.setPhone("(19) 3897-4283");
			establishment.setAddress("Av Dr Moraes Sales");
			establishment.setCep("13185-157");
			establishment.setCity("Campinas");
			establishment.setCnpj("2100150/500-01");
			establishment.setComplement("Nova Campinas");
			establishment.setShortName("New Store");
			establishment.setState("SP");

			establishment = this.establishmentDao.save(establishment);
			hasPreviousEstablishment = false;
		}
		
		List<Beacon> listBeacon = new ArrayList<Beacon>();
		
		Beacon beacon = new Beacon();
		beacon.setEstablishment(establishment);
		
		if(this.beaconDao.findByFilter(beacon).isEmpty()) {
			for(int i=0;i<=5;i++) {
				beacon.setDescription("Esta é a descrição de um Beacon "+i);
				beacon.setLocale("Localização do Beacon "+i);
				beacon.setUuid("1237846 8712634871 2736489172368 ("+i+")");
				beacon.setMajor("18723468 ("+i+")");
				beacon.setMinor("98723 ("+i+")");
				beacon.setEstablishment(establishment);
				
				listBeacon.add(beaconDao.save(beacon));
			}
		}
		
		if(!hasPreviousAdmin && !hasPreviousEstablishment) {
			admin.setEstablishment(establishment);
			admin = this.userDao.save(admin);
			user.setEstablishment(establishment);
		}
	}

}