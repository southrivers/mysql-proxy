package cn.parser;

/**
 * 说明：这里的visit方法不应该返回super，而是当前visitor对象的对应的visit方法，这样就可以通过当前这一个visitor把所有的信息都收集到
 */
public class MyVisitor extends StarRocksBaseVisitor<String>{

    @Override
    public String visitSingleStatement(StarRocksParser.SingleStatementContext ctx) {

        return visit(ctx.statement());
    }

    @Override
    public String visitStatement(StarRocksParser.StatementContext ctx) {
        return super.visitStatement(ctx);
    }

    @Override
    public String visitFrom(StarRocksParser.FromContext ctx) {
        System.out.println(ctx.getText());
        return super.visitFrom(ctx);
    }
}


