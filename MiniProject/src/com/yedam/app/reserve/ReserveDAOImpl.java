package com.yedam.app.reserve;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReserveDAOImpl extends DAO implements reserveDAO {

	// 사용할 sql문 정리
	private final String INSERT = "INSERT INTO reservelist VALUES(sysdate,?,?)";
	private final String SELECT_ONE = "SELECT r.id, m.name, r.type, r.reserve_date, m.phone, m.email * FROM reservelist r JOIN member m USING (id) WHERE r.id = ?";
	private final String SELECT_ALL = "SELECT r.id, m.name, r.type, r.reserve_date, m.phone, m.email * FROM reservelist r JOIN member m USING (id)";
	private final String DELETE = "DELETE from reservelist WHERE userId = ?";

	// 싱글톤
	private static reserveDAO instance = new ReserveDAOImpl();

	public static reserveDAO getInstance() {
		return instance;
	}

	// 예약 기능 - insert
	@Override
	public int insert(String type, String userId) {
		
		int result = 0;
		
		try {
			connect();
			
			System.out.println(type + "," + userId);

			pstmt = conn.prepareStatement(INSERT);
			pstmt.setString(1, type);
			pstmt.setString(2, userId);

			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return result;
	}

	// 예약 조회 기능(단건)
	@Override
	public Reserve selectOne(String userId) {
		Reserve reserve = null;
		try {
			connect();
			pstmt = conn.prepareStatement(SELECT_ONE);
			pstmt.setString(1, userId);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				reserve = new Reserve();
				reserve.setReserveDate(rs.getDate("reserveDate"));
				reserve.setReserveType(rs.getString("reserveType"));
				reserve.setUserId(rs.getString("userId"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return reserve;
	}

	// 예약 조회 기능(전체)
	@Override
	public List<Reserve> selectAll() {
		List<Reserve> list = new ArrayList<>();
		try {
			connect();

			stmt = conn.createStatement();
			rs = stmt.executeQuery(SELECT_ALL);
			while (rs.next()) {
				Reserve reserve = new Reserve();
				reserve.setUserId(rs.getString("userId"));
				reserve.setReserveType(rs.getString("reserveType"));
				reserve.setReserveDate(rs.getDate("reserveDate"));

				list.add(reserve);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return list;
	}

	// 예약 취소 기능
	@Override
	public int delete(String userId) {
		int result = 0;
		try {
			connect();

			pstmt = conn.prepareStatement(DELETE);
			pstmt.setString(1, userId);

			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return result;
	}
}
