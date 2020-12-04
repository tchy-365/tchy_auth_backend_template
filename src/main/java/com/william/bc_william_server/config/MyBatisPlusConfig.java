package com.william.bc_william_server.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @program: arz-netty-server
 * @description: mybatisPlus配置
 * @author: Mr.Wang
 * @create: 2019-08-15 15:32
 **/
@EnableTransactionManagement
@MapperScan("com.william.bc_william_server.mapper.**")
@Configuration
public class MyBatisPlusConfig {

//    /**
//     * 配置事务管理器
//     * @param dataSource
//     * @return
//     */
//    @Bean(name = "transactionManager")
//    @Primary
//    public DataSourceTransactionManager testTransactionManager(@Qualifier("datasource") DataSource dataSource) {
//        return new DataSourceTransactionManager(dataSource);
//    }
//
//    @Bean
//    @Primary
//    public BeanNameAutoProxyCreator transactionAutoProxy() {
//        BeanNameAutoProxyCreator bapc = new BeanNameAutoProxyCreator();
//        bapc.setExposeProxy(true);
//        bapc.setBeanNames("*ServiceImpl");
//        bapc.setInterceptorNames("txAdvice");
//        return bapc;
//    }
//
//    @Bean(name = "txAdvice")
//    @Primary
//    public TransactionInterceptor transactionInterceptor(PlatformTransactionManager transactionManager) {
//        final String PROPAGATION_REQUIRED = "PROPAGATION_REQUIRED,-Exception";
//        TransactionInterceptor interceptor = new TransactionInterceptor();
//        interceptor.setTransactionManager(transactionManager);
//        Properties transactionAttributes = new Properties();
//        // TransactionDefinition.PROPAGATION_REQUIRED;
//        transactionAttributes.setProperty("insert*", PROPAGATION_REQUIRED);
//        transactionAttributes.setProperty("save*", PROPAGATION_REQUIRED);
//        transactionAttributes.setProperty("update*", PROPAGATION_REQUIRED);
//        transactionAttributes.setProperty("delete*", PROPAGATION_REQUIRED);
//        transactionAttributes.setProperty("trans*", PROPAGATION_REQUIRED);
//        transactionAttributes.setProperty("get*", PROPAGATION_REQUIRED + ",readOnly");
//        transactionAttributes.setProperty("select*", PROPAGATION_REQUIRED + ",readOnly");
//        interceptor.setTransactionAttributes(transactionAttributes);
//        return interceptor;
//    }

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    /**
     * SQL执行效率插件
     */
    @Bean
//    @Profile({"dev","test"})
    public PerformanceInterceptor performanceInterceptor() {
        PerformanceInterceptor performanceInterceptor=new PerformanceInterceptor();
        /*<!-- SQL 执行性能分析，开发环境使用，线上不推荐。 maxTime 指的是 sql 最大执行时长 -->*/
        //performanceInterceptor.setMaxTime(1000);
        /*<!--SQL是否格式化 默认false-->*/
        performanceInterceptor.setFormat(false);
        return performanceInterceptor;
    }
    /**
     * 注入数据源
     * @return
     */
    @ConfigurationProperties(prefix = "spring.datasource.druid")
    public DataSource dataSource() {
        return DruidDataSourceBuilder.create().build();
    }
}
