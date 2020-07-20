package com.hinz.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hinz.blog.common.constant.CodeEnum;
import com.hinz.blog.common.util.Result;
import com.hinz.blog.mapper.BlogUserMapper;
import com.hinz.blog.model.BlogMember;
import com.hinz.blog.model.User;
import com.hinz.blog.service.BlogUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BlogUserServiceImpl extends ServiceImpl<BlogUserMapper, BlogMember> implements BlogUserService {

    @Autowired
    BlogUserMapper blogUserMapper;

    @Override
    public Result login(BlogMember blogMember) {

        BlogMember umsMemberFromDb = loginFromDb(blogMember);
        Result result = new Result();
        //验证密码是否正确
        if(umsMemberFromDb!= null){
            result.setCode(CodeEnum.SUCCESS.getValue());
            result.setMsg("登录成功");
            result.setData(umsMemberFromDb);
//            //重置登录失败次数
//            umsMemberFromDb.setLoginFail(0);
//            //更新最后登录时间
//            user.setLastLoginTime(new Date());
//            updateById(user);
        }else{
//            //密码每错误一次，失败次数+1
//            Integer loginFail=user.getLoginFail()+1;
//            user.setLoginFail(loginFail);
//            //超过阈值，禁用账号
//            if(loginFail>= Const.LOGIN_FAIL_COUNT){
//                user.setDisable(true);
               result.setMsg("账号或密码错误！");
//            }else{
//                result.setMsg(String.format("密码错误，连续输错密码%d次后将禁用账号，您已经输错了%d次。",Const.LOGIN_FAIL_COUNT,loginFail));
//            }
//            updateById(user);
        }
        return result;

    }
    private BlogMember loginFromDb(BlogMember blogMember) {
       /// List<BlogMember> select = blogUserMapper.select(blogMember);
        BlogMember blogMember1=getOne(new QueryWrapper<BlogMember>().eq("username",blogMember.getUsername()).eq("password",blogMember.getPassword()));
        return  blogMember1;
    }
}
