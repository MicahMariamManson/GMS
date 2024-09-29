package com.example.GMS.Controller;

import com.example.GMS.Services.GrievanceService;
import com.example.GMS.request.GrievanceRequest;
import com.example.GMS.response.GrievanceResponse;
import com.example.GMS.model.Grievance;
import com.example.GMS.model.Technician;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grievances")
public class GrievanceController {

    private final GrievanceService grievanceService;  // Only declare it once at the class level

    public GrievanceController(GrievanceService grievanceService) {
        this.grievanceService = grievanceService;  // Constructor initializes the field
    }

    // Create a new grievance
    @PostMapping("/create")
    public ResponseEntity<?> createGrievance(@RequestBody GrievanceRequest grievanceRequest) {
        try {
            GrievanceResponse response = grievanceService.createGrievance(grievanceRequest);  // Use the field directly
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return new ResponseEntity<>("Error creating grievance: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get grievances by username
    @GetMapping("/find/{username}")
    public ResponseEntity<?> getGrievancesByUsername(@PathVariable String username) {
        try {
            List<Grievance> grievances = grievanceService.getGrievancesByUsername(username);  // Use the field directly
            if (grievances.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(grievances, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error fetching grievances: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get all unassigned grievances
    @GetMapping("/unassigned")
    public ResponseEntity<?> getUnassignedGrievances() {
        try {
            List<Grievance> grievances = grievanceService.getUnassignedGrievances();  // Use the field directly
            if (grievances.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(grievances, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error fetching unassigned grievances: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get all technicians (for Assignee)
    @GetMapping("/technicians")
    public ResponseEntity<?> getAllTechnicians() {
        try {
            List<Technician> technicians = grievanceService.getAllTechnicians();  // Use the field directly
            return new ResponseEntity<>(technicians, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error fetching technicians: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Assign a technician to a grievance (for Assignee)
    @PatchMapping("/assign/{grievanceId}/{technicianId}")
    public ResponseEntity<?> assignTechnicianToGrievance(@PathVariable Long grievanceId, @PathVariable Long technicianId) {
        try {
            grievanceService.assignTechnicianToGrievance(grievanceId, technicianId);  // Use the field directly
            return new ResponseEntity<>("Technician assigned successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error assigning technician: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get grievances by technician ID
    @GetMapping("/get-grievances/{technicianId}")
    public ResponseEntity<?> getGrievancesByTechnicianId(@PathVariable Long technicianId) {
        try {
            List<Grievance> grievances = grievanceService.getGrievancesByTechnicianId(technicianId);  // Use the field directly
            if (grievances.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(grievances, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error fetching grievances: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update the status of a grievance (for Technician)
    @PatchMapping("/update-grievance-status/{grievanceId}")
    public ResponseEntity<?> updateGrievanceStatus(@PathVariable Long grievanceId, @RequestParam String status) {
        try {
            Grievance grievance = grievanceService.updateGrievanceStatus(grievanceId, status);  // Use the field directly
            return new ResponseEntity<>("Grievance status updated to " + grievance.getStatus(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error updating grievance status: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update technician's own status (for Technician)
    @PatchMapping("/update-technician-status/{technicianId}")
    public ResponseEntity<?> updateTechnicianStatus(@PathVariable Long technicianId, @RequestParam String status) {
        try {
            Technician technician = grievanceService.updateTechnicianStatus(technicianId, status);  // Use the field directly
            return new ResponseEntity<>("Technician status updated to " + technician.getStatus(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error updating technician status: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
