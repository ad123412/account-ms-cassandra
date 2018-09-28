package com.digital.api.repository;

import com.digital.api.model.PartyAccount;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartyAccountRepository extends ReactiveCassandraRepository<PartyAccount, String> {
}
