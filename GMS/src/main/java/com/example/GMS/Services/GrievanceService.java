package com.example.GMS.Services;

import com.example.GMS.model.Grievance;
import com.example.GMS.model.Technician;
import com.example.GMS.repositories.GrievanceRepository;
import com.example.GMS.repositories.TechnicianRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GrievanceService {

    @Autowired
    private GrievanceRepository grievanceRepository;

    @Autowired
    private TechnicianRepository technicianRepository;

    // Create a new grievance and set its attributes
    public void createGrievance(Grievance grievance) {
        // Get the authenticated user's username
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        grievance.setUsername(username);
        grievance.setSubmittedDate(LocalDateTime.now());
        grievance.setStatus("Submitted");

        // Save the grievance to the database
        grievanceRepository.save(grievance);
    }

    // Get grievances by username
    public List<Grievance> getGrievancesByUsername(String username) {
        return grievanceRepository.findByUsername(username);
    }

    // Get all unassigned grievances
    public List<Grievance> getUnassignedGrievances() {
        return grievanceRepository.findByTechnicianIdIsNull();
    }

    // Get all technicians (for Assignee)
    public List<Technician> getAllTechnicians() {
        return technicianRepository.findAll();
    }

    // Assign a technician to a grievance (for Assignee)
    public Technician assignTechnicianToGrievance(Long grievanceId, Long technicianId) {
        Grievance grievance = grievanceRepository.findById(grievanceId)
                .orElseThrow(() -> new RuntimeException("Grievance not found"));

        Technician technician = technicianRepository.findById(technicianId)
                .orElseThrow(() -> new RuntimeException("Technician not found"));

        grievance.setTechnician(technician);
        grievanceRepository.save(grievance);

        return technician;
    }

    // Get grievances by technician ID
    public List<Grievance> getGrievancesByTechnicianId(Long technicianId) {
        return grievanceRepository.findByTechnicianId(technicianId);
    }

    // Update the status of a grievance (for Technician)
    public Grievance updateGrievanceStatus(Long grievanceId, String status) {
        Grievance grievance = grievanceRepository.findById(grievanceId)
                .orElseThrow(() -> new RuntimeException("Grievance not found"));

        grievance.setStatus(status);
        grievance.setUpdatedDate(LocalDateTime.now());
        grievanceRepository.save(grievance);

        return grievance;
    }

    // Update technician's own status (for Technician)
    public Technician updateTechnicianStatus(Long technicianId, String status) {
        Technician technician = technicianRepository.findById(technicianId)
                .orElseThrow(() -> new RuntimeException("Technician not found"));

        technician.setStatus(status);
        technicianRepository.save(technician);

        return technician;
    }
}
