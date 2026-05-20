package domain.member;

import java.time.LocalDateTime;

public class MemberDTO {
	private Long memberId;
	private String name;
	private int totalPoint;
	private LocalDateTime cretedAt;
	private Long groupId;
	
	public MemberDTO() {}

	public MemberDTO(Long memberId, String name, int totalPoint, LocalDateTime cretedAt, Long groupId) {
		super();
		this.memberId = memberId;
		this.name = name;
		this.totalPoint = totalPoint;
		this.cretedAt = cretedAt;
		this.groupId = groupId;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTotalPoint() {
		return totalPoint;
	}

	public void setTotalPoint(int totalPoint) {
		this.totalPoint = totalPoint;
	}

	public LocalDateTime getCreatedAt() {
		return cretedAt;
	}

	public void setCretedAt(LocalDateTime cretedAt) {
		this.cretedAt = cretedAt;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	@Override
	public String toString() {
		return "MemberDTO [getMemberId()=" + getMemberId() + ", getName()=" + getName() + ", getTotalPoint()="
				+ getTotalPoint() + ", getCretedAt()=" + getCreatedAt() + ", getGroupId()=" + getGroupId() + "]";
	}
	
	
	
}

