package com.example.aadhiStore.service;

import com.example.aadhiStore.dto.InvalidInput;
import com.example.aadhiStore.entity.BrokerMaster;
import com.example.aadhiStore.repository.BrokerMasterRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrokerMasterService {

    @Autowired
    private final BrokerMasterRepository brokerMasterRepository;

    public BrokerMasterService(BrokerMasterRepository brokerMasterRepository) {
        this.brokerMasterRepository = brokerMasterRepository;
    }


    public List<BrokerMaster> getAllBrokers() {
        return brokerMasterRepository.findAll();
    }

    public BrokerMaster getBrokerById(Long id) {
        return brokerMasterRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Broker not found" +id));
    }

    public BrokerMaster createBroker(BrokerMaster brokerMaster) {
        return brokerMasterRepository.save(brokerMaster);
    }

    public BrokerMaster updateBroker(Long id, BrokerMaster brokerMaster) {
        if (brokerMasterRepository.existsById(id)) {
            brokerMaster.setId(id);
            return brokerMasterRepository.save(brokerMaster);
        }
        else {
            throw new InvalidInput("Broker not found for this id" +id);
        }
    }

    public void deleteBroker(Long id) {
        brokerMasterRepository.deleteById(id);
    }
}
