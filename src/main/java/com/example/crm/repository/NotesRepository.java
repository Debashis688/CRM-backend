package com.example.crm.repository;

import com.example.crm.model.Notes;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NotesRepository extends JpaRepository<Notes, Long> {
    List<Notes> findByCustomerLeadIdOrderByCreatedAtDesc(Long leadId);
}