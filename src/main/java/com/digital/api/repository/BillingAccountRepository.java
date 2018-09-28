package com.digital.api.repository;

import com.digital.api.model.BillingAccount;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillingAccountRepository extends ReactiveCassandraRepository<BillingAccount, String> {
}
