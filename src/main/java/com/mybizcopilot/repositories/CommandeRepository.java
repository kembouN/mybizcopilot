package com.mybizcopilot.repositories;

import com.mybizcopilot.entities.Collaborateur;
import com.mybizcopilot.entities.Commande;
import com.mybizcopilot.entities.StatutCommande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommandeRepository extends JpaRepository<Commande, Integer> {

    List<Commande> findAllByClientEntrepriseIdEntreprise(Integer idEntreprise);

    int countByCollaborateurAndStatutCommandeIsNotLike(Collaborateur collaborateur, StatutCommande termine);

}
