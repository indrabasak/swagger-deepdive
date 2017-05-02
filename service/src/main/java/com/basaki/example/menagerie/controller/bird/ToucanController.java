package com.basaki.example.menagerie.controller.bird;

import com.basaki.example.menagerie.model.Gender;
import com.basaki.example.menagerie.model.MenagerieRequest;
import com.basaki.example.menagerie.model.bird.Toucan;
import com.basaki.example.menagerie.service.ToucanService;
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
 * {@code ToucanController} is the spring REST controller for Toucan API.
 * Exposes all CRUD operations on toucan.
 * <p/>
 *
 * @author Indra Basak
 * @since 4/29/17
 */
@SuppressWarnings("MVCPathVariableInspection")
@RestController
@Slf4j
@Api(value = "Toucan API",
        description = "Toucan API",
        produces = "application/json", tags = {"Toucan"})
@Classification(kingdom = "Animalia", phylum = "Chordata", clazz = "Aves",
        order = "Piciformes", family = "Ramphastidae", genus = "Ramphastos",
        species = "Ramphastos dicolorus")
public class ToucanController {
    private static final String TOUCAN_URL = "/toucans";

    private static final String TOUCAN_BY_ID_URL = TOUCAN_URL + "/{id}";

    private static final String HEADER_TXN_ID = "X-TXN-ID";

    private static final String HEADER_TXN_DATE = "X-TXN-DATE";

    private ToucanService service;

    @Autowired
    public ToucanController(ToucanService service) {
        this.service = service;
    }

    @ApiOperation(
            value = "Creates a Toucan.",
            notes = "Requires a Toucan name and gender.",
            response = Toucan.class)
    @ApiResponses({
            @ApiResponse(code = 201, response = Toucan.class,
                    message = "Toucan created successfully")})
    @RequestMapping(method = RequestMethod.POST, value = TOUCAN_URL,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Toucan create(
            @ApiParam(value = "Transaction ID as UUID", defaultValue = TYPE_UUID)
            @RequestHeader(HEADER_TXN_ID) UUID txnId,
            @ApiParam(value = "ISO formatted Transaction Date", defaultValue = TYPE_ISO_DATE_TIME)
            @RequestHeader(HEADER_TXN_DATE) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date txnDate,
            @RequestBody MenagerieRequest request) {
        return service.create(request);
    }

    @ApiOperation(
            value = "Retrieves a toucan by ID.",
            notes = "Requires a toucan identifier",
            response = Toucan.class)
    @RequestMapping(method = RequestMethod.GET, value = TOUCAN_BY_ID_URL,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public Toucan read(
            @ApiParam(value = "Transaction ID as UUID", defaultValue = TYPE_UUID)
            @RequestHeader(HEADER_TXN_ID) UUID txnId,
            @ApiParam(value = "ISO formatted Transaction Date", defaultValue = TYPE_ISO_DATE_TIME)
            @RequestHeader(HEADER_TXN_DATE) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date txnDate,
            @ApiParam(value = "Toucan ID", required = true)
            @PathVariable("id") UUID id) {
        return service.read(id);
    }

    @ApiOperation(
            value = "Retrieves all the toucans associated with the search string.",
            notes = "In absence of any parameter, it will return all the toucans.",
            response = Toucan.class, responseContainer = "List")
    @RequestMapping(method = RequestMethod.GET, value = TOUCAN_URL,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public List<Toucan> readAll(
            @ApiParam(value = "Transaction ID as UUID", defaultValue = TYPE_UUID)
            @RequestHeader(HEADER_TXN_ID) UUID txnId,
            @ApiParam(value = "ISO formatted Transaction Date", defaultValue = TYPE_ISO_DATE_TIME)
            @RequestHeader(HEADER_TXN_DATE) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date txnDate,
            @ApiParam(value = "Search string to search a toucan based on search criteria. Returns all toucans if empty.")
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "gender", required = false) Gender gender) {
        return service.readAll(name, gender);
    }

    @ApiOperation(value = "Updates a toucan.", response = Toucan.class)
    @ApiResponses({
            @ApiResponse(code = 201, response = Toucan.class,
                    message = "Updated a toucan successfully")})
    @RequestMapping(method = RequestMethod.PUT, value = TOUCAN_BY_ID_URL,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Toucan update(
            @ApiParam(value = "Transaction ID as UUID", defaultValue = TYPE_UUID)
            @RequestHeader(HEADER_TXN_ID) UUID txnId,
            @ApiParam(value = "ISO formatted Transaction Date", defaultValue = TYPE_ISO_DATE_TIME)
            @RequestHeader(HEADER_TXN_DATE) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date txnDate,
            @ApiParam(value = "Toucan ID", required = true)
            @PathVariable("id") UUID id,
            @RequestBody MenagerieRequest request) {
        return service.update(id, request);
    }

    @ApiOperation(value = "Deletes a toucan by ID.")
    @RequestMapping(method = RequestMethod.DELETE, value = TOUCAN_BY_ID_URL)
    @ResponseBody
    public void delete(
            @ApiParam(value = "Transaction ID as UUID", defaultValue = TYPE_UUID)
            @RequestHeader(HEADER_TXN_ID) UUID txnId,
            @ApiParam(value = "ISO formatted Transaction Date", defaultValue = TYPE_ISO_DATE_TIME)
            @RequestHeader(HEADER_TXN_DATE) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date txnDate,
            @ApiParam(value = "Toucan ID", required = true)
            @PathVariable("id") UUID id) {
        service.delete(id);
    }

    @ApiOperation(value = "Deletes all toucans.")
    @RequestMapping(method = RequestMethod.DELETE, value = TOUCAN_URL)
    @ResponseBody
    public void deleteAll(
            @ApiParam(value = "Transaction ID as UUID", defaultValue = TYPE_UUID)
            @RequestHeader(HEADER_TXN_ID) UUID txnId,
            @ApiParam(value = "ISO formatted Transaction Date", defaultValue = TYPE_ISO_DATE_TIME)
            @RequestHeader(HEADER_TXN_DATE) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date txnDate) {
        service.deleteAll();
    }
}
