package domain.rank;

public class Rank {
	private int rank;
	private String memberName;
	private String groupName;
	private int point;
	private int solveCount;

	public Rank(int rank, String memberName, String groupName, int point, int solveCount) {
		this.rank = rank;
		this.memberName = memberName;
		this.groupName = groupName;
		this.point = point;
		this.solveCount = solveCount;
	}

	public int getRank() {
		return rank;
	}

	public String getMemberName() {
		return memberName;
	}

	public String getGroupName() {
		return groupName;
	}

	public int getPoint() {
		return point;
	}

	public int getSolveCount() {
		return solveCount;
	}

	@Override
	public String toString() {
		return rank + "위 | " + memberName + " | " + groupName + " | " + point + "점 | " + solveCount + "문제";
	}
}
