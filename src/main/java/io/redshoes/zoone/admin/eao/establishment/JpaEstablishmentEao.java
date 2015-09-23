package io.redshoes.zoone.admin.eao.establishment;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import io.redshoes.zoone.admin.eao.GenericJpaEao;
import io.redshoes.zoone.admin.entity.Beacon;
import io.redshoes.zoone.admin.entity.Establishment;
import io.redshoes.zoone.admin.entity.User;


/**
 * JPA Implementation of a {@link EstablishmentEao}.
 * 
 * @author Rafael Adegas <adegasp.rafael@gmail.com>
 */
public class JpaEstablishmentEao extends GenericJpaEao<Establishment, Long> implements EstablishmentEao
{

	public JpaEstablishmentEao()
	{
		super(Establishment.class);
	}

	@Override
	public Establishment findByFullName(String fullName) {
		final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Establishment> criteriaQuery = builder.createQuery(this.entityClass);

		Root<Establishment> root = criteriaQuery.from(this.entityClass);
		Path<String> namePath = root.get("fullName");
		criteriaQuery.where(builder.equal(namePath, fullName));

		TypedQuery<Establishment> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
		List<Establishment> establishments = typedQuery.getResultList();

		if (establishments.isEmpty()) {
			return null;
		}

		return establishments.iterator().next();
	}

	@Override
	public Establishment findByUsername(String userName) {
		
		String queryString = "select e.* from establishments e join users u on u.id_establishment = e.id_establishment where u.s_username = ?";
		Query query = super.getEntityManager().createNativeQuery(queryString, Establishment.class);
		query.setParameter(1, userName);

		Establishment result = null;
		try {  
            result = (Establishment) query.getSingleResult();
        } catch (NoResultException e) {  
            // nada a fazer  
        }
		return result;
	}

}
