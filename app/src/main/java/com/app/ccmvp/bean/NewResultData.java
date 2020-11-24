package com.app.ccmvp.bean;

import com.app.ccmvp.base.BaseResponse;

import java.util.List;

/**
 * 新闻实体类
 */
public class NewResultData extends BaseResponse {
    public Result result;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public static class Result {
        private String stat;
        private List <Data> data;

        public String getStat() {
            return stat;
        }

        public void setStat(String stat) {
            this.stat = stat;
        }

        public List <Data> getData() {
            return data;
        }

        public void setData(List <Data> data) {
            this.data = data;
        }

        public static class Data {
            String category;

            public String getCategory() {
                return category;
            }

            public void setCategory(String category) {
                this.category = category;
            }
        }
    }
}