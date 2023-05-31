package com.example.food.controller;

import com.example.food.model.Appointment;
import com.example.food.model.AppointmentRequest;
import com.example.food.model.ChangeAppointmentRequest;
import com.example.food.service.AppointmentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/appointment")
public class AppointmentController {
    private AppointmentService appointmentService;

    @PostMapping("users/{userId}/book")
    public ResponseEntity<String> addAppointment(@PathVariable Long userId, @RequestBody AppointmentRequest request) {
        appointmentService.addAppointment(userId, request.getGroomingPackageId(),request.getDate(), request.getDay(),request.getTime(), request.getPrice(), request.getStatus());
        return ResponseEntity.ok("Create appointment successfully.");
    }
    @PutMapping("users/{userId}/{appointmentId}/reschedule")
    public ResponseEntity<String> rescheduleAppointment(@PathVariable("appointmentId") int appointmentId, @RequestBody ChangeAppointmentRequest appointmentRequest, @PathVariable String userId) {
        try {
            appointmentService.changeAppointment(userId,appointmentId,appointmentRequest);

            return ResponseEntity.ok("Appointment rescheduled successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to reschedule appointment");
        }
    }

    @GetMapping("/checkAvailable")
    public Map<String, List<String>> checkAvailability(@RequestParam String date) {
        List<String> bookedTimeSlots = appointmentService.getBookedTimeSlots(date);
        Map<String, List<String>> availabilityData = new HashMap<>();
        availabilityData.put("bookedTimeSlots", bookedTimeSlots);
        return availabilityData;
    }

    @GetMapping("/{userId}/{status}")
    public List<Appointment> getAppointmentsByUserId(@PathVariable Long userId, @PathVariable int status) {
        return appointmentService.findByUserId(userId,status);
    }

    @GetMapping("/{userId}/upcomingAppointment")
    public List<Appointment> getClosestAppointmentByUserId(@PathVariable Long userId){
        return appointmentService.findClosestByUserId(userId);
    }

    @PutMapping("/{appointmentId}/cancel")
    public ResponseEntity<String> cancelAppointment(@PathVariable("appointmentId") int appointmentId) {
        appointmentService.cancelAppointment(appointmentId);
        return ResponseEntity.ok("Appointment canceled successfully.");
    }

    @DeleteMapping("/{appointmentId}/delete")
    public ResponseEntity<String> deleteAppointment(@PathVariable("appointmentId") int appointmentId){
        appointmentService.deleteAppointment(appointmentId);
        return ResponseEntity.ok("Appointment deleted successfully.");
    }

    @PutMapping("/users/{userId}/checkAndUpdateAppointments")
    public ResponseEntity<String> checkAndUpdateAppointments(@PathVariable Long userId) {
        // Call the service method to check and update appointments
        boolean result = appointmentService.checkAndUpdateAppointments(userId);
        if (result) {
            return ResponseEntity.ok("Appointments checked and updated successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to check and update appointments.");
        }
    }
}
