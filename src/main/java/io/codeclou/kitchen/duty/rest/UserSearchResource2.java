package io.codeclou.kitchen.duty.rest;

import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.plugins.rest.common.security.AnonymousAllowed;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * A resource of message.
 */
@Component
@Path("/message")
public class UserSearchResource2 {

    private final JiraAuthenticationContext authContext;

    @Autowired
    public UserSearchResource2(@ComponentImport JiraAuthenticationContext authContext) {
        this.authContext = authContext;
    }

    @GET
    @AnonymousAllowed
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getMessage()
    {
        authContext.getLoggedInUser();
       return Response.ok(new UserSearchResource2Model("Hello World")).build();
    }
}