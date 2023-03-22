package io.codeclou.kitchen.duty.rest;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.jira.bc.user.search.UserSearchService;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.plugins.rest.common.security.AnonymousAllowed;
import com.atlassian.sal.api.transaction.TransactionCallback;
import io.codeclou.kitchen.duty.ao.KitchenDutyActiveObjectHelper;
import io.codeclou.kitchen.duty.ao.User;
import io.codeclou.kitchen.duty.ao.UserToWeek;
import io.codeclou.kitchen.duty.ao.Week;

import javax.inject.Inject;
import javax.inject.Named;
import javax.print.attribute.standard.Media;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import net.java.ao.DBParam;
import net.java.ao.Query;
import net.sf.hibernate.id.IncrementGenerator;

@Named
@Path("/planning")
public class KitchenDutyPlanningResource {

  @ComponentImport
  private ActiveObjects activeObjects;

  @Inject
  public KitchenDutyPlanningResource(ActiveObjects activeObjects) {
    this.activeObjects = activeObjects;
  }

  public KitchenDutyPlanningResource() {
  }


  @GET
  @Path("/persistTest")
  @Produces({MediaType.APPLICATION_JSON})
  @AnonymousAllowed
  public Response persistTest() {
    activeObjects.executeInTransaction(new TransactionCallback<Week>() {
      @Override
      public Week doInTransaction() {
        final Week testWeek = activeObjects.create(Week.class, new DBParam("WEEK", 42));
        testWeek.save();

        final User user = activeObjects.create(
            User.class,
            new DBParam("NAME", "ichiban"));
        user.save();

        final UserToWeek relationship =
            activeObjects.create(UserToWeek.class);
        relationship.setUser(user);
        relationship.setWeek(testWeek);
        relationship.save();

        return testWeek;
      }
    });
    return Response.ok("ok").build();
  }


  @GET
  @Path("/health")
  @Produces({MediaType.APPLICATION_JSON})
  @AnonymousAllowed
  public Response health() {
    return Response.ok("ok").build();
  }

  /**
   * Get the Users assigned to the weekNumber.
   *
   * @param weekNumber
   * @return
   */
  @GET
  @Path("/week/{weekNumber}/users")
  @Produces({MediaType.APPLICATION_JSON})
  @AnonymousAllowed
  public Response getUsersForWeek(@PathParam("weekNumber") final Integer weekNumber) {
    Week[] weeks = activeObjects.executeInTransaction(new TransactionCallback<Week[]>() {
      @Override
      public Week[] doInTransaction() {
        return activeObjects.find(
            Week.class,
            Query.select().where("WEEK = ?", weekNumber));
      }
    });
    List<KitchenDutyPlanningResourceUserModel> users = new ArrayList<>();
    if (weeks != null && weeks.length > 0) {
      for (User user : weeks[0].getUsers()) {
        users.add(
            new KitchenDutyPlanningResourceUserModel(
                user.getID(),
                user.getName()
            )
        );
      }
    }
    return Response.ok(users).build();
  }

  /**
   * Get the Weeks assigned to the User.
   *
   * @param username
   * @return
   */
  @GET
  @Path("/user/{username}/weeks")
  @Produces({MediaType.APPLICATION_JSON})
  @AnonymousAllowed
  public Response getWeeksForUser(@PathParam("username") final String username) {
    User[] users = activeObjects.executeInTransaction(new TransactionCallback<User[]>() {
      @Override
      public User[] doInTransaction() {
        return activeObjects.find(User.class, Query.select().where("NAME = ?", username));
      }
    });
    List<KitchenDutyPlanningResourceWeekModel> weeks = new ArrayList<>();
    if (users != null && users.length > 0) {
      for (Week week : users[0].getWeeks()) {
        weeks.add(new KitchenDutyPlanningResourceWeekModel(week.getID(), week.getWeek()));
      }
    }
    return Response.ok(weeks).build();
  }

  /**
   * Add the User to the Week
   *
   * @param weekNumber
   * @return
   */
  @PUT
  @Path("/week/{weekNumber}/users")
  @Produces({MediaType.APPLICATION_JSON})
  @AnonymousAllowed
  public Response addUserToWeek(@PathParam("weekNumber") final Integer weekNumber,
      final KitchenDutyPlanningResourceUserModel userParam) {
    activeObjects.executeInTransaction(new TransactionCallback<Void>() {
      @Override
      public Void doInTransaction() {
        //
        // WEEK
        //
        Week week = KitchenDutyActiveObjectHelper.findUniqueWeek(
            activeObjects,
            weekNumber
        );
        if (week == null) {
          week = activeObjects.create(
              Week.class,
              new DBParam("WEEK", weekNumber)
          );
          week.save();
        }

        //
        // USER
        //
        User user = KitchenDutyActiveObjectHelper.findUniqueUser(
            activeObjects,
            userParam.getUsername()
        );
        if (user == null) {
          user = activeObjects.create(
              User.class,
              new DBParam("NAME", userParam.getUsername())
          );
          user.save();
        }

        //
        // Establish ManyToMany Relationship
        //
        UserToWeek relationship = KitchenDutyActiveObjectHelper
            .findRelationship(activeObjects, user, week);
        if (relationship != null) {
          // relation already exists
          return null;
        }
        relationship = activeObjects.create(UserToWeek.class);
        relationship.setUser(user);
        relationship.setWeek(week);
        relationship.save();

        return null;
      }
    });
    return Response.ok().build();
  }

  /**
   * Remove the User from Week
   *
   * @param weekNumber
   * @return
   */
  @DELETE
  @Path("/week/{weekNumber}/users")
  @Produces({MediaType.APPLICATION_JSON})
  @AnonymousAllowed
  public Response deleteUserFomWeek(@PathParam("weekNumber") final Integer weekNumber,
      final KitchenDutyPlanningResourceUserModel userParam) {
    activeObjects.executeInTransaction(new TransactionCallback<Void>() {
      @Override
      public Void doInTransaction() {
        //
        // WEEK
        //
        Week week = KitchenDutyActiveObjectHelper.findUniqueWeek(
            activeObjects,
            weekNumber
        );
        if (week == null) {
          // week does not exist => no relation to delete
          return null;
        }

        //
        // USER
        //
        User user = KitchenDutyActiveObjectHelper.findUniqueUser(
            activeObjects,
            userParam.getUsername()
        );
        if (user == null) {
          // user does not exist => no relation to delete
          return null;
        }

        //
        // Delete ManyToMany Relationship
        //
        UserToWeek relationship = KitchenDutyActiveObjectHelper
            .findRelationship(activeObjects, user, week);
        if (relationship != null) {
          activeObjects.delete(relationship);
        }

        return null;
      }
    });
    return Response.ok().build();
  }
}