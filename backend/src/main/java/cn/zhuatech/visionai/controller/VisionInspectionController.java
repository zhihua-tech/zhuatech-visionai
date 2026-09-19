/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.visionai.controller;

import cn.zhuatech.visionai.common.ApiResponse;
import cn.zhuatech.visionai.service.VisionInspectionService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@RestController
@RequestMapping("/api/ai/vision")
@PreAuthorize("hasAnyRole('DOMAIN_USER','DOMAIN_OPERATOR','ADMIN')")
public class VisionInspectionController {
    private final VisionInspectionService service;
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public VisionInspectionController(VisionInspectionService service) { this.service = service; }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @PostMapping("/inspect")
    public ApiResponse<VisionInspectionService.Result> inspect(@Valid @RequestBody VisionInspectionService.Request request) {
        return ApiResponse.ok("视觉质检判定完成", service.inspect(request));
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @PostMapping("/monitor-drift")
    public ApiResponse<VisionInspectionService.DriftResult> monitorDrift(
        @Valid @RequestBody VisionInspectionService.DriftRequest request) {
        return ApiResponse.ok("视觉模型漂移诊断完成", service.monitorDrift(request));
    }
}
