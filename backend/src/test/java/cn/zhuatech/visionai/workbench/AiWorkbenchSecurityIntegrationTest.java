/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.visionai.workbench;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import cn.zhuatech.visionai.security.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

/** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
@SpringBootTest
@AutoConfigureMockMvc
class AiWorkbenchSecurityIntegrationTest {
  @Autowired MockMvc mvc;
  @Autowired JwtService jwt;

  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  @Test
  void securityFilterRejectsAnonymous() throws Exception {
    mvc.perform(
            post("/api/workbench/detection-nms")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    "{\"iouThreshold\":0.5,\"minimumScore\":0.5,\"boxes\":[{\"id\":\"A\",\"category\":\"scratch\",\"score\":0.9,\"x1\":0,\"y1\":0,\"x2\":1,\"y2\":1},{\"id\":\"B\",\"category\":\"scratch\",\"score\":0.8,\"x1\":0.1,\"y1\":0.1,\"x2\":1,\"y2\":1},{\"id\":\"C\",\"category\":\"dent\",\"score\":0.7,\"x1\":0,\"y1\":0,\"x2\":1,\"y2\":1}]}"))
        .andExpect(status().isUnauthorized());
  }

  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  @Test
  void securityFilterRejectsInvalidBearer() throws Exception {
    mvc.perform(
            post("/api/workbench/detection-nms")
                .header("Authorization", "Bearer invalid-token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    "{\"iouThreshold\":0.5,\"minimumScore\":0.5,\"boxes\":[{\"id\":\"A\",\"category\":\"scratch\",\"score\":0.9,\"x1\":0,\"y1\":0,\"x2\":1,\"y2\":1},{\"id\":\"B\",\"category\":\"scratch\",\"score\":0.8,\"x1\":0.1,\"y1\":0.1,\"x2\":1,\"y2\":1},{\"id\":\"C\",\"category\":\"dent\",\"score\":0.7,\"x1\":0,\"y1\":0,\"x2\":1,\"y2\":1}]}"))
        .andExpect(status().isUnauthorized());
  }

  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  @Test
  void authenticatedCallerCanCompute() throws Exception {
    mvc.perform(
            post("/api/workbench/detection-nms")
                .header("Authorization", "Bearer " + jwt.generate("operator"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    "{\"iouThreshold\":0.5,\"minimumScore\":0.5,\"boxes\":[{\"id\":\"A\",\"category\":\"scratch\",\"score\":0.9,\"x1\":0,\"y1\":0,\"x2\":1,\"y2\":1},{\"id\":\"B\",\"category\":\"scratch\",\"score\":0.8,\"x1\":0.1,\"y1\":0.1,\"x2\":1,\"y2\":1},{\"id\":\"C\",\"category\":\"dent\",\"score\":0.7,\"x1\":0,\"y1\":0,\"x2\":1,\"y2\":1}]}"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(true));
  }

  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  @Test
  void authenticatedCallerGetsValidationError() throws Exception {
    mvc.perform(
            post("/api/workbench/detection-nms")
                .header("Authorization", "Bearer " + jwt.generate("operator"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.success").value(false));
  }
}
