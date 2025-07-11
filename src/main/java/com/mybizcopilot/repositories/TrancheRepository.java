package com.mybizcopilot.repositories;

import com.mybizcopilot.entities.Tranche;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrancheRepository extends JpaRepository<Tranche, Integer> {
}
