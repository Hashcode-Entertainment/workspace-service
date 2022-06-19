package com.workspaceservice.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
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
    private UUID id;
    private String owner;
    private UUID template;
    private String url;

    @OneToOne(cascade = CascadeType.ALL)
    private UpdateHookDAO updateHook;
}
