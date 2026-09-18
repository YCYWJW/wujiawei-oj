package com.wujiawei.oj.service;

/**
 * AI 智能摘要服务
 *
 * @author wujiawei
 * @date 2026/9/18
 * 注释：根据帖子标题和内容生成一段简短的 AI 摘要
 */
public interface AiSummaryService {

    /**
     * 生成摘要
     *
     * @param title   帖子标题
     * @param content 帖子正文
     * @return 摘要文本
     */
    String generateSummary(String title, String content);
}