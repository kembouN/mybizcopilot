package com.mybizcopilot.services.impl;

import com.mybizcopilot.entities.Typeprospect;
import com.mybizcopilot.repositories.TypeprospectRepository;
import com.mybizcopilot.services.ITypeprospectService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class TypeprospectService implements ITypeprospectService {

    @Autowired
    private TypeprospectRepository typeprospectRepository;
    @Override
    public List<Typeprospect> getAllTypeprospect() {
        return typeprospectRepository.findAll();
    }
}
