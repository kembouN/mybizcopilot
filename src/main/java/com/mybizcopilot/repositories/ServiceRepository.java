package com.mybizcopilot.repositories;

import com.mybizcopilot.entities.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Integer> {

    @Query("SELECT COUNT(s) FROM Service  s WHERE LOWER(s.libelleService) = LOWER(:libelle) ")
    int countByLibelleService(@Param("libelle") String libelle);

    @Query("select count(s) from Service s where lower(s.libelleService) = lower(:libelle) and s.idService <> :idService ")
    int countByLibelleServiceWhereIdServiceNot(@Param("libelle") String libelle, @Param("idService") Integer idService);

    List<Service> findAllByEntrepriseIdEntreprise(Integer idEntreprise);
}
