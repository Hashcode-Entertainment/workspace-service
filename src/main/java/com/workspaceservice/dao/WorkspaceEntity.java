package com.workspaceservice.dao;

import com.workspaceservice.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class WorkspaceEntity {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Type(type = "com.workspaceservice.user.UserHibernateType")
    private User owner;

    private String template;

    public WorkspaceEntity(User owner, String template) {
        this.id = UUID.randomUUID();
        this.owner = owner;
        this.template = template;
    }
}
