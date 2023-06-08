package com.zpp.controller;

import com.zpp.annotation.SystemLog;
import com.zpp.domain.ResponseResult;
import com.zpp.domain.dto.AddLinkDto;
import com.zpp.domain.dto.AddTagDto;
import com.zpp.domain.entity.Link;
import com.zpp.domain.service.LinkService;
import com.zpp.domain.vo.LinkVo;
import com.zpp.domain.vo.TagVo;
import com.zpp.utils.BeanCopyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/content/link")
@Api(tags = "友链相关接口")
public class LinkController {
    @Resource
    private LinkService linkService;
    @GetMapping("/list")
    @ApiOperation(value = "查询友链",notes = "查询友链")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "页号",dataTypeClass = Integer.class,required = true),
            @ApiImplicitParam(name = "pageSize",value = "每页大小",dataTypeClass = Integer.class,required = true),
            @ApiImplicitParam(name = "name",value = "友链名称",dataTypeClass = String.class),
            @ApiImplicitParam(name = "status",value = "友链状态",dataTypeClass = String.class)
    })
    public ResponseResult linkList(Integer pageNum,Integer pageSize,String name,String status){
        return linkService.pageLinkList(pageNum,pageSize,name,status);

    }
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除友链",notes = "删除友链")
    @SystemLog(businessName = "删除友链")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "被删除友链id的集合",dataTypeClass = String.class)
    })
    public ResponseResult deleteLink(@PathVariable List<String> id){
        return linkService.deleteLink(id);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "单个友链查询",notes = "修改时需要回显友链,先查询")
    @SystemLog(businessName = "单个友链查询——修改友链")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "友链回显",dataTypeClass = String.class)
    })
    public ResponseResult<LinkVo> selectOne(@PathVariable String id){
        Link link = linkService.getById(id);
        LinkVo linkVo = BeanCopyUtils.copyBean(link, LinkVo.class);
        return ResponseResult.okResult(linkVo);
    }

    @PutMapping
    @ApiOperation(value = "修改友链",notes = "修改友链")
    @SystemLog(businessName = "修改友链")
    public ResponseResult updateTag(@RequestBody AddLinkDto addLinkDto){
        Link link = BeanCopyUtils.copyBean(addLinkDto, Link.class);
        linkService.updateById(link);
        return ResponseResult.okResult("您已成功修改该友链");
    }

    @PostMapping
    @ApiOperation(value = "新增友链",notes = "增加友链")
    @SystemLog(businessName = "新增友链")
    public ResponseResult addTag(@RequestBody AddLinkDto addLinkDto){
        Link link = BeanCopyUtils.copyBean(addLinkDto, Link.class);
        return linkService.addLink(link);
    }

    //put请求审核通过与审核不通过，条件修改changeLinkStatus
    @PutMapping("/changeLinkStatus")
    @ApiOperation(value = "友链状态审核",notes = "审核通过与审核不通过")
    @SystemLog(businessName = "友链状态审核")
    public ResponseResult changeLinkStatus(@RequestBody AddLinkDto addLinkDto){
        Link link = BeanCopyUtils.copyBean(addLinkDto, Link.class);
        linkService.updateById(link);
        return ResponseResult.okResult();
    }
}
