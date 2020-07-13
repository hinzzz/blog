package com.hinz.blog.controller.api;


import com.hinz.blog.common.util.Result;
import com.hinz.blog.common.util.ValidatorUtils;
import com.hinz.blog.service.LinkService;
import com.hinz.blog.model.Link;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 链接管理
 * @author hinz
 * @since 2020-07-13
 */
@RestController
@RequestMapping("api/link")
public class ApLinkController {
    @Autowired
    private LinkService linkService;

    @GetMapping
    public Result list(){
        return Result.success("查询成功",linkService.list());
    }

    @PostMapping
    public Result save(@RequestBody Link link){
        ValidatorUtils.validate(link);
        linkService.saveOrUpdate(link);
        return Result.success("保存成功",link);
    }

    @PutMapping
    public Result info(Long id){
        return Result.success("查询成功",linkService.getById(id));
    }

    @DeleteMapping
    public Result remove(Long id){
        return linkService.removeById(id)?Result.success("删除成功"):Result.fail("删除失败");
    }
}
