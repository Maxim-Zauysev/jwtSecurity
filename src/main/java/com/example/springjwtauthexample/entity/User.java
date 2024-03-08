package com.example.springjwtauthexample.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user_users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;

    @ElementCollection
    @CollectionTable(name = "user_emails", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "email")
    private Set<String> emails;

    @ElementCollection
    @CollectionTable(name = "user_phones", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "phone")
    private Set<String> phones;

    @Temporal(TemporalType.DATE)
    private Date birthDate;

    @ElementCollection(targetClass = RoleType.class,fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "roles", nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Set<RoleType> roles = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bankAccount_id", referencedColumnName = "id")
    private BankAccount bankAccount;

    public void addEmail(String email){
        emails.add(email);
    };

    public void addPhone(String phone){
        phones.add(phone);
    }

    public void deletePhone(String phone){
        phones.remove(phone);
    }

    public void deleteEmail(String email){
        emails.remove(email);
    }
}
