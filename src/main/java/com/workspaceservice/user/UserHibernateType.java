package com.workspaceservice.user;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.AbstractSingleColumnStandardBasicType;

import org.hibernate.type.descriptor.sql.VarcharTypeDescriptor;

public class UserHibernateType extends AbstractSingleColumnStandardBasicType<User> {
    public static final UserHibernateType INSTANCE = new UserHibernateType();

    public UserHibernateType() {
        super(VarcharTypeDescriptor.INSTANCE, UserDescriptor.INSTANCE);
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Object resolve(Object value, SharedSessionContractImplementor session, Object owner, Boolean overridingEager) throws HibernateException {
        return super.resolve(value, session, owner, overridingEager);
    }
}
