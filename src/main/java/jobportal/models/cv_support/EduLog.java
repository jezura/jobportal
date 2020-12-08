package jobportal.models.cv_support;

public class EduLog {
    private String logText = "";

    public EduLog() {
    }

    public String getLogText() {
        return logText;
    }

    public void setLogText(String logText) {
        this.logText = logText;
    }

    public void addLogText(String logLine) {
        this.logText = this.logText.concat("\n" + logLine);
    }
}