/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.visionai.workbench;

import static cn.zhuatech.visionai.workbench.WorkbenchInput.*;

import java.math.*;
import java.util.*;
import org.springframework.stereotype.Service;

/** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
@Service
public class AiWorkbenchService {
  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  public Map<String, Object> execute(Map<String, Object> r) {
    validate(r);

    keys(r, "iouThreshold", "minimumScore", "boxes");
    double threshold = decimal(r, "iouThreshold", 0, 1).doubleValue(),
        min = decimal(r, "minimumScore", 0, 1).doubleValue();
    var boxes = new ArrayList<>(rows(r, "boxes", 0, 200));
    Set<String> ids = new HashSet<>();
    int filtered = 0;
    for (var box : boxes) {
      keys(box, "id", "category", "score", "x1", "y1", "x2", "y2");
      unique(ids, id(box, "id"));
      id(box, "category");
      decimal(box, "score", 0, 1);
      for (String key : List.of("x1", "y1", "x2", "y2")) {
        var n = decimal(box, key, 0, 1);
        if (n.stripTrailingZeros().scale() > 6) throw new IllegalArgumentException("坐标最多6位小数");
      }
      if (((Number) box.get("x1")).doubleValue() >= ((Number) box.get("x2")).doubleValue()
          || ((Number) box.get("y1")).doubleValue() >= ((Number) box.get("y2")).doubleValue())
        throw new IllegalArgumentException("检测框必须有正面积");
    }
    boxes.sort(
        Comparator.<Map<String, Object>>comparingDouble(
                x -> ((Number) x.get("score")).doubleValue())
            .reversed()
            .thenComparing(x -> x.get("id").toString()));
    List<Map<String, Object>> kept = new ArrayList<>(), suppressed = new ArrayList<>();
    for (var box : boxes) {
      if (((Number) box.get("score")).doubleValue() < min) {
        filtered++;
        continue;
      }
      Map<String, Object> suppressor = null;
      double overlap = 0;
      for (var prior : kept)
        if (prior.get("category").equals(box.get("category"))) {
          double v = boxIou(prior, box);
          if (v > threshold) {
            suppressor = prior;
            overlap = v;
            break;
          }
        }
      if (suppressor == null) kept.add(box);
      else
        suppressed.add(
            Map.of(
                "id", box.get("id"), "suppressedBy", suppressor.get("id"), "iou", round(overlap)));
    }
    return Map.of(
        "kept",
        kept,
        "suppressed",
        suppressed,
        "belowScoreCount",
        filtered,
        "imageInferencePerformed",
        false);
  }
}
