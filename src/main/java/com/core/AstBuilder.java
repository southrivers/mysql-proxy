//package com.core;
//
//import io.trino.sql.tree.AstVisitor;
//import io.trino.sql.tree.Select;
//import io.trino.sql.tree.Union;
//
//public class AstBuilder extends AstVisitor<String, Void> {
//
//    @Override
//    protected String visitUnion(Union node, Void context) {
//        return super.visitUnion(node, context);
//    }
//}
// 说明starrocks中astbuilder有两个，分别是trino语法的，和对应的starrocks语法的。