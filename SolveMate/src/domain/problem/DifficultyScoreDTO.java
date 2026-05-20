package domain.problem;

public class DifficultyScoreDTO {
	private Long dsId;
	private String platform;
	private String level;
	private int point;
	
	
	
	public DifficultyScoreDTO() { }
	
	public DifficultyScoreDTO(Long dsId, String platform, String level, int point) {
		super();
		this.dsId = dsId;
		this.platform = platform;
		this.level = level;
		this.point = point;
	}

	public Long getDsId() {
		return dsId;
	}
	
	public void setDsId(Long dsId) {
		this.dsId = dsId;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	@Override
	public String toString() {
		return "DifficultyScoreDTO [getDsId()=" + getDsId() + ", getPlatform()=" + getPlatform() + ", getLevel()="
				+ getLevel() + ", getPoint()=" + getPoint() + "]";
	}
	
	
	
}
