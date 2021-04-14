package jobportal.models.internal_models.cv_support;

/**
 * EduLog is a very simple class created to store log data from education information extraction process.
 * This log should help to understand the extraction process and provide control over this process.
 */
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