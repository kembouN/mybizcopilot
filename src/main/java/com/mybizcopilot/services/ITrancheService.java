package com.mybizcopilot.services;

import com.mybizcopilot.entities.Tranche;
import com.mybizcopilot.repositories.TrancheRepository;

import java.util.List;

public interface ITrancheService {

    List<Tranche> getAllTranches();
}
