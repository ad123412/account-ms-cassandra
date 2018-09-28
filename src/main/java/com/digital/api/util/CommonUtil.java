package com.digital.api.util;

import com.digital.api.model.*;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class CommonUtil {

    @Value("${contact-ms.base-url}")
    private String contactMsBaseUrl;

    @Value("${billing-ms.base-url}")
    private String billMsBaseUrl;

    public CompletableFuture<List<Contact>> retrieveContacts(List<Contact> financialAccountContact){

        return CompletableFuture.<List<Contact>>supplyAsync(

                () -> {
                    List<Contact> contacts = new ArrayList<>();
                    financialAccountContact.stream()
                            .forEach(
                                    contact -> {
                                        contacts.addAll(getContacts(contact));
                                    }
                            );
                    return contacts;
                }
        )  ;
    }

    @HystrixCommand(fallbackMethod = "contactsFallback")
    public List<Contact> getContacts(Contact contact){
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(contactMsBaseUrl);
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.TEMPLATE_AND_VALUES);
        WebClient client = WebClient.builder().uriBuilderFactory(factory).build();
        List<Contact> contactsList = client.get()
                .uri("/account/contact/" + contact.getId()).accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(Contact.class)
                .collectList()
                .block();
        return contactsList;
    }

    public List<Contact> contactsFallback(List<Contact> accountContactList){{
        System.out.println("inside contactsFallback fallback >>>> " + accountContactList);
        return accountContactList;
    }}

    public CompletableFuture<List<RelatedPartyRef>> retrieveRelatedPartyRefs(List<RelatedPartyRef> relatedPartyRefList){

        return CompletableFuture.<List<RelatedPartyRef>>supplyAsync(
                () -> {
                    List<RelatedPartyRef> finalRelatedPartyRefs = new ArrayList<>();
                    relatedPartyRefList.stream()
                            .forEach(
                                    relatedPartyRef -> {
                                        finalRelatedPartyRefs.addAll(getRelatedParty(relatedPartyRef));
                                    }
                            );
                    return finalRelatedPartyRefs;
                }
        );
    }

    @HystrixCommand(fallbackMethod = "relatedPartyFallback")
    public List<RelatedPartyRef> getRelatedParty(RelatedPartyRef relatedPartyRef){

        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(contactMsBaseUrl);
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.TEMPLATE_AND_VALUES);
        WebClient client = WebClient.builder().uriBuilderFactory(factory).build();
        Flux<RelatedPartyRef> flux = client.get()
                .uri("/account/contact/relatedParty/" + relatedPartyRef.getId())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(RelatedPartyRef.class);
        Mono<List<RelatedPartyRef>> relPartyRefMono = flux.collectList();
        List<RelatedPartyRef> partyRefLst = relPartyRefMono.block();
        return  partyRefLst;
    }

    public List<RelatedPartyRef> relatedPartyFallback(List<RelatedPartyRef> relatedPartyRefList){{
        System.out.println("inside relatedPartyFallback fallback >>>> " + relatedPartyRefList);
        return relatedPartyRefList;
    }}

    public CompletableFuture<List<BillPresentationMediaRef>> populateBillPresentationMedia
            (List<BillPresentationMediaRef> billPresentationMediaRefList){

        return CompletableFuture.<List<BillPresentationMediaRef>>supplyAsync(
                () -> {
                    List<BillPresentationMediaRef> finalBillPresentationMediaRefs = new ArrayList<>();
                    billPresentationMediaRefList.stream()
                            .forEach(
                                    presentationMediaRef -> {
                                        finalBillPresentationMediaRefs.addAll(
                                                getListOfBillPresentationMedia(presentationMediaRef));
                                    }
                            );
                    return finalBillPresentationMediaRefs;
                }
        );
    }

    @HystrixCommand(fallbackMethod = "billPresentationMediaFallback")
    public List<BillPresentationMediaRef> getListOfBillPresentationMedia(BillPresentationMediaRef presentationMediaRef){

        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(billMsBaseUrl);
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.TEMPLATE_AND_VALUES);
        WebClient client = WebClient.builder().uriBuilderFactory(factory).build();
        Flux<BillPresentationMediaRef> flux = client.get()
                .uri("/billPresentationMedia/" + presentationMediaRef.getId())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(BillPresentationMediaRef.class);
        Mono<List<BillPresentationMediaRef>> billListMono = flux.collectList();
        List<BillPresentationMediaRef> billRefLst = billListMono.block();
        return billRefLst;
    }

    public List<BillPresentationMediaRef> billPresentationMediaFallback(
            List<BillPresentationMediaRef> billPresentationMediaRefList){{
        return billPresentationMediaRefList;
    }}

    public CompletableFuture<BillingCycleSpecificationRef> populateBillingCycleSpecification
            (BillingCycleSpecificationRef billingCycleSpecificationRef){

        return CompletableFuture.<BillingCycleSpecificationRef>supplyAsync(
                () -> {
                        return getBillingCycleSpecification(billingCycleSpecificationRef);
                }
        );
    }

    @HystrixCommand(fallbackMethod = "billingCycleSpecificationFallback")
    public BillingCycleSpecificationRef getBillingCycleSpecification(BillingCycleSpecificationRef billingCycleSpecificationRef){

        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(billMsBaseUrl);
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.TEMPLATE_AND_VALUES);
        WebClient client = WebClient.builder().uriBuilderFactory(factory).build();
        Mono<List<BillingCycleSpecificationRef>> mono = client.get()
                .uri("/billingCycleSpecification/" + billingCycleSpecificationRef.getId())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(BillingCycleSpecificationRef.class).collectList();
        List<BillingCycleSpecificationRef> finaBillingCycleSpecificationRefs =
                mono.block();
        if(finaBillingCycleSpecificationRefs != null && finaBillingCycleSpecificationRefs.size() > 0){
            return finaBillingCycleSpecificationRefs.get(0);
        }
        return null;
    }

    public BillingCycleSpecificationRef billingCycleSpecificationFallback(
            BillingCycleSpecificationRef billingCycleSpecificationRef){{
        return billingCycleSpecificationRef;
    }}

    public CompletableFuture<BillFormatRef> populateBillFormat
            (BillFormatRef billFormatRef){

        return CompletableFuture.<BillFormatRef>supplyAsync(
                () -> {
                    return getBillFormat(billFormatRef);
                }
        );
    }

    @HystrixCommand(fallbackMethod = "billFormatFallback")
    public BillFormatRef getBillFormat(BillFormatRef billFormatRef){
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(billMsBaseUrl);
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.TEMPLATE_AND_VALUES);
        WebClient client = WebClient.builder().uriBuilderFactory(factory).build();
        Mono<List<BillFormatRef>> billFormatRefFinalMonoList = client.get()
                .uri("/billFormat/" + billFormatRef.getId())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(BillFormatRef.class).collectList();
        List<BillFormatRef> finalListBillFormatRef = billFormatRefFinalMonoList.block();
        if(finalListBillFormatRef != null && finalListBillFormatRef.size() > 0){
            return finalListBillFormatRef.get(0);
        }
        return null;
    }

    public BillFormatRef billFormatFallback(
            BillFormatRef billFormatRef){{
        return billFormatRef;
    }}
}
