package cn.parser;

import org.antlr.v4.runtime.*;

import java.util.HashMap;
import java.util.Map;

public class Main {
    static Map<String, String> map = new HashMap<>();
    public static void main(String[] args) {
        /*String sql = "select order_id1 ,client_id, count(1) as cnt from (\n" +
                "\tSELECT item_id1 as order_id1, item_name as client_id  from goods\n" +
                "\tunion all\n" +
                "\tselect order_id ,client_id  from order_list\n" +
                ") as tmp\n" +
                "where order_id1 > 1001 and order_id1 < 10002\n" +
                "group by order_id1 ,client_id";
        String originSql = "SELECT ORDER_ID1 ,CLIENT_ID, COUNT(1) AS CNT FROM TMP\n" +
                "WHERE ORDER_ID1 > 1001 AND ORDER_ID1 < 10002\n" +
                "GROUP BY ORDER_ID1 ,CLIENT_ID";

        map.put("TMP", "SELECT ITEM_ID1 AS ORDER_ID1, ITEM_NAME AS CLIENT_ID  FROM GOODS\n" +
                "\tUNION ALL\n" +
                "\tSELECT ORDER_ID ,CLIENT_ID  FROM ORDER_LIST");
        CharStream input = CharStreams.fromString(originSql.toUpperCase());
        StarRocksLexer lexer = new StarRocksLexer(input);
        CommonTokenStream commonTokenStream = new CommonTokenStream(lexer);
        StarRocksParser parser = new StarRocksParser(commonTokenStream);
        // 构建visitor
        MyVisitor myVisitor = new MyVisitor();
        // TODO 应该使用被访问对象的accept方法来接收visitor并触发对象的访问，待分析双分派模式
        parser.statement().accept(myVisitor);*/
        // 使用viistor访问对应的语法树
//<<<<<<< HEAD
        // TODO 不能使用visitor的visit方法直接访问某个节点，这样起不到动态的效果，待分析原因
//        myVisitor.visit(parser.singleStatement());
//=======
//        myVisitor.visit(parser.sqlStatements());
//>>>>>>> b4dd17f46c9258112ee5b8afcd6435c6a9b4e0fc
        // 这里起始节点是statement，返回的是StatementContext
//        System.out.println(parser.statement().toStringTree());

        System.out.println("=============================");
        System.out.println("解析视图");
        System.out.println("=============================");
        String cvSql = "CREATE VIEW unified_orders AS\n" +
                "SELECT ITEM_ID1 AS ORDER_ID1, ITEM_NAME AS CLIENT_ID FROM GOODS\n" +
                "UNION ALL\n" +
                "SELECT ORDER_ID, CLIENT_ID FROM ORDER_LIST;\n";
        ViewVisitor viewVisitor = new ViewVisitor();
        CodePointCharStream codePointCharStream = CharStreams.fromString(cvSql);
        StarRocksLexer starRocksLexer = new StarRocksLexer(codePointCharStream);
        CommonTokenStream tokenStream = new CommonTokenStream(starRocksLexer);
        StarRocksParser starRocksParser = new StarRocksParser(tokenStream);

        String visit = viewVisitor.visit(starRocksParser.queryPrimary());
        System.out.println(visit);
    }

}
