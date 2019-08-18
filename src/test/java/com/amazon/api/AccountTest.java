package com.amazon.api;

import java.util.List;
import java.util.Map;
import org.testng.annotations.Test;
import com.force.api.Account;
import com.force.api.ApiConfig;
import com.force.api.ApiVersion;
import com.force.api.Fixture;
import com.force.api.ForceApi;
import com.force.api.QueryResult;

public class AccountTest {

	//ApiConfig mycfg = new ApiConfig().setApiVersionString("v99.0");

	/*   Username / Password Authentication   */

	/*ForceApi api1 = new ForceApi(new ApiConfig()
		    .setUsername("user@domain.com")
		    .setPassword("password"));*/

	/*   OAuth Web Server Flow   */

	/*String url = Auth.startOAuthWebServerFlow(new AuthorizationRequest()
			.apiConfig(new ApiConfig()
				.setClientId("longclientidalphanumstring")
				.setRedirectURI("https://myapp.mydomain.com/oauth"))
			.state("mystate"));

		// redirect browser to url
		// Browser will get redirected back to your app after user authentication at
		// https://myapp.mydomain.com/oauth with a code parameter. Now do:

		ApiSession s = Auth.completeOAuthWebServerFlow(new AuthorizationResponse()
			.apiConfig(new ApiConfig()
				.setClientId("longclientidalphanumstring")
				.setClientSecret("notsolongnumeric")
				.setRedirectURI("https://myapp.mydomain.com/oauth"))
			.code("alphanumericstringpassedbackinbrowserrequest"));

		ForceApi api = new ForceApi(s.getApiConfig(),s);*/


	/*    instantiate with existing accessToken and endpoint   */

	/*ApiConfig c = new ApiConfig()
		    .setRefreshToken("refreshtoken")
		    .setClientId("longclientidalphanumstring")
		    .setClientSecret("notsolongnumeric"),

		ApiSession s = new ApiSession()
		    .setAccessToken("accessToken")
		    .setApiEndpoint("apiEndpoint");

		ForceApi api = new ForceApi(c,s);*/

	@Test
	public void test()
	{
		ApiConfig mycfg = new ApiConfig().setApiVersion(ApiVersion.V38);

		/*   OAuth Username/Password Authentication Flow   */

		ForceApi api2 = new ForceApi(mycfg
				.setUsername(Fixture.get("username"))
				.setPassword(Fixture.get("password"))
				.setClientId(Fixture.get("clientId"))
				.setClientSecret(Fixture.get("clientSecret")));
		
		Account a = new Account();
		a.setName("Test account ");
		String id = api2.createSObject("account", a);
		System.out.println(id);
		
		
		QueryResult<Account> res2 = api2.query("SELECT id FROM Account where id='"+id+"'", Account.class);
		System.out.println(" Number of account created "+res2.getTotalSize());
		

		api2.deleteSObject("account", id);
		
		QueryResult<Account> res3 = api2.query("SELECT id FROM Account where id='"+id+"'", Account.class);
		System.out.println(res3.getTotalSize());
		
		List<Map> result = api2.query("SELECT name FROM Account").getRecords();
        System.out.println("RESULT : "+result.size());
        System.out.println(result);

	}

}
