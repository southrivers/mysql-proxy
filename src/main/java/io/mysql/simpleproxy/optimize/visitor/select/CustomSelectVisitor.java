package io.mysql.simpleproxy.optimize.visitor.select;

import io.mysql.simpleproxy.optimize.model.CustomRelation;
import io.mysql.simpleproxy.optimize.visitor.from.CustomFromItemVisitor;
import net.sf.jsqlparser.statement.select.*;

import java.util.LinkedList;
import java.util.List;

/**
 * 自定义的visitor，需要在visitor中构建继承关系
 */
public class CustomSelectVisitor extends SelectVisitorAdapter {
    /**
     * 这里就是根集合节点了，接下来就是要构建集合树
     */
    CustomRelation customRelation;

    public CustomSelectVisitor(CustomRelation customRelation) {
        this.customRelation = customRelation;
    }

    /**
     * 应该不会走到这里
     *
     * @param parenthesedSelect
     */
/*    @Override
    public void visit(ParenthesedSelect parenthesedSelect) {
        // 获取select信息
        PlainSelect plainSelect = parenthesedSelect.getPlainSelect();
        plainSelect.accept(this);
        // 获取group by信息
        GroupByElement groupBy = plainSelect.getGroupBy();
    }*/

    @Override
    public void visit(PlainSelect plainSelect) {
        // 获取project信息，这里需要解析出来字段
        List<SelectItem<?>> selectItems = plainSelect.getSelectItems();
        // TODO 字段解析出来保存到集合中

        FromItem fromItem = plainSelect.getFromItem();
        // 判断集合是否需要向下传播
        if (!customRelation.isLeafRelation()) {
            // 将集合传递下去
            fromItem.accept(new CustomFromItemVisitor(customRelation));
        }
    }

    /**
     * TODO 很重要！说明只要不在对应的方法里面继续调用accept方法，集合的处理就不会再向下扩散了
     *
     * @param setOpList
     */
    @Override
    public void visit(SetOperationList setOpList) {
        // customRelation
        System.out.println(setOpList);
        List<Select> selects = setOpList.getSelects();
        // 待去掉
        int i = 0;
        // 用来记录union all中的指标记录
        List<String> metrics = new LinkedList<>();
        // 记录需要删除的union all的子集
        List<Select> toBeRemoved = new LinkedList<>();
        for (int index = 0; index < selects.size(); index++) {
            Select select = selects.get(index);
            // 为每一个selectSql生成一个集合
            CustomRelation selectRelation = new CustomRelation();
            // 构建一个集合的父子关系
            selectRelation.setParent(customRelation);
            customRelation.getSubRelations().add(selectRelation);
            // 解析完这个集合之后不再向下传播
            selectRelation.setLeafRelation(true);
            select.accept(new CustomSelectVisitor(selectRelation));
            // FIXME 这里是否删除，需要根据集合里面的指标和维度来确定
            i ++;
            // 可以获取每一个select字段，根据其中的metric来确定是否选择移除
            System.out.println(select);
            if (i == 2) {
                // 移除指定的union，可以简单的通过对象移除的方式来完成移除的操作
                selects.remove(null);
            }
        }
    }


}
