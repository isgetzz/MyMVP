package com.app.ccmvp.bean;

import com.app.ccmvp.base.BaseResponse;

import java.util.List;

/**
 * 新闻实体类
 */
public class NewResultDataB extends BaseResponse {
    public NewResultData.Result result;

    public NewResultData.Result getResult() {
        return result;
    }

    public void setResult(NewResultData.Result result) {
        this.result = result;
    }

    public static class Result {
        private String stat;
        private List<NewResultData.Result.Data> data;

        public String getStat() {
            return stat;
        }

        public void setStat(String stat) {
            this.stat = stat;
        }

        public List <NewResultData.Result.Data> getData() {
            return data;
        }

        public void setData(List <NewResultData.Result.Data> data) {
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