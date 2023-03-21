package io.codeclou.kitchen.duty.rest;

import com.atlassian.plugins.rest.common.security.AnonymousAllowed;

import javax.inject.Named;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.springframework.stereotype.Component;

/**
 * A resource of message.
 */
@Component
@Path("/message")
public class UserSearchResource2 {

    @GET
    @AnonymousAllowed
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getMessage()
    {
       return Response.ok(new UserSearchResource2Model("Hello World")).build();
    }
}