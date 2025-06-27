package com.mybizcopilot.repositories;

import com.mybizcopilot.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {

    Optional<Utilisateur> findByUsername(String username);

    int countByUsername(String username);

    @Query("SELECT COUNT(u) FROM Utilisateur u WHERE u.username = :username AND u.idUtilisateur <> :idUser")
    int countByUsernamExceptUser(@Param("idUser") Integer idUser, @Param("username") String username);

}
