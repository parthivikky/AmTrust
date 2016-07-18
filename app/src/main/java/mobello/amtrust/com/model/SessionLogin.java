package mobello.amtrust.com.model;

/**
 * Created by Parthi on 22-Apr-16.
 */
public class SessionLogin {

    private Boolean success;
    private Error error;
    public Result result;

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
     * @return The error
     */
    public Error getError() {
        return error;
    }

    /**
     * @param error The error
     */
    public void setError(Error error) {
        this.error = error;
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

    public class Error {

        private String code;
        private String message;

        /**
         * @return The code
         */
        public String getCode() {
            return code;
        }

        /**
         * @param code The code
         */
        public void setCode(String code) {
            this.code = code;
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

    public class Result {

        private String sessionName;
        private String userId;
        private String version;
        private String vtigerVersion;

        /**
         * @return The sessionName
         */
        public String getSessionName() {
            return sessionName;
        }

        /**
         * @param sessionName The sessionName
         */
        public void setSessionName(String sessionName) {
            this.sessionName = sessionName;
        }

        /**
         * @return The userId
         */
        public String getUserId() {
            return userId;
        }

        /**
         * @param userId The userId
         */
        public void setUserId(String userId) {
            this.userId = userId;
        }

        /**
         * @return The version
         */
        public String getVersion() {
            return version;
        }

        /**
         * @param version The version
         */
        public void setVersion(String version) {
            this.version = version;
        }

        /**
         * @return The vtigerVersion
         */
        public String getVtigerVersion() {
            return vtigerVersion;
        }

        /**
         * @param vtigerVersion The vtigerVersion
         */
        public void setVtigerVersion(String vtigerVersion) {
            this.vtigerVersion = vtigerVersion;
        }
    }
}
