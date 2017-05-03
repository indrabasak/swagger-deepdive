package com.basaki.example.menagerie.controller.animal;

import com.basaki.example.menagerie.model.Gender;
import com.basaki.example.menagerie.model.MenagerieRequest;
import com.basaki.example.menagerie.model.animal.Lion;
import com.basaki.example.menagerie.service.LionService;
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
 * {@code LionController} is the spring REST controller for Lion API.
 * Exposes all CRUD operations on lion.
 * <p>
 *
 * @author Indra Basak
 * @since 4/30/17
 */
@SuppressWarnings("MVCPathVariableInspection")
@RestController
@Slf4j
@Api(description = "Lion API", produces = "application/json", tags = {"A1"})
@Classification(kingdom = "Animalia", phylum = "Chordata", clazz = "Mammalia",
        order = "Carnivora", family = "Felidae", genus = "Panthera",
        species = "Panthera leo")
public class LionController {

    private static final String LION_URL = "/lions";

    private static final String LION_BY_ID_URL = LION_URL + "/{id}";

    private static final String HEADER_TXN_ID = "X-TXN-ID";

    private static final String HEADER_TXN_DATE = "X-TXN-DATE";

    private LionService service;

    @Autowired
    public LionController(LionService service) {
        this.service = service;
    }

    @ApiOperation(
            value = "Creates a lion.",
            notes = "Requires a lion name and gender.",
            response = Lion.class)
    @ApiResponses({
            @ApiResponse(code = 201, response = Lion.class,
                    message = "Lion created successfully")})
    @RequestMapping(method = RequestMethod.POST, value = LION_URL,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Lion create(
            @ApiParam(value = "Transaction ID as UUID", defaultValue = TYPE_UUID)
            @RequestHeader(HEADER_TXN_ID) UUID txnId,
            @ApiParam(value = "ISO formatted Transaction Date", defaultValue = TYPE_ISO_DATE_TIME)
            @RequestHeader(HEADER_TXN_DATE) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date txnDate,
            @RequestBody MenagerieRequest request) {
        return service.create(request);
    }

    @ApiOperation(
            value = "Retrieves a lion by ID.",
            notes = "Requires a lion identifier",
            response = Lion.class)
    @RequestMapping(method = RequestMethod.GET, value = LION_BY_ID_URL,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public Lion read(
            @ApiParam(value = "Transaction ID as UUID", defaultValue = TYPE_UUID)
            @RequestHeader(HEADER_TXN_ID) UUID txnId,
            @ApiParam(value = "ISO formatted Transaction Date", defaultValue = TYPE_ISO_DATE_TIME)
            @RequestHeader(HEADER_TXN_DATE) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date txnDate,
            @ApiParam(value = "Lion ID", required = true)
            @PathVariable("id") UUID id) {
        return service.read(id);
    }

    @ApiOperation(
            value = "Retrieves all the lions associated with the search string.",
            notes = "In absence of any parameter, it will return all the lions.",
            response = Lion.class, responseContainer = "List")
    @RequestMapping(method = RequestMethod.GET, value = LION_URL,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public List<Lion> readAll(
            @ApiParam(value = "Transaction ID as UUID", defaultValue = TYPE_UUID)
            @RequestHeader(HEADER_TXN_ID) UUID txnId,
            @ApiParam(value = "ISO formatted Transaction Date", defaultValue = TYPE_ISO_DATE_TIME)
            @RequestHeader(HEADER_TXN_DATE) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date txnDate,
            @ApiParam(value = "Search string to search a lion based on search criteria. Returns all lions if empty.")
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "gender", required = false) Gender gender) {
        return service.readAll(name, gender);
    }

    @ApiOperation(value = "Updates a lion.", response = Lion.class)
    @ApiResponses({
            @ApiResponse(code = 201, response = Lion.class,
                    message = "Updated a lion successfully")})
    @RequestMapping(method = RequestMethod.PUT, value = LION_BY_ID_URL,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Lion update(
            @ApiParam(value = "Transaction ID as UUID", defaultValue = TYPE_UUID)
            @RequestHeader(HEADER_TXN_ID) UUID txnId,
            @ApiParam(value = "ISO formatted Transaction Date", defaultValue = TYPE_ISO_DATE_TIME)
            @RequestHeader(HEADER_TXN_DATE) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date txnDate,
            @ApiParam(value = "Lion ID", required = true)
            @PathVariable("id") UUID id,
            @RequestBody MenagerieRequest request) {
        return service.update(id, request);
    }

    @ApiOperation(value = "Deletes a lion by ID.")
    @RequestMapping(method = RequestMethod.DELETE, value = LION_BY_ID_URL)
    @ResponseBody
    public void delete(
            @ApiParam(value = "Transaction ID as UUID", defaultValue = TYPE_UUID)
            @RequestHeader(HEADER_TXN_ID) UUID txnId,
            @ApiParam(value = "ISO formatted Transaction Date", defaultValue = TYPE_ISO_DATE_TIME)
            @RequestHeader(HEADER_TXN_DATE) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date txnDate,
            @ApiParam(value = "Lion ID", required = true)
            @PathVariable("id") UUID id) {
        service.delete(id);
    }

    @ApiOperation(value = "Deletes all lions.")
    @RequestMapping(method = RequestMethod.DELETE, value = LION_URL)
    @ResponseBody
    public void deleteAll(
            @ApiParam(value = "Transaction ID as UUID", defaultValue = TYPE_UUID)
            @RequestHeader(HEADER_TXN_ID) UUID txnId,
            @ApiParam(value = "ISO formatted Transaction Date", defaultValue = TYPE_ISO_DATE_TIME)
            @RequestHeader(HEADER_TXN_DATE) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date txnDate) {
        service.deleteAll();
    }
}
