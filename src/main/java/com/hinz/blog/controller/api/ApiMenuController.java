package com.hinz.blog.controller.api;


import com.hinz.blog.common.util.Result;
import com.hinz.blog.common.util.ValidatorUtils;
import com.hinz.blog.service.MenuService;
import com.hinz.blog.model.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 目录管理
 * @author hinz
 * @since 2020-07-13
 */
@RestController
@RequestMapping("api/menu")
public class ApiMenuController {
    @Autowired
    private MenuService menuService;

    @GetMapping
    public Result list() {
        return Result.success("查询成功", menuService.list());
    }

    @PostMapping
    public Result save(@RequestBody Menu menu){
        ValidatorUtils.validate(menu);
        menuService.saveOrUpdate(menu);
        return Result.success("保存成功",menu);
    }

    @PutMapping
    public Result info(Long id){
        return Result.success("查询成功",menuService.getById(id));
    }

    @DeleteMapping
    public Result remove(Long id){
        return menuService.removeById(id)?Result.success("删除成功"):Result.fail("删除失败");
    }
}
