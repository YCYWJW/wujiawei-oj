package com.wujiawei.oj.model.dto.forum;

import com.wujiawei.oj.model.dto.cursor.CursorPageBaseRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForumCursorPageRequest extends CursorPageBaseRequest {
    private String keyWord;
}
