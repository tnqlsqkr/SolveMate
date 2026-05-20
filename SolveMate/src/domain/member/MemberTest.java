package domain.member;

import java.util.List;
import java.util.Scanner;

public class MemberTest {

    private static MemberService memberService = new MemberServiceImple();
    private static Scanner sc = new Scanner(System.in);

    // 회원 등록
    private static void insertTest() {
        MemberDTO member = new MemberDTO();

        System.out.print("회원 이름 입력: ");
        member.setName(sc.nextLine());

        System.out.print("소속 스터디 group_id 입력: ");
        member.setGroupId(Long.parseLong(sc.nextLine()));

        int result = memberService.insertMember(member);

        if (result > 0) System.out.println("회원 등록 성공");
        else System.out.println("회원 등록 실패");
    }

    // 회원 전체 조회
    private static void searchAllTest() {
        List<MemberDTO> memberList = memberService.searchAllMembers();

        if (memberList.isEmpty()) {
            System.out.println("등록된 회원이 없습니다.");
            return;
        }

        for (MemberDTO member : memberList) {
            printMember(member);
        }
    }

    // 회원 조회
    private static void getMemberTest() {
        System.out.print("조회할 회원 ID 입력: ");
        Long memberId = Long.parseLong(sc.nextLine());

        MemberDTO member = memberService.getMember(memberId);

        if (member == null) {
            System.out.println("해당 회원이 없습니다.");
            return;
        }

        printMember(member);
    }

    // 스터디별 회원 조회
    private static void searchByGroupTest() {
        System.out.print("조회할 스터디 group_id 입력: ");
        Long groupId = Long.parseLong(sc.nextLine());

        List<MemberDTO> memberList = memberService.searchGroupMembers(groupId);

        if (memberList.isEmpty()) {
            System.out.println("해당 스터디에 회원이 없습니다.");
            return;
        }

        for (MemberDTO member : memberList) {
            printMember(member);
        }
    }

    // 회원 수정
    private static void updateTest() {
        MemberDTO member = new MemberDTO();

        System.out.print("수정할 회원 ID 입력: ");
        member.setMemberId(Long.parseLong(sc.nextLine()));

        System.out.print("새 회원 이름 입력: ");
        member.setName(sc.nextLine());

        System.out.print("새 총 포인트 입력: ");
        member.setTotalPoint(Integer.parseInt(sc.nextLine()));

        System.out.print("새 소속 스터디 group_id 입력: ");
        member.setGroupId(Long.parseLong(sc.nextLine()));

        int result = memberService.updateMember(member);

        if (result > 0) System.out.println("회원 수정 성공");
        else System.out.println("회원 수정 실패");
    }

    // 회원 삭제
    private static void deleteTest() {
        System.out.print("삭제할 회원 ID 입력: ");
        Long memberId = Long.parseLong(sc.nextLine());

        int result = memberService.deleteMember(memberId);

        if (result > 0) System.out.println("회원 삭제 성공");
        else System.out.println("회원 삭제 실패");
    }

    // 회원 출력
    private static void printMember(MemberDTO member) {
        System.out.println("회원 ID: " + member.getMemberId());
        System.out.println("이름: " + member.getName());
        System.out.println("총 포인트: " + member.getTotalPoint());
        System.out.println("생성일: " + member.getCreatedAt());
        System.out.println("스터디 ID: " + member.getGroupId());
        System.out.println();
    }

    public static void main(String[] args) {

        while (true) {
            System.out.println("1. 회원 등록");
            System.out.println("2. 회원 전체 조회");
            System.out.println("3. 회원 조회");
            System.out.println("4. 스터디별 회원 조회");
            System.out.println("5. 회원 수정");
            System.out.println("6. 회원 삭제");
            System.out.println("0. 종료");
            System.out.print("선택: ");

            int menu = Integer.parseInt(sc.nextLine());

            switch (menu) {
                case 1:
                    insertTest();
                    break;
                case 2:
                    searchAllTest();
                    break;
                case 3:
                    getMemberTest();
                    break;
                case 4:
                    searchByGroupTest();
                    break;
                case 5:
                    updateTest();
                    break;
                case 6:
                    deleteTest();
                    break;
                case 0:
                    System.out.println("종료합니다.");
                    return;
                default:
                	throw new IllegalArgumentException("Unexpected value: " + menu);
            }

            System.out.println();
        }
    }
}