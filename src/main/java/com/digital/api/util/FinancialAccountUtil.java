package com.digital.api.util;

import com.digital.api.model.Contact;
import com.digital.api.model.FinancialAccount;
import com.digital.api.model.RelatedPartyRef;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class FinancialAccountUtil {

    @Autowired
    private CommonUtil commonUtil;

    public FinancialAccount populateFinancialAccount(FinancialAccount financialAccount){

        CompletableFuture<List<Contact>> listOfContactCompletableFuture =
                commonUtil.retrieveContacts(financialAccount.getContact());
        Mono<List<Contact>> contactMono = Mono.fromFuture(listOfContactCompletableFuture);

        CompletableFuture<List<RelatedPartyRef>> listOfRelatedPartyCompletableFuture =
                commonUtil.retrieveRelatedPartyRefs(financialAccount.getRelatedParty());
        Mono<List<RelatedPartyRef>> relatedPartyRefMono = Mono.fromFuture(listOfRelatedPartyCompletableFuture);

        return Mono.zip(contactMono, relatedPartyRefMono)
                        .map(tuple -> {

                            List<Contact> contactFinalList = tuple.getT1();
                            financialAccount.setContact(contactFinalList);

                            List<RelatedPartyRef> relatedRefFinalList = tuple.getT2();
                            financialAccount.setRelatedParty(relatedRefFinalList);

                            return financialAccount;
                        }).block();
    }



}
