package com.openSource.attendanceListManager.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name="days")
public class Days {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Integer monthDay;

    private Integer weekDay;

    private Integer color = 0;

    public Days(Integer weekDay){
        this.weekDay = weekDay;
    }
}
