package com.wmeimob.tool.db;

import org.mybatis.generator.api.dom.java.CompilationUnit;
import tk.mybatis.mapper.generator.MapperCommentGenerator;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 自定义注释
 * Created by Shinez on 2016/3/8.
 */
public class ShiNezCommentGenerator extends MapperCommentGenerator {

    /**
     * java文件注释
     */
    @Override
    public void addJavaFileComment(CompilationUnit compilationUnit) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        compilationUnit.addFileCommentLine("/*");
        compilationUnit.addFileCommentLine("* " + compilationUnit.getType().getShortName() + ".java");
        compilationUnit.addFileCommentLine("* EnTropyShiNe");
        compilationUnit.addFileCommentLine("* Copyright © 2017 ShiNez All Rights Reserved");
        compilationUnit.addFileCommentLine("* 作者：ShiNez");
        compilationUnit.addFileCommentLine("* QQ：136266602");
        compilationUnit.addFileCommentLine("* " + simpleDateFormat.format(new Date()) + " Created");
        compilationUnit.addFileCommentLine("*/ ");
    }


//    /**
//     * 类注释
//     * @param innerClass
//     * @param introspectedTable
//     */
//    @Override
//    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable) {
//        innerClass.addJavaDocLine("/**");
////        ShiNezIntrospectedTable shiNezIntrospectedTable = (ShiNezIntrospectedTable) introspectedTable;
////        innerClass.addJavaDocLine("* "+ shiNezIntrospectedTable.getFullyQualifiedTable().getRemark());
//        innerClass.addJavaDocLine(" @author ShiNez ");
//        innerClass.addJavaDocLine(" */");
//    }

}
