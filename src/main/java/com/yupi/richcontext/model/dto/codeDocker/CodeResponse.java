package com.yupi.richcontext.model.dto.codeDocker;

import lombok.Data;

@Data
public class CodeResponse {

    private Integer status;
    private String result;
    private String errorMessage;
}
