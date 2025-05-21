package com.mybizcopilot.repositories;

import com.mybizcopilot.entities.Commande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommandeRepository extends JpaRepository<Commande, Integer> {

    List<Commande> findAllByServiceEntrepriseIdEntreprise(Integer idEntreprise);

    List<Commande> findDistinctByServiceEntrepriseIdEntreprise(Integer idEntreprise);
}
