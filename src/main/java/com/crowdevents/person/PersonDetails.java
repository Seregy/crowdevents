package com.crowdevents.person;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;


public class PersonDetails extends User {
    private Long personId;

    /**
     * Constructs new PersonDetails instance.
     *
     * @param username name(email) of the person
     * @param password password of the person
     * @param personId id of the person
     * @param authorities granted authorities of the person
     */
    public PersonDetails(String username, String password, Long personId,
                         Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.personId = personId;
    }

    /**
     * Constructs new PersonDetails instance.
     *
     * @param username name(email) of the person
     * @param password password of the person
     * @param personId id of the person
     * @param enabled whether the person is enabled or not
     * @param accountNonExpired whether the account hasn't expired
     * @param credentialsNonExpired whether the credentials have not expired
     * @param accountNonLocked whether the account isn't locked
     * @param authorities granted authorities of the person
     */
    public PersonDetails(String username, String password, Long personId, boolean enabled,
                         boolean accountNonExpired, boolean credentialsNonExpired,
                         boolean accountNonLocked,
                         Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired,
                accountNonLocked, authorities);
        this.personId = personId;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }
}
