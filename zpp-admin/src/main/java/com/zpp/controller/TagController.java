package com.zpp.controller;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.zpp.annotation.SystemLog;
import com.zpp.domain.ResponseResult;
import com.zpp.domain.dto.AddTagDto;
import com.zpp.domain.entity.Tag;
import com.zpp.domain.service.TagService;
import com.zpp.domain.vo.PageVo;
import com.zpp.domain.vo.TagVo;
import com.zpp.utils.BeanCopyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/content/tag")
@Api(tags = "标签相关接口")
@PreAuthorize("@zpp.hasPermission('content:tag:index')")
public class TagController {
    @Resource
    private TagService tagService;
    @GetMapping("/listAllTag")
    @ApiOperation(value = "写博文标签",notes = "写博文界面的选择标签")
    public ResponseResult<TagVo> listAllTag(){
        return tagService.AllTag();
    }
    @GetMapping("/list")
    @SystemLog(businessName = "查询标签")
    @ApiOperation(value = "查询标签",notes = "查询标签")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "页号",dataTypeClass = Integer.class,required = true),
            @ApiImplicitParam(name = "pageSize",value = "每页大小",dataTypeClass = Integer.class,required = true),
            @ApiImplicitParam(name = "name",value = "标签名称",dataTypeClass = String.class)
    })
    public ResponseResult<PageVo> list(Integer pageNum,Integer pageSize,String name){
        return tagService.pageTagList(pageNum,pageSize,name);
    }

    @PostMapping
    @ApiOperation(value = "新增标签",notes = "增加标签")
    @SystemLog(businessName = "新增标签")
    public ResponseResult addTag(@RequestBody AddTagDto addTagDto){
        Tag tag = BeanCopyUtils.copyBean(addTagDto, Tag.class);
        return tagService.addTag(tag);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除标签",notes = "删除标签")
    @SystemLog(businessName = "删除标签")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "被删除标签id的集合",dataTypeClass = String.class)
    })
    public ResponseResult deleteTag(@PathVariable List<String> id){
        return tagService.deleteTag(id);
    }
    @GetMapping("/{id}")
    @ApiOperation(value = "单个标签查询",notes = "修改时需要回显标签,先查询")
    @SystemLog(businessName = "单个标签查询——修改标签")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "标签回显",dataTypeClass = String.class)
    })
    public ResponseResult<TagVo> selectOne(@PathVariable String id){
        Tag tag = tagService.getById(id);
        TagVo tagVo = BeanCopyUtils.copyBean(tag, TagVo.class);
        return ResponseResult.okResult(tagVo);
    }
    @PutMapping
    @ApiOperation(value = "修改标签",notes = "修改标签")
    @SystemLog(businessName = "修改标签")
    public ResponseResult updateTag(@RequestBody AddTagDto addTagDto){
        Tag tag = BeanCopyUtils.copyBean(addTagDto, Tag.class);
        tagService.updateById(tag);
        return ResponseResult.okResult("您已成功修改该标签");
    }
}
