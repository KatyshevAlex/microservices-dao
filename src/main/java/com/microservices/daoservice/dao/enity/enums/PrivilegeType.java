package com.microservices.daoservice.dao.enity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public enum PrivilegeType {
    READ,
    WRITE,
    DELETE
}