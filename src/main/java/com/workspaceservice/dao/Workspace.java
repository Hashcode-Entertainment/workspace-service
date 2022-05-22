package com.workspaceservice.dao;

import com.workspaceservice.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Workspace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Type(type = "com.workspaceservice.user.UserHibernateType")
    private User owner;

    private String template;

    public Workspace(User owner, String template) {
        this.owner = owner;
        this.template = template;
    }
}
