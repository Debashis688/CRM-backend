package com.example.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.crm.model.LeadType;

public interface LeadTypeRepository extends JpaRepository<LeadType, Long> {

}