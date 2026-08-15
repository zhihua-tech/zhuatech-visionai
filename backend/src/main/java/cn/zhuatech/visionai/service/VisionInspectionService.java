/* Copyright 2026 上海如静知华信息科技有限公司 */
package cn.zhuatech.visionai.service;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class VisionInspectionService {
    public Result inspect(Request request) {
        boolean hardBlock = request.criticalDefectCount() > 0
            || request.defectAreaRate().compareTo(new BigDecimal("0.050")) >= 0;
        boolean lowConfidence = request.modelConfidence().compareTo(new BigDecimal("0.85")) < 0;
        String decision = hardBlock ? "BLOCK" : lowConfidence || request.totalDefectCount() > 0 ? "REVIEW" : "PASS";
        List<String> findings = new ArrayList<>();
        if (request.criticalDefectCount() > 0) findings.add("发现关键缺陷，禁止自动放行");
        if (request.defectAreaRate().compareTo(new BigDecimal("0.050")) >= 0) findings.add("缺陷面积比例超过质量门槛");
        if (lowConfidence) findings.add("模型置信度不足，需要人工复核");
        if (findings.isEmpty()) findings.add("未发现超出质量规则的视觉异常");
        String route = "BLOCK".equals(decision) ? "隔离区" : "REVIEW".equals(decision) ? "人工复核台" : "下一工序";
        return new Result(request.inspectionNo(), decision, route, request.modelConfidence(),
            request.totalDefectCount(), findings, true);
    }

    public record Request(@NotBlank String inspectionNo, @NotBlank String modelVersion,
                          @DecimalMin("0") @DecimalMax("1") BigDecimal modelConfidence,
                          @Min(0) int totalDefectCount, @Min(0) int criticalDefectCount,
                          @DecimalMin("0") @DecimalMax("1") BigDecimal defectAreaRate) {}
    public record Result(String inspectionNo, String decision, String routeTo,
                         BigDecimal confidence, int defectCount,
                         List<String> findings, boolean humanOverrideAllowed) {}
}
