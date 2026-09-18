package com.wujiawei.oj.chat.domain.vo.request;

import com.wujiawei.oj.model.dto.cursor.CursorPageBaseRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wujiawei
 * @date 2023/12/31 12:31:34
 * 注释：
 */
@Data
//@ApiModel("消息-游标翻页请求")
public class MessagePageRequest extends CursorPageBaseRequest {

    @ApiModelProperty("房间id")
    private Long roomId;
}
