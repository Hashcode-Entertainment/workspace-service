package com.workspaceservice.dto;

import com.workspaceservice.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.annotations.Type;

@Getter
@AllArgsConstructor
public class WorkspaceDTO {
    @Type(type = "com.workspaceservice.user.UserHibernateType")
    private User owner;
    private String template;
}
