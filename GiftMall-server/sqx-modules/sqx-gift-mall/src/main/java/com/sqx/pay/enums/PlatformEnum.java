package com.sqx.pay.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author chengming
 * @since 2025/4/22
 */
public enum PlatformEnum {
    ALI("1", "支付宝"),
    WX("2", "微信"),
    LOCAL("5", "余额"),
    ;

    private final String code;
    private final String desc;

    PlatformEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String getDescByCode(String code) {
        for (PlatformEnum platform : PlatformEnum.values()) {
            if (platform.getCode().equals(code)) {
                return platform.getDesc();
            }
        }

        throw new IllegalArgumentException("错误的平台code: " + code);
    }

    public static List<String> allCode() {
        return Arrays.stream(PlatformEnum.values())
                .map(PlatformEnum::getCode)
                .collect(Collectors.toList());
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
