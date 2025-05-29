package com.lukianchykov.dto.payload.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtResponse {

    private List<String> roles;

    private String token;

    @Builder.Default
    private String type = "Bearer";

    private Long id;

    private String username;

    private String email;
}
