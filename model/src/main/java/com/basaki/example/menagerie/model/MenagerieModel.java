package com.basaki.example.menagerie.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by indra.basak on 4/29/17.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class MenagerieModel {
    private UUID id;
    private String name;
    private Gender gender;

    @ApiModelProperty(access = "hidden", hidden = true)
    private boolean doNotShow;
}
