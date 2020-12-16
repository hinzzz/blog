package com.hinz.blog.config;


import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("file:/home/blog/application-prod.yml")
public class CloudConfig {

    //七牛AK
    private String QN_AK;
    //七牛SK
    public String QN_SK;
    //七牛存储空间名
    public String QN_BUCKET;
    //七牛默认域名
    public String QN_DOMAIN;

}