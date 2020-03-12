package com.uuhnaut69.mall.search.constant;

/**
 * @author uuhnaut
 * @project mall
 */
public final class EsConstants {

    /**
     * Product
     */
    public static final String PRODUCT_INDEX = "productIndex";
    public static final String PRODUCT_INDEX_TYPE = "product";
    public static final String PRODUCT_SUGGEST = "productSuggest";

    /**
     * Tag
     */
    public static final String TAG_INDEX = "tagIndex";
    public static final String TAG_INDEX_TYPE = "tag";

    /**
     * Tag
     */
    public static final String BRAND_INDEX = "brandIndex";
    public static final String BRAND_INDEX_TYPE = "brand";

    /**
     * Catalog
     */
    public static final String CATALOG_INDEX = "catalogIndex";
    public static final String CATALOG_INDEX_TYPE = "catalog";

    /**
     * User
     */
    public static final String USER_INDEX = "userIndex";
    public static final String USER_INDEX_TYPE = "user";

    /**
     * Analyzer
     */
    public static final String STANDARD_ANALYZER = "standard";

    private EsConstants() {
    }
}