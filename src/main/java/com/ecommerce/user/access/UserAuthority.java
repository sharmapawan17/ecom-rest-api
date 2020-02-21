package com.ecommerce.user.access;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_authority")
@IdClass(UserAuthorityId.class)
public class UserAuthority implements Serializable {

    @Id
    @Column(name = "user_id")
    private long userId;
    @Id
    @Column(name = "authority_name")
    private String authorityName;

    public UserAuthority(long userId, String authorityName) {
        this.userId = userId;
        this.authorityName = authorityName;
    }

    public UserAuthority() {
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getAuthorityName() {
        return authorityName;
    }

    public void setAuthorityName(String authorityName) {
        this.authorityName = authorityName;
    }
}
