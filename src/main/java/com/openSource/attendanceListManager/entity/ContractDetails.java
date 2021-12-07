package com.openSource.attendanceListManager.entity;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;


@Entity
@Setter
@Getter
@Table(name = "contract_details")
public class ContractDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long inspectorId;

    @NotBlank
    private String position;

    @NotBlank
    private String attendanceListType;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "contract_details_id")
    private List<DaysAmount> listDaysAmount;
}
