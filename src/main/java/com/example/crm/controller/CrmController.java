package com.example.crm.controller;

import com.example.crm.model.CustomerLead;
import com.example.crm.service.CrmService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(
    origins = {
        "http://localhost:4200",
        "http://localhost:51347"
    },
    allowedHeaders = "*",
    exposedHeaders = "*",
    methods = {
        RequestMethod.GET,
        RequestMethod.POST,
        RequestMethod.PUT,
        RequestMethod.DELETE,
        RequestMethod.OPTIONS
    }
)
@RestController
@RequestMapping("/api")
public class CrmController {

    private final CrmService crmService;

    public CrmController(CrmService crmService) {
        this.crmService = crmService;
    }

    // LOGIN API
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody Map<String, String> credentials) {

        String username = credentials.get("username");
        String password = credentials.get("password");

        if (username != null && password != null
                && ("admin".equalsIgnoreCase(username)
                || "admin@debslook.com".equalsIgnoreCase(username))
                && "admin123".equals(password)) {

            Map<String, String> response = new HashMap<>();
            response.put("message", "Login Successful");

            return ResponseEntity.ok(response);
        }

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("Invalid credentials");
    }

    // DASHBOARD STATISTICS
    @GetMapping("/dashboard/stats")
    public ResponseEntity<Map<String, Object>> getStats() {
        return ResponseEntity.ok(
                crmService.getDashboardStats()
        );
    }

    // GET ALL LEADS
    @GetMapping("/leads")
    public ResponseEntity<List<CustomerLead>> getLeads() {
        return ResponseEntity.ok(
                crmService.filterLeads(null, null, null, null, null)
        );
    }

    // CREATE NEW LEAD
    @PostMapping("/leads")
    public ResponseEntity<?> createLead(
            @RequestBody CustomerLead lead) {

        System.out.println("========== RECEIVED DATA ==========");
        System.out.println("Customer Name : " + lead.getCustomerName());
        System.out.println("Mobile        : " + lead.getMobile());
        System.out.println("Status        : " + lead.getStatus());
        System.out.println("Priority      : " + lead.getPriority());
        System.out.println("===================================");

        try {
            if (lead.getCustomerName() == null
                    || lead.getCustomerName().trim().isEmpty()) {

                return ResponseEntity.badRequest()
                        .body("Customer name is required");
            }

            if (lead.getMobile() == null
                    || lead.getMobile().trim().isEmpty()) {

                return ResponseEntity.badRequest()
                        .body("Mobile number is required");
            }

            if (lead.getStatus() == null
                    || lead.getStatus().trim().isEmpty()) {

                lead.setStatus("New");
            }

            if (lead.getPriority() == null
                    || lead.getPriority().trim().isEmpty()) {

                lead.setPriority("Cold");
            }

            CustomerLead savedLead = crmService.saveLead(lead);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(savedLead);

        } catch (Exception e) {
            e.printStackTrace();

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error saving lead: " + e.getMessage());
        }
    }

    // DELETE LEAD API
    @DeleteMapping("/leads/{id}")
    public ResponseEntity<?> deleteLead(@PathVariable Long id) {

        try {
            crmService.deleteLead(id);

            Map<String, String> response = new HashMap<>();
            response.put(
                    "message",
                    "Lead record deleted successfully from database"
            );

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting lead: " + e.getMessage());
        }
    }

    // UPDATE (EDIT) LEAD API
    @PutMapping("/leads/{id}")
    public ResponseEntity<?> updateLead(
            @PathVariable Long id,
            @RequestBody CustomerLead leadDetails) {

        try {
            leadDetails.setId(id);

            CustomerLead updatedLead =
                    crmService.saveLead(leadDetails);

            return ResponseEntity.ok(updatedLead);

        } catch (Exception e) {
            e.printStackTrace();

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating lead: " + e.getMessage());
        }
    }
}