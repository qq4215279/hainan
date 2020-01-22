package com.gobestsoft.api.modular.basic;

import lombok.Data;
import org.apache.ibatis.session.RowBounds;

/**
 * SQL分页插件
 */
@Data
public class BasicRowBounds extends RowBounds {

    public BasicRowBounds(int pageIndex, int pageSize) {
        super((pageIndex - 1) * pageSize, pageSize);
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }


    private int pageIndex;

    private int pageSize;


}
