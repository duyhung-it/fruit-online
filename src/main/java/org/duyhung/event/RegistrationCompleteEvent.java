package org.duyhung.event;

import lombok.Getter;
import lombok.Setter;
import org.duyhung.entity.User;
import org.springframework.context.ApplicationEvent;
@Getter
@Setter
public class RegistrationCompleteEvent extends ApplicationEvent {
    private User user;
    private String url;
    public RegistrationCompleteEvent(User user,String url) {
        super(user);
        this.user = user;
        this.url = url;
    }
}