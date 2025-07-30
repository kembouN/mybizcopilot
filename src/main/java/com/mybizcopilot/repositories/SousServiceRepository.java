package com.mybizcopilot.repositories;

import com.mybizcopilot.entities.Entreprise;
import com.mybizcopilot.entities.Service;
import com.mybizcopilot.entities.SousService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SousServiceRepository extends JpaRepository<SousService, Integer> {

    @Query("SELECT COUNT(ss) FROM SousService ss WHERE LOWER(ss.libelle) = :libelle AND ss.service = :service")
    int countAllByLibelleIgnoreCase(@Param("libelle") String libelle, @Param("service") Service service);

    @Query("SELECT COUNT(ss) FROM SousService ss WHERE LOWER(ss.libelle) = :libelle AND ss.idSousservice <> :idSousService AND ss.service = :service")
    int countAllSousServiceWithLibelleAndNotWithId(@Param("libelle") String libelle, @Param("idSousService") Integer idSousService, @Param("service") Service service);

    List<SousService> findAllByServiceEntreprise(Entreprise entreprise);

}
