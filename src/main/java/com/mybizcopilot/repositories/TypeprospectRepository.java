package com.mybizcopilot.repositories;

import com.mybizcopilot.entities.Typeprospect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeprospectRepository extends JpaRepository<Typeprospect, Integer> {

    Typeprospect findByIdTypeprospect(Integer idTypeprospect);
}
