package com.mybizcopilot.repositories;

import com.mybizcopilot.entities.Service;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceRepository extends JpaRepository<Service, Integer> {

    int countByLibelleServiceIgnoreCase(String libelle);

    List<Service> findAllByEntrepriseIdEntreprise(Integer idEntreprise);
}
