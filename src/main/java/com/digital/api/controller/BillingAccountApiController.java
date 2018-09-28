package com.digital.api.controller;

import com.digital.api.model.BillingAccount;
import com.digital.api.service.BillAccountServiceImpl;
import com.digital.api.util.BillAccountUtil;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class BillingAccountApiController {

    @Autowired
    private BillAccountServiceImpl billAccountService;

    @Autowired
    private BillAccountUtil billAccountUtil;

    @ApiResponses(value = {
    @ApiResponse(code = 200, message = "Ok", response = BillingAccount.class),
    @ApiResponse(code = 400, message = "Bad Request", response = BillingAccount.class),
    @ApiResponse(code = 401, message = "Unauthorized", response = BillingAccount.class),
    @ApiResponse(code = 403, message = "Forbidden", response = BillingAccount.class),
    @ApiResponse(code = 404, message = "Not Found", response = BillingAccount.class),
    @ApiResponse(code = 405, message = "Method Not allowed", response = BillingAccount.class),
    @ApiResponse(code = 409, message = "Conflict", response = BillingAccount.class),
    @ApiResponse(code = 500, message = "Internal Server Error", response = BillingAccount.class)})
    @GetMapping(value = "/billingAccount", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public Flux<BillingAccount> listBillingAccount(@ApiParam(value = "Comma separated properties to display in response") @RequestParam(value = "fields", required = false) String fields
            , @ApiParam(value = "Requested index for start of resources to be provided in response") @RequestParam(value = "offset", required = false) Integer offset
            , @ApiParam(value = "Requested number of resources to be provided in response") @RequestParam(value = "limit", required = false) Integer limit
    ) {

        return billAccountService.getAllBillingAccount()
                .map(billingAccount -> billAccountUtil.populateBillingAccount(billingAccount));
    }


    @ApiResponses(value = {
    @ApiResponse(code = 200, message = "Ok", response = BillingAccount.class),
    @ApiResponse(code = 400, message = "Bad Request", response = BillingAccount.class),
    @ApiResponse(code = 401, message = "Unauthorized", response = BillingAccount.class),
    @ApiResponse(code = 403, message = "Forbidden", response = BillingAccount.class),
    @ApiResponse(code = 404, message = "Not Found", response = BillingAccount.class),
    @ApiResponse(code = 405, message = "Method Not allowed", response = BillingAccount.class),
    @ApiResponse(code = 409, message = "Conflict", response = BillingAccount.class),
    @ApiResponse(code = 500, message = "Internal Server Error", response = BillingAccount.class)})
    @GetMapping(value = "/billingAccount/{id}", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public Flux<BillingAccount> retrieveBillingAccount(
            @ApiParam(value = "Identifier of the Billing Account", required = true) @PathVariable("id") String id) {

        return billAccountService.getBillingAccountById(id)
                .map(billingAccount -> billAccountUtil.populateBillingAccount(billingAccount))
                .flatMapMany(Flux::just);
    }

}