package com.app.ccmvp.bean;

import com.app.ccmvp.base.BaseResponse;

import java.util.List;

public class SystemData extends BaseResponse {
    private Data data;
    public static Data systemSetting;

    public  Data getData() {
        return data;
    }

    public static class Data {
        private String start_adver_imgurl;
        private String android_forceup;
        private int android_version;
        private String android_downurl;
        private String ios_forceup;
        private String ios_version;
        private String ios_downurl;
        private List<MenuNav> menu_nav;

        public String getStart_adver_imgurl() {
            return start_adver_imgurl;
        }

        public void setStart_adver_imgurl(String start_adver_imgurl) {
            this.start_adver_imgurl = start_adver_imgurl;
        }

        public String getAndroid_forceup() {
            return android_forceup;
        }

        public void setAndroid_forceup(String android_forceup) {
            this.android_forceup = android_forceup;
        }

        public int getAndroid_version() {
            return android_version;
        }

        public void setAndroid_version(int android_version) {
            this.android_version = android_version;
        }

        public String getAndroid_downurl() {
            return android_downurl;
        }

        public void setAndroid_downurl(String android_downurl) {
            this.android_downurl = android_downurl;
        }

        public String getIos_forceup() {
            return ios_forceup;
        }

        public void setIos_forceup(String ios_forceup) {
            this.ios_forceup = ios_forceup;
        }

        public String getIos_version() {
            return ios_version;
        }

        public void setIos_version(String ios_version) {
            this.ios_version = ios_version;
        }

        public String getIos_downurl() {
            return ios_downurl;
        }

        public void setIos_downurl(String ios_downurl) {
            this.ios_downurl = ios_downurl;
        }

        public List<MenuNav> getMenu_nav() {
            return menu_nav;
        }

        public void setMenu_nav(List<MenuNav> menu_nav) {
            this.menu_nav = menu_nav;
        }

        public static class MenuNav {
            private String title;
            private String img_url;
            private String img_url_hover;
            private String web_url;

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

            public String getImg_url_hover() {
                return img_url_hover;
            }

            public void setImg_url_hover(String img_url_hover) {
                this.img_url_hover = img_url_hover;
            }

            public String getWeb_url() {
                return web_url;
            }

            public void setWeb_url(String web_url) {
                this.web_url = web_url;
            }
        }

        public static class NewGoodsItems {
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
    }
}
