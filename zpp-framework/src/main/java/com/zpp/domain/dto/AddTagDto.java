package com.zpp.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "添加标签")
public class AddTagDto {
    @ApiModelProperty(notes = "主键id")
    private Long id;
    //标签名
    @ApiModelProperty(notes = "标签名")
    private String name;
    //备注
    @ApiModelProperty(notes = "标签备注")
    private String remark;
}
