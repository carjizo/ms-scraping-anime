package com.scraping.animeflv.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {

    @NotBlank
    private String idIdent;

    @NotBlank
    private String role;

    private boolean status;
}
