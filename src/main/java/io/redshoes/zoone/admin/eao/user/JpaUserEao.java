package io.redshoes.zoone.admin.eao.user;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import io.redshoes.zoone.admin.eao.GenericJpaEao;
import io.redshoes.zoone.admin.entity.Establishment;
import io.redshoes.zoone.admin.entity.User;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;


public class JpaUserEao extends GenericJpaEao<User, Long> implements UserEao
{

	public JpaUserEao()
	{
		super(User.class);
	}


	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
	{
		User user = this.findByName(username);
		if (null == user) {
			throw new UsernameNotFoundException("The user with name " + username + " was not found");
		}

		return user;
	}


	@Override
	@Transactional(readOnly = true)
	public User findByName(String name)
	{
		final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<User> criteriaQuery = builder.createQuery(this.entityClass);

		Root<User> root = criteriaQuery.from(this.entityClass);
		Path<String> namePath = root.get("username");
		criteriaQuery.where(builder.equal(namePath, name));

		TypedQuery<User> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
		List<User> users = typedQuery.getResultList();

		if (users.isEmpty()) {
			return null;
		}

		return users.get(0);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<User> findAllFromEstablishment(Establishment establishment, User currentUser) {
		
		Query query = this.getEntityManager().createQuery("select u from User u where u.establishment = :establishment and :role not member of u.roles and u.username != :currentUser")
					.setParameter("establishment", establishment)
					.setParameter("role", new String("admin"))
					.setParameter("currentUser", new String(currentUser.getUsername()));

		return (List<User>)query.getResultList();
	}

}
