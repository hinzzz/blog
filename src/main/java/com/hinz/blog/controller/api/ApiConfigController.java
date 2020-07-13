package com.hinz.blog.controller.api;

import com.hinz.blog.common.constant.ConfigConst;
import com.hinz.blog.common.util.Result;
import com.hinz.blog.common.util.ValidatorUtils;
import com.hinz.blog.service.ConfigService;
import com.hinz.blog.model.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 配置管理
 * @author hinz
 * @since 2020-07-13
 */
@RestController
@RequestMapping("api/config")
public class ApiConfigController {
    @Autowired
    private ConfigService configService;

    @GetMapping
    public Result list() {
        return Result.success("查询成功",configService.list());
    }

    @PostMapping
    public Result save(@RequestBody Config config){
        ValidatorUtils.validate(config);
        if(config.getName().equals(ConfigConst.FILE_STORAGE)){
            ValidatorUtils.validateStorageConfig(config.getValue());
        }
        boolean result=configService.saveOrUpdate(config);
        if(result){
            configService.clearCache();
            return Result.success("保存成功");
        }else{
            return Result.success("保存失败");
        }
    }

    @PutMapping
    public Result info(Long id){
        return Result.success("查询成功",configService.getById(id));
    }

    @DeleteMapping
    public Result remove(Long id){
        boolean result=configService.removeById(id);
        if(result){
            configService.clearCache();
            return Result.success("删除成功");
        }else{
            return Result.success("删除失败");
        }
    }

    @GetMapping("global")
    public Result global() {
        return Result.success("查询成功",configService.findAllGlobal());
    }
}
