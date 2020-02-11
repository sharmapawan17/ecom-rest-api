package com.ecommerce.entity;

import java.io.Serializable;
import java.util.Objects;

public class UserAuthorityId implements Serializable {
    private long userId;
    private long authorityId;

    public UserAuthorityId(long userId, long authorityId) {
        this.userId = userId;
        this.authorityId = authorityId;
    }

    public UserAuthorityId() { }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAuthorityId that = (UserAuthorityId) o;
        return userId == that.userId &&
                authorityId == that.authorityId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, authorityId);
    }
}
