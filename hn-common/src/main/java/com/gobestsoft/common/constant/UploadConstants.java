package com.gobestsoft.common.constant;

import com.gobestsoft.core.util.DateUtil;

public class UploadConstants {


    public static final String VIDEO = "video/";

    public static final String AUDIO = "audio/";

    public static final String OTHERS = "others/";

    // 头像
    public static final String AVATAR = "avatar/";

    public static final String ARTICLE = "article/";

    public static final String STRAITENED = "straitened/";

    public static final String SUPPORT = "support/";

    public static final String SUPPORT_EVIDENCE = "support/evidence/";

    public static final String SUPPORT_INCOME = "support/income/";

    public static final String SUPPORT_BOOKLET= "support/booklet/";

    public static final String COMPANY = "company/";

    public static final String BANNER = "banner/";

    public static final String ADVERTISEMENT = "advertisement/";

    // 我的投稿
    public static final String MY_CONTRIBUTE = "my_contribute/";

    // 困难帮扶附件存放目录
    public static final String STRAITENED_ATTACHMENT = "straitened_attachment/";

    // 媒体矩阵logo图
    public static final String MEDIA_MATRIX_LOGO = "mediaMatrix/logo/";

    /**
     * 互动圈小组头像目录
     */
    public static final String HDQ_HEAD_IMG = "hdq/head/";

    /**
     * 互动圈图片
     */
    public static final String HDQ_IMG = "hdq/image/";

    //秀吧上传的文件、音频及视频放置文件夹
    public static final String SHOW = "show/";

    public static final String SRV_STRAITENED = "srv/straitened/";

    public static final String CKY_STUDIO = "studio/";

    public static final String CKY_STUDIO_BANNER = "studio/banner/";

    public static final String CKY_STUDIO_HONOR = "studio/honor/";

    public static final String ICON = "icon/";


    //法律援助保存文件夹
    public static final String SRV_LAW_SUPPORT = "srv/law/support/";

    public static final String EXPORT_EXCEL = "export/excel/";

    public static final String ECHART_EXCEL = "echart/excel/";
    public static final String APK_MIR = "apk/";


    public static final String XLZX_AVATAR = "xlyz/avatar/";

    public static final String XLYZ_AM = "xlyz/xlam/";


    /**
     * 困难帮扶申请-身份证人像面照片保存基地址
     */
    private static final String SRV_STRAITENED_IDENTITY_FACE = "straitened_identityFace/";

    /**
     * 困难帮扶申请-身份证国徽面照片保存基地址
     */
    private static final String SRV_STRAITENED_CARD_NATIONAL_EMBLEM = "straitened_cardNationalEmblem/";


    /**
     * 困难帮扶申请-承诺书照片保存基地址
     */
    private static final String SRV_STRAITENED_COMMIT_LETTER = "straitened_commitLetter/";

    /**
     * 困难帮扶申请-所在单位证明照片保存基地址
     */
    private static final String SRV_STRAITENED_UNIT_PROVE = "straitened_unitProve/";

    /**
     * 集体合同
     */
    public static final String COLLECTIVE_CONTRACT = "collective_contract/";

    /**
     * 获取当天的文件夹
     *
     * @return
     */
    private static String getTodayFolder() {
        return DateUtil.getDays() + "/";
    }

    /**
     * 身份证人像面照片保存地址
     *
     * @return
     */
    public static String getSrvStraitenedIdentityFace() {
        return SRV_STRAITENED_IDENTITY_FACE + getTodayFolder();
    }

    /**
     * 身份证国徽面保存路径
     *
     * @return
     */
    public static String getSrvStraitenedCardNationalEmblem() {
        return SRV_STRAITENED_CARD_NATIONAL_EMBLEM + getTodayFolder();
    }

    /**
     * 承诺书照片保存地址
     *
     * @return
     */
    public static String getSrvStraitenedCommitLetter() {
        return SRV_STRAITENED_COMMIT_LETTER + getTodayFolder();
    }

    /**
     * 所在单位证明照片保存地址
     *
     * @return
     */
    public static String getSrvStraitenedUnitProve() {
        return SRV_STRAITENED_UNIT_PROVE + getTodayFolder();
    }


    /**
     * 困难帮扶附加保存
     *
     * @return
     */
    public static String getSrvStraitenedAttachment() {
        return STRAITENED_ATTACHMENT + getTodayFolder();

    }
}
