package domain.problem;

import java.time.LocalDateTime;

public class ProblemDTO {
	 private Long problemId;
	 private String title;
	 private String url;
	 private Long dsId;
	 
	 // JOIN
	 private DifficultyScoreDTO difficultyScore;
	 
	 public ProblemDTO() {}

	 public ProblemDTO(Long problemId, String title, String url, Long dsId) {
		super();
		this.problemId = problemId;
		this.title = title;
		this.url = url;
		this.dsId = dsId;
	 }

	 public Long getProblemId() {
		 return problemId;
	 }

	 public void setProblemId(Long problemId) {
		 this.problemId = problemId;
	 }

	 public String getTitle() {
		 return title;
	 }

	 public void setTitle(String title) {
		 this.title = title;
	 }

	 public String getUrl() {
		 return url;
	 }

	 public void setUrl(String url) {
		 this.url = url;
	 }

	 public Long getDsId() {
		 return dsId;
	 }

	 public void setDsId(Long dsId) {
		 this.dsId = dsId;
	 }

	 public DifficultyScoreDTO getDifficultyScore() {
		return difficultyScore;
	 }

	 public void setDifficultyScore(DifficultyScoreDTO difficultyScore) {
		this.difficultyScore = difficultyScore;
	 }

	 @Override
	 public String toString() {
		return "ProblemDTO [getProblemId()=" + getProblemId() + ", getTitle()=" + getTitle() + ", getUrl()=" + getUrl()
				+ ", getDsId()=" + getDsId() + ", getDifficultyScore()=" + getDifficultyScore() + "]";
	 }
	  
	 
}
