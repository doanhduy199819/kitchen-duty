package io.codeclou.kitchen.duty.webwork;

import com.atlassian.jira.web.action.JiraWebActionSupport;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.webresource.api.assembler.PageBuilderService;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
public class KitchenDutyPlanningWebworkAction extends JiraWebActionSupport {

  private static final Logger log = LoggerFactory.getLogger(KitchenDutyPlanningWebworkAction.class);

  @ComponentImport
  private PageBuilderService pageBuilderService;

  @Override
  public String execute() throws Exception {
    pageBuilderService.assembler().resources().requireWebResource(
        "io.codeclou.kitchen-duty-plugin:kitchen-duty-plugin-resources"
    ).requireWebResource(
        "io.codeclou.kitchen-duty-plugin:kitchen-duty-plugin-resources--planning-page"
    );
    return "kitchen-duty-planning-success";
  }

  public void setPageBuilderService(PageBuilderService pageBuilderService) {
    this.pageBuilderService = pageBuilderService;
  }
}
