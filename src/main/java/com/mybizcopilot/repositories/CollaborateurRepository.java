package com.mybizcopilot.repositories;

import com.mybizcopilot.entities.Collaborateur;
import com.mybizcopilot.entities.Entreprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollaborateurRepository extends JpaRepository<Collaborateur, Integer> {

    List<Collaborateur> findAllByEntreprise(Entreprise entreprise);

    @Query("SELECT COUNT(c) FROM Collaborateur c WHERE c.telephoneUn = :tel")
    int countAllByTelephoneUn(@Param("tel") String tel);

    @Query("SELECT COUNT(c) FROM Collaborateur c WHERE c.telephoneDeux = :tel")
    int countAllByTelephoneDeux(@Param("tel") String tel);

    @Query("SELECT COUNT(c) FROM Collaborateur c WHERE c.telephoneUn = :tel AND c.idCollaborateur <> :idCollaborateur")
    int countAllByTelephoneUnExcept(@Param("tel") String tel);

    @Query("SELECT COUNT(c) FROM Collaborateur c WHERE c.telephoneDeux = :tel AND c.idCollaborateur <> :idCollaborateur")
    int countAllByTelephoneDeuxExcept(@Param("tel") String tel, @Param("idCollaborateur") Integer idCollaborateur);
}
