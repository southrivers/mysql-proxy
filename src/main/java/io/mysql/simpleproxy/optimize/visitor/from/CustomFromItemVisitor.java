package io.mysql.simpleproxy.optimize.visitor.from;

import io.mysql.simpleproxy.optimize.model.CustomRelation;
import io.mysql.simpleproxy.optimize.visitor.select.CustomSelectVisitor;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.*;

/**
 * 这里可以继续构建visitor
 */
public class CustomFromItemVisitor extends FromItemVisitorAdapter {

    CustomRelation customRelation;

    public CustomFromItemVisitor(CustomRelation customRelation) {
        this.customRelation = customRelation;
    }

    /**
     * 不会走到这里
     *
     * @param selectBody
     */
    /*    @Override
    public void visit(Table table) {
        // 这里应该是直接return
        System.out.println(table.getName());
    }*/
    @Override
    public void visit(ParenthesedSelect selectBody) {
        /**
         * 这里是一个fromItem，并不是一个集合，因此其简单的将集合向下透传，并不生成新的集合
         */
        Select select = selectBody.getSelect();
        select.accept(new CustomSelectVisitor(customRelation));
    }

    /**
     * 不会走到这里
     */
    /*
        @Override
        public void visit(LateralSubSelect lateralSubSelect) {

        }
    */

}
