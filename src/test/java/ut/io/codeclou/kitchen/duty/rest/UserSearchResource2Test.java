//package ut.io.codeclou.kitchen.duty.rest;
//
//import org.junit.Test;
//import org.junit.After;
//import org.junit.Before;
//import org.mockito.Mockito;
//import static org.junit.Assert.*;
//import static org.mockito.Mockito.*;
//import io.codeclou.kitchen.duty.rest.UserSearchResource2;
//import io.codeclou.kitchen.duty.rest.UserSearchResource2Model;
//import javax.ws.rs.core.Response;
//import javax.ws.rs.core.GenericEntity;
//
//public class UserSearchResource2Test {
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
//        UserSearchResource2 resource = new UserSearchResource2();
//
//        Response response = resource.getMessage();
//        final UserSearchResource2Model message = (UserSearchResource2Model) response.getEntity();
//
//        assertEquals("wrong message","Hello World",message.getMessage());
//    }
//}
