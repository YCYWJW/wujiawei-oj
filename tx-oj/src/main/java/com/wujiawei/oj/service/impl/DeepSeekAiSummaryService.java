package com.wujiawei.oj.service.impl;

import com.wujiawei.oj.service.AiSummaryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DeepSeek 摘要服务
 *
 * 当 ai-summary.enabled=true 时启用，调用 DeepSeek 大模型生成摘要。
 * 内置连接/读超时、失败降级为 Mock 结果、API Key 从环境变量兜底读取等保护逻辑。
 */
@Service
@ConditionalOnProperty(name = "ai-summary.enabled", havingValue = "true")
@Slf4j
public class DeepSeekAiSummaryService implements AiSummaryService {

    /**
     * 摘要最大长度（字符）
     */
    private static final int MAX_SUMMARY_LEN = 200;

    /**
     * 连接超时（毫秒）：5 秒
     */
    private static final int CONNECT_TIMEOUT = 5000;

    /**
     * 读超时（毫秒）：10 秒
     */
    private static final int READ_TIMEOUT = 10000;

    /**
     * Mock 降级结果取正文前多少字
     */
    private static final int MOCK_PREFIX_LEN = 50;

    @Value("${ai-summary.deepseek-api-key:}")
    private String apiKey;

    @Value("${ai-summary.api-url:https://api.deepseek.com/chat/completions}")
    private String apiUrl;

    @Value("${ai-summary.model:deepseek-chat}")
    private String model;

    @Value("${ai-summary.max-tokens:200}")
    private Integer maxTokens;

    /**
     * 带超时配置的 RestTemplate（连接 5s、读 10s）
     */
    private final RestTemplate restTemplate = createRestTemplate();

    private static RestTemplate createRestTemplate() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(CONNECT_TIMEOUT);
        factory.setReadTimeout(READ_TIMEOUT);
        return new RestTemplate(factory);
    }

    /**
     * 优先用配置里的 Key；配置为占位符或空时，回退到环境变量 DEEPSEEK_API_KEY
     */
    private String resolveApiKey() {
        if (StringUtils.isBlank(apiKey) || apiKey.contains("请填入")) {
            String envKey = System.getenv("DEEPSEEK_API_KEY");
            return StringUtils.isNotBlank(envKey) ? envKey : apiKey;
        }
        return apiKey;
    }

    @Override
    public String generateSummary(String title, String content) {
        try {
            String summary = callDeepSeek(title, content);
            if (StringUtils.isNotBlank(summary) && summary.length() > MAX_SUMMARY_LEN) {
                summary = summary.substring(0, MAX_SUMMARY_LEN);
            }
            return StringUtils.isBlank(summary) ? mockFallback(title, content) : summary;
        } catch (Exception e) {
            // 关键：API 挂掉也不能影响发帖，记日志并降级为占位摘要
            log.error("DeepSeek 摘要生成失败，降级为占位摘要", e);
            return mockFallback(title, content);
        }
    }

    @SuppressWarnings("unchecked")
    private String callDeepSeek(String title, String content) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + resolveApiKey());

        Map<String, Object> systemMessage = new HashMap<>();
        systemMessage.put("role", "system");
        systemMessage.put("content", "你是一个文本摘要助手。请用不超过100个字概括用户给出的帖子内容，直接返回摘要文本，不要任何多余的话。");

        Map<String, Object> userMessage = new HashMap<>();
        userMessage.put("role", "user");
        userMessage.put("content", "标题：" + title + "\n正文：" + content);

        List<Map<String, Object>> messages = new ArrayList<>();
        messages.add(systemMessage);
        messages.add(userMessage);

        Map<String, Object> body = new HashMap<>();
        body.put("model", model);
        body.put("messages", messages);
        body.put("max_tokens", maxTokens);
        body.put("temperature", 0.3);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
        Map<String, Object> response = restTemplate.postForObject(apiUrl, entity, Map.class);
        if (response == null) {
            throw new IllegalStateException("DeepSeek 返回为空");
        }
        List<Map<String, Object>> choices = (List<Map<String, Object>>) response.get("choices");
        if (choices == null || choices.isEmpty()) {
            throw new IllegalStateException("DeepSeek 返回缺少 choices");
        }
        Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
        if (message == null) {
            throw new IllegalStateException("DeepSeek 返回缺少 message");
        }
        Object contentResult = message.get("content");
        return contentResult == null ? null : contentResult.toString();
    }

    /**
     * Mock 降级结果：AI 调用失败时返回这段占位摘要，绝不让用户看到报错
     */
    private String mockFallback(String title, String content) {
        String text = StringUtils.isNotBlank(content) ? content : StringUtils.defaultString(title, "");
        if (text.length() > MOCK_PREFIX_LEN) {
            text = text.substring(0, MOCK_PREFIX_LEN);
        }
        return "【AI摘要占位】" + text + "……";
    }
}