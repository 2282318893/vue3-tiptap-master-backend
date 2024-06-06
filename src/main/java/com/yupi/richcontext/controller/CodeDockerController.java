package com.yupi.richcontext.controller;

import com.yupi.richcontext.common.BaseResponse;
import com.yupi.richcontext.common.ErrorCode;
import com.yupi.richcontext.common.ResultUtils;
import com.yupi.richcontext.exception.BusinessException;
import com.yupi.richcontext.model.dto.codeDocker.CodeRequest;
import com.yupi.richcontext.model.dto.codeDocker.CodeResponse;
import com.yupi.richcontext.service.CodeDockerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class CodeDockerController {

    @Resource
    private CodeDockerService codeDockerService;

    @PostMapping("/code")
    public BaseResponse<CodeResponse> executeCode(@RequestBody CodeRequest codeRequest) {
        if (codeRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        CodeResponse codeResponse = codeDockerService.codeDockerApi(codeRequest);

        return ResultUtils.success(codeResponse);
    }

}
