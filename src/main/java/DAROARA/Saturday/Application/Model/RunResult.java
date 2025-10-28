package DAROARA.Saturday.Application.Model;

public class RunResult {
    private String output;
    private String error;

    public RunResult(String output, String error) {
        this.output = output;
        this.error = error;
    }
    public String getOutput() {
        return output;
    }
    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setOutput(String output) {
        this.output = output;
    }
}
