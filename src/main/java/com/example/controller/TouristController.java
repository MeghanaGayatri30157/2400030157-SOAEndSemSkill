package com.example.controller;



import com.example.entity.Tourist;
import com.example.service.TouristService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tourists")
public class TouristController {

    private final TouristService service;

    public TouristController(TouristService service) {
        this.service = service;
    }
    @PostMapping
    public ResponseEntity<Tourist> addTourist(
            @RequestBody Tourist tourist) {

        Tourist saved = service.addTourist(tourist);

        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTourist(

            @PathVariable Long id,

            @RequestHeader("X-Request-Token") String token,

            @RequestBody Tourist tourist) {

        try {
            Tourist updated = service.updateTourist(
                    id, tourist, token);

            return ResponseEntity.ok(updated);

        } catch (RuntimeException e) {

            if (e.getMessage().equals("Invalid Request Token")) {
                return ResponseEntity.status(401)
                        .body(e.getMessage());
            }

            if (e.getMessage().equals("Tourist not found")) {
                return ResponseEntity.status(404)
                        .body(e.getMessage());
            }

            return ResponseEntity.internalServerError()
                    .body("Update failed");
        }
    }
}