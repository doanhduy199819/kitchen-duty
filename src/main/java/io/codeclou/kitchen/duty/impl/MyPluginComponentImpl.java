//package io.codeclou.kitchen.duty.impl;
//
//import com.atlassian.jira.bc.user.search.UserSearchService;
//import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
//import com.atlassian.sal.api.ApplicationProperties;
//import com.atlassian.webresource.api.assembler.PageBuilderService;
//import io.codeclou.kitchen.duty.api.MyPluginComponent;
//
//import javax.inject.Named;
//import org.springframework.stereotype.Component;
//
//@Component()
//public class MyPluginComponentImpl implements MyPluginComponent {
//    @ComponentImport
//    private final ApplicationProperties applicationProperties;
//
//    @ComponentImport
//    private UserSearchService userSearchService;
//
//    @ComponentImport
//    private PageBuilderService pageBuilderService;
//
//    public MyPluginComponentImpl(final @ComponentImport ApplicationProperties applicationProperties) {
//        this.applicationProperties = applicationProperties;
//    }
//
//    public String getName() {
//        if (null != applicationProperties) {
//            return "myComponent:" + applicationProperties.getDisplayName();
//        }
//
//        return "myComponent";
//    }
//}