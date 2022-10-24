package com.dino.blog.constants;

public class SystemConstants {
    /**
     * 文章是草稿
     */
    public static final int ARTICLE_STATUS_DRAFT = 1;
    /**
     * 文章是正常分布状态
     */
    public static final int ARTICLE_STATUS_NORMAL = 0;

    /**
     * 评论状态 -1 为根评论
     */
    public static final long ROOT_COMMENT_ID = -1;

    public static final String STATUS_NORMAL = "0";
    /**
     * 友链状态为审核通过
     */
    public static final String LINK_STATUS_NORMAL = "0";
    /**
     * 评论类型为：文章评论
     */
    public static final String ARTICLE_COMMENT = "0";
    /**
     * 评论类型为：友联评论
     */
    public static final String LINK_COMMENT = "1";
    public static final String MENU = "C";
    public static final String BUTTON = "F";
    /**
     * 正常状态
     */
    public static final String NORMAL = "0";
    public static final String ADMAIN = "1";

    /**
     * Redis前台用户前缀
     */
    public static final  String USER_LOGIN_PREFIX = "blogLogin:";
    /**
     * Redis后台用户登陆前缀
     */
    public static final String USER_ADMIN_LOGIN_PREFIX = "login";
    /**
     * Redis中存储ViewCount的Map key
     */
    public static final String REDIS_ARTICLE_VIEW_COUNT  = "articleViewCount";

    /**
     * 后端登陆返回字段
     */
    public static final String TOKEN = "token";

    /**
     * 菜单表中的类型（C:Category菜单  F: 按钮）
     */
    public static final String MENU_TYPE_C = "C";
    public static final String MENU_TYPE_F = "F";
    /**
     * 菜单状态
     */
    public static final int MENU_STATUS_NORMAL = 0;
}
