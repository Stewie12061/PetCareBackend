package com.example.food.repository;
import com.example.food.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Integer> {

    @Query("SELECT a.time FROM Appointment a WHERE a.date = :date and a.status = 1")
    List<String> findBookedTimeSlotsByDate(String date);

    @Query(value = "SELECT * FROM Appointment a WHERE a.id = :appointmentId", nativeQuery = true)
    Optional<Appointment> findById(int appointmentId);

    @Query(value = "SELECT * FROM Appointment a WHERE a.user_id like %:userId% and a.status like %:status% ORDER BY a.date, a.time" ,nativeQuery = true)
    Optional<List<Appointment>> findByUserIdAndStatus(Long userId, int status);

    @Query(value = "SELECT * FROM Appointment a WHERE a.user_id like %:userId% and a.status = 1" ,nativeQuery = true)
    Optional<List<Appointment>> findByUserId(Long userId);

    @Query(value = "SELECT * FROM Appointment a WHERE a.user_id like %:userId% and a.id like %:appointmentId%" ,nativeQuery = true)
    Optional<Appointment> findByUserIdAndAppointmentId(int appointmentId, String userId);
}
