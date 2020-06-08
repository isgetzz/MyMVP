package com.app.ccmvp.bean;

import com.app.ccmvp.base.BaseResponse;

import java.util.List;

/**
 * 首页数据
 */
public class MainData extends BaseResponse {
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    /**
     * 首页数据
     * Advert_items 轮播图
     * new_goods_list_link 查看更多
     * new_goods_items 最近上新轮播
     * Category_items 品类导航
     * Featured_series_items 精选系列
     **/
    public static class Data {
        private List<AdvertItems> advert_items;
        private String new_goods_list_link;
        private List<NewGoodsItems> new_goods_items;
        private List<CategoryItems> category_items;
        private List<FeaturedSeriesItems> featured_series_items;

        public List<AdvertItems> getAdvert_items() {
            return advert_items;
        }

        public void setAdvert_items(List<AdvertItems> advert_items) {
            this.advert_items = advert_items;
        }

        public String getNew_goods_list_link() {
            return new_goods_list_link;
        }

        public void setNew_goods_list_link(String new_goods_list_link) {
            this.new_goods_list_link = new_goods_list_link;
        }

        public List<NewGoodsItems> getNew_goods_items() {
            return new_goods_items;
        }

        public void setNew_goods_items(List<NewGoodsItems> new_goods_items) {
            this.new_goods_items = new_goods_items;
        }

        public List<CategoryItems> getCategory_items() {
            return category_items;
        }

        public void setCategory_items(List<CategoryItems> category_items) {
            this.category_items = category_items;
        }

        public List<FeaturedSeriesItems> getFeatured_series_items() {
            return featured_series_items;
        }

        public void setFeatured_series_items(List<FeaturedSeriesItems> featured_series_items) {
            this.featured_series_items = featured_series_items;
        }

        public class NewGoodsItems {
            private String title;
            private String market_price;
            private String sell_price;
            private String img_url;
            private String link_url;
            private String sales_num;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getMarket_price() {
                return market_price;
            }

            public void setMarket_price(String market_price) {
                this.market_price = market_price;
            }

            public String getSell_price() {
                return sell_price;
            }

            public void setSell_price(String sell_price) {
                this.sell_price = sell_price;
            }

            public String getImg_url() {
                return img_url;
            }

            public void setImg_url(String img_url) {
                this.img_url = img_url;
            }

            public String getLink_url() {
                return link_url;
            }

            public void setLink_url(String link_url) {
                this.link_url = link_url;
            }

            public String getSales_num() {
                return sales_num;
            }

            public void setSales_num(String sales_num) {
                this.sales_num = sales_num;
            }
        }

        public static class AdvertItems {
            private String title;
            private String file_path;
            private String link_url;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getFile_path() {
                return file_path;
            }

            public void setFile_path(String file_path) {
                this.file_path = file_path;
            }

            public String getLink_url() {
                return link_url;
            }

            public void setLink_url(String link_url) {
                this.link_url = link_url;
            }
        }

        public static class CategoryItems {
            private int category_id;
            private String title;
            private String img_two_url;
            private String link_url;

            public int getCategory_id() {
                return category_id;
            }

            public void setCategory_id(int category_id) {
                this.category_id = category_id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getImg_two_url() {
                return img_two_url;
            }

            public void setImg_two_url(String img_two_url) {
                this.img_two_url = img_two_url;
            }

            public String getLink_url() {
                return link_url;
            }

            public void setLink_url(String link_url) {
                this.link_url = link_url;
            }
        }

        public static class FeaturedSeriesItems {
            private String img_url;
            private String link_url;
            private List<GoodsItems> goods_items;
            private int category_id;

            public int getCategory_id() {
                return category_id;
            }

            public void setCategory_id(int category_id) {
                this.category_id = category_id;
            }

            public String getImg_url() {
                return img_url;
            }

            public void setImg_url(String img_url) {
                this.img_url = img_url;
            }

            public String getLink_url() {
                return link_url;
            }

            public void setLink_url(String link_url) {
                this.link_url = link_url;
            }

            public List<GoodsItems> getGoods_items() {
                return goods_items;
            }

            public void setGoods_items(List<GoodsItems> goods_items) {
                this.goods_items = goods_items;
            }

            public static class GoodsItems {
                private String title;
                private String img_url;
                private String link_url;

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getImg_url() {
                    return img_url;
                }

                public void setImg_url(String img_url) {
                    this.img_url = img_url;
                }

                public String getLink_url() {
                    return link_url;
                }

                public void setLink_url(String link_url) {
                    this.link_url = link_url;
                }
            }

        }
    }
}
