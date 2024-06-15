package com.example.forodiscusion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.forodiscusion.model.Tema;

@Repository
public interface TemaRepository extends JpaRepository<Tema, Long> {
}
