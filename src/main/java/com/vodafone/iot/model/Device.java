package com.vodafone.iot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import javax.persistence.OneToOne;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Device {
    @Id
    private String id;
    private String name;
    private String description;
    private String status = "NOT_READY";

    @OneToOne()
    private Sim sim;
    private Date createdDate = new Date();
    private Date lastModifiedDate;

}
