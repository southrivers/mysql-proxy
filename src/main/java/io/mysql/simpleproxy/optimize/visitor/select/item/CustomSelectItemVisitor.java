package io.mysql.simpleproxy.optimize.visitor.select.item;

import net.sf.jsqlparser.statement.select.ParenthesedSelect;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.SelectVisitorAdapter;
import net.sf.jsqlparser.statement.select.SetOperationList;

public class CustomSelectItemVisitor extends SelectVisitorAdapter {

    @Override
    public void visit(ParenthesedSelect parenthesedSelect) {
        super.visit(parenthesedSelect);
    }

    @Override
    public void visit(PlainSelect plainSelect) {
        super.visit(plainSelect);
    }

    @Override
    public void visit(SetOperationList setOpList) {
        super.visit(setOpList);
    }
}
