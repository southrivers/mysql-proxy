package cn.parser;

import org.antlr.v4.runtime.tree.ParseTree;

public class MyVisitor extends StarRocksBaseVisitor<ParseTree>{

    @Override
    public ParseTree visitQueryStatement(StarRocksParser.QueryStatementContext ctx) {
        StarRocksParser.QueryRelationContext queryRelationContext = ctx.queryRelation();
        System.out.println(queryRelationContext);
        return visit(ctx.queryRelation());
    }

//    @Override
//    public ParseTree visitSqlStatements(StarRocksParser.SqlStatementsContext ctx) {
//        return visit(ctx.singleStatement());
//    }

    @Override
    public ParseTree visitSingleStatement(StarRocksParser.SingleStatementContext ctx) {
        System.out.println("hello");
        return visit(ctx.statement());
    }

//    @Override
//    public ParseTree visitStatement(StarRocksParser.StatementContext ctx) {
//        return visit(ctx.ri);
//    }

//    @Override
//    public ParseTree visitQuerySpecification(StarRocksParser.QuerySpecificationContext ctx) {
//        System.out.println(ctx.where);
//        return super.visitQuerySpecification(ctx);
//    }

//    @Override
//    public ParseTree visitQueryRelation(StarRocksParser.QueryRelationContext ctx) {
//        int childCount = ctx.getChildCount();
//        for (int i = 0; i < childCount; ++i) {
//            System.out.println(ctx.getChild(i).toStringTree());
//        }
//        return visit(ctx.);
//    }
}


