package com.basaki.example.menagerie.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
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
@ApiModel(value="Menagerie Resident")
public abstract class MenagerieModel {

    @ApiModelProperty(value = "identity of the menagerie resident")
    private UUID id;

    @ApiModelProperty(value = "name of the menagerie resident")
    private String name;

    @ApiModelProperty(value = "gender of the menagerie resident")
    private Gender gender;

    @ApiModelProperty(access = "hidden", hidden = true)
    private String link;
}
