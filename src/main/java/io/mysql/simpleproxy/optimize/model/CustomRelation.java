package io.mysql.simpleproxy.optimize.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomRelation {

    /**
     * 用来表征集合是否是叶子节点的集合，如何是的话就不在进行向下传播
     */
    boolean leafRelation;

    String dbName;

    String tableName;
    /**
     * 别名映射 -> 原始字段的映射
     */
    Map<String, List<String>> fields = new HashMap<>();

    /**
     * 当前集合的上层集合
     */
    CustomRelation parent;

    /**
     * 当前集合的子集合
     */
    List<CustomRelation> subRelations = new LinkedList<>();

    /**
     * 用来记录不属于当前集合的指标，因为对应的字段为NULL as xxx，所以可以把不属于当前集合的指标取出来，从而确定当前集合的指标应该是什么
     */
    List<String> beyondMetric = new LinkedList<>();

}
