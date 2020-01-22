package com.gobestsoft.core.util.model;

import lombok.Data;

/**
 * create by gutongwei
 * on 2018/9/12 下午9:10
 */

@Data
public class RowCellRangeAddressesModel {

    public RowCellRangeAddressesModel(int firstRows, int lastRows, int firstCellColumns, int lastCellColumns) {
        this.firstRows = firstRows;
        this.lastRows = lastRows;
        this.firstCellColumns = firstCellColumns;
        this.lastCellColumns = lastCellColumns;
    }

    private int firstRows;

    private int lastRows;

    private int firstCellColumns;

    private int lastCellColumns;
}
