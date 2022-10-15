package com.naturpoint.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {

    private Integer idCategory;

    @NotNull
    private String name;

    @NotNull
    private String photoUrl;

    @NotNull
    private String description;
}
