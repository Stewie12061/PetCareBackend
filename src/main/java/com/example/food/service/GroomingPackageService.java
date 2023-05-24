package com.example.food.service;

import com.example.food.model.GroomingPackage;
import com.example.food.repository.GroomingPackageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GroomingPackageService {
    private final GroomingPackageRepository groomingPackageRepository;

    public List<GroomingPackage> getDogGroomingPackage() {
        return groomingPackageRepository.findDogPackage();
    }

    public List<GroomingPackage> getCatGroomingPackage() {
        return groomingPackageRepository.findCatPackage();

    }
}
