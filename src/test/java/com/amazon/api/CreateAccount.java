package com.amazon.api;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.force.api.Account;
import com.force.api.ApiConfig;
import com.force.api.ApiVersion;
import com.force.api.Fixture;
import com.force.api.ForceApi;
import com.force.api.QueryResult;

public class CreateAccount {

	private ForceApi api2 ;
	private Account a;
	private String id = "";

	@BeforeClass
	public void setup()
	{
		ApiConfig mycfg = new ApiConfig().setApiVersion(ApiVersion.V42);

		/*   OAuth Username/Password Authentication Flow   */

		api2 = new ForceApi(mycfg
				.setUsername(Fixture.get("username"))
				.setPassword(Fixture.get("password"))
				.setClientId(Fixture.get("clientId"))
				.setClientSecret(Fixture.get("clientSecret")));
	}


	@Test(description="To create the SObject",priority=1)
	public void createSObjecttest()
	{
		//Create SObject
		a = new Account();
		a.setName("Test account 2");
		id = api2.createSObject("account", a);
		System.out.println(id);
		//api2.deleteSObject("account", id);
	}
	
	@Test(description="To update the SObject",priority=2)
	public void updateSObject()
	{
		//Update SObject
		a.setName("Updated Test Account");
		api2.updateSObject("account", id, a);
	}
	
	
	@Test(description="To create and update external field value with SObject",priority=3)
	public void createNupdateSObject()
	{
		//Create or Update SObject
		a = new Account();
		a.setName("Perhaps existing account");
		a.setAnnualRevenue(31.65);
		api2.createOrUpdateSObject("account", "externalId__c", "165", a);
	}
	
	@Test(description="To delete a SObject",priority=4)
	public void deleteSObject()
	{
		//Delete an SObject
		api2.deleteSObject("account", id);
		//api2.deleteSObject("account","0010K0000280InT");
	}
	
	@Test(description="To Query a SObject",priority=5)
	public void QuerySObject()
	{
		//Query SObjects
		QueryResult<Account> res = api2.query("SELECT id FROM Account WHERE name LIKE 'Perhaps existing account%'", Account.class);
		System.out.println(res.getTotalSize());
	}
	

}
