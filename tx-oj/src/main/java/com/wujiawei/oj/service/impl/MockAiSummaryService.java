package com.wujiawei.oj.service.impl;

import com.wujiawei.oj.service.AiSummaryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * Mock 摘要服务
 *
 * 当 ai-summary.enabled=false（或未配置）时启用，不调用真实 AI 接口，
 * 直接返回一段占位摘要，用于本地开发与测试。
 */
@Service
@ConditionalOnProperty(name = "ai-summary.enabled", havingValue = "false", matchIfMissing = true)
public class MockAiSummaryService implements AiSummaryService {

    /**
     * Mock 摘要取正文前多少字
     */
    private static final int PREFIX_LEN = 50;

    @Override
    public String generateSummary(String title, String content) {
        String text = StringUtils.isNotBlank(content) ? content : StringUtils.defaultString(title, "");
        if (text.length() > PREFIX_LEN) {
            text = text.substring(0, PREFIX_LEN);
        }
        return "【AI摘要占位】" + text + "……";
    }
}