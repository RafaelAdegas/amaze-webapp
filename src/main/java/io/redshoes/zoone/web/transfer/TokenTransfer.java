package io.redshoes.zoone.web.transfer;

public class TokenTransfer
{

	private final String token;
	private String status;


	public TokenTransfer(String token)
	{
		this.token = token;
	}
	public TokenTransfer(String token, String status)
	{
		this.token = token;
		this.status = status;
	}

	public String getToken()
	{
		return this.token;
	}
	
	public String getStatus()
	{
		return this.status;
	}

}