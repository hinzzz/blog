package com.hinz.blog.controller.api;


import com.hinz.blog.common.util.Result;
import com.hinz.blog.common.util.ValidatorUtils;
import com.hinz.blog.service.TagService;
import com.hinz.blog.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 标签管理
 * @author hinz
 * @since 2020-07-13
 */
@RestController
@RequestMapping("api/tag")
public class ApiTagController {
    @Autowired
    private TagService tagService;

    @GetMapping
    public Result list() {
        return Result.success("查询成功", tagService.list());
    }

    @PostMapping
    public Result save(@RequestBody Tag tag){
        ValidatorUtils.validate(tag);
        tagService.saveOrUpdate(tag);
        return Result.success("保存成功",tag);
    }

    @PutMapping
    public Result info(Long id){
        return Result.success("查询成功",tagService.getById(id));
    }

    @DeleteMapping
    public Result remove(Long id){
        return tagService.removeById(id)?Result.success("删除成功"):Result.fail("删除失败");
    }
}
