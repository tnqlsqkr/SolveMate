package domain.submission;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Submission implements Serializable, Cloneable {

    private long subId;
    private SubmissionStatus status;
    private LocalDateTime submittedAt;
    private long memberId;
    private long problemId;

    public Submission() {}

    public Submission(long subId, SubmissionStatus status, LocalDateTime submittedAt,
                      long memberId, long problemId) {
        this.subId = subId;
        this.status = status;
        this.submittedAt = submittedAt;
        this.memberId = memberId;
        this.problemId = problemId;
    }

    
    public long getSubId() {
        return subId;
    }

    public void setSubId(long subId) {
        this.subId = subId;
    }

    public SubmissionStatus getStatus() {
        return status;
    }

    public void setStatus(SubmissionStatus status) {
        this.status = status;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }

    public long getMemberId() {
        return memberId;
    }

    public void setMemberId(long memberId) {
        this.memberId = memberId;
    }

    public long getProblemId() {
        return problemId;
    }

    public void setProblemId(long problemId) {
        this.problemId = problemId;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Submission) {
            Submission s = (Submission) obj;
            return this.subId == s.subId;
        }
        return false;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return "Submission{" +
                "subId=" + subId +
                ", status=" + status +
                ", submittedAt=" + submittedAt +
                ", memberId=" + memberId +
                ", problemId=" + problemId +
                '}';
    }
}
