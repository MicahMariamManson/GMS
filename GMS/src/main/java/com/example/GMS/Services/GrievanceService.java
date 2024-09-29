package com.example.GMS.Services;

import com.example.GMS.model.Grievance;
import com.example.GMS.model.Technician;
import com.example.GMS.request.GrievanceRequest;
import com.example.GMS.response.GrievanceResponse;
import com.example.GMS.repositories.GrievanceRepository;
import com.example.GMS.repositories.TechnicianRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GrievanceService {

    private final GrievanceRepository grievanceRepository;
    private final TechnicianRepository technicianRepository;

    public GrievanceService(GrievanceRepository grievanceRepository, TechnicianRepository technicianRepository) {
        this.grievanceRepository = grievanceRepository;
        this.technicianRepository = technicianRepository;
    }

    // Create a new grievance and return GrievanceResponse
    public GrievanceResponse createGrievance(GrievanceRequest grievanceRequest) {
        try {
            Grievance grievance = new Grievance();
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            grievance.setUsername(username);
            grievance.setDescription(grievanceRequest.getDescription());
            grievance.setType(grievanceRequest.getType());
            grievance.setSubmittedDate(LocalDateTime.now());
            grievance.setStatus("Submitted");

            Grievance savedGrievance = grievanceRepository.save(grievance);

            return new GrievanceResponse(
                    savedGrievance.getGrievanceId(),
                    savedGrievance.getDescription(),
                    savedGrievance.getStatus(),
                    savedGrievance.getSubmittedDate()
            );
        } catch (Exception e) {
            throw new RuntimeException("Error creating grievance: " + e.getMessage());
        }
    }

    // Get grievances by username
    public List<Grievance> getGrievancesByUsername(String username) {
        try {
            return grievanceRepository.findByUsername(username);
        } catch (Exception e) {
            throw new RuntimeException("Error fetching grievances by username: " + e.getMessage());
        }
    }

    // Get all unassigned grievances
    public List<Grievance> getUnassignedGrievances() {
        try {
            return grievanceRepository.findByTechnicianIdIsNull();
        } catch (Exception e) {
            throw new RuntimeException("Error fetching unassigned grievances: " + e.getMessage());
        }
    }

    // Get all technicians (for Assignee)
    public List<Technician> getAllTechnicians() {
        try {
            return technicianRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error fetching technicians: " + e.getMessage());
        }
    }

    // Assign a technician to a grievance (for Assignee)
    public Technician assignTechnicianToGrievance(Long grievanceId, Long technicianId) {
        try {
            Grievance grievance = grievanceRepository.findById(grievanceId)
                    .orElseThrow(() -> new RuntimeException("Grievance not found"));

            Technician technician = technicianRepository.findById(technicianId)
                    .orElseThrow(() -> new RuntimeException("Technician not found"));

            grievance.setTechnician(technician);
            grievanceRepository.save(grievance);

            return technician;
        } catch (Exception e) {
            throw new RuntimeException("Error assigning technician: " + e.getMessage());
        }
    }

    // Get grievances by technician ID
    public List<Grievance> getGrievancesByTechnicianId(Long technicianId) {
        try {
            return grievanceRepository.findByTechnicianId(technicianId);
        } catch (Exception e) {
            throw new RuntimeException("Error fetching grievances by technician ID: " + e.getMessage());
        }
    }

    // Update the status of a grievance (for Technician)
    public Grievance updateGrievanceStatus(Long grievanceId, String status) {
        try {
            Grievance grievance = grievanceRepository.findById(grievanceId)
                    .orElseThrow(() -> new RuntimeException("Grievance not found"));

            grievance.setStatus(status);
            return grievanceRepository.save(grievance);
        } catch (Exception e) {
            throw new RuntimeException("Error updating grievance status: " + e.getMessage());
        }
    }

    // Update technician's own status (for Technician)
    public Technician updateTechnicianStatus(Long technicianId, String status) {
        try {
            Technician technician = technicianRepository.findById(technicianId)
                    .orElseThrow(() -> new RuntimeException("Technician not found"));

            technician.setStatus(status);
            return technicianRepository.save(technician);
        } catch (Exception e) {
            throw new RuntimeException("Error updating technician status: " + e.getMessage());
        }
    }
}
