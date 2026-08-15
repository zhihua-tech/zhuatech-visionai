/* Copyright 2026 上海如静知华信息科技有限公司 */
package cn.zhuatech.visionai;

import cn.zhuatech.visionai.service.VisionInspectionService;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.assertj.core.api.Assertions.assertThat;

class VisionInspectionServiceTests {
    private final VisionInspectionService service = new VisionInspectionService();

    @Test void blocksCriticalDefect() {
        var result = service.inspect(new VisionInspectionService.Request("VI-001", "surface-v3",
            new BigDecimal("0.97"), 3, 1, new BigDecimal("0.018")));
        assertThat(result.decision()).isEqualTo("BLOCK");
        assertThat(result.routeTo()).isEqualTo("隔离区");
    }

    @Test void sendsLowConfidenceResultToReview() {
        var result = service.inspect(new VisionInspectionService.Request("VI-002", "surface-v3",
            new BigDecimal("0.72"), 0, 0, BigDecimal.ZERO));
        assertThat(result.decision()).isEqualTo("REVIEW");
        assertThat(result.findings()).anyMatch(item -> item.contains("人工复核"));
    }
}
