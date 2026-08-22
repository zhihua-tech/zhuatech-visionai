/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.visionai.service;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

    public DriftResult monitorDrift(DriftRequest request) {
        BigDecimal defectRateShift = request.currentDefectRate().subtract(request.baselineDefectRate());
        BigDecimal confidenceDrop = request.baselineConfidence().subtract(request.currentConfidence()).max(BigDecimal.ZERO);
        BigDecimal disagreementRate = BigDecimal.valueOf(Math.min(request.disagreementCount(), request.reviewedSamples()))
            .divide(BigDecimal.valueOf(request.reviewedSamples()), 4, RoundingMode.HALF_UP);
        int score = 0;
        List<String> signals = new ArrayList<>();
        if (defectRateShift.abs().compareTo(request.maximumDefectRateShift()) > 0) {
            score += 40;
            signals.add("当前缺陷率相对基线发生显著漂移");
        }
        if (confidenceDrop.compareTo(request.maximumConfidenceDrop()) > 0) {
            score += 30;
            signals.add("模型平均置信度下降超过门槛");
        }
        if (disagreementRate.compareTo(request.maximumDisagreementRate()) > 0) {
            score += 30;
            signals.add("人工复核分歧率超过门槛");
        }
        if (signals.isEmpty()) signals.add("缺陷率、置信度与人工复核保持稳定");
        String status = score >= 70 ? "RETRAIN" : score >= 40 ? "MONITOR" : "STABLE";
        String recommendation = "RETRAIN".equals(status) ? "冻结自动放行并启动样本复核与再训练"
            : "MONITOR".equals(status) ? "扩大抽检并观察后续三个批次" : "维持当前模型与抽检策略";
        return new DriftResult(request.modelVersion(), score, status,
            defectRateShift.movePointRight(2).setScale(2, RoundingMode.HALF_UP),
            confidenceDrop.movePointRight(2).setScale(2, RoundingMode.HALF_UP),
            disagreementRate.movePointRight(2).setScale(2, RoundingMode.HALF_UP), signals, recommendation);
    }

    public record Request(@NotBlank String inspectionNo, @NotBlank String modelVersion,
                          @DecimalMin("0") @DecimalMax("1") BigDecimal modelConfidence,
                          @Min(0) int totalDefectCount, @Min(0) int criticalDefectCount,
                          @DecimalMin("0") @DecimalMax("1") BigDecimal defectAreaRate) {}
    public record Result(String inspectionNo, String decision, String routeTo,
                         BigDecimal confidence, int defectCount,
                         List<String> findings, boolean humanOverrideAllowed) {}
    public record DriftRequest(@NotBlank String modelVersion,
                               @DecimalMin("0") @DecimalMax("1") BigDecimal baselineDefectRate,
                               @DecimalMin("0") @DecimalMax("1") BigDecimal currentDefectRate,
                               @DecimalMin("0") @DecimalMax("1") BigDecimal baselineConfidence,
                               @DecimalMin("0") @DecimalMax("1") BigDecimal currentConfidence,
                               @Min(1) int reviewedSamples, @Min(0) int disagreementCount,
                               @DecimalMin("0") @DecimalMax("1") BigDecimal maximumDefectRateShift,
                               @DecimalMin("0") @DecimalMax("1") BigDecimal maximumConfidenceDrop,
                               @DecimalMin("0") @DecimalMax("1") BigDecimal maximumDisagreementRate) {}
    public record DriftResult(String modelVersion, int driftScore, String driftStatus,
                              BigDecimal defectRateShiftPoints, BigDecimal confidenceDropPoints,
                              BigDecimal disagreementRatePercent, List<String> signals,
                              String recommendation) {}
}
