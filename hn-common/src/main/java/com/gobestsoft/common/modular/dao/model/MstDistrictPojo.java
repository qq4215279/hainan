package com.gobestsoft.common.modular.dao.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

@TableName("mst_district")
public class MstDistrictPojo extends Model<MstDistrictPojo> {

    private static final long serialVersionUID = 1L;

	@Override
	protected Serializable pkVal() {
		return this.districtCode;
	}
    
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column mst_district.district_code
     *
     * @mbg.generated Tue Dec 05 12:46:32 CST 2017
     */
    private String districtCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column mst_district.p_district_code
     *
     * @mbg.generated Tue Dec 05 12:46:32 CST 2017
     */
    private String pDistrictCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column mst_district.district_name
     *
     * @mbg.generated Tue Dec 05 12:46:32 CST 2017
     */
    private String districtName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column mst_district.p_district_name
     *
     * @mbg.generated Tue Dec 05 12:46:32 CST 2017
     */
    private String pDistrictName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column mst_district.level
     *
     * @mbg.generated Tue Dec 05 12:46:32 CST 2017
     */
    private Short level;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column mst_district.city_code
     *
     * @mbg.generated Tue Dec 05 12:46:32 CST 2017
     */
    private String cityCode;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column mst_district.district_code
     *
     * @return the value of mst_district.district_code
     *
     * @mbg.generated Tue Dec 05 12:46:32 CST 2017
     */
    public String getDistrictCode() {
        return districtCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column mst_district.district_code
     *
     * @param districtCode the value for mst_district.district_code
     *
     * @mbg.generated Tue Dec 05 12:46:32 CST 2017
     */
    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column mst_district.p_district_code
     *
     * @return the value of mst_district.p_district_code
     *
     * @mbg.generated Tue Dec 05 12:46:32 CST 2017
     */
    public String getpDistrictCode() {
        return pDistrictCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column mst_district.p_district_code
     *
     * @param pDistrictCode the value for mst_district.p_district_code
     *
     * @mbg.generated Tue Dec 05 12:46:32 CST 2017
     */
    public void setpDistrictCode(String pDistrictCode) {
        this.pDistrictCode = pDistrictCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column mst_district.district_name
     *
     * @return the value of mst_district.district_name
     *
     * @mbg.generated Tue Dec 05 12:46:32 CST 2017
     */
    public String getDistrictName() {
        return districtName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column mst_district.district_name
     *
     * @param districtName the value for mst_district.district_name
     *
     * @mbg.generated Tue Dec 05 12:46:32 CST 2017
     */
    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column mst_district.p_district_name
     *
     * @return the value of mst_district.p_district_name
     *
     * @mbg.generated Tue Dec 05 12:46:32 CST 2017
     */
    public String getpDistrictName() {
        return pDistrictName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column mst_district.p_district_name
     *
     * @param pDistrictName the value for mst_district.p_district_name
     *
     * @mbg.generated Tue Dec 05 12:46:32 CST 2017
     */
    public void setpDistrictName(String pDistrictName) {
        this.pDistrictName = pDistrictName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column mst_district.level
     *
     * @return the value of mst_district.level
     *
     * @mbg.generated Tue Dec 05 12:46:32 CST 2017
     */
    public Short getLevel() {
        return level;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column mst_district.level
     *
     * @param level the value for mst_district.level
     *
     * @mbg.generated Tue Dec 05 12:46:32 CST 2017
     */
    public void setLevel(Short level) {
        this.level = level;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column mst_district.city_code
     *
     * @return the value of mst_district.city_code
     *
     * @mbg.generated Tue Dec 05 12:46:32 CST 2017
     */
    public String getCityCode() {
        return cityCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column mst_district.city_code
     *
     * @param cityCode the value for mst_district.city_code
     *
     * @mbg.generated Tue Dec 05 12:46:32 CST 2017
     */
    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
}