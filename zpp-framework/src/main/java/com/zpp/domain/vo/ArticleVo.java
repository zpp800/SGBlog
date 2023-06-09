package com.zpp.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleVo {
    private Long id;
    //标题
    private String title;
    //文章摘要
    private String summary;
    /**
     * 文章内容
     */
    private String content;
    /**
     * 所属分类id
     */
    private Long categoryId;
    //所属分类名
//    private String categoryName;
    //缩略图
    private String thumbnail;

    private Date createTime;
    /**
     * 是否允许评论 1是，0否
     */
    private String isComment;
    private String isTop;
    /**
     * 状态（0已发布，1草稿）
     */
    private String status;
}
