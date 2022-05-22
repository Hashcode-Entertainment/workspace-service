package com.workspaceservice.user;

import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractTypeDescriptor;
import org.hibernate.type.descriptor.java.ImmutableMutabilityPlan;

public class UserDescriptor extends AbstractTypeDescriptor<User> {
    public static final UserDescriptor INSTANCE = new UserDescriptor();

    protected UserDescriptor() {
        super(User.class, ImmutableMutabilityPlan.INSTANCE);
    }

    @Override
    public String toString(User value) {
        return super.toString(value);
    }

    @Override
    public User fromString(String string) {
        return null;
    }

    @Override
    public <X> X unwrap(User value, Class<X> type, WrapperOptions options) {
        if(value == null) {
            return null;
        }
        if(String.class.isAssignableFrom(type)){
            return (X) value.id();
        }
        throw unknownUnwrap(type);
    }

    @Override
    public <X> User wrap(X value, WrapperOptions options) {
        if (value == null)
            return null;

        if(String.class.isInstance(value))
            return new User((String) value);

        throw unknownWrap(value.getClass());
    }
}
