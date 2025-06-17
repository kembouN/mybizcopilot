package com.mybizcopilot.repositories;

import com.mybizcopilot.entities.Client;
import com.mybizcopilot.entities.Entreprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer>, JpaSpecificationExecutor<Client> {

    List<Client> findAllByEntreprise(Entreprise entreprise);

    @Query("SELECT COUNT(c) FROM Client c WHERE c.telephoneUn = :telephone")
    int countAllByTelephoneUnIs(@Param("telephone") String telephone);

    @Query("SELECT COUNT(c) FROM Client c WHERE c.telephoneDeux = :tel")
    int countAllByTelephoneDeux(@Param("tel") String tel);

    @Query("SELECT COUNT(c) FROM Client c WHERE c.telephoneUn = :tel AND c.idClient <> :idClient")
    int countAllByTelephoneUnExceptClient(@Param("tel") String tel, @Param("idClient") Integer idClient);

    @Query("SELECT COUNT(c) FROM Client c WHERE c.telephoneDeux = :tel AND c.idClient <> :idClient")
    int countAllByTelephoneDeuxExceptClient(@Param("tel") String tel, @Param("idClient") Integer idClient);
}
