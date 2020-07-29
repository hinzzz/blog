package com.hinz.blog.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hinz.blog.common.constant.CodeEnum;
import com.hinz.blog.common.constant.Const;
import com.hinz.blog.common.exception.BlogException;
import com.hinz.blog.service.TagService;
import com.hinz.blog.model.Article;
import com.hinz.blog.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 前台标签控制器
 * @author hinz
 * @date 2020-07-13
 */
@Controller
@RequestMapping("tag")
public class TagController {

    @Autowired
    private TagService tagService;

    /**
     * 标签集合页
     * @return
     */
    @GetMapping("/")
    public String tags(Model model) {
        List<Tag> tags=tagService.list();
        model.addAttribute("list",tags);
        return "tags";
    }

    /**
     * 获得指定标签下的文章
     * @return
     */
//    @GetMapping("{url}")
//    public String tag(Model model,@PathVariable(value = "url") String url) {
//        return tag(model,url,1);
//    }

    /**
     *
     * @param model
     * @param url 标签链接
     * @param pageIndex 需要加载的页码
     * @return
     */
//    @GetMapping("{url}/{pageIndex}")
//    public String tag(Model model,@PathVariable(value = "url") String url,@PathVariable(value = "pageIndex") Integer pageIndex) {
//        Tag tag=tagService.getOne(new QueryWrapper<Tag>().eq("url",url));
//        if(null==tag){
//            throw new BlogException(CodeEnum.NOT_FOUND.getValue(),"标签不存在："+url);
//        }
//        IPage<Article> page=articleService.findPageByTag(new Page<>(pageIndex, Const.PAGE_SIZE),tag.getId());
//        model.addAttribute("info",tag);
//        model.addAttribute("page",page);
//        //分页代码
//        model.addAttribute("pageHtml",pagination((int)page.getCurrent(),(int)page.getPages(), "/tag/"+url+"/"));
//        return "list";
//    }
}
