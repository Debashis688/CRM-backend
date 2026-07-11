package com.example.crm.repository;

import com.example.crm.model.FollowUp;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FollowUpRepository extends JpaRepository<FollowUp, Long> {
    List<FollowUp> findByCustomerLeadIdOrderByFollowUpDateDesc(Long leadId);
}