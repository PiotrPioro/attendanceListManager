package com.openSource.attendanceListManager.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "contracts")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    private String name;

    @NotBlank
    private String typeOfService;

    @NotBlank
    private String description;

    @ManyToOne
    private Inspector contractAdministrator;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "contractList")
    private List<Inspector> inspectorList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.MERGE)
    @JoinColumn(name = "contract_id")
    private List<ContractDetails> contractDetails = new ArrayList<>();
}
