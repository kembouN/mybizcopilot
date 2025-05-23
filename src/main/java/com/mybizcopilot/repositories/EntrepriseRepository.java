package com.mybizcopilot.repositories;

import com.mybizcopilot.entities.Entreprise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EntrepriseRepository extends JpaRepository<Entreprise, Integer> {

    List<Entreprise> findAllByUtilisateurIdUtilisateur(Integer idUser);

    int countByNomEntreprise(String Entreprise);

}
