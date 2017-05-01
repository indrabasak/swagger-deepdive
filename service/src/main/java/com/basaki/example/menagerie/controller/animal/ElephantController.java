package com.basaki.example.menagerie.controller.animal;

import com.basaki.example.menagerie.model.Gender;
import com.basaki.example.menagerie.model.MenagerieRequest;
import com.basaki.example.menagerie.model.animal.Elephant;
import com.basaki.example.menagerie.service.ElephantService;
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

import static com.basaki.example.menagerie.swagger.ApiParamUiidAndDateParameterBuilder.TYPE_ISO_DATE_TIME;
import static com.basaki.example.menagerie.swagger.ApiParamUiidAndDateParameterBuilder.TYPE_UUID;

/**
 * {@code ElephantController} is the spring REST controller for Elephant API.
 * Exposes all CRUD operations on elephant.
 * <p/>
 *
 * @author Indra Basak
 * @since 4/29/17
 */
@SuppressWarnings("MVCPathVariableInspection")
@RestController
@Slf4j
@Api(value = "Elephant API",
        description = "Elephant API",
        produces = "application/json", tags = {"A3"})
public class ElephantController {

    private static final String ELEPHANT_URL = "/elephants";

    private static final String ELEPHANT_BY_ID_URL = ELEPHANT_URL + "/{id}";

    private static final String HEADER_TXN_ID = "X-TXN-ID";

    private static final String HEADER_TXN_DATE = "X-TXN-DATE";

    static final String SAMPLE_ELEPHANT_POST = "```javascript\n" +
            "Example Request Body:\n" +
            "{\n" +
            "  \"name\": \"Babar\",\n" +
            "  \"gender\": \"MALE\"\n" +
            "}\n" +
            "```";


    private ElephantService service;

    @Autowired
    public ElephantController(ElephantService service) {
        this.service = service;
    }

    @ApiOperation(
            value = "Creates an Elephant.",
            notes = SAMPLE_ELEPHANT_POST,
            response = Elephant.class,
            position = 1)
    @ApiResponses({
            @ApiResponse(code = 201, response = Elephant.class,
                    message = "Elephant created successfully")})
    @RequestMapping(method = RequestMethod.POST, value = ELEPHANT_URL,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Elephant create(
            @ApiParam(value = "Transaction ID as UUID", defaultValue = TYPE_UUID)
            @RequestHeader(HEADER_TXN_ID) UUID txnId,
            @ApiParam(value = "ISO formatted Transaction Date", defaultValue = TYPE_ISO_DATE_TIME)
            @RequestHeader(HEADER_TXN_DATE) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date txnDate,
            @RequestBody MenagerieRequest request) {
        return service.create(request);
    }

    @ApiOperation(
            value = "Retrieves an Elephant by ID.",
            notes = "Requires an Elephant identifier",
            response = Elephant.class,
            position = 2)
    @RequestMapping(method = RequestMethod.GET, value = ELEPHANT_BY_ID_URL,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public Elephant read(
            @ApiParam(value = "Transaction ID as UUID", defaultValue = TYPE_UUID)
            @RequestHeader(HEADER_TXN_ID) UUID txnId,
            @ApiParam(value = "ISO formatted Transaction Date", defaultValue = TYPE_ISO_DATE_TIME)
            @RequestHeader(HEADER_TXN_DATE) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date txnDate,
            @ApiParam(value = "Elephant ID", required = true)
            @PathVariable("id") UUID id) {
        return service.read(id);
    }

    @ApiOperation(
            value = "Retrieves all the Elephants associated with the search string.",
            notes = "In absence of any parameter, it will return all the Elephants.",
            response = Elephant.class, responseContainer = "List",
            position = 3)
    @RequestMapping(method = RequestMethod.GET, value = ELEPHANT_URL,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public List<Elephant> readAll(
            @ApiParam(value = "Transaction ID as UUID", defaultValue = TYPE_UUID)
            @RequestHeader(HEADER_TXN_ID) UUID txnId,
            @ApiParam(value = "ISO formatted Transaction Date", defaultValue = TYPE_ISO_DATE_TIME)
            @RequestHeader(HEADER_TXN_DATE) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date txnDate,
            @ApiParam(value = "Search string to search a Elephant based on search criteria. Returns all Elephants if empty.")
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "gender", required = false) Gender gender) {
        return service.readAll(name, gender);
    }

    @ApiOperation(value = "Updates an Elephant.", response = Elephant.class,
            position = 4)
    @ApiResponses({
            @ApiResponse(code = 201, response = Elephant.class,
                    message = "Updated an Elephant successfully")})
    @RequestMapping(method = RequestMethod.PUT, value = ELEPHANT_BY_ID_URL,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Elephant update(
            @ApiParam(value = "Transaction ID as UUID", defaultValue = TYPE_UUID)
            @RequestHeader(HEADER_TXN_ID) UUID txnId,
            @ApiParam(value = "ISO formatted Transaction Date", defaultValue = TYPE_ISO_DATE_TIME)
            @RequestHeader(HEADER_TXN_DATE) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date txnDate,
            @ApiParam(value = "Elephant ID", required = true)
            @PathVariable("id") UUID id,
            @RequestBody MenagerieRequest request) {
        return service.update(id, request);
    }

    @ApiOperation(value = "Deletes an Elephant by ID.", position = 5)
    @RequestMapping(method = RequestMethod.DELETE, value = ELEPHANT_BY_ID_URL)
    @ResponseBody
    public void delete(
            @ApiParam(value = "Transaction ID as UUID", defaultValue = TYPE_UUID)
            @RequestHeader(HEADER_TXN_ID) UUID txnId,
            @ApiParam(value = "ISO formatted Transaction Date", defaultValue = TYPE_ISO_DATE_TIME)
            @RequestHeader(HEADER_TXN_DATE) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date txnDate,
            @ApiParam(value = "Elephant ID", required = true)
            @PathVariable("id") UUID id) {
        service.delete(id);
    }

    @ApiOperation(value = "Deletes all Elephants.", position = 6)
    @RequestMapping(method = RequestMethod.DELETE, value = ELEPHANT_URL)
    @ResponseBody
    public void deleteAll(
            @ApiParam(value = "Transaction ID as UUID", defaultValue = TYPE_UUID)
            @RequestHeader(HEADER_TXN_ID) UUID txnId,
            @ApiParam(value = "ISO formatted Transaction Date", defaultValue = TYPE_ISO_DATE_TIME)
            @RequestHeader(HEADER_TXN_DATE) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date txnDate) {
        service.deleteAll();
    }
}
