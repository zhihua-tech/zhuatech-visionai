/* Copyright 2026 上海如静知华信息科技有限公司 */
package cn.zhuatech.visionai.controller;

import cn.zhuatech.visionai.common.ApiResponse;
import cn.zhuatech.visionai.service.VisionInspectionService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai/vision")
@PreAuthorize("hasAnyRole('DOMAIN_USER','DOMAIN_OPERATOR','ADMIN')")
public class VisionInspectionController {
    private final VisionInspectionService service;
    public VisionInspectionController(VisionInspectionService service) { this.service = service; }

    @PostMapping("/inspect")
    public ApiResponse<VisionInspectionService.Result> inspect(@Valid @RequestBody VisionInspectionService.Request request) {
        return ApiResponse.ok("视觉质检判定完成", service.inspect(request));
    }

    @PostMapping("/monitor-drift")
    public ApiResponse<VisionInspectionService.DriftResult> monitorDrift(
        @Valid @RequestBody VisionInspectionService.DriftRequest request) {
        return ApiResponse.ok("视觉模型漂移诊断完成", service.monitorDrift(request));
    }
}
