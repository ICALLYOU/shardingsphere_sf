package com.mysql.sharding.proxy.complex;

import com.mysql.sharding.proxy.util.*;
import org.apache.shardingsphere.sharding.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.sharding.api.sharding.complex.ComplexKeysShardingValue;

import java.util.*;

/**
 * @author baichongyu
 * @since 2023-2-27
 * 类描述： 复合分片算法
 */
public class OrderDbComplex implements ComplexKeysShardingAlgorithm<Long> {

    private Properties props = new Properties();

    /**
     * 功能描述： 实现分片的具体实现
     *
     * @param collection
     * @param complexKeysShardingValue
     * @return
     */
    @Override
    public Collection<String> doSharding(Collection<String> collection, ComplexKeysShardingValue<Long> complexKeysShardingValue) {
        List<String> shardingList = new ArrayList();
        // 获取请求参数
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
         * 分片规则：分库按照年份进行取余然后分库。数据库名为Year_年份，每个数据库分为十二个表
         */
        double MAX=(id+k+ ComplexUtils.getpingjun(c)+ ComplexUtils.getpingjun(pad));
        modVal=(int)MAX%new Settings().getDB_num();

        for (Iterator<String> iter = collection.iterator(); iter.hasNext(); ) {
            String str = iter.next();
            if (("ds_" + modVal).equals(str)) {
                shardingList.add(str);
            }
        }
        return shardingList;
    }
    @Override
    public String getType() {
        return "OrderDbComplex";
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