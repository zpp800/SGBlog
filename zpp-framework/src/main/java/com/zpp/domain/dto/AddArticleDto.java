package com.zpp.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "添加文章dto")
public class AddArticleDto {
    //文章id
    @ApiModelProperty(notes = "文章ID")
    private Long id;
    //标题
    @ApiModelProperty(notes = "文章标题")
    private String title;
    //文章内容
    @ApiModelProperty(notes = "文章内容")
    private String content;
    //文章摘要
    @ApiModelProperty(notes = "文章摘要")
    private String summary;
    //所属文章id
    @ApiModelProperty(notes = "文章分类id")
    private Long categoryId;
    //缩略图
    @ApiModelProperty(notes = "缩略图")
    private String thumbnail;
    //是否置顶（0否，1是）
    @ApiModelProperty(notes = "文章是否置顶（0否，1是）")
    private String isTop;
    //状态（0已发布，1草稿）
    @ApiModelProperty(notes = "文章状态（0已发布，1草稿）")
    private String status;
    //是否允许评论 1是，0否
    @ApiModelProperty(notes = "文章是否允许评论 1是，0否")
    private String isComment;
    //文章对应的标签，一对多
    @ApiModelProperty(notes = "文章对应的标签，一对多")
    private List<Long> tags;
}
