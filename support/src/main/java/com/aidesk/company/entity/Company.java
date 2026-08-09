package com.aidesk.company.entity;

import com.aidesk.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Company extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 200)
    private String name;

    @Column(nullable = false,unique = true,length = 255)
    private String email;

    @Column(nullable = false,length = 500)
    private String status;
}
