package com.uuhnaut69.search.constant;

/**
 * @author uuhnaut
 * @project mall
 */
public final class EsConstants {

    /**
     * Product
     */
    public static final String PRODUCT_INDEX = "product-index";
    public static final String PRODUCT_INDEX_TYPE = "product";
    public static final String PRODUCT_SUGGEST = "productSuggest";

    /**
     * Tag
     */
    public static final String TAG_INDEX = "tag-index";
    public static final String TAG_INDEX_TYPE = "tag";

    /**
     * User
     */
    public static final String USER_INDEX = "user-index";
    public static final String USER_INDEX_TYPE = "user";

    public static final String CATEGORY_INDEX = "category-index";
    public static final String CATEGORY_INDEX_TYPE = "category";

    /**
     * Analyzer
     */
    public static final String STANDARD_ANALYZER = "standard";

    private EsConstants() {
    }
}
