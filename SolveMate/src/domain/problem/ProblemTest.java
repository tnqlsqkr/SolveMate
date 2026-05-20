package domain.problem;

import java.util.List;
import java.util.Scanner;

public class ProblemTest {

    private static ProblemService problemService = new ProblemServiceImple();
    private static Scanner sc = new Scanner(System.in);

    // 문제 등록
    private static void insertTest() {
        ProblemDTO problem = new ProblemDTO();

        System.out.print("문제 제목 입력: ");
        problem.setTitle(sc.nextLine());

        System.out.print("문제 URL 입력: ");
        problem.setUrl(sc.nextLine());
        System.out.print("난이도 점수 ID(ds_id) 입력: ");
        problem.setDsId(Long.parseLong(sc.nextLine()));

        int result = problemService.insertProblem(problem);

        if (result > 0) System.out.println("문제 등록 성공");
        else System.out.println("문제 등록 실패");
    }

    // 문제 전체 조회
    private static void searchAllTest() {
        List<ProblemDTO> problemList = problemService.seachAllProblems();

        if (problemList.isEmpty()) {
            System.out.println("등록된 문제가 없습니다.");
            return;
        }

        for (ProblemDTO problem : problemList) {
            printProblem(problem);
        }
    }

    // 문제 단건 조회
    private static void getProblemTest() {
        System.out.print("조회할 문제 ID 입력: ");
        Long problemId = Long.parseLong(sc.nextLine());

        ProblemDTO problem = problemService.getProblem(problemId);

        if (problem == null) {
            System.out.println("해당 문제가 없습니다.");
            return;
        }

        printProblem(problem);
    }

    // 문제 수정
    private static void updateTest() {
        ProblemDTO problem = new ProblemDTO();

        System.out.print("수정할 문제 ID 입력: ");
        problem.setProblemId(Long.parseLong(sc.nextLine()));

//        System.out.print("새 문제 제목 입력: ");
//        problem.setTitle(sc.nextLine());
        problem.setTitle(problem.getTitle());

//        System.out.print("새 문제 URL 입력: ");
//        problem.setUrl(sc.nextLine());
        problem.setTitle(problem.getUrl());

        System.out.print("새 난이도 점수 ID(ds_id) 입력: ");
        problem.setDsId(Long.parseLong(sc.nextLine()));

        int result = problemService.updateProblem(problem);

        if (result > 0) System.out.println("문제 수정 성공");
        else System.out.println("문제 수정 실패");
    }

    // 문제 삭제
    private static void deleteTest() {
        System.out.print("삭제할 문제 ID 입력: ");
        Long problemId = Long.parseLong(sc.nextLine());

        int result = problemService.deleteProblem(problemId);

        if (result > 0) System.out.println("문제 삭제 성공");
        else System.out.println("문제 삭제 실패");
    }

    private static void printProblem(ProblemDTO problem) {
        System.out.println("문제 ID: " + problem.getProblemId());
        System.out.println("제목: " + problem.getTitle());
        System.out.println("URL: " + problem.getUrl());
        System.out.println("난이도 점수 ID: " + problem.getDsId());
        DifficultyScoreDTO ds = problem.getDifficultyScore();
        if (ds != null) {
            System.out.println("플랫폼: " + ds.getPlatform());
            System.out.println("난이도: " + ds.getLevel());
            System.out.println("포인트: " + ds.getPoint());
        }
        System.out.println();
    }

    public static void main(String[] args) {

        while (true) {
            System.out.println("1. 문제 등록");
            System.out.println("2. 문제 전체 조회");
            System.out.println("3. 문제 조회");
            System.out.println("4. 문제 수정");
            System.out.println("5. 문제 삭제");
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
                    getProblemTest();
                    break;
                case 4:
                    updateTest();
                    break;
                case 5:
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