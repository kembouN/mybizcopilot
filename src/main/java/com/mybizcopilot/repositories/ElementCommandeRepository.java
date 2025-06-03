package com.mybizcopilot.repositories;

import com.mybizcopilot.entities.Commande;
import com.mybizcopilot.entities.ElementCommande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ElementCommandeRepository extends JpaRepository<ElementCommande, Integer> {

    List<ElementCommande> findAllByCommande(Commande commande);

    ElementCommande findByIdElementcommande(Integer idElement);
}
