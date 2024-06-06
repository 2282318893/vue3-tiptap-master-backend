package com.yupi.richcontext.minio;

import io.minio.MinioClient;
import io.minio.UploadObjectArgs;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

public class MinioTest {

    private static final MinioClient minioClient =
            MinioClient.builder()
                    .endpoint("http://localhost:9000")
                    .credentials("minioadmin", "minioadmin")
                    .build();

    @Test
    void testUploadObject() {
        try {
            UploadObjectArgs uploadObjectArgs = UploadObjectArgs.builder()
                    .bucket("test-bucket")
                    .object("pic.jpg")
                    .filename("/Users/apple/Downloads/pexels-suju-22475982.jpg")
                    .build();
            minioClient.uploadObject(uploadObjectArgs);
            System.out.println("上传成功！");
        } catch (Exception e) {
            System.out.println("上传失败！");
        }
    }



}
