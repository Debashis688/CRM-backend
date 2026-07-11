package com.example.crm.repository;

import com.example.crm.model.CustomerLead;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;


public interface CustomerLeadRepository extends JpaRepository<CustomerLead, Long> {


    // Search and filter leads
    @Query("""
            SELECT c FROM CustomerLead c
            WHERE (:name IS NULL OR LOWER(c.customerName) LIKE LOWER(CONCAT('%', :name, '%')))
            AND (:mobile IS NULL OR c.mobile LIKE CONCAT('%', :mobile, '%'))
            AND (:city IS NULL OR LOWER(c.city) = LOWER(:city))
            AND (:status IS NULL OR c.status = :status)
            AND (:priority IS NULL OR c.priority = :priority)
            """)
    List<CustomerLead> searchLeads(
            @Param("name") String name,
            @Param("mobile") String mobile,
            @Param("city") String city,
            @Param("status") String status,
            @Param("priority") String priority
    );



    // Dashboard count queries

    long countByStatus(String status);


    long countByPriority(String priority);



    // Today's follow-ups

    @Query("""
            SELECT COUNT(c)
            FROM CustomerLead c
            WHERE c.nextFollowUpDate = :today
            """)
    long countTodayFollowUps(
            @Param("today") LocalDate today
    );



    // Pending follow-ups

    @Query("""
            SELECT COUNT(c)
            FROM CustomerLead c
            WHERE c.nextFollowUpDate < :today
            AND c.status <> 'Closed Won'
            AND c.status <> 'Closed Lost'
            """)
    long countPendingFollowUps(
            @Param("today") LocalDate today
    );



    // Reminder list

    List<CustomerLead> findByNextFollowUpDate(LocalDate date);

}