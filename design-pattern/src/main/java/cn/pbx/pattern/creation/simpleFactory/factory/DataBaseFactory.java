package cn.pbx.pattern.creation.simpleFactory.factory;

import cn.pbx.pattern.creation.simpleFactory.factory.impl.MysqlPool;
import cn.pbx.pattern.creation.simpleFactory.factory.impl.OraclePool;
import cn.pbx.pattern.creation.simpleFactory.factory.impl.SqlServerPool;

/**
 * @author BruceXu
 * @date 2021/4/21
 */
public class DataBaseFactory {
    public DataBasePool getPool(String type) {
        if (type == null) {
            return null;
        }
        if ("mysql".equalsIgnoreCase(type)) {
            return new MysqlPool();
        } else if ("oracle".equalsIgnoreCase(type)) {
            return new OraclePool();
        } else if ("sqlserver".equalsIgnoreCase(type)) {
            return new SqlServerPool();
        }
        return null;
    }
}
