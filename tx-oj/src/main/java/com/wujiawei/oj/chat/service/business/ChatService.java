package com.wujiawei.oj.chat.service.business;

import com.wujiawei.oj.chat.domain.vo.request.ChatMessageRequest;
import com.wujiawei.oj.chat.domain.vo.request.MessagePageRequest;
import com.wujiawei.oj.chat.domain.vo.response.ChatMemberStatisticVO;
import com.wujiawei.oj.chat.domain.vo.response.ChatMessageVO;
import com.wujiawei.oj.model.vo.cursor.CursorPageBaseVO;

/**
 * @author wujiawei
 * @date 2023/12/29 16:13:33
 * 注释：
 */
public interface ChatService {
    Long sendMsg(ChatMessageRequest chatMessageRequest, Long userId);

    ChatMessageVO getMessageVO(Long msgId, Long userId);

    CursorPageBaseVO<ChatMessageVO> getMsgPageByCursor(MessagePageRequest pageRequest, Long userId);

    void msgRead(Long userId, Long roomId);

    ChatMemberStatisticVO getChatMemberStatisticVO();
}
