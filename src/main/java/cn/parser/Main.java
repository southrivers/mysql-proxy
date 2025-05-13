package cn.parser;

import org.antlr.v4.runtime.*;

public class Main {
    public static void main(String[] args) {
        String sql = "select order_id1 ,client_id, count(1) as cnt from (\n" +
                "\tSELECT item_id1 as order_id1, item_name as client_id  from goods\n" +
                "\tunion all\n" +
                "\tselect order_id ,client_id  from order_list\n" +
                ") as tmp\n" +
                "where order_id1 > 1001 and order_id1 < 10002\n" +
                "group by order_id1 ,client_id";

        CharStream input = CharStreams.fromString(sql);
        StarRocksLexer lexer = new StarRocksLexer(input);
        CommonTokenStream commonTokenStream = new CommonTokenStream(lexer);
        StarRocksParser parser = new StarRocksParser(commonTokenStream);
        // 这里起始节点是statement，返回的是StatementContext
        System.out.println(parser.statement().toStringTree());
    }
}
