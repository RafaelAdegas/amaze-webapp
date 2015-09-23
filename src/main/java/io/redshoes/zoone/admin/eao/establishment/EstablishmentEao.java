package io.redshoes.zoone.admin.eao.establishment;

import io.redshoes.zoone.admin.eao.GenericEao;
import io.redshoes.zoone.admin.entity.Establishment;


/**
 * Definition of a Data Access Object that can perform CRUD Operations for {@link NewsEntry}s.
 * 
 * @author Philip W. Sorst <philip@sorst.net>
 */
public interface EstablishmentEao extends GenericEao<Establishment, Long>
{
	Establishment findByFullName(String fullName);

	Establishment findByUsername(String userName);
}