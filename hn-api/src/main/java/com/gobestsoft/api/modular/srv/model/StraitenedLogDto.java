package com.gobestsoft.api.modular.srv.model;

import lombok.Data;

import java.util.Objects;

/**
 * create by gutongwei
 * on 2018/7/27 上午11:14
 */
@Data
public class StraitenedLogDto {

    private int sort;

    private String orgName;

    private String desc;

    private int status;

    public StraitenedLogDto() {
    }

    public StraitenedLogDto(int sort, String orgName, String desc, int status) {
        this.sort = sort;
        this.orgName = orgName;
        this.desc = desc;
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StraitenedLogDto that = (StraitenedLogDto) o;
        return Objects.equals(orgName, that.orgName);
    }

    @Override
    public int hashCode() {
        return orgName.hashCode();
    }
}
