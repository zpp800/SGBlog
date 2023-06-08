package com.zpp.domain.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "添加分类dto")
public class AddCategoryDto {
    @ApiModelProperty(notes = "分类ID")
    private Long id;
    //分类名
    @ApiModelProperty(notes = "分类名称")
    private String name;
    //父分类id，如果没有父分类为-1
    @ApiModelProperty(notes = "父分类id")
    private Long pid;
    //描述
    @ApiModelProperty(notes = "分类描述")
    private String description;
    //状态0:正常,1禁用
    @ApiModelProperty(notes = "分类状态0:正常,1禁用")
    private String status;
    @ApiModelProperty(notes = "创建人")
    private Long createBy;
    @ApiModelProperty(notes = "创建时间")
    private Date createTime;
    @ApiModelProperty(notes = "更新人")
    private Long updateBy;
    @ApiModelProperty(notes = "更新时间")
    private Date updateTime;
    //删除标志（0代表未删除，1代表已删除）
    @ApiModelProperty(notes = "分类删除标志（0代表未删除，1代表已删除）")
    private Integer delFlag;
}
