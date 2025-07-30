package com.mybizcopilot.repositories;

import com.mybizcopilot.entities.Entreprise;
import com.mybizcopilot.entities.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Integer> {

    @Query("SELECT COUNT(s) FROM Service  s WHERE LOWER(s.libelleService) = LOWER(:libelle) AND s.entreprise = :entreprise")
    int countByLibelleService(@Param("libelle") String libelle, @Param("entreprise")Entreprise entreprise);

    @Query("select count(s) from Service s where lower(s.libelleService) = lower(:libelle) AND s.idService <> :idService AND s.entreprise = :entreprise")
    int countByLibelleServiceWhereIdServiceNot(@Param("libelle") String libelle, @Param("idService") Integer idService, @Param("entreprise")Entreprise entreprise);

    List<Service> findAllByEntrepriseIdEntreprise(Integer idEntreprise);
}
