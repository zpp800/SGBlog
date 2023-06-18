package com.zpp.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.zpp.annotation.SystemLog;
import com.zpp.domain.ResponseResult;
import com.zpp.domain.dto.AddCategoryDto;
import com.zpp.domain.dto.AddLinkDto;
import com.zpp.domain.entity.Category;
import com.zpp.domain.service.CategoryService;
import com.zpp.domain.vo.CategoryVo;
import com.zpp.domain.vo.ExcelCategoryVo;
import com.zpp.domain.vo.LinkVo;
import com.zpp.enums.AppHttpCodeEnum;
import com.zpp.utils.BeanCopyUtils;
import com.zpp.utils.WebUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/content/category")
@Api(tags = "分类相关接口")
public class CategoryController {
    @Resource
    private CategoryService categoryService;
    @PreAuthorize("@zpp.hasPermission('content:article:writer')")
    @GetMapping("/listAllCategory")
    @ApiOperation(value = "写博文分类",notes = "写博文界面的选择分类")
    public ResponseResult listAllCategory(){
        return categoryService.listAllCategory();
    }

    @GetMapping("/list")
    @ApiOperation(value = "分类查询",notes = "分类查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "页号",dataTypeClass = Integer.class,required = true),
            @ApiImplicitParam(name = "pageSize",value = "每页大小",dataTypeClass = Integer.class,required = true),
            @ApiImplicitParam(name = "name",value = "分类名称",dataTypeClass = String.class),
            @ApiImplicitParam(name = "status",value = "分类状态",dataTypeClass = String.class)
    })
    public ResponseResult linkList(Integer pageNum,Integer pageSize,String name,String status){
        return categoryService.pageLinkList(pageNum,pageSize,name,status);

    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除分类",notes = "删除分类")
    @SystemLog(businessName = "删除分类")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "被删除分类id的集合",dataTypeClass = String.class)
    })
    public ResponseResult deleteLink(@PathVariable List<String> id){
        return categoryService.deleteCategory(id);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "单个分类查询",notes = "修改时需要回显分类,先查询")
    @SystemLog(businessName = "单个分类查询——修改分类")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "分类回显",dataTypeClass = String.class)
    })
    public ResponseResult<LinkVo> selectOne(@PathVariable String id){
        Category category = categoryService.getById(id);
        CategoryVo categoryVo = BeanCopyUtils.copyBean(category, CategoryVo.class);
        return ResponseResult.okResult(categoryVo);
    }

    @PutMapping
    @ApiOperation(value = "修改分类",notes = "修改分类")
    @SystemLog(businessName = "修改分类")
    public ResponseResult updateTag(@RequestBody AddCategoryDto addCategoryDto){
        Category category = BeanCopyUtils.copyBean(addCategoryDto, Category.class);
        categoryService.updateById(category);
        return ResponseResult.okResult("您已成功修改该分类");
    }


    @PostMapping
    @ApiOperation(value = "新增分类",notes = "增加分类")
    @SystemLog(businessName = "新增分类")
    public ResponseResult addTag(@RequestBody AddCategoryDto addCategoryDto){
        Category category = BeanCopyUtils.copyBean(addCategoryDto, Category.class);
        return categoryService.addLink(category);
    }

    //分类导出
    @PreAuthorize("@zpp.hasPermission('content:category:export')")
    @GetMapping("/export")
    @ApiOperation(value = "导出分类",notes = "导出分类")
    public void export(HttpServletResponse response){
        try {
            //设置下载文件的请求头
            WebUtils.setDownLoadHeader("分类.xlsx",response);
            //获取需要导出的数据
            List<Category> categoryVos = categoryService.list();

            List<ExcelCategoryVo> excelCategoryVos = BeanCopyUtils.copyBeanList(categoryVos, ExcelCategoryVo.class);
            //把数据写入到Excel中
            EasyExcel.write(response.getOutputStream(), ExcelCategoryVo.class).autoCloseStream(Boolean.FALSE).sheet("分类导出")
                    .doWrite(excelCategoryVos);

        } catch (Exception e) {
            //如果出现异常也要响应json
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
            WebUtils.renderString(response, JSON.toJSONString(result));
        }
    }
}