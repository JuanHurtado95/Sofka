package com.example.cuenta_movimiento.domain.report.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ClientReportDTO {

    private Long id;
    private int identification;
    private String name;
    private String gender;
    private Long age;
    private String address;
    private String phone;
    private String password;
    private Boolean status;
}
