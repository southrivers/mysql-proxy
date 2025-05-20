package io.mysql.simpleproxy.optimize;

import io.mysql.simpleproxy.optimize.model.CustomRelation;
import io.mysql.simpleproxy.optimize.visitor.CustomStatementVisitor;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.Select;

public class UnionAllOptimize {

    public static void main(String[] args) throws JSQLParserException {
        Statement parse = CCJSqlParserUtil.parse("SELECT\n" +
                "    d1,\n" +
                "    d2,\n" +
                "    d3,\n" +
                "    MAX(m1) AS max_m1,\n" +
                "    MAX(m2) AS max_m2,\n" +
                "    MAX(m3) AS max_m3\n" +
                "FROM (\n" +
                "    SELECT d1, d2, d3, m1, NULL AS m2, NULL AS m3 FROM table1\n" +
                "    UNION ALL\n" +
                "    SELECT d1, d2, d3, NULL AS m1, m2, NULL AS m3 FROM table2\n" +
                "    UNION ALL\n" +
                "    SELECT d1, d2, d3, NULL AS m1, NULL AS m2, m3 FROM table3\n" +
                ") AS unioned\n" +
                "GROUP BY d1, d2, d3;\n");
        if (!(parse instanceof Select)) {
            return;
        }
        CustomRelation root = new CustomRelation();
//        root.getFields().put("")
        parse.accept(new CustomStatementVisitor(root));
        System.out.println("=========");
        System.out.println(parse);
    }
}
