package api.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import api.endpoints.UserEndPoints;
import api.payload.User;
import api.utilities.DataProviders;
import io.restassured.response.Response;

public class UserDDTests {
	
	Logger logger = LogManager.getLogger(this.getClass());			// for generating log
	
	@Test(priority=1, dataProvider="Data", dataProviderClass=DataProviders.class )
	public void testPostuser(String userID, String userName,String fname,String lname,String useremail,String pwd,String ph)
	{												// As per Excel sheet parameter provided to method
		
		User userPayload=new User();				// By using POJO class we send the payload
		
		userPayload.setId(Integer.parseInt(userID));
		userPayload.setUsername(userName);
		userPayload.setFirstName(fname);
		userPayload.setLastName(lname);
		userPayload.setEmail(useremail);
		userPayload.setPassword(pwd);
		userPayload.setPhone(ph);
		
		logger.info("*********** Creating User by using DDT **************");

		Response response=UserEndPoints.createUser(userPayload);
		Assert.assertEquals(response.getStatusCode(),200);
		
		logger.info("*********** User Created by using DDT **************");

	}
	
	@Test(priority=2, dataProvider="UserNames", dataProviderClass=DataProviders.class)
	public void testGetUserByName(String userName)
	{
		logger.info("********** Get User Details by DDT ***************");
		
		Response response=UserEndPoints.readUser(userName);	// Geting user by name not by ID
		
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(),200);
		
		logger.info("********** User Details is Displayed by DDT***************");
	}
	
	
	@Test(priority=3, dataProvider="UserNames", dataProviderClass=DataProviders.class)
	public void testDeleteUserByName(String userName)
	{
		logger.info("********** Deleting User by DDT ***************");

			Response response=UserEndPoints.deleteUser(userName);
			Assert.assertEquals(response.getStatusCode(),200);	
		
		logger.info("********** Deleted User by DDT ***************");

	}
	
	
	
}
