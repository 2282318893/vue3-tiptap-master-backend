package com.yupi.richcontext.service;

import com.yupi.richcontext.model.dto.codeDocker.CodeRequest;
import com.yupi.richcontext.model.dto.codeDocker.CodeResponse;

public interface CodeDockerService {

    /**
     * 访问代码执行模块
     * @param codeRequest
     * @return
     */
    public CodeResponse codeDockerApi(CodeRequest codeRequest);
}
