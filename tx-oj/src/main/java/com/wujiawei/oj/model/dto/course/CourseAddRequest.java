package com.wujiawei.oj.model.dto.course;

import com.wujiawei.oj.model.vo.course.CourseVO;
import com.wujiawei.oj.model.vo.course.CourseVideoVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseAddRequest {
    CourseVO courseVO;

    List<CourseVideoVO> videoVOList;
}
