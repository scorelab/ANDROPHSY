package lk.score.androphsy.database;// Copyright 2015 Indeewari Akarawita
//
// This file is a part of ANDROPHSY
//
// ANDROPHSY is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program. If not, see <http://www.gnu.org/licenses/>.

import lk.score.androphsy.exceptions.PropertyNotDefinedException;
import lk.score.androphsy.util.AndrophsyConstants;
import lk.score.androphsy.util.AndrophsyProperties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * @author indeewari
 *
 */
//FIXME This class must be using the singleton pattern
public class ConnectDb {
	private String userName;
	private String password;
	private String url;
	private String dbName;
	private String driver;

	private AndrophsyProperties androphsyProperties;

	public Connection getConnection() {

		androphsyProperties = new AndrophsyProperties();

		Connection con = null;
		Properties conProperties = new Properties();
		conProperties.setProperty("user", userName);
		conProperties.setProperty("password", password);

		try {
			Class.forName(driver).newInstance();
			con = DriverManager.getConnection(url + dbName, userName, password);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return con;
	}

	private void init() {
		try {
			userName = androphsyProperties.getProperty(AndrophsyConstants.DATABASE_USERNAME);
			password = androphsyProperties.getProperty(AndrophsyConstants.DATABASE_PASSWORD);
			dbName = androphsyProperties.getProperty(AndrophsyConstants.DATABASE_NAME);
			driver = androphsyProperties.getProperty(AndrophsyConstants.DATABASE_DRIVER);
			url = androphsyProperties.getProperty(AndrophsyConstants.DATABASE_URL);

		} catch (PropertyNotDefinedException e) {
			e.printStackTrace();
		}
	}
	public void UpdateTable(String query) {
		ConnectDb condb = new ConnectDb();
		Connection con = condb.getConnection();
		try {
			Statement stmt = con.createStatement();
			int count = stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
