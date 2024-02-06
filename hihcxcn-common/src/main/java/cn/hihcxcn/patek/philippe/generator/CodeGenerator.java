package cn.hihcxcn.patek.philippe.generator;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import io.micrometer.common.util.StringUtils;

import java.util.Collections;
import java.util.Scanner;

public class CodeGenerator {
    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }
    
    public static void main(String[] args) {
        FastAutoGenerator.create("url", "username", "password")
        // 全局配置
        .globalConfig(builder -> {
            builder.author("yong") // 设置作者
            .commentDate("yyyy-MM-dd hh:mm:ss")   //注释日期
            .outputDir(System.getProperty("user.dir") + "/hihcxcn-common/src/main/java/cn/hihcxcn/patek/philippe/auto") // 指定输出目录
            .disableOpenDir() //禁止打开输出目录，默认打开
            ;
        })
        // 包配置
        .packageConfig(builder -> {
            builder.parent("cn.hihcxcn.patek.philippe") // 设置父包名！！！！
            .pathInfo(Collections.singletonMap(OutputFile.xml, System.getProperty("user.dir") + "/hihcxcn-common/src/main/resources/mappers")); // 设置mapperXml生成路径
        })
        // 策略配置
        .strategyConfig(builder -> {
            builder
            //.addInclude("sys_menu") // 设置需要生成的表名
            .addInclude(scanner("表名，多个英文逗号分割").split(","))
//           .addTablePrefix("sys_") // 设置过滤表前缀
            // Entity 策略配置
            .entityBuilder()
            .enableLombok() //开启 Lombok
            .enableFileOverride() // 覆盖已生成文件
            .naming(NamingStrategy.underline_to_camel)  //数据库表映射到实体的命名策略：下划线转驼峰命
            .columnNaming(NamingStrategy.underline_to_camel)    //数据库表字段映射到实体的命名策略：下划线转驼峰命
            // Mapper 策略配置
            .mapperBuilder()
            .enableFileOverride() // 覆盖已生成文件
            // Service 策略配置
            .serviceBuilder()
            .enableFileOverride() // 覆盖已生成文件
            .formatServiceFileName("%sService") //格式化 service 接口文件名称，%s进行匹配表名，如 UserService
            .formatServiceImplFileName("%sServiceImpl") //格式化 service 实现类文件名称，%s进行匹配表名，如 UserServiceImpl
            // Controller 策略配置
            .controllerBuilder()
            .enableFileOverride() // 覆盖已生成文件
            ;
        })
        .templateEngine(new FreemarkerTemplateEngine()) // 我们这里使用Freemarker引擎模板，默认的是Velocity引擎模板
        .execute();

    }
}
