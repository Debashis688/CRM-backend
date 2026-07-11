package com.example.crm.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@Table(name = "customer_lead")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CustomerLead {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @Column(
        name = "customer_name",
        nullable = false,
        length = 100
    )
    private String customerName;



    @Column(
        nullable = false,
        length = 15
    )
    private String mobile;



    @Column(
        name = "alternate_number",
        length = 15
    )
    private String alternateNumber;



    @Column(length = 100)
    private String email;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lead_type_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private LeadType leadType;



    @Column(length = 50)
    private String city;



    @Column(columnDefinition = "TEXT")
    private String address;



    @Column(columnDefinition = "TEXT")
    private String requirement;



    @Column(
        name = "lead_source",
        length = 50
    )
    private String leadSource;



    @Column(
        name = "assigned_executive",
        length = 100
    )
    private String assignedExecutive;



    @Column(
        name = "discussion_details",
        columnDefinition = "TEXT"
    )
    private String discussionDetails;



    @Column(name = "visit_date")
    private LocalDate visitDate;



    @Column(name = "next_follow_up_date")
    private LocalDate nextFollowUpDate;



    @Column(length = 30)
    private String status = "New";



    @Column(length = 20)
    private String priority = "Cold";



    @Column(
        name = "created_date",
        updatable = false
    )
    private LocalDateTime createdDate;



    @PrePersist
    protected void onCreate() {


        if (this.status == null || this.status.isEmpty()) {

            this.status = "New";

        }


        if (this.priority == null || this.priority.isEmpty()) {

            this.priority = "Cold";

        }


        this.createdDate = LocalDateTime.now();

    }
    public String getCustomerName() {
        return customerName;
    }


    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }


    public String getMobile() {
        return mobile;
    }


    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


    public String getStatus() {
        return status;
    }


    public void setStatus(String status) {
        this.status = status;
    }


    public String getPriority() {
        return priority;
    }


    public void setPriority(String priority) {
        this.priority = priority;
    }
	public void setId(Long id) {
        this.id = id;		
	}

}