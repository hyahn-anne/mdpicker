package pe.ahn.mdpicker.model.category;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CategoryInfo {
    TOP(1L, "상의"),
    OUTER(2L, "아우터"),
    BOTTOM(3L, "바지"),
    SNEAKERS(4L, "스니커즈"),
    BAG(5L, "가방"),
    CAP(6L, "모자"),
    SOCKS(7L, "양말"),
    ACCESSORIES(8L, "액세서리"),
    ;
    private final Long code;
    private final String name;

    public static String getCategoryInfo(Long code) {
        String categoryName = null;
        for (CategoryInfo categoryInfo : CategoryInfo.values()) {
            if (categoryInfo.getCode().equals(code)) {
                categoryName = categoryInfo.getName();
            }
        }
        return categoryName;
    }
}
