package com.mysql.sharding.proxy.complex;

import com.mysql.sharding.proxy.util.*;
import org.apache.shardingsphere.sharding.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.sharding.api.sharding.complex.ComplexKeysShardingValue;

import java.util.*;

/**
 * @author baibai
 * @since 2023-2-27
 * 类描述： 复合分片算法
 */
public class OrderTableComplex implements ComplexKeysShardingAlgorithm<Long> {


    private Properties props = new Properties();

    /**
     * 功能描述： 实现分片的具体实现,数据库根据用户ID和订单ID来实现分片，
     * 意味着只要某张表中应用了这个复合分片算法，然后字段里面有包含用户ID
     * 和订单ID的SQL那就会实现数据库的分片。
     *
     * @param collection
     * @param complexKeysShardingValue
     * @return
     */
    @Override
    public Collection<String> doSharding(Collection<String> collection, ComplexKeysShardingValue<Long> complexKeysShardingValue) {
        List<String> shardingList = new ArrayList();
        // 获取当前的表名
        String logicTableName = complexKeysShardingValue.getLogicTableName();
        // 获取请求参数
        System.out.println("测试点1");
        Map<String, Collection<Long>> columnNameAndShardingValuesMap = complexKeysShardingValue.getColumnNameAndShardingValuesMap();
        List IdList = (List) columnNameAndShardingValuesMap.get("id");
        List kList = (List) columnNameAndShardingValuesMap.get("k");
        List cList=(List)columnNameAndShardingValuesMap.get("c");
        List padList=(List)columnNameAndShardingValuesMap.get("pad");
        Long id = IdList != null ? ComplexUtils.getObjLong(IdList.get(0)) : 0L;
        Long k = kList != null ? ComplexUtils.getObjLong(kList.get(0)) : 0L;
        String c=cList != null ? ComplexUtils.getObjStr(cList.get(0)) : null;
        String pad=padList != null ? ComplexUtils.getObjStr(padList.get(0)) : null;
        int modVal = 0;
        /**
         * 分片规则：id、k为数字，c、pad为字符串，需要进行混合分片，分片规则如下：
         * 1、求和值为MAX=（id+k+c的ASCII值+pad的ASCII值）
         * 2、对和取余进行分表
         */
        double MAX=(id+k+ ComplexUtils.getpingjun(c)+ ComplexUtils.getpingjun(pad));
        modVal=(int)MAX%new Settings().getTable_num();
        for (Iterator<String> iter = collection.iterator(); iter.hasNext(); ) {
            String str = iter.next();
            System.out.println(str);
            if ((logicTableName + modVal).equals(str)) {
                shardingList.add(str);
            }
        }

        return shardingList;
    }
    @Override
    public String getType() {
        return "OrderTableComplex";
    }

    @Override
    public Properties getProps() {
        return this.props;
    }

    public interface SPIPostProcess{
        void init(Properties props);
    }
    @Override
    public void init(Properties properties) {

    }
}
