package com.gobestsoft.common.modular.dao.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

@TableName("app_version")
public class AppVersionPojo extends Model<AppVersionPojo> {

    private static final long serialVersionUID = 1L;

	@Override
	protected Serializable pkVal() {
		return this.id;
	}
    
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column app_version.id
     *
     * @mbg.generated Tue Dec 05 12:46:32 CST 2017
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column app_version.version_code
     *
     * @mbg.generated Tue Dec 05 12:46:32 CST 2017
     */
    private Integer versionCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column app_version.version_name
     *
     * @mbg.generated Tue Dec 05 12:46:32 CST 2017
     */
    private String versionName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column app_version.v_type
     *
     * @mbg.generated Tue Dec 05 12:46:32 CST 2017
     */
    private String vType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column app_version.download_address
     *
     * @mbg.generated Tue Dec 05 12:46:32 CST 2017
     */
    private String downloadAddress;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column app_version.description
     *
     * @mbg.generated Tue Dec 05 12:46:32 CST 2017
     */
    private String description;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column app_version.update_time
     *
     * @mbg.generated Tue Dec 05 12:46:32 CST 2017
     */
    private String updateTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column app_version.force_upgrade
     *
     * @mbg.generated Tue Dec 05 12:46:32 CST 2017
     */
    private String forceUpgrade;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column app_version.del_flg
     *
     * @mbg.generated Tue Dec 05 12:46:32 CST 2017
     */
    private Short delFlg;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column app_version.id
     *
     * @return the value of app_version.id
     *
     * @mbg.generated Tue Dec 05 12:46:32 CST 2017
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column app_version.id
     *
     * @param id the value for app_version.id
     *
     * @mbg.generated Tue Dec 05 12:46:32 CST 2017
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column app_version.version_code
     *
     * @return the value of app_version.version_code
     *
     * @mbg.generated Tue Dec 05 12:46:32 CST 2017
     */
    public Integer getVersionCode() {
        return versionCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column app_version.version_code
     *
     * @param versionCode the value for app_version.version_code
     *
     * @mbg.generated Tue Dec 05 12:46:32 CST 2017
     */
    public void setVersionCode(Integer versionCode) {
        this.versionCode = versionCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column app_version.version_name
     *
     * @return the value of app_version.version_name
     *
     * @mbg.generated Tue Dec 05 12:46:32 CST 2017
     */
    public String getVersionName() {
        return versionName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column app_version.version_name
     *
     * @param versionName the value for app_version.version_name
     *
     * @mbg.generated Tue Dec 05 12:46:32 CST 2017
     */
    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column app_version.v_type
     *
     * @return the value of app_version.v_type
     *
     * @mbg.generated Tue Dec 05 12:46:32 CST 2017
     */
    public String getvType() {
        return vType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column app_version.v_type
     *
     * @param vType the value for app_version.v_type
     *
     * @mbg.generated Tue Dec 05 12:46:32 CST 2017
     */
    public void setvType(String vType) {
        this.vType = vType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column app_version.download_address
     *
     * @return the value of app_version.download_address
     *
     * @mbg.generated Tue Dec 05 12:46:32 CST 2017
     */
    public String getDownloadAddress() {
        return downloadAddress;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column app_version.download_address
     *
     * @param downloadAddress the value for app_version.download_address
     *
     * @mbg.generated Tue Dec 05 12:46:32 CST 2017
     */
    public void setDownloadAddress(String downloadAddress) {
        this.downloadAddress = downloadAddress;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column app_version.description
     *
     * @return the value of app_version.description
     *
     * @mbg.generated Tue Dec 05 12:46:32 CST 2017
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column app_version.description
     *
     * @param description the value for app_version.description
     *
     * @mbg.generated Tue Dec 05 12:46:32 CST 2017
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column app_version.update_time
     *
     * @return the value of app_version.update_time
     *
     * @mbg.generated Tue Dec 05 12:46:32 CST 2017
     */
    public String getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column app_version.update_time
     *
     * @param updateTime the value for app_version.update_time
     *
     * @mbg.generated Tue Dec 05 12:46:32 CST 2017
     */
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column app_version.force_upgrade
     *
     * @return the value of app_version.force_upgrade
     *
     * @mbg.generated Tue Dec 05 12:46:32 CST 2017
     */
    public String getForceUpgrade() {
        return forceUpgrade;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column app_version.force_upgrade
     *
     * @param forceUpgrade the value for app_version.force_upgrade
     *
     * @mbg.generated Tue Dec 05 12:46:32 CST 2017
     */
    public void setForceUpgrade(String forceUpgrade) {
        this.forceUpgrade = forceUpgrade;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column app_version.del_flg
     *
     * @return the value of app_version.del_flg
     *
     * @mbg.generated Tue Dec 05 12:46:32 CST 2017
     */
    public Short getDelFlg() {
        return delFlg;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column app_version.del_flg
     *
     * @param delFlg the value for app_version.del_flg
     *
     * @mbg.generated Tue Dec 05 12:46:32 CST 2017
     */
    public void setDelFlg(Short delFlg) {
        this.delFlg = delFlg;
    }
}