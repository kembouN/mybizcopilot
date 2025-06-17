package com.mybizcopilot.services.impl;

import com.mybizcopilot.entities.Client;
import com.mybizcopilot.entities.Pays;
import com.mybizcopilot.entities.Typeprospect;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class ClientSpecification {

    public static Specification<Client> clientNameEquals(String name) {
        return ((root, query, criteriaBuilder) -> {
            if (name == null || name.isEmpty())
                return null;
            return criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("nomClient")), "%" + name.toLowerCase() + "%"),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("agentLiaison")), "%" + name.toLowerCase() + "%")
            );
        });
    }

    public static Specification<Client> typeCLientEquels(String typeClient) {
        return ((root, query, criteriaBuilder) -> {
           if (typeClient == null || typeClient.isEmpty())
               return null;

           return criteriaBuilder.like(root.get("typeClient"), "%"+typeClient+"%");
        });
    }

    public static Specification<Client> localisationEquals(String localisation) {
        return ((root, query, criteriaBuilder) -> {
            if (localisation == null || localisation.isEmpty())
                return null;

            Join<Client, Pays> clientPaysJoin = root.join("pays", JoinType.INNER);
            return criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("ville")), "%"+localisation.toLowerCase()+"%"),
                    criteriaBuilder.like(criteriaBuilder.lower(clientPaysJoin.get("libellePays")), "%"+localisation.toLowerCase()+"%"),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("adresse")), "%"+localisation.toLowerCase()+"%")
            );
        });
    }

    public static Specification<Client> statutClientEquals(Typeprospect typeprospect) {
        return ((root, query, criteriaBuilder) -> {
            if (typeprospect == null)
                return null;

            return criteriaBuilder.or(
                    criteriaBuilder.equal(root.get("typeProspect"), typeprospect)
            );
        });
    }
}
