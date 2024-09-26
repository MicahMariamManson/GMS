package com.example.GMS.Controller;

import com.example.GMS.Services.GrievanceService;
import com.example.GMS.model.Grievance;
import com.example.GMS.model.Technician;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/grievances")
public class GrievanceController {

    @Autowired
    private GrievanceService grievanceService;

    // Create a new grievance
    @PostMapping("/create")
    public ResponseEntity<String> createGrievance(@RequestBody Grievance grievance) {
        grievanceService.createGrievance(grievance);
        return new ResponseEntity<>("Grievance created successfully", HttpStatus.CREATED);
    }

    // Get grievances by username
    @GetMapping("/find/{username}")
    public ResponseEntity<List<Grievance>> getGrievancesByUsername(@PathVariable String username) {
        List<Grievance> grievances = grievanceService.getGrievancesByUsername(username);
        if (grievances.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(grievances, HttpStatus.OK);
    }

    // Get all unassigned grievances
    @GetMapping("/unassigned")
    public ResponseEntity<List<Grievance>> getUnassignedGrievances() {
        List<Grievance> grievances = grievanceService.getUnassignedGrievances();
        if (grievances.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(grievances, HttpStatus.OK);
    }

    // Get all technicians (for Assignee)
    @GetMapping("/technicians")
    public ResponseEntity<List<Technician>> getAllTechnicians() {
        List<Technician> technicians = grievanceService.getAllTechnicians();
        return new ResponseEntity<>(technicians, HttpStatus.OK);
    }

    // Assign a technician to a grievance (for Assignee)
    @PatchMapping("/assign/{grievanceId}/{technicianId}")
    public ResponseEntity<String> assignTechnicianToGrievance(@PathVariable Long grievanceId, @PathVariable Long technicianId) {
        grievanceService.assignTechnicianToGrievance(grievanceId, technicianId);
        return new ResponseEntity<>("Technician assigned successfully", HttpStatus.OK);
    }

    // Get grievances by technician ID
    @GetMapping("/get-grievances/{technicianId}")
    public ResponseEntity<List<Grievance>> getGrievancesByTechnicianId(@PathVariable Long technicianId) {
        List<Grievance> grievances = grievanceService.getGrievancesByTechnicianId(technicianId);
        if (grievances.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(grievances, HttpStatus.OK);
    }

    // Update the status of a grievance (for Technician)
    @PatchMapping("/update-grievance-status/{grievanceId}")
    public ResponseEntity<String> updateGrievanceStatus(@PathVariable Long grievanceId, @RequestParam String status) {
        Grievance grievance = grievanceService.updateGrievanceStatus(grievanceId, status);
        return new ResponseEntity<>("Grievance status updated to " + grievance.getStatus(), HttpStatus.OK);
    }

    // Update technician's own status (for Technician)
    @PatchMapping("/update-technician-status/{technicianId}")
    public ResponseEntity<String> updateTechnicianStatus(@PathVariable Long technicianId, @RequestParam String status) {
        Technician technician = grievanceService.updateTechnicianStatus(technicianId, status);
        return new ResponseEntity<>("Technician status updated to " + technician.getStatus(), HttpStatus.OK);
    }
}
