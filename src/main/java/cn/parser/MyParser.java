package cn.parser;

public class MyParser extends StarRocksBaseVisitor<String>{

    @Override
    public String visitStatement(StarRocksParser.StatementContext ctx) {
        return super.visitStatement(ctx);
    }
}


