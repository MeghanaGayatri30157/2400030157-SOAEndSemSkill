package com.example.service;



import java.security.MessageDigest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.entity.Tourist;
import com.example.repostiory.TouristRepository;

@Service
public class TouristService {

    private final TouristRepository repository;

    @Value("${app.request.token}")
    private String validToken;

    public TouristService(TouristRepository repository) {
        this.repository = repository;
    }

    public boolean validateToken(String token) {

        if (token == null) {
            return false;
        }

        return MessageDigest.isEqual(
                token.getBytes(java.nio.charset.StandardCharsets.UTF_8),
                validToken.getBytes(java.nio.charset.StandardCharsets.UTF_8)
        );
    }
    public Tourist addTourist(Tourist tourist) {
        return repository.save(tourist);
    }

    @Transactional
    public Tourist updateTourist(
            Long id,
            Tourist updatedTourist,
            String token) {

        // Step 1: Validate token
        if (!validateToken(token)) {
            throw new RuntimeException("Invalid Request Token");
        }

        // Step 2: Find existing tourist
        Tourist tourist = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Tourist not found"));

        // Step 3: Update tourist details
        tourist.setDestination(updatedTourist.getDestination());
        tourist.setContact(updatedTourist.getContact());

        // Step 4: Save changes
        return repository.save(tourist);
    }
}