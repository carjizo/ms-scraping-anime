package com.scraping.animeflv.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestLoginDTO {

    private String idIdent;
    private String password;
    private String role;
}
