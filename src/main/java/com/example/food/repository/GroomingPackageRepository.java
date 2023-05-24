package com.example.food.repository;

import com.example.food.model.GroomingPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroomingPackageRepository extends JpaRepository<GroomingPackage,Integer> {

    @Query(value = "SELECT * FROM grooming_package WHERE type = 'cat' " ,nativeQuery = true)
    List<GroomingPackage> findCatPackage();

    @Query(value = "SELECT * FROM grooming_package WHERE type = 'dog'" ,nativeQuery = true)
    List<GroomingPackage> findDogPackage();
}
