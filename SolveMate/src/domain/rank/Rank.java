package domain.rank;

public class Rank {
	private int rank;
	private String memberName;
	private String groupName;
	private int point;
	
	public Rank(int rank, String memberName, String groupName, int point) {
        this.rank = rank;
        this.memberName = memberName;
        this.groupName = groupName;
        this.point = point;
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

    @Override
    public String toString() {
        return rank + "등 | " + memberName + " | " + groupName + " | " + point + "점";
    }
}	

