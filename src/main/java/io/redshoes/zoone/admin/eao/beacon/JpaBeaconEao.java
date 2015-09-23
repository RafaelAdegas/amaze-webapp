package io.redshoes.zoone.admin.eao.beacon;

import io.redshoes.zoone.admin.eao.GenericJpaEao;
import io.redshoes.zoone.admin.entity.Beacon;


/**
 * JPA Implementation of a {@link BeaconEao}.
 * 
 * @author Rafael Adegas <adegasp.rafael@gmail.com>
 */
public class JpaBeaconEao extends GenericJpaEao<Beacon, Long> implements BeaconEao
{

	public JpaBeaconEao()
	{
		super(Beacon.class);
	}

}
