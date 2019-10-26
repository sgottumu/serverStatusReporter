package pojo;

public class Status {

    long requests_count;
    String application;
    String version;
    long success_count;
    long error_count;

    public long getRequests_count() {
        return requests_count;
    }

    public void setRequests_count(long requests_count) {
        this.requests_count = requests_count;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public long getSuccess_count() {
        return success_count;
    }

    public void setSuccess_count(long success_count) {
        this.success_count = success_count;
    }

    public long getError_count() {
        return error_count;
    }

    public void setError_count(long error_count) {
        this.error_count = error_count;
    }

    @Override
    public String toString() {
        return "Status{" +
                "requests_count=" + requests_count +
                ", application='" + application + '\'' +
                ", version='" + version + '\'' +
                ", success_count=" + success_count +
                ", error_count=" + error_count +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Status)) return false;

        Status status = (Status) o;

        if (requests_count != status.requests_count) return false;
        if (success_count != status.success_count) return false;
        if (error_count != status.error_count) return false;
        if (!application.equals(status.application)) return false;
        return version.equals(status.version);
    }

    @Override
    public int hashCode() {
        int result = (int) (requests_count ^ (requests_count >>> 32));
        result = 31 * result + application.hashCode();
        result = 31 * result + version.hashCode();
        result = 31 * result + (int) (success_count ^ (success_count >>> 32));
        result = 31 * result + (int) (error_count ^ (error_count >>> 32));
        return result;
    }
}
