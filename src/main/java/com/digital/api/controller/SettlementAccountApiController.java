package com.digital.api.controller;

import com.digital.api.model.SettlementAccount;
import com.digital.api.service.SettlementAccountServiceImpl;
import com.digital.api.util.SettlementAccountUtil;
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
public class SettlementAccountApiController{

    @Autowired
    private SettlementAccountServiceImpl settlementAccountService;

    @Autowired
    private SettlementAccountUtil settlementAccountUtil;

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok", response = SettlementAccount.class),
            @ApiResponse(code = 400, message = "Bad Request", response = SettlementAccount.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = SettlementAccount.class),
            @ApiResponse(code = 403, message = "Forbidden", response = SettlementAccount.class),
            @ApiResponse(code = 404, message = "Not Found", response = SettlementAccount.class),
            @ApiResponse(code = 405, message = "Method Not allowed", response = SettlementAccount.class),
            @ApiResponse(code = 409, message = "Conflict", response = SettlementAccount.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SettlementAccount.class) })
    @GetMapping(value = "/settlementAccount", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public Flux<SettlementAccount> listSettlementAccount(@ApiParam(value = "Comma separated properties to display in response") @RequestParam(value = "fields", required = false) String fields
        , @ApiParam(value = "Requested index for start of resources to be provided in response") @RequestParam(value = "offset", required = false) Integer offset
            , @ApiParam(value = "Requested number of resources to be provided in response") @RequestParam(value = "limit", required = false) Integer limit
    ) {

        return settlementAccountService.getAllSettlementAccounts()
                .map(settlementAccount -> settlementAccountUtil.populateSettlementAccount(settlementAccount));
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok", response = SettlementAccount.class),
            @ApiResponse(code = 400, message = "Bad Request", response = SettlementAccount.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = SettlementAccount.class),
            @ApiResponse(code = 403, message = "Forbidden", response = SettlementAccount.class),
            @ApiResponse(code = 404, message = "Not Found", response = SettlementAccount.class),
            @ApiResponse(code = 405, message = "Method Not allowed", response = SettlementAccount.class),
            @ApiResponse(code = 409, message = "Conflict", response = SettlementAccount.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SettlementAccount.class) })
    @GetMapping(value = "/settlementAccount/{id}", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public Flux<SettlementAccount> retrieveSettlementAccount(
        @ApiParam(value = "Identifier of the Settlement Account",required=true ) @PathVariable("id") String id
    ) {

        return settlementAccountService.getSettlementAccountById(id)
                .map(settlementAccount -> settlementAccountUtil.populateSettlementAccount(settlementAccount))
                .flatMapMany(Flux::just);
    }

}
