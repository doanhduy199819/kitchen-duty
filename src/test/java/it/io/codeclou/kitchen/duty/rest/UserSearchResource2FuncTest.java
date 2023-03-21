//package it.io.codeclou.kitchen.duty.rest;
//
//import org.junit.Test;
//import org.junit.After;
//import org.junit.Before;
//import org.mockito.Mockito;
//import static org.junit.Assert.*;
//import static org.mockito.Mockito.*;
//import io.codeclou.kitchen.duty.rest.UserSearchResource2;
//import io.codeclou.kitchen.duty.rest.UserSearchResource2Model;
//import org.apache.wink.client.Resource;
//import org.apache.wink.client.RestClient;
//
//public class UserSearchResource2FuncTest {
//
//    @Before
//    public void setup() {
//
//    }
//
//    @After
//    public void tearDown() {
//
//    }
//
//    @Test
//    public void messageIsValid() {
//
//        String baseUrl = System.getProperty("baseurl");
//        String resourceUrl = baseUrl + "/rest/kitchenduty/1.0/message";
//
//        RestClient client = new RestClient();
//        Resource resource = client.resource(resourceUrl);
//
//        UserSearchResource2Model message = resource.get(UserSearchResource2Model.class);
//
//        assertEquals("wrong message","Hello World",message.getMessage());
//    }
//}
