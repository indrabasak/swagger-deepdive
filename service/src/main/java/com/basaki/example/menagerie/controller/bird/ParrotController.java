package com.basaki.example.menagerie.controller.bird;

import com.basaki.example.menagerie.model.Gender;
import com.basaki.example.menagerie.model.MenagerieRequest;
import com.basaki.example.menagerie.model.bird.Parrot;
import com.basaki.example.menagerie.service.ParrotService;
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
 * {@code ParrotController} is the spring REST controller for Parrot API.
 * Exposes all CRUD operations on parrot.
 * <p/>
 *
 * @author Indra Basak
 * @since 4/29/17
 */
@SuppressWarnings("MVCPathVariableInspection")
@RestController
@Slf4j
@Api(value = "Parrot API",
        description = "Parrot API",
        produces = "application/json", tags = {"Parrot"})
@Classification(kingdom = "Animalia", phylum = "Chordata", clazz = "Aves",
        order = "Psittaciformes", family = "Psittacidae", genus = "Ara",
        species = "Ara ararauna")
public class ParrotController {
    private static final String PARROT_URL = "/parrots";

    private static final String PARROT_BY_ID_URL = PARROT_URL + "/{id}";

    private static final String HEADER_TXN_ID = "X-TXN-ID";

    private static final String HEADER_TXN_DATE = "X-TXN-DATE";

    private ParrotService service;

    @Autowired
    public ParrotController(ParrotService service) {
        this.service = service;
    }

    @ApiOperation(
            value = "Creates a Parrot.",
            notes = "Requires a Parrot name and gender.",
            response = Parrot.class)
    @ApiResponses({
            @ApiResponse(code = 201, response = Parrot.class,
                    message = "Parrot created successfully")})
    @RequestMapping(method = RequestMethod.POST, value = PARROT_URL,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Parrot create(
            @ApiParam(value = "Transaction ID as UUID", defaultValue = TYPE_UUID)
            @RequestHeader(HEADER_TXN_ID) UUID txnId,
            @ApiParam(value = "ISO formatted Transaction Date", defaultValue = TYPE_ISO_DATE_TIME)
            @RequestHeader(HEADER_TXN_DATE) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date txnDate,
            @RequestBody MenagerieRequest request) {
        return service.create(request);
    }

    @ApiOperation(
            value = "Retrieves a parrot by ID.",
            notes = "Requires a parrot identifier",
            response = Parrot.class)
    @RequestMapping(method = RequestMethod.GET, value = PARROT_BY_ID_URL,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public Parrot read(
            @ApiParam(value = "Transaction ID as UUID", defaultValue = TYPE_UUID)
            @RequestHeader(HEADER_TXN_ID) UUID txnId,
            @ApiParam(value = "ISO formatted Transaction Date", defaultValue = TYPE_ISO_DATE_TIME)
            @RequestHeader(HEADER_TXN_DATE) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date txnDate,
            @ApiParam(value = "Parrot ID", required = true)
            @PathVariable("id") UUID id) {
        return service.read(id);
    }

    @ApiOperation(
            value = "Retrieves all the parrots associated with the search string.",
            notes = "In absence of any parameter, it will return all the parrots.",
            response = Parrot.class, responseContainer = "List")
    @RequestMapping(method = RequestMethod.GET, value = PARROT_URL,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public List<Parrot> readAll(
            @ApiParam(value = "Transaction ID as UUID", defaultValue = TYPE_UUID)
            @RequestHeader(HEADER_TXN_ID) UUID txnId,
            @ApiParam(value = "ISO formatted Transaction Date", defaultValue = TYPE_ISO_DATE_TIME)
            @RequestHeader(HEADER_TXN_DATE) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date txnDate,
            @ApiParam(value = "Search string to search a parrot based on search criteria. Returns all parrots if empty.")
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "gender", required = false) Gender gender) {
        return service.readAll(name, gender);
    }

    @ApiOperation(value = "Updates a parrot.", response = Parrot.class)
    @ApiResponses({
            @ApiResponse(code = 201, response = Parrot.class,
                    message = "Updated a parrot successfully")})
    @RequestMapping(method = RequestMethod.PUT, value = PARROT_BY_ID_URL,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Parrot update(
            @ApiParam(value = "Transaction ID as UUID", defaultValue = TYPE_UUID)
            @RequestHeader(HEADER_TXN_ID) UUID txnId,
            @ApiParam(value = "ISO formatted Transaction Date", defaultValue = TYPE_ISO_DATE_TIME)
            @RequestHeader(HEADER_TXN_DATE) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date txnDate,
            @ApiParam(value = "Parrot ID", required = true)
            @PathVariable("id") UUID id,
            @RequestBody MenagerieRequest request) {
        return service.update(id, request);
    }

    @ApiOperation(value = "Deletes a parrot by ID.")
    @RequestMapping(method = RequestMethod.DELETE, value = PARROT_BY_ID_URL)
    @ResponseBody
    public void delete(
            @ApiParam(value = "Transaction ID as UUID", defaultValue = TYPE_UUID)
            @RequestHeader(HEADER_TXN_ID) UUID txnId,
            @ApiParam(value = "ISO formatted Transaction Date", defaultValue = TYPE_ISO_DATE_TIME)
            @RequestHeader(HEADER_TXN_DATE) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date txnDate,
            @ApiParam(value = "Parrot ID", required = true)
            @PathVariable("id") UUID id) {
        service.delete(id);
    }

    @ApiOperation(value = "Deletes all parrots.")
    @RequestMapping(method = RequestMethod.DELETE, value = PARROT_URL)
    @ResponseBody
    public void deleteAll(
            @ApiParam(value = "Transaction ID as UUID", defaultValue = TYPE_UUID)
            @RequestHeader(HEADER_TXN_ID) UUID txnId,
            @ApiParam(value = "ISO formatted Transaction Date", defaultValue = TYPE_ISO_DATE_TIME)
            @RequestHeader(HEADER_TXN_DATE) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date txnDate) {
        service.deleteAll();
    }
}
