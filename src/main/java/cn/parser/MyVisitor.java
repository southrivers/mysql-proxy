package cn.parser;

/**
 * 说明：这里的visit方法不应该返回super，而是当前visitor对象的对应的visit方法，这样就可以通过当前这一个visitor把所有的信息都收集到
 */
public class MyVisitor extends StarRocksBaseVisitor<String>{

    @Override
    public String visitSqlStatements(StarRocksParser.SqlStatementsContext ctx) {
        return visit(ctx.singleStatement(0));
    }

    @Override
    public String visitSingleStatement(StarRocksParser.SingleStatementContext ctx) {
        System.out.println("step1");
        return visit(ctx.statement());
    }

    @Override
    public String visitStatement(StarRocksParser.StatementContext ctx) {
        System.out.println("step2");
        return visit(ctx.queryStatement());
    }

    @Override
    public String visitQueryStatement(StarRocksParser.QueryStatementContext ctx) {
        System.out.println("step3");
        return visit(ctx.queryRelation());
    }

    @Override
    public String visitQueryRelation(StarRocksParser.QueryRelationContext ctx) {
        System.out.println("step4");
        return visit(ctx.queryNoWith());
    }

    @Override
    public String visitSetOperation(StarRocksParser.SetOperationContext ctx) {
        System.out.println("step5");
        return super.visitSetOperation(ctx);
    }

    //    @Override
//    public String visitFrom(StarRocksParser.FromContext ctx) {
//        System.out.println(ctx.getText());
//        return super.visitFrom(ctx);
//    }
}


