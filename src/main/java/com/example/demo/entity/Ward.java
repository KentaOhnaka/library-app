package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "WARD")
public class Ward {

    @Id
    @Column(name = "WARD_CODE")
    private String wardCode;

    @Column(name = "WARD_NAME")
    private String wardName;

    @Column(name = "WARD_COORDINATES")
    private String wardCoordinates;
}