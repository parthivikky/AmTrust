package mobello.amtrust.com.model;

/**
 * Created by Parthi on 05-Jul-16.
 */
public class UserExist {

    private Boolean success;
    private Result result;

    /**
     *
     * @return
     * The success
     */
    public Boolean getSuccess() {
        return success;
    }

    /**
     *
     * @param success
     * The success
     */
    public void setSuccess(Boolean success) {
        this.success = success;
    }

    /**
     *
     * @return
     * The result
     */
    public Result getResult() {
        return result;
    }

    /**
     *
     * @param result
     * The result
     */
    public void setResult(Result result) {
        this.result = result;
    }

    public class Result {

        private Integer is_exist_policy;
        private Integer is_exist_user;
        private String message;

        /**
         * @return The is_exist_policy
         */
        public Integer getIsExistPolicy() {
            return is_exist_policy;
        }

        /**
         * @param is_exist_policy The is_exist_policy
         */
        public void setIsExistPolicy(Integer is_exist_policy) {
            this.is_exist_policy = is_exist_policy;
        }

        /**
         * @return The is_exist_user
         */
        public Integer getIsExistUser() {
            return is_exist_user;
        }

        /**
         * @param is_exist_user The is_exist_user
         */
        public void setIsExistUser(Integer is_exist_user) {
            this.is_exist_user = is_exist_user;
        }

        /**
         * @return The message
         */
        public String getMessage() {
            return message;
        }

        /**
         * @param message The message
         */
        public void setMessage(String message) {
            this.message = message;
        }
    }
}
