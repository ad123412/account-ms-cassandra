package com.digital.api.controller;

import com.digital.api.model.FinancialAccount;
import com.digital.api.service.FinancialAccountServiceImpl;
import com.digital.api.util.FinancialAccountUtil;
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
public class FinancialAccountApiController {

    @Autowired
    private FinancialAccountServiceImpl financialAccountService;

    @Autowired
    private FinancialAccountUtil financialAccountUtil;

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok", response = FinancialAccount.class),
            @ApiResponse(code = 400, message = "Bad Request", response = FinancialAccount.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = FinancialAccount.class),
            @ApiResponse(code = 403, message = "Forbidden", response = FinancialAccount.class),
            @ApiResponse(code = 404, message = "Not Found", response = FinancialAccount.class),
            @ApiResponse(code = 405, message = "Method Not allowed", response = FinancialAccount.class),
            @ApiResponse(code = 409, message = "Conflict", response = FinancialAccount.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = FinancialAccount.class) })
    @GetMapping(value = "/financialAccount", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public Flux<FinancialAccount> listFinancialAccount(@ApiParam(value = "Comma separated properties to display in response") @RequestParam(value = "fields", required = false) String fields
        ,@ApiParam(value = "Requested index for start of resources to be provided in response") @RequestParam(value = "offset", required = false) Integer offset
        ,@ApiParam(value = "Requested number of resources to be provided in response") @RequestParam(value = "limit", required = false) Integer limit
    ) {

        return financialAccountService.getAllFinancialAccount()
                .map(financialAccount -> financialAccountUtil.populateFinancialAccount(financialAccount));
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok", response = FinancialAccount.class),
            @ApiResponse(code = 400, message = "Bad Request", response = FinancialAccount.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = FinancialAccount.class),
            @ApiResponse(code = 403, message = "Forbidden", response = FinancialAccount.class),
            @ApiResponse(code = 404, message = "Not Found", response = FinancialAccount.class),
            @ApiResponse(code = 405, message = "Method Not allowed", response = FinancialAccount.class),
            @ApiResponse(code = 409, message = "Conflict", response = FinancialAccount.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = FinancialAccount.class) })
    @GetMapping(value = "/financialAccount/{id}", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public Flux<FinancialAccount> retrieveFinancialAccount(
        @ApiParam(value = "Identifier of the Financial Account",required=true ) @PathVariable("id") String id
    ){

        return financialAccountService.getFinancialAccountById(id)
                .flatMapMany(financialAccount -> Flux.just(financialAccountUtil.populateFinancialAccount(financialAccount)));
    }

}
