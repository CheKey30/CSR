package com.csr.datasearching.entity;

import lombok.Data;

@Data
public class ReturnEntity<T> {

    private String code;

    private T data;

    private String message;


}
