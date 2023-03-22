package io.codeclou.kitchen.duty.rest;

import javax.inject.Named;
import javax.xml.bind.annotation.*;
import org.springframework.stereotype.Component;

@Component
@XmlRootElement(name = "message")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserSearchResource2Model {

    @XmlElement
    private String text;

    @XmlElement
    private String id;

    public UserSearchResource2Model() {
    }

    public UserSearchResource2Model(String text, String id) {
        this.text = text;
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}