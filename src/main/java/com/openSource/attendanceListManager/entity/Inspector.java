package com.openSource.attendanceListManager.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.context.annotation.SessionScope;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "inspectors")
@Setter
@Getter
@NoArgsConstructor
@SessionScope
public class Inspector {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Email
    @NotBlank
    @Column(unique = true)
    private String email;

    @Digits(integer = 9, fraction = 0)
    private int phoneNumber;

    @NotBlank
    private String password;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private List<Contract> contractList = new ArrayList<>();

    /*@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Set<Role> role = new HashSet<>();*/
    private String role;

    public String getFullName(){
        return firstName + " " + lastName;
    }
}
