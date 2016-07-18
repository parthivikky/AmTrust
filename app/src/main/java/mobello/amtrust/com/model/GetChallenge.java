package mobello.amtrust.com.model;

/**
 * Created by Parthi on 22-Apr-16.
 */
public class GetChallenge {

    private Boolean success;
    private Result result;

    /**
     * @return The success
     */
    public Boolean getSuccess() {
        return success;
    }

    /**
     * @param success The success
     */
    public void setSuccess(Boolean success) {
        this.success = success;
    }

    /**
     * @return The result
     */
    public Result getResult() {
        return result;
    }

    /**
     * @param result The result
     */
    public void setResult(Result result) {
        this.result = result;
    }

    public class Result {

        private String token;
        private Integer serverTime;
        private Integer expireTime;

        /**
         * @return The token
         */
        public String getToken() {
            return token;
        }

        /**
         * @param token The token
         */
        public void setToken(String token) {
            this.token = token;
        }

        /**
         * @return The serverTime
         */
        public Integer getServerTime() {
            return serverTime;
        }

        /**
         * @param serverTime The serverTime
         */
        public void setServerTime(Integer serverTime) {
            this.serverTime = serverTime;
        }

        /**
         * @return The expireTime
         */
        public Integer getExpireTime() {
            return expireTime;
        }

        /**
         * @param expireTime The expireTime
         */
        public void setExpireTime(Integer expireTime) {
            this.expireTime = expireTime;
        }

    }
}
