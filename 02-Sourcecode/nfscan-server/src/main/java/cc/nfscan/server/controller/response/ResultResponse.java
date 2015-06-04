package cc.nfscan.server.controller.response;

/**
 * Base JSON response model
 *
 * @author Paulo Miguel Almeida <a href="http://github.com/PauloMigAlmeida">@PauloMigAlmeida</a>
 */
public class ResultResponse {
    /**
     * whether we have proccessed this request successfully or not
     */
    private boolean success;

    /**
     * Default constructor
     *
     * @param success whether we have proccessed this request successfully or not
     */
    public ResultResponse(boolean success) {
        this.success = success;
    }

    /**
     * success getter
     *
     * @return a boolean
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * success setter
     *
     * @param success a boolean
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }
}
