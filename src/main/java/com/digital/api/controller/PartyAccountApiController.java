package com.digital.api.controller;

import com.digital.api.model.PartyAccount;
import com.digital.api.service.PartyAccountServiceImpl;
import com.digital.api.util.PartyAccountUtil;
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
public class PartyAccountApiController {

    @Autowired
    private PartyAccountServiceImpl partyAccountService;

    @Autowired
    private PartyAccountUtil partyAccountUtil;

    @ApiResponses(value = {
    @ApiResponse(code = 200, message = "Ok", response = PartyAccount.class),
    @ApiResponse(code = 400, message = "Bad Request", response = PartyAccount.class),
    @ApiResponse(code = 401, message = "Unauthorized", response = PartyAccount.class),
    @ApiResponse(code = 403, message = "Forbidden", response = PartyAccount.class),
    @ApiResponse(code = 404, message = "Not Found", response = PartyAccount.class),
    @ApiResponse(code = 405, message = "Method Not allowed", response = PartyAccount.class),
    @ApiResponse(code = 409, message = "Conflict", response = PartyAccount.class),
    @ApiResponse(code = 500, message = "Internal Server Error", response = PartyAccount.class) })
    @GetMapping(value = "/partyAccount", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public Flux<PartyAccount> listPartyAccount(@ApiParam(value = "Comma separated properties to display in response") @RequestParam(value = "fields", required = false) String fields
        , @ApiParam(value = "Requested index for start of resources to be provided in response") @RequestParam(value = "offset", required = false) Integer offset
        , @ApiParam(value = "Requested number of resources to be provided in response") @RequestParam(value = "limit", required = false) Integer limit
    ) {

        return partyAccountService.getAllPartyAccounts()
                .map(partyAccount -> partyAccountUtil.populatePartyAccount(partyAccount));
    }


    @ApiResponses(value = {
    @ApiResponse(code = 200, message = "Ok", response = PartyAccount.class),
    @ApiResponse(code = 400, message = "Bad Request", response = PartyAccount.class),
    @ApiResponse(code = 401, message = "Unauthorized", response = PartyAccount.class),
    @ApiResponse(code = 403, message = "Forbidden", response = PartyAccount.class),
    @ApiResponse(code = 404, message = "Not Found", response = PartyAccount.class),
    @ApiResponse(code = 405, message = "Method Not allowed", response = PartyAccount.class),
    @ApiResponse(code = 409, message = "Conflict", response = PartyAccount.class),
    @ApiResponse(code = 500, message = "Internal Server Error", response = PartyAccount.class) })
    @GetMapping(value = "/partyAccount/{id}", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public Flux<PartyAccount> retrievePartyAccount(
        @ApiParam(value = "Identifier of the Party Account",required=true ) @PathVariable("id") String id
    ) {

        return partyAccountService.getPartyAccountById(id)
                .flatMapMany(partyAccount -> Flux.just(partyAccountUtil.populatePartyAccount(partyAccount)));
    }

}
