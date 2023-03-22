package io.codeclou.kitchen.duty.rest;

import com.atlassian.jira.bc.user.search.UserSearchParams;
import com.atlassian.jira.bc.user.search.UserSearchService;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.plugins.rest.common.security.AnonymousAllowed;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * A resource of message.
 */
@Component
@Path("/user")
public class UserSearchResource2 {

    @GET
    @AnonymousAllowed
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getMessage()
    {
       return Response.ok(new UserSearchResource2Model("Hello World", "")).build();
    }
    @ComponentImport
    private UserSearchService userSearchService;

    @Inject
    public UserSearchResource2(UserSearchService userSearchService) {
        this.userSearchService = userSearchService;
    }


    public UserSearchResource2() {
    }

    @GET
    @Path("/health")
    @Produces({MediaType.APPLICATION_JSON})
    @AnonymousAllowed
    public Response health() {
        return Response.ok(new UserSearchResource2Model("Ok", "")).build();
    }

    /**
     * Call from select2 JS plugin
     * Response needs to look like this:
     * [{ 'id': 1, 'text': 'Demo' }, { 'id': 2, 'text': 'Demo 2'}]
     */
    @GET
    @Path("/search")
    @Produces({MediaType.APPLICATION_JSON})
    public Response searchUsers(@QueryParam("query") final String userQuery,
        @Context HttpServletRequest request ) {
        List<UserSearchResource2Model> users = findUsers(userQuery);
        return Response.ok(users).build();
    }

    private List<UserSearchResource2Model> findUsers(String query) {
        List<UserSearchResource2Model> userSearchResourceModels =
            new ArrayList<UserSearchResource2Model>();
        UserSearchParams searchParams = UserSearchParams.builder()
            .includeActive(true)
            .sorted(true)
            .build();
        List<String> users = userSearchService.findUserNames(query, searchParams);
        if (users != null) {
            for (String user : users) {
                userSearchResourceModels.add(new UserSearchResource2Model(user, user));
            }
        }
        return userSearchResourceModels;
    }
}