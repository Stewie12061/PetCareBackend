package com.example.food.controller;

import com.example.food.model.GroomingPackage;
import com.example.food.service.GroomingPackageService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/grooming")
public class GroomingPackageController {
    private final GroomingPackageService groomingPackageService;

    @GetMapping("/dog")
    public ResponseEntity<List<GroomingPackage>> getDogGroomingPackage() {
        List<GroomingPackage> dogGroomingPackage = groomingPackageService.getDogGroomingPackage();
        return ResponseEntity.status(HttpStatus.OK).body(dogGroomingPackage);
    }
    @GetMapping("/cat")
    public ResponseEntity<List<GroomingPackage>> getCatGroomingPackage() {
        List<GroomingPackage> catGroomingPackage = groomingPackageService.getCatGroomingPackage();
        return ResponseEntity.status(HttpStatus.OK).body(catGroomingPackage);
    }
}
