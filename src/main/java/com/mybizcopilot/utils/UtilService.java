package com.mybizcopilot.utils;

import com.mybizcopilot.entities.Utilisateur;
import com.mybizcopilot.exception.OperationNonPermittedException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Random;

@Service
@AllArgsConstructor
public class UtilService {

    public Integer generateActivationCode(){return new Random().nextInt(100000, 999999);}

    public String generateUserStrongPasswordReset(Utilisateur user){
        String[] userPseudo = user.getUsername().split("[.@]+");
        int year = user.getCreatedAt().getYear();
        Integer month = user.getCreatedAt().getMonthValue();
        return "!"+""+ Arrays.toString(userPseudo) +""+year+""+generateActivationCode();
    }


    public String generateEnterpriseCode(){
        int aleatoire = new Random().nextInt(100, 999);
        int year = LocalDate.now().getYear();
        int month = LocalDate.now().getMonthValue();
        int day = LocalDate.now().getDayOfMonth();
        return "ET"+""+year+""+month+""+day+""+aleatoire;
    }

    public String generateClientCode(){
        int year = LocalDate.now().getYear();
        int month = LocalDate.now().getMonthValue();
        int day = LocalDate.now().getDayOfMonth();
        int aleatoire = new Random().nextInt(100, 999);
        return "CLI"+""+year+""+month+""+day+""+aleatoire;
    }


    public void checkPasswordConfirmation(String pwd, String cPass) {
        if (!pwd.equals(cPass)){
            throw new OperationNonPermittedException("La confirmation du mot de passe est incorrecte");
        }
    }

}
