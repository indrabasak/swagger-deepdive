package com.basaki.example.menagerie.controller.animal;

import com.basaki.example.menagerie.model.Gender;
import com.basaki.example.menagerie.model.MenagerieRequest;
import com.basaki.example.menagerie.model.animal.Tiger;
import com.basaki.example.menagerie.service.TigerService;
import com.basaki.example.menagerie.swagger.annotation.ApiOperationSince;
import com.basaki.example.menagerie.swagger.annotation.Classification;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static com.basaki.example.menagerie.swagger.plugin.UiidAndDateParameterBuilder.TYPE_ISO_DATE_TIME;
import static com.basaki.example.menagerie.swagger.plugin.UiidAndDateParameterBuilder.TYPE_UUID;

/**
 * {@code TigerController} is the spring REST controller for Tiger API.
 * Exposes all CRUD operations on tiger.
 * <p>
 *
 * @author Indra Basak
 * @since 4/29/17
 */
@SuppressWarnings("MVCPathVariableInspection")
@RestController
@Slf4j
@Api(value = "Tiger API",
        description = "Tiger API",
        produces = "application/json", tags = {"A2"})
@Classification(kingdom = "Animalia", phylum = "Chordata", clazz = "Mammalia",
        order = "Carnivora", family = "Felidae", genus = "Panthera",
        species = "Panthera tigris")
public class TigerController {
    private static final String TIGER_URL = "/tigers";

    private static final String TIGER_BY_ID_URL = TIGER_URL + "/{id}";

    private static final String HEADER_TXN_ID = "X-TXN-ID";

    private static final String HEADER_TXN_DATE = "X-TXN-DATE";

    private TigerService service;

    @Autowired
    public TigerController(TigerService service) {
        this.service = service;
    }

    @ApiOperation(
            value = "Creates a Tiger.",
            notes = "Requires a Tiger name and gender.",
            response = Tiger.class)
    @ApiResponses({
            @ApiResponse(code = 201, response = Tiger.class,
                    message = "Tiger created successfully")})
    @ApiOperationSince(value = "1.0", description = "Release 1.0")
    @RequestMapping(method = RequestMethod.POST, value = TIGER_URL,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Tiger create(
            @ApiParam(value = "Transaction ID as UUID", defaultValue = TYPE_UUID)
            @RequestHeader(HEADER_TXN_ID) UUID txnId,
            @ApiParam(value = "ISO formatted Transaction Date", defaultValue = TYPE_ISO_DATE_TIME)
            @RequestHeader(HEADER_TXN_DATE) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date txnDate,
            @RequestBody MenagerieRequest request) {
        return service.create(request);
    }

    @ApiOperation(
            value = "Retrieves a tiger by ID.",
            notes = "Requires a tiger identifier",
            response = Tiger.class)
    @RequestMapping(method = RequestMethod.GET, value = TIGER_BY_ID_URL,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public Tiger read(
            @ApiParam(value = "Transaction ID as UUID", defaultValue = TYPE_UUID)
            @RequestHeader(HEADER_TXN_ID) UUID txnId,
            @ApiParam(value = "ISO formatted Transaction Date", defaultValue = TYPE_ISO_DATE_TIME)
            @RequestHeader(HEADER_TXN_DATE) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date txnDate,
            @ApiParam(value = "Tiger ID", required = true)
            @PathVariable("id") UUID id) {
        return service.read(id);
    }

    @ApiOperation(
            value = "Retrieves all the tigers associated with the search string.",
            notes = "In absence of any parameter, it will return all the tigers.",
            response = Tiger.class, responseContainer = "List")
    @RequestMapping(method = RequestMethod.GET, value = TIGER_URL,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public List<Tiger> readAll(
            @ApiParam(value = "Transaction ID as UUID", defaultValue = TYPE_UUID)
            @RequestHeader(HEADER_TXN_ID) UUID txnId,
            @ApiParam(value = "ISO formatted Transaction Date", defaultValue = TYPE_ISO_DATE_TIME)
            @RequestHeader(HEADER_TXN_DATE) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date txnDate,
            @ApiParam(value = "Search string to search a tiger based on search criteria. Returns all tigers if empty.")
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "gender", required = false) Gender gender) {
        return service.readAll(name, gender);
    }

    @ApiOperation(value = "Updates a tiger.", response = Tiger.class)
    @ApiResponses({
            @ApiResponse(code = 201, response = Tiger.class,
                    message = "Updated a tiger successfully")})
    @RequestMapping(method = RequestMethod.PUT, value = TIGER_BY_ID_URL,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Tiger update(
            @ApiParam(value = "Transaction ID as UUID", defaultValue = TYPE_UUID)
            @RequestHeader(HEADER_TXN_ID) UUID txnId,
            @ApiParam(value = "ISO formatted Transaction Date", defaultValue = TYPE_ISO_DATE_TIME)
            @RequestHeader(HEADER_TXN_DATE) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date txnDate,
            @ApiParam(value = "Tiger ID", required = true)
            @PathVariable("id") UUID id,
            @RequestBody MenagerieRequest request) {
        return service.update(id, request);
    }

    @ApiOperation(value = "Deletes a tiger by ID.")
    @RequestMapping(method = RequestMethod.DELETE, value = TIGER_BY_ID_URL)
    @ResponseBody
    public void delete(
            @ApiParam(value = "Transaction ID as UUID", defaultValue = TYPE_UUID)
            @RequestHeader(HEADER_TXN_ID) UUID txnId,
            @ApiParam(value = "ISO formatted Transaction Date", defaultValue = TYPE_ISO_DATE_TIME)
            @RequestHeader(HEADER_TXN_DATE) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date txnDate,
            @ApiParam(value = "Tiger ID", required = true)
            @PathVariable("id") UUID id) {
        service.delete(id);
    }

    @ApiOperation(value = "Deletes all tigers.")
    @RequestMapping(method = RequestMethod.DELETE, value = TIGER_URL)
    @ResponseBody
    public void deleteAll(
            @ApiParam(value = "Transaction ID as UUID", defaultValue = TYPE_UUID)
            @RequestHeader(HEADER_TXN_ID) UUID txnId,
            @ApiParam(value = "ISO formatted Transaction Date", defaultValue = TYPE_ISO_DATE_TIME)
            @RequestHeader(HEADER_TXN_DATE) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date txnDate) {
        service.deleteAll();
    }
}
