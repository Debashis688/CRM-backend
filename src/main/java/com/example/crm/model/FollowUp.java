package com.example.crm.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "follow_up")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FollowUp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "lead_id", nullable = false)
    private CustomerLead customerLead;

    @Column(name = "follow_up_date", updatable = false)
    private LocalDateTime followUpDate;

    @Column(name = "discussion_remarks", columnDefinition = "TEXT", nullable = false)
    private String discussionRemarks;

    @Column(name = "next_date")
    private LocalDate nextDate;

    @PrePersist
    protected void onCreate() {
        this.followUpDate = LocalDateTime.now();
        
             
    }

	public void setCustomerLead(CustomerLead lead) {
		// TODO Auto-generated method stub
		
	}
}