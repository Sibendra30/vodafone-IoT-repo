package com.vodafone.iot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Device {
    @javax.persistence.Id
    private String id;
    private String name;
    private String description;
    private String status = "NOT_READY";

    @OneToOne
    @JoinColumn(name = "simId")
    private Sim sim;
    private Date createdDate = new Date();
    private Date lastModifiedDate;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
