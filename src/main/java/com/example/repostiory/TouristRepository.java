package com.example.repostiory;



import com.example.entity.Tourist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TouristRepository
        extends JpaRepository<Tourist, Long> {
}