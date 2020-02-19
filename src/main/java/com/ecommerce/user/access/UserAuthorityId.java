package com.ecommerce.user.access;

import java.io.Serializable;
import java.util.Objects;

public class UserAuthorityId implements Serializable {
    private long userId;
    private String authorityName;

    public UserAuthorityId(long userId, String authorityName) {
        this.userId = userId;
        this.authorityName = authorityName;
    }

    public UserAuthorityId() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAuthorityId that = (UserAuthorityId) o;
        return userId == that.userId &&
                authorityName == that.authorityName;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, authorityName);
    }
}
