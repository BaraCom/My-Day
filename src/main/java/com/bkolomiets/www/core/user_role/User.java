package com.bkolomiets.www.core.user_role;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.Set;

/**
 * @author Borislav Kolomiets
 */
@Entity
@Getter
@Setter
@Table(name = "usr")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY/*, generator = "user"*/)
//    @SequenceGenerator(name="user", sequenceName="user_id_seq")
    private Long id;

    @Column(name = "user_name")
    private String username;

    @Column(name = "password")
    private String password;
    private boolean active;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.LAZY)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;
}
