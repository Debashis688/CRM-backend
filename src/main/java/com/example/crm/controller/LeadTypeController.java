package com.example.crm.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.crm.model.LeadType;
import com.example.crm.repository.LeadTypeRepository;


@RestController
@RequestMapping("/api/lead-types")
@CrossOrigin
public class LeadTypeController {


    private final LeadTypeRepository leadTypeRepository;


    public LeadTypeController(LeadTypeRepository leadTypeRepository) {
        this.leadTypeRepository = leadTypeRepository;
    }


    // Get all lead types
    @GetMapping
    public List<LeadType> getAllLeadTypes() {

        return leadTypeRepository.findAll();

    }


    // Create lead type
    @PostMapping
    public LeadType createLeadType(@RequestBody LeadType leadType) {

        return leadTypeRepository.save(leadType);

    }

}