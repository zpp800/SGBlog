package com.zpp.enums;

public enum AppHttpCodeEnum {
    // 成功
    SUCCESS(200,"操作成功"),
    // 登录
    NEED_LOGIN(401,"需要登录后操作"),
    NO_OPERATOR_AUTH(403,"无权限操作"),
    SYSTEM_ERROR(500,"出现错误"),
    USERNAME_EXIST(501,"用户名已存在"),
    PHONENUMBER_EXIST(502,"手机号已存在"),
    EMAIL_EXIST(503, "邮箱已存在"),
    REQUIRE_USERNAME(504, "必需填写用户名"),
    LOGIN_ERROR(505,"用户名或密码错误"),
    CONTENT_NOT_NULL(506,"评论内容不能为空" ),
    FILE_TYPE_ERROR(507,"文件类型错误，请上传png文件"),
    USERNAME_NOT_NULL(508, "用户名不能为空"),
    NICKNAME_NOT_NULL(509, "昵称不能为空"),
    PASSWORD_NOT_NULL(510, "密码不能为空"),
    EMAIL_NOT_NULL(511, "邮箱不能为空"),
    NICKNAME_EXIST(512, "昵称已存在"),
    TAG_NOT_NULL(513,"标签名称不能为空" ),
    TAG_NAME_EXIST(514,"标签名称已存在" ),
    LINK_NOT_NULL(515,"友链名称不能为空" ),
    LINK_LOGO_NOT_NULL(516,"友链logo不能为空" ),
    LINK_ADDRESS_NOT_NULL(517,"友链地址不能为空" ),
    LINK_DESCRIPTION_NOT_NULL(518,"友链描述不能为空" ),
    LINK_STATUS_NOT_NULL(519,"友链状态不能为空" ),
    LINK_NAME_EXIST(520,"友链名称已存在" ),
    CATEGORY_NOT_NULL(521,"分类名称不能为空" ),
    CATEGORY_DESCRIPTION_NOT_NULL(522,"分类描述不能为空" ),
    CATEGORY_STATUS_NOT_NULL(523,"分类状态不能为空" ),
    CATEGORY_NAME_EXIST(524,"分类名称已存在" );
    int code;
    String msg;

    AppHttpCodeEnum(int code, String errorMessage){
        this.code = code;
        this.msg = errorMessage;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
