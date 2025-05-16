package cn.parser;

import org.antlr.v4.runtime.tree.ParseTree;

import java.util.List;

/**
 * 说明：这里的visit方法不应该返回super，而是当前visitor对象的对应的visit方法，这样就可以通过当前这一个visitor把所有的信息都收集到
 */
public class MyVisitor extends StarRocksBaseVisitor<String>{

    @Override
    public String visitQuerySpecification(StarRocksParser.QuerySpecificationContext ctx) {
        // TODO 应该在这里作为入口实现sql重写
        // 选择的字段
        List<StarRocksParser.SelectItemContext> selectItemContexts = ctx.selectItem();
        System.out.println("select field start");
        for (StarRocksParser.SelectItemContext selectItemContext : selectItemContexts) {
            System.out.println();
            // 存在多种select情况
            for (ParseTree child : selectItemContext.children) {
                System.out.print(child.getText() + " ");
            }
            System.out.println();
        }
        System.out.println("select field end");
        // from 字段
        System.out.println("table field start");
        StarRocksParser.FromClauseContext fromClauseContext = ctx.fromClause();
        String table = null;
        for (ParseTree child : fromClauseContext.children) {
            System.out.println(child.getText() + " ");
            table = child.getText();
            System.out.println(child.getClass());
        }

        System.out.println("table field end");
        String view = Main.map.get(table);
        System.out.println(view);
        // where 子句
        StarRocksParser.ExpressionContext where = ctx.where;
        for (ParseTree child : where.children) {
            System.out.println(child.getText());
        }
//        System.out.println(where.getText());
        // where后面的条件语句
        List<StarRocksParser.ExpressionContext> expression = ctx.expression();
        // 这里和上面的where的文本一样，需要看下
//        System.out.println(expression.get(0).getText());
        // 分组条件
        StarRocksParser.GroupingElementContext groupingElementContext = ctx.groupingElement();
        return null;
    }
}


