package cn.parser;

import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;

import java.util.List;

public class ViewVisitor extends StarRocksBaseVisitor<String> {

    CommonTokenStream tokenStream;

    public ViewVisitor() {
    }

    public ViewVisitor(CommonTokenStream tokenStream) {
        this.tokenStream = tokenStream;
    }

    String viewName;

    StringBuilder builder = new StringBuilder();

    @Override
    public String visitQueryPrimaryDefault(StarRocksParser.QueryPrimaryDefaultContext ctx) {

//        List<Token> tokens = tokenStream.get(ctx.start.getStartIndex(), ctx.stop.getStopIndex());
//        System.out.println(tokens);
        List<Token> tokens = tokenStream.getTokens();
        for (Token token : tokens) {
            System.out.println(token.getText());
        }
        StarRocksParser.QuerySpecificationContext querySpecificationContext = ctx.querySpecification();
        return ctx.getText();
    }


}
