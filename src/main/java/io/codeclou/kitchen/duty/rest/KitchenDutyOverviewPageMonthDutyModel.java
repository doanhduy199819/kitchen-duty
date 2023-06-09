package io.codeclou.kitchen.duty.rest;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.sal.api.transaction.TransactionCallback;
import io.codeclou.kitchen.duty.ao.KitchenDutyActiveObjectHelper;
import io.codeclou.kitchen.duty.ao.User;
import io.codeclou.kitchen.duty.ao.UserToWeek;
import io.codeclou.kitchen.duty.ao.Week;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import net.java.ao.Query;

/*
 * We want this entity to be directly usable as full calendar event object
 * https://fullcalendar.io/docs/event-object
 */
@XmlRootElement(name = "duty")
@XmlAccessorType(XmlAccessType.FIELD)
public class KitchenDutyOverviewPageMonthDutyModel {

  @XmlElement
  private Long week;

  @XmlElement
  private String start; /* Date String - First day of week (Sunday) */
  @XmlElement
  private String end; /* Date String - Last day of week (Monday) */

  @XmlElement
  private List<String> users;

  public KitchenDutyOverviewPageMonthDutyModel(Long week, String start, String end, List<String> users) {
    this.start = start;
    this.end = end;
    this.week = week;
    this.users = users;
  }

  public Long getWeek() {
    return week;
  }

  public void setWeek(Long week) {
    this.week = week;
  }

  public List<String> getUsers() {
    return users;
  }

  public void setUsers(List<String> users) {
    this.users = users;
  }

  public String getStart() {
    return start;
  }

  public void setStart(String start) {
    this.start = start;
  }

  public String getEnd() {
    return end;
  }

  public void setEnd(String end) {
    this.end = end;
  }

  //
  // TRANSACTIONAL
  //

  public static Week getWeekByWeekNumberInTransaction(ActiveObjects activeObjects, Long weekNumber) {
    return activeObjects.executeInTransaction(new TransactionCallback<Week>() {
      @Override
      public Week doInTransaction() {
        Week[] weeks = activeObjects.find(Week.class, Query.select().where("WEEK = ?", weekNumber));
        if (weeks != null && weeks.length > 0) {
          return weeks[0];
        }
        return null;
      }
    });
  }

  public static List<User> getUsersAssignedToWeekInTransaction(ActiveObjects activeObjects, Week week) {
    List<User> users = new ArrayList<>();
    if (week != null) {
      UserToWeek[] relationships = activeObjects.executeInTransaction(new TransactionCallback<UserToWeek[]>() {
        @Override
        public UserToWeek[] doInTransaction() {
          return KitchenDutyActiveObjectHelper.findAllRelationships(activeObjects, week);
        }
      });
      if (relationships != null) {
        for (UserToWeek userToWeek : relationships) {
          users.add(userToWeek.getUser());
        }
      }
    }
    return users;
  }
}