package DAROARA.Saturday.Application.Model;

public class RunResult {

    private String output;
    private String error;

    public RunResult() {
        // Required for JSON (Jackson)
    }

    public RunResult(String output, String error) {
        this.output = output != null ? output : "";
        this.error = error != null ? error : "";
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
