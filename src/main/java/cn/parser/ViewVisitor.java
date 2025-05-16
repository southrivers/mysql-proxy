package cn.parser;

public class ViewVisitor extends StarRocksBaseVisitor<String> {

    @Override
    public String visitQueryPrimaryDefault(StarRocksParser.QueryPrimaryDefaultContext ctx) {
        System.out.println(ctx.getText());
        return ctx.getText();
    }
}
