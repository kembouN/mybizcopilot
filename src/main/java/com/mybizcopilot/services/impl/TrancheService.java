package com.mybizcopilot.services.impl;

import com.mybizcopilot.entities.Tranche;
import com.mybizcopilot.repositories.TrancheRepository;
import com.mybizcopilot.services.ITrancheService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TrancheService implements ITrancheService {

    @Autowired
    private TrancheRepository trancheRepository;

    @Override
    public List<Tranche> getAllTranches() {
        return trancheRepository.findAll();
    }
}
