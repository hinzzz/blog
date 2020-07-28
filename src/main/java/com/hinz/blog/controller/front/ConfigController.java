package com.hinz.blog.controller.front;

import com.hinz.blog.common.util.R;
import com.hinz.blog.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：quanhz
 * @date ：Created in 2020/7/28 17:35
 */
@RequestMapping("/config")
@RestController
public class ConfigController {

    @Autowired
    private ConfigService configService;

    @RequestMapping("/getConfigByName")
    public R getConfigByName(@RequestParam(value = "name",required = true) String name){
        return R.ok().put("value",configService.getConfigByName(name));
    }

}
