package com.digital.api.util;

import com.digital.api.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class BillAccountUtil {

    @Autowired
    private CommonUtil commonUtil;

    public BillingAccount populateBillingAccount(BillingAccount billingAccount){

        CompletableFuture<List<RelatedPartyRef>> listOfRelatedPartyCompletableFuture =
                commonUtil.retrieveRelatedPartyRefs(billingAccount.getRelatedParty());
        Mono<List<RelatedPartyRef>> relatedPartyRefMono = Mono.fromFuture(listOfRelatedPartyCompletableFuture);

        CompletableFuture<List<Contact>> listOfContactsCompletableFuture =
                commonUtil.retrieveContacts(billingAccount.getContact());
        Mono<List<Contact>> contactMono = Mono.fromFuture(listOfContactsCompletableFuture);

        BillStructure billStructure = billingAccount.getBillStructure();
        CompletableFuture<List<BillPresentationMediaRef>> listOfBillPresentationMediaCompletableFuture =
                commonUtil.populateBillPresentationMedia(billStructure.getPresentationMedia());
        Mono<List<BillPresentationMediaRef>> billPreListMono = Mono.fromFuture(listOfBillPresentationMediaCompletableFuture);

        CompletableFuture<BillingCycleSpecificationRef> billingCycleSpecificationRefCompletableFuture =
                commonUtil.populateBillingCycleSpecification(billStructure.getCycleSpecification());
        Mono<BillingCycleSpecificationRef> billingCycleSpecificationRefMono =
                Mono.fromFuture(billingCycleSpecificationRefCompletableFuture);

        CompletableFuture<BillFormatRef> billFormatRefCompletableFuture =
                commonUtil.populateBillFormat(billStructure.getFormat());
        Mono<BillFormatRef> billFormatRefMono = Mono.fromFuture(billFormatRefCompletableFuture);

        return Mono.zip(relatedPartyRefMono,contactMono, billPreListMono,
                billingCycleSpecificationRefMono,billFormatRefMono)
                .map(tuple -> {

                    List<Contact> contactFinalList = tuple.getT2();
                    billingAccount.setContact(contactFinalList);

                    List<RelatedPartyRef> relatedRefFinalList = tuple.getT1();
                    billingAccount.setRelatedParty(relatedRefFinalList);

                    List<BillPresentationMediaRef> billPresentationMediaFinalList = tuple.getT3();
                    billStructure.setPresentationMedia(billPresentationMediaFinalList);

                    BillingCycleSpecificationRef billingCycleSpecificationFinal = tuple.getT4();
                    billStructure.setCycleSpecification(billingCycleSpecificationFinal);

                    BillFormatRef billFormatFinal = tuple.getT5();
                    billStructure.setFormat(billFormatFinal);

                    return billingAccount;
                }).block();
    }
}
