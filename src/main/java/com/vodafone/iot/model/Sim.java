package com.vodafone.iot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Sim {
    @Id
    private String simId;
    private String operatorCode;
    private String country;
    private String status;
    private Date createdDate = new Date();
    private Date lastModifiedDate;
}

