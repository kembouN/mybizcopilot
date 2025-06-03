package com.mybizcopilot.repositories;

import com.mybizcopilot.entities.Collaborateur;
import com.mybizcopilot.entities.Entreprise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CollaborateurRepository extends JpaRepository<Collaborateur, Integer> {

    List<Collaborateur> findAllByEntreprise(Entreprise entreprise);
}
