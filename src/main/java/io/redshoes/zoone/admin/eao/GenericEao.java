package io.redshoes.zoone.admin.eao;

import java.util.List;


public interface GenericEao<T, I>
{

	List<T> findAll();


	T find(I id);


	T save(T entity);


	void delete(I id);
	
	List<T> findByFilter(T entity);

}