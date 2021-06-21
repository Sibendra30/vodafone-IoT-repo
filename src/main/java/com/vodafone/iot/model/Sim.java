package com.vodafone.iot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Sim {
    @Id
    private String id;
    private String operatorCode;
    private String country;
    private String status;
    private Date createdDate = new Date();
    private Date lastModifiedDate;

}

