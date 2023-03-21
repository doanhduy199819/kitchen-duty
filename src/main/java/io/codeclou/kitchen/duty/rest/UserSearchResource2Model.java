package io.codeclou.kitchen.duty.rest;

import javax.inject.Named;
import javax.xml.bind.annotation.*;
import org.springframework.stereotype.Component;

@Component
@XmlRootElement(name = "message")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserSearchResource2Model {

    @XmlElement(name = "value")
    private String message;

    public UserSearchResource2Model() {
    }

    public UserSearchResource2Model(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}