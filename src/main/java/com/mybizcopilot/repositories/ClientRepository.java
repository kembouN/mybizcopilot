package com.mybizcopilot.repositories;

import com.mybizcopilot.entities.Client;
import com.mybizcopilot.entities.Entreprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

    List<Client> findAllByEntreprise(Entreprise entreprise);
}
