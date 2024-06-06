package com.yupi.richcontext.service.impl;

import cn.hutool.json.JSONUtil;
import com.yupi.richcontext.model.dto.codeDocker.CodeRequest;
import com.yupi.richcontext.model.dto.codeDocker.CodeResponse;
import com.yupi.richcontext.service.CodeDockerService;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

@Service
public class CodeDockerServiceImpl implements CodeDockerService {


    @Override
    public CodeResponse codeDockerApi(CodeRequest codeRequest) {

        CodeResponse codeResponse = new CodeResponse();

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            // 创建 POST 请求
            HttpPost postRequest = new HttpPost("http://localhost:8080/code");

            // 设置请求头
            postRequest.setHeader("Content-Type", "application/json");
            postRequest.setHeader("User-Agent", "Mozilla/5.0");

            // 创建 JSON 数据
            String json = JSONUtil.toJsonStr(codeRequest);

            // 设置请求体
            StringEntity entity = new StringEntity(json);
            postRequest.setEntity(entity);

            // 发送请求并获取响应
            try (CloseableHttpResponse response = httpClient.execute(postRequest)) {
                // 获取响应状态码
                int statusCode = response.getStatusLine().getStatusCode();
                System.out.println("Response Code: " + statusCode);
                codeResponse.setStatus(statusCode);
                // 获取响应体
                String responseBody = EntityUtils.toString(response.getEntity());
                System.out.println("Response Body: " + responseBody);
                codeResponse.setResult(responseBody);
            }
            codeResponse.setErrorMessage("no message");
        } catch (Exception e) {
            e.printStackTrace();
            codeResponse.setResult(e.toString());
        }
        return codeResponse;
    }
}
