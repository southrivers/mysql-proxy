package io.mysql.simpleproxy.optimize.visitor;

import io.mysql.simpleproxy.optimize.model.CustomRelation;
import io.mysql.simpleproxy.optimize.visitor.select.CustomSelectVisitor;
import net.sf.jsqlparser.statement.StatementVisitorAdapter;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;

public class CustomStatementVisitor extends StatementVisitorAdapter {

    CustomRelation customRelation;

    public CustomStatementVisitor(CustomRelation customRelation) {
        this.customRelation = customRelation;
    }

    /**
     * 外面需要把收集到的原始sql字段信息作为集合传进来
     * @param select
     */
    @Override
    public void visit(Select select) {
        // 这是一个根select
        PlainSelect plainSelect = select.getPlainSelect();
        plainSelect.accept(new CustomSelectVisitor(customRelation));
    }
}
