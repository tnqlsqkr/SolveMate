package domain.study;

import java.time.LocalDateTime;

public class StudyDTO {

	private Long groupId; // 그룹 아이디
	private String gname; // 이름
	private LocalDateTime create_at; // 생성일
	
	public StudyDTO() {}
	
	public StudyDTO(Long gorupId, String gname, LocalDateTime create_at) {
		super();
		this.groupId = groupId;
		this.gname = gname;
		this.create_at = create_at;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public String getGname() {
		return gname;
	}

	public void setGname(String gname) {
		this.gname = gname;
	}

	public LocalDateTime getCreate_at() {
		return create_at;
	}

	public void setCreate_at(LocalDateTime create_at) {
		this.create_at = create_at;
	}

	@Override
	public String toString() {
		return "StudyDTO [getGroupId()=" + getGroupId() + ", getGname()=" + getGname() + ", getCreate_at()="
				+ getCreate_at() + "]";
	}
	
	
	
}
