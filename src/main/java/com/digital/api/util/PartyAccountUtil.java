package com.digital.api.util;

import com.digital.api.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class PartyAccountUtil {

    @Autowired
    private CommonUtil commonUtil;

    public PartyAccount populatePartyAccount(PartyAccount partyAccount){

        CompletableFuture<List<Contact>> listOfContactCompletableFuture =
                commonUtil.retrieveContacts(partyAccount.getContact());
        Mono<List<Contact>> contactMono = Mono.fromFuture(listOfContactCompletableFuture);

        CompletableFuture<List<RelatedPartyRef>> listOfRelatedPartyCompletableFuture =
                commonUtil.retrieveRelatedPartyRefs(partyAccount.getRelatedParty());
        Mono<List<RelatedPartyRef>> relatedPartyRefMono = Mono.fromFuture(listOfRelatedPartyCompletableFuture);

        BillStructure billStructure = partyAccount.getBillStructure();
        CompletableFuture<List<BillPresentationMediaRef>> listOfBillPreListCompletableFuture =
                commonUtil.populateBillPresentationMedia(billStructure.getPresentationMedia());
        Mono<List<BillPresentationMediaRef>> billPreListMono = Mono.fromFuture(listOfBillPreListCompletableFuture);

        CompletableFuture<BillingCycleSpecificationRef> billingCycleSpecificationRefCompletableFuture =
                commonUtil.populateBillingCycleSpecification(billStructure.getCycleSpecification());
        Mono<BillingCycleSpecificationRef> billingCycleSpecificationRefMono =
                Mono.fromFuture(billingCycleSpecificationRefCompletableFuture);

        CompletableFuture<BillFormatRef> billFormatRefCompletableFuture =
                commonUtil.populateBillFormat(billStructure.getFormat());
        Mono<BillFormatRef> billFormatRefMono = Mono.fromFuture(billFormatRefCompletableFuture);

        return Mono.zip(contactMono, relatedPartyRefMono, billPreListMono, billingCycleSpecificationRefMono,billFormatRefMono)
                .map(tuple -> {

                    List<Contact> contactFinalList = tuple.getT1();
                    partyAccount.setContact(contactFinalList);

                    List<RelatedPartyRef> relatedRefFinalList = tuple.getT2();
                    partyAccount.setRelatedParty(relatedRefFinalList);

                    List<BillPresentationMediaRef> billPresentationMediaFinalList = tuple.getT3();
                    billStructure.setPresentationMedia(billPresentationMediaFinalList);

                    BillingCycleSpecificationRef billingCycleSpecificationFinal = tuple.getT4();
                    billStructure.setCycleSpecification(billingCycleSpecificationFinal);

                    BillFormatRef billFormatFinal = tuple.getT5();
                    billStructure.setFormat(billFormatFinal);

                    return partyAccount;
                }).block();
    }
}
