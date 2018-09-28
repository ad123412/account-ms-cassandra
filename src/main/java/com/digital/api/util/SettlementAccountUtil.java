package com.digital.api.util;

import com.digital.api.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class SettlementAccountUtil {

    @Autowired
    private CommonUtil commonUtil;

    public SettlementAccount populateSettlementAccount(SettlementAccount settlementAccount){

        BillStructure billStructure = settlementAccount.getBillStructure();
        CompletableFuture<List<BillPresentationMediaRef>> listOfBillPreListCompletableFuture =
                commonUtil.populateBillPresentationMedia(billStructure.getPresentationMedia());
        Mono<List<BillPresentationMediaRef>> billPreListMono = Mono.fromFuture(listOfBillPreListCompletableFuture);

        CompletableFuture<List<Contact>> listOfContactCompletableFuture =
                commonUtil.retrieveContacts(settlementAccount.getContact());
        Mono<List<Contact>> contactMono = Mono.fromFuture(listOfContactCompletableFuture);

        CompletableFuture<List<RelatedPartyRef>> listOfRelatedPartyCompletableFuture =
                commonUtil.retrieveRelatedPartyRefs(settlementAccount.getRelatedParty());
        Mono<List<RelatedPartyRef>> relatedPartyRefMono = Mono.fromFuture(listOfRelatedPartyCompletableFuture);

        CompletableFuture<BillingCycleSpecificationRef> billingCycleSpecificationRefCompletableFuture =
                commonUtil.populateBillingCycleSpecification(billStructure.getCycleSpecification());
        Mono<BillingCycleSpecificationRef> billingCycleSpecificationRefMono =
                Mono.fromFuture(billingCycleSpecificationRefCompletableFuture);

        CompletableFuture<BillFormatRef> billFormatRefCompletableFuture =
                commonUtil.populateBillFormat(billStructure.getFormat());
        Mono<BillFormatRef> billFormatRefMono = Mono.fromFuture(billFormatRefCompletableFuture);

        return Mono.zip(contactMono, relatedPartyRefMono, billPreListMono, billFormatRefMono, billingCycleSpecificationRefMono)
                .map(tuple -> {

                    List<Contact> contactFinalList = tuple.getT1();
                    settlementAccount.setContact(contactFinalList);

                    List<RelatedPartyRef> relatedRefFinalList = tuple.getT2();
                    settlementAccount.setRelatedParty(relatedRefFinalList);

                    List<BillPresentationMediaRef> billPresentationMediaFinalList = tuple.getT3();
                    billStructure.setPresentationMedia(billPresentationMediaFinalList);

                    BillingCycleSpecificationRef billingCycleSpecificationFinal = tuple.getT5();
                    billStructure.setCycleSpecification(billingCycleSpecificationFinal);

                    BillFormatRef billFormatFinal = tuple.getT4();
                    billStructure.setFormat(billFormatFinal);

                    return settlementAccount;
                }).block();
    }
}
