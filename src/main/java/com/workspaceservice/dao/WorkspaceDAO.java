package com.workspaceservice.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class WorkspaceDAO {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;
//    @Type(type = "com.workspaceservice.user.UserHibernateType")
    private String owner;
    private UUID template;
    private String url;
    @OneToOne
    private UpdateHookDAO updateHook;
}
