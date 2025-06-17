package com.mybizcopilot.repositories;

import com.mybizcopilot.entities.Entreprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntrepriseRepository extends JpaRepository<Entreprise, Integer> {

    List<Entreprise> findAllByUtilisateurIdUtilisateur(Integer idUser);

    int countByNomEntreprise(String Entreprise);

    int countAllByTelephone1Entreprise(@Param("telephone") String telephone);

    int countAllByTelephone2Entreprise(@Param("telephone") String telephone);

    @Query("SELECT COUNT(e) FROM Entreprise e WHERE e.telephone1Entreprise = :telephone AND e.idEntreprise <> :idEntreprise")
    int countAllByTelephone1EntrepriseExcept(@Param("telephone") String telephone, @Param("idEntreprise") Integer idEntreprise);

    @Query("SELECT COUNT(e) FROM Entreprise e WHERE e.telephone2Entreprise = :telephone AND e.idEntreprise <> :idEntreprise")
    int countAllByTelephone2EntrepriseExcept(@Param("telephone") String telephone, @Param("idEntreprise") Integer idEntreprise);

}
