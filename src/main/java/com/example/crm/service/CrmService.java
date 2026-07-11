package com.example.crm.service;

import com.example.crm.model.*;
import com.example.crm.repository.*;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class CrmService {


    private final UserRepository userRepository;
    private final LeadTypeRepository leadTypeRepository;
    private final CustomerLeadRepository leadRepository;
    private final FollowUpRepository followUpRepository;
    private final NotesRepository notesRepository;


    public CrmService(
            UserRepository userRepository,
            LeadTypeRepository leadTypeRepository,
            CustomerLeadRepository leadRepository,
            FollowUpRepository followUpRepository,
            NotesRepository notesRepository
    ) {
        this.userRepository = userRepository;
        this.leadTypeRepository = leadTypeRepository;
        this.leadRepository = leadRepository;
        this.followUpRepository = followUpRepository;
        this.notesRepository = notesRepository;
    }



    // Authentication
    public Optional<User> validateUser(String username, String password) {

        return userRepository.findByUsername(username)
                .filter(user -> user.getPassword().equals(password));
    }



    // Lead Type Management

    public List<LeadType> getAllLeadTypes() {

        return leadTypeRepository.findAll();
    }


    public LeadType saveLeadType(LeadType type) {

        return leadTypeRepository.save(type);
    }


    public void deleteLeadType(Long id) {

        leadTypeRepository.deleteById(id);
    }



    // Customer Lead Management

    public List<CustomerLead> getAllLeads() {

        return leadRepository.findAll();
    }



    public List<CustomerLead> filterLeads(
            String name,
            String mobile,
            String city,
            String status,
            String priority
    ) {

        return leadRepository.searchLeads(
                (name == null || name.isBlank()) ? null : name,
                (mobile == null || mobile.isBlank()) ? null : mobile,
                (city == null || city.isBlank()) ? null : city,
                (status == null || status.isBlank()) ? null : status,
                (priority == null || priority.isBlank()) ? null : priority
        );
    }



    // SAVE LEAD (Updated)

    public CustomerLead saveLead(CustomerLead lead) {


        // Default values
        if (lead.getStatus() == null || lead.getStatus().isEmpty()) {
            lead.setStatus("New");
        }


        if (lead.getPriority() == null || lead.getPriority().isEmpty()) {
            lead.setPriority("Cold");
        }


        return leadRepository.save(lead);
    }



    public Optional<CustomerLead> getLeadById(Long id) {

        return leadRepository.findById(id);
    }



    public void deleteLead(Long id) {

        leadRepository.deleteById(id);
    }



    // Follow Up Management

    public List<FollowUp> getFollowUpsByLead(Long leadId) {

        return followUpRepository
                .findByCustomerLeadIdOrderByFollowUpDateDesc(leadId);
    }


    public FollowUp addFollowUp(FollowUp followUp) {

        return followUpRepository.save(followUp);
    }



    // Notes Management

    public List<Notes> getNotesByLead(Long leadId) {

        return notesRepository
                .findByCustomerLeadIdOrderByCreatedAtDesc(leadId);
    }


    public Notes addNote(Notes note) {

        return notesRepository.save(note);
    }



    // Dashboard Statistics

    public Map<String, Object> getDashboardStats() {

        Map<String, Object> stats = new HashMap<>();

        LocalDate today = LocalDate.now();


        stats.put("totalLeads", leadRepository.count());

        stats.put("hotCustomers",
                leadRepository.countByPriority("Hot"));

        stats.put("closedWon",
                leadRepository.countByStatus("Closed Won"));

        stats.put("closedLost",
                leadRepository.countByStatus("Closed Lost"));

        stats.put("todayFollowUps",
                leadRepository.countTodayFollowUps(today));

        stats.put("pendingFollowUps",
                leadRepository.countPendingFollowUps(today));


        return stats;
    }



    // Reminder Utility

    public List<CustomerLead> getTodayReminders() {

        return leadRepository.findByNextFollowUpDate(LocalDate.now());
    }

}