package com.digital.api.service;

import com.digital.api.model.BillingAccount;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BillAccountService {

    public Mono<BillingAccount> getBillingAccountById(String id);

    public Flux<BillingAccount> getAllBillingAccount();
}
