package com.yupi.richcontext.codedocker;

import cn.hutool.json.JSONUtil;
import lombok.ToString;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.codehaus.commons.compiler.CompileException;
import org.codehaus.janino.Java;
import org.codehaus.janino.SimpleCompiler;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.lang.reflect.InvocationTargetException;


public class JavaDockerTest {

    @Test
    public void remoteTest(){
        // 创建 HttpClient 实例
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            // 创建 POST 请求
            HttpPost postRequest = new HttpPost("http://localhost:8080/code");

            // 设置请求头
            postRequest.setHeader("Content-Type", "application/json");
            postRequest.setHeader("User-Agent", "Mozilla/5.0");

            CodeRequest codeRequest = new CodeRequest();

//            codeRequest.setType("java");
//
//            String sourceCode =
//                    "public class Example {\n" +
//                            "    public static void main(String[] args) {" +
//                            "        System.out.println(\"Hello, world!\");" +
//                            "    }"
//                            + "}";

//            codeRequest.setType("python");
//            String sourceCode = "print('Hello from Python')";

            codeRequest.setType("javascript");
            String sourceCode = "// 定义一个函数，打印一个问候消息\n" +
                    "function greet(name) {\n" +
                    "    console.log(\"Hello, \" + name + \"!\");\n" +
                    "}\n" +
                    "\n" +
                    "// 定义一个数组，包含一些名字\n" +
                    "const names = [\"Alice\", \"Bob\", \"Charlie\"];\n" +
                    "\n" +
                    "// 遍历数组，并对每个名字调用 greet 函数\n" +
                    "names.forEach(function(name) {\n" +
                    "    greet(name);\n" +
                    "});\n" +
                    "\n" +
                    "// 打印一个完成消息\n" +
                    "console.log(\"All greetings have been printed.\");\n" +
                    "\n" +
                    "// 打印一个对象\n" +
                    "const person = {\n" +
                    "    firstName: \"John\",\n" +
                    "    lastName: \"Doe\",\n" +
                    "    age: 30\n" +
                    "};\n" +
                    "console.log(\"Person object:\", person);\n" +
                    "\n" +
                    "// 打印一个错误消息\n" +
                    "console.error(\"This is an error message.\");\n";
            codeRequest.setCode(sourceCode);
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

                // 获取响应体
                String responseBody = EntityUtils.toString(response.getEntity());
                System.out.println("Response Body: " + responseBody);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void javaTest() {
        String sourceCode =
                "public class Example {\n" +
                        "    public static void main(String[] args) {" +
                        "        System.out.println(\"Hello, world!\");" +
                        "    }"
                        + "}";

        System.err.println(javaDocker(sourceCode));
    }

    @Test
    public void pythonTest() {
        String pythonCode = "print('Hello from Python')";
        System.err.println(pythonDocker(pythonCode));
    }

    @Test
    public void javaScriptTest() {
        String jsCode = "console.log('Hello from JavaScript');";
        System.err.println(javaScriptDocker(jsCode));
    }

    /**
     * java docker
     *
     * @param sourceCode
     * @return
     */
    public static String javaDocker(String sourceCode) {
        try {
            // 定义要编译和执行的 Java 源代码
            // 创建一个编译器
            SimpleCompiler compiler = new SimpleCompiler();
            compiler.cook(sourceCode);

            // 获取编译后的类
            Class<?> exampleClass = compiler.getClassLoader().loadClass("Example");

            // 创建一个字节输出流来捕获输出
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PrintStream printStream = new PrintStream(outputStream);

            // 保存原始的 System.out
            PrintStream originalOut = System.out;

            // 重定向 System.out 到 printStream
            System.setOut(printStream);

            // 执行类的 main 方法
            exampleClass.getMethod("main", String[].class).invoke(null, (Object) new String[]{});

            // 恢复原始的 System.out
            System.setOut(originalOut);

            // 获取捕获的输出
            String capturedOutput = outputStream.toString();
            System.out.println("Captured Output: " + capturedOutput);
            return capturedOutput;

        } catch (Exception e) {
            //System.out.println(e);
            return e.toString();
        }
    }

    /**
     * python docker
     *
     * @param pythonCode
     * @return
     */
    public String pythonDocker(String pythonCode) {
        try {
            // Python 代码作为字符串

            // 定义命令和参数
            String[] cmd = {
                    "python3", "-c", pythonCode
            };

            // 创建进程构建器
            ProcessBuilder pb = new ProcessBuilder(cmd);

            // 启动进程
            Process p = pb.start();

            // 获取进程的输入流（即脚本的输出）
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // 获取进程的错误流（即脚本的错误输出）
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            while ((line = errorReader.readLine()) != null) {
                System.err.println(line);
            }

            // 等待进程结束并获取退出状态
            int exitCode = p.waitFor();
            System.out.println("Exited with code: " + exitCode);

            return exitCode + "";
        } catch (Exception e) {
            return e.toString();
        }
    }

    /**
     * jsDocker
     * @param jsCode
     * @return
     */
    public String javaScriptDocker(String jsCode) {
        try {
            String[] cmd = {
                    "node", "-e", jsCode
            };
            ProcessBuilder pb = new ProcessBuilder(cmd);
            Process p = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            while ((line = errorReader.readLine()) != null) {
                System.err.println(line);
            }
            int exitCode = p.waitFor();
            System.out.println("Exited with code: " + exitCode);
            return exitCode + "";
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return e.toString();
        }
    }

}
