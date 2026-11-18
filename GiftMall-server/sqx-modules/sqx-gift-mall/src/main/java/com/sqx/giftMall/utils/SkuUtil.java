package com.sqx.giftMall.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chengming
 * @since 2025/9/22
 */
public class SkuUtil {

    /**
     * 计算多个列表的笛卡尔积
     * 输入：[[A, B], [1, 2]] → 输出：[[A,1], [A,2], [B,1], [B,2]]
     */
    public static List<List<String>> cartesian(List<List<String>> lists) {
        if (lists == null || lists.isEmpty()) {
            return new ArrayList<>();
        }

        // 初始化结果：第一组规格值
        List<List<String>> result = new ArrayList<>();
        List<String> firstList = lists.get(0);

        if (firstList == null || firstList.isEmpty()) {
            return result; // 第一组为空，直接返回空
        }

        // 初始化：把第一个规格组的每个值变成单元素列表
        for (String item : firstList) {
            List<String> single = new ArrayList<>();
            single.add(item);
            result.add(single);
        }

        // 从第二组开始，逐层做笛卡尔积
        for (int i = 1; i < lists.size(); i++) {
            List<String> currentList = lists.get(i);
            if (currentList == null || currentList.isEmpty()) {
                return new ArrayList<>(); // 任一组为空，结果为空
            }

            List<List<String>> newResult = new ArrayList<>();

            // 对当前结果中的每个组合
            for (List<String> existingCombination : result) {
                // 和当前规格组的每个值组合
                for (String newValue : currentList) {
                    List<String> newCombination = new ArrayList<>(existingCombination); // 复制
                    newCombination.add(newValue); // 添加新值
                    newResult.add(newCombination);
                }
            }

            // 替换为新结果
            result = newResult;
        }

        return result;
    }
}
