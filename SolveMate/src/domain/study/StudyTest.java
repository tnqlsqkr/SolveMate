package domain.study;

import java.util.List;
import java.util.Scanner;

public class StudyTest {

    private static StudyService studyService = new StudyServiceImple();
    private static Scanner sc = new Scanner(System.in);
    
    // 스터디 등록 테스트
	private static void insertTest() {
		System.out.println("스터디명 입력: ");
		String gname = sc.nextLine();
	      int result = studyService.insertStudy(gname);
	      if(result > 0) System.out.println("등록 성공");
	      else System.out.println("등록 실패");
	}
	
	// 스터디 전체 조회 테스트
	private static void searchAllTest() {
        // 스터디 전체 조회 테스트
        List<StudyDTO> studyList = studyService.searchAllStudies();

        for (StudyDTO study : studyList) {
            System.out.println("ID: " + study.getGroupId());
            System.out.println("스터디명: " + study.getGname());
            System.out.println("생성일: " + study.getCreate_at());
            System.out.println("");
        }
	}
	
	// 스터디 아이디 조회 테스트
	private static void getStudyTest() {
      System.out.println("조회할 그룹id 입력 : ");
      Long groupId = Long.parseLong(sc.nextLine());
      StudyDTO study = studyService.getStudy(groupId);
      System.out.println("ID: " + study.getGroupId());
      System.out.println("스터디명: " + study.getGname());
      System.out.println("생성일: " + study.getCreate_at());
	}
	
	// 스터디 수정 테스트
	private static void updateTest() {
	  System.out.println("수정할 그룹id 입력 : ");
	  Long groupId = Long.parseLong(sc.nextLine());
	  
	  System.out.println("새 스터디명 입력: ");
	  String gname = sc.nextLine();
	  
      int result = studyService.updateStudy(groupId, gname);
      if(result > 0) System.out.println("수정 성공");
      else System.out.println("수정 실패");
	}
	
	// 스터디 삭제 테스트
    private static void deleteTest() {
        System.out.print("삭제할 그룹id 입력: ");
        Long groupId = Long.parseLong(sc.nextLine());

        int result = studyService.deleteStudy(groupId);

        if (result > 0) System.out.println("삭제 성공");
        else System.out.println("삭제 실패");
    }
	
	
    public static void main(String[] args) {
    	while(true) {
    		System.out.println("1. 스터디 등록");
    		System.out.println("2. 스터디 전체 조회");
    		System.out.println("3. 스터디 조회");
    		System.out.println("4. 스터디 수정");
    		System.out.println("5. 스터디 삭제");
    		System.out.println("0. 종료");
    		System.out.print("선택 : ");
    		
    		int menu = Integer.parseInt(sc.nextLine());
    		switch (menu) {
			case 1: {
				insertTest();
				break;
			}
			case 2: {
				searchAllTest();
				break;
			}
			case 3: {
				getStudyTest();
				break;
			}
			case 4: {
				updateTest();
				break;
			}
			case 5: {
				deleteTest();
				break;
			}
			case 0: {
				System.out.println("종료합니다.");
				return;
			}
			default:
				throw new IllegalArgumentException("Unexpected value: " + menu);
			}
    		System.out.println();
    	}
    }
}











