package io.redshoes.zoone.web.transfer;

import io.redshoes.zoone.admin.entity.Establishment;

import java.util.Map;


public class UserTransfer
{

	private final String name;

	private final Map<String, Boolean> roles;
	
	private final Establishment establishment;

	public UserTransfer(String userName, Establishment establishment, Map<String, Boolean> roles)
	{
		this.name = userName;
		this.establishment = establishment;
		this.roles = roles;
	}

	public String getName()
	{
		return this.name;
	}

	public Establishment getEstablishment()
	{
		return this.establishment;
	}

	public Map<String, Boolean> getRoles()
	{
		return this.roles;
	}

}