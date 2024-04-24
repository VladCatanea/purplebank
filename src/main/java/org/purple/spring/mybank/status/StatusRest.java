package org.purple.spring.mybank.status;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.purple.spring.mybank.Deposit;
import org.purple.spring.mybank.EmbeddedJdbcConfig;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatusRest {

	private long requestsCount;
	private DataSource ds = new EmbeddedJdbcConfig().dataSource();

	@GetMapping("/status")
	public AppStatus getStatus() {
		requestsCount++;
		return new AppStatus(requestsCount, "yellow");
	}

	@GetMapping("/deposits")
	public Deposit getDeposits() {
		ResultSet rs = null;
		Statement stmt = null;
		Connection con = null;
		Deposit response = null;
		try {
			con = ds.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM deposits;");
			if (rs.next()) { //first object from the database
				response = new Deposit(rs.getInt("id"), rs.getInt("duration"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return response;
	}

	@PostMapping(value = "/createDeposit", consumes = "application/json", produces = "application/json")
	public String createDeposit(@RequestBody Deposit deposit) {
		ResultSet rs = null;
		Statement stmt = null;
		Connection con = null;
		try {
			con = ds.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery("INSERT INTO deposits (duration) VALUES (" + deposit.duration() + ");");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return "ok";
	}

}
