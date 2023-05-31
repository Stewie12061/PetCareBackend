package com.example.food.service;
import com.example.food.model.Appointment;
import com.example.food.model.ChangeAppointmentRequest;
import com.example.food.model.GroomingPackage;
import com.example.food.model.User;
import com.example.food.repository.AppointmentRepository;
import com.example.food.repository.GroomingPackageRepository;
import com.example.food.repository.UserRepository;
import com.google.api.gax.rpc.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class AppointmentService {
    private final UserRepository userRepository;
    private final GroomingPackageRepository groomingPackageRepository;
    private final AppointmentRepository appointmentRepository;

    public void addAppointment(Long userId, int groomingPackageId, String date, String day, String time, Double price, int status) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        GroomingPackage groomingPackage = groomingPackageRepository.findById(groomingPackageId)
                .orElseThrow(() -> new EntityNotFoundException("Package not found"));

        Appointment appointment = new Appointment();
            appointment.setDate(date);
            appointment.setDay(day);
            appointment.setTime(time);
            appointment.setPrice(price);
            appointment.setGroomingPackage(groomingPackage);
            appointment.setUser(user);
            appointment.setStatus(status);
            appointmentRepository.save(appointment);

    }

    public List<String> getBookedTimeSlots(String date) {
        return appointmentRepository.findBookedTimeSlotsByDate(date);
    }

    public List<Appointment> findByUserId(Long userId, int status) {
        return appointmentRepository.findByUserIdAndStatus(userId,status).orElseThrow(()->new IllegalArgumentException("No user found"));
    }

    public void cancelAppointment(int appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow(() -> new IllegalArgumentException("Invalid appointment ID: " + appointmentId));


        appointment.setStatus(3); // Set status as canceled
        appointmentRepository.save(appointment);
    }

    public List<Appointment> findClosestByUserId(Long userId) {

        LocalDate systemDay = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");

        List<Appointment> appointments = appointmentRepository.findByUserId(userId).orElseThrow(() -> new IllegalArgumentException("No user found"));

        Appointment closestAppointment = null;
        long closestDaysDifference = Long.MAX_VALUE;

        for (Appointment appointment : appointments) {
            String appointmentDate = appointment.getDate();
            LocalDate parsedAppointmentDate = LocalDate.parse(appointmentDate, formatter);

            long daysDifference = Math.abs(systemDay.toEpochDay() - parsedAppointmentDate.toEpochDay());
            if (daysDifference < closestDaysDifference) {
                closestAppointment = appointment;
                closestDaysDifference = daysDifference;
            }
        }

        List<Appointment> closestAppointmentList = new ArrayList<>();
        if (closestAppointment != null) {
            closestAppointmentList.add(closestAppointment);
        }

        return closestAppointmentList;
    }


    public void deleteAppointment(int appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow(() -> new IllegalArgumentException("Invalid appointment ID: " + appointmentId));
        appointmentRepository.delete(appointment);
    }

    public boolean checkAndUpdateAppointments(Long userId) {
        // Get the current date and time
        LocalDateTime currentDateTime = LocalDateTime.now();

        // Get appointments for the specified user
        List<Appointment> appointments = appointmentRepository.findByUserId(userId).orElseThrow(() -> new IllegalArgumentException("No user founded"));

        // Iterate over the appointments and update the status if the appointment date and time have passed
        for (Appointment appointment : appointments) {
            LocalDateTime appointmentDateTime = LocalDateTime.parse(appointment.getDate() + " " + appointment.getTime(), DateTimeFormatter.ofPattern("M/d/yyyy H:mm a"));
            if (appointmentDateTime.isBefore(currentDateTime)) {
                appointment.setStatus(2); // Set status to 2 (completed)
            }
        }

        appointmentRepository.saveAll(appointments);
        return true;
    }

    public void changeAppointment(String userId, int appointmentId, ChangeAppointmentRequest appointmentRequest) {
        Appointment existingAppointment = appointmentRepository.findByUserIdAndAppointmentId(appointmentId,userId)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found"));

        // Update the appointment date and time
        existingAppointment.setDate(appointmentRequest.getDate());
        existingAppointment.setTime(appointmentRequest.getTime());

        // Save the updated appointment
        appointmentRepository.save(existingAppointment);
    }
}
