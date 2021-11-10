package com.yedam.app;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.yedam.app.reserve.DAO;
import com.yedam.app.reserve.Reserve;
import com.yedam.app.reserve.ReserveDAOImpl;
import com.yedam.app.reserve.reserveDAO;
import com.yedam.java.app.common.Frame;
import com.yedam.java.app.login.User;
import com.yedam.java.app.login.userDAO;

public class ReserveFrame implements Frame {

	// 필드
	Scanner scanner = new Scanner(System.in);
	userDAO dao = new userDAO();
	reserveDAO rdao = ReserveDAOImpl.getInstance();
	int vipSeat = 5;
	int sSeat = 5;
	int aSeat = 5;
	int bSeat = 5;
	String userId;

	// 메소드
	// 실행 메소드
	public void run(String userId) {
		this.userId = userId;

		while (true) {
			menuPrint();
			int menuNo = menuSelect();

			if (menuNo == 1) {
				// 공연 예매
				concertReserve();
			} else if (menuNo == 2) {
				// 예약 조회
				selectOne();
			} else if (menuNo == 3) {
				// 전체 조회
				selectAll();
			} else if (menuNo == 4) {
				// 취소
				cancelReserve();
			} else if (menuNo == 5) {
				// 대기
			} else if (menuNo == 9) {
				end();
				break;
			} else {
				System.out.println("존재하지 않는 메뉴입니다. 다시 선택하세요.");
				break;
			}
		}
	}

	// 메뉴를 출력하는 메소드
	void menuPrint() {
		System.out.println("");
		System.out.println("==================================================================================");
		System.out.println("== 1. 공연 예약  2. 예약 조회  3. 예약 전체 조회  4. 예약 취소  5. 예약 대기  9. 프로그램 종료 ==");
		System.out.println("==================================================================================");
		System.out.println("선택>>");
	}

	// 메뉴를 선택하는 메소드
	int menuSelect() {
		int menuNo = 0;
		try {
			menuNo = scanner.nextInt();
		} catch (Exception e) {
			System.out.println("메뉴 선택에 실패하였습니다. 다시 시도하세요.");
		}
		return menuNo;
	}

	// 종료
	void end() {
		System.out.println("프로그램을 종료합니다.");
	}

	// 공연 예약
	void concertReserve() {
		try {
			System.out.println("ㅡㅡㅡㅡㅡㅡㅡ<좌석 현황>ㅡㅡㅡㅡㅡㅡㅡ");
			System.out.println(" VIP석 잔여 (" + vipSeat + ")석 ");
			System.out.println(" S석 잔여 (" + sSeat + ")석    ");
			System.out.println(" A석 잔여 (" + aSeat + ")석    ");
			System.out.println(" B석 잔여 (" + bSeat + ")석    ");
			System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
			System.out.println();
			System.out.println("예매하고자 하는 좌석 타입에 맞는 번호를 입력하세요.");
			System.out.println("VIP석<1번>  S석<2번>  A석<3번>  B석<4번>");
			System.out.println();
			System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
			int inputSeat = scanner.nextInt();

			switch (inputSeat) {
			case 1:
				if (vipSeat < 1) {
					System.out.println("매진된 좌석입니다.");
				} else {
					int reserve = rdao.insert("VIP석", userId);
					System.out.println("예매가 성공적으로 완료되었습니다.");
					vipSeat--;
				}

			case 2:
				if (sSeat < 1) {
					System.out.println("매진된 좌석입니다.");
				} else {
					int reserve = rdao.insert("S석", userId);
					System.out.println("예매가 성공적으로 완료되었습니다.");
					sSeat--;
				}
				break;

			case 3:
				if (aSeat < 1) {
					System.out.println("매진된 좌석입니다.");
				} else {
					int reserve = rdao.insert("A석", userId);
					System.out.println("예매가 성공적으로 완료되었습니다.");
					bSeat--;
				}
				break;

			case 4:
				if (bSeat < 1) {
					System.out.println("매진된 좌석입니다.");
				} else {
					int reserve = rdao.insert("B석", userId);
					System.out.println("예매가 성공적으로 완료되었습니다.");

					bSeat--;
					System.out.println("성공적으로 예매되었습니다.");
				}
				break;
			}
		} catch (Exception e) {
			System.out.println("오류가 발생했습니다. 다시 시도해주세요.");
			System.out.println("사유 : " + e.getMessage());
		}
	}

	// 전체조회
	void selectAll() {
		if (userId.equals("admin")) {
			List<Reserve> list = rdao.selectAll();
			for (Reserve resv : list) {
				System.out.println(resv);
			}
		} else {
			System.out.println("관리자 기능입니다. 관리자로 로그인하세요.");
		}
	}

	// 단건조회
	void selectOne() {
		Reserve resv = rdao.selectOne(userId);
		System.out.println(resv);
	}

	// 삭제
	void cancelReserve() {
		
		rdao.delete(userId);
	}

	// 예약 대기??
	
}
