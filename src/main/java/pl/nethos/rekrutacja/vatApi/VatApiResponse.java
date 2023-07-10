package pl.nethos.rekrutacja.vatApi;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VatApiResponse {
    @JsonProperty("result")
    private Result result;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public static class Result {
        @JsonProperty("accountAssigned")
        private String accountAssigned;

        @JsonProperty("requestDateTime")
        private String requestDateTime;

        @JsonProperty("requestId")
        private String requestId;

        public String getAccountAssigned() {
            return accountAssigned;
        }

        public void setAccountAssigned(String accountAssigned) {
            this.accountAssigned = accountAssigned;
        }

        public String getRequestDateTime() {
            return requestDateTime;
        }

        public void setRequestDateTime(String requestDateTime) {
            this.requestDateTime = requestDateTime;
        }

        public String getRequestId() {
            return requestId;
        }

        public void setRequestId(String requestId) {
            this.requestId = requestId;
        }
    }
}
