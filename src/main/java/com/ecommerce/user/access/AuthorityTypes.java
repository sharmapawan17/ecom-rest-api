package com.ecommerce.user.access;

import javax.persistence.*;

@Entity
@Table(name = "authority_types", uniqueConstraints = {
        @UniqueConstraint(columnNames = "name", name = "uniqueAuthorityNameConstraint")
})
public class AuthorityTypes {
    private long id;
    private String name;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
