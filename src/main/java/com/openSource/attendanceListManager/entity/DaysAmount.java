package com.openSource.attendanceListManager.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="days_amount")
public class DaysAmount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int amountOfDaysInMonth;

    private int monthNumber;

    private int year;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "days_amount_id")
    private List<Days> attendanceList;
}
