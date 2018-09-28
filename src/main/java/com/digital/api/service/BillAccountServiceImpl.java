package com.digital.api.service;

import com.digital.api.model.BillingAccount;
import com.digital.api.repository.BillingAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BillAccountServiceImpl implements BillAccountService {

    @Autowired
    private BillingAccountRepository billingAccountRepository;

    @Override
    public Mono<BillingAccount> getBillingAccountById(String id) {
        return billingAccountRepository.findById(id);
    }

    @Override
    public Flux<BillingAccount> getAllBillingAccount() {
        return billingAccountRepository.findAll();
    }
}
