package com.digital.api.service;

import com.digital.api.model.PartyAccount;
import com.digital.api.repository.PartyAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Service
public class PartyAccountServiceImpl implements PartyAccountService {

    @Autowired
    private PartyAccountRepository partyAccountRepository;


    @Override
    public Mono<PartyAccount> getPartyAccountById(String id) {
        return partyAccountRepository.findById(id);
    }

    @Override
    public Flux<PartyAccount> getAllPartyAccounts() {
        return partyAccountRepository.findAll();
    }
}
