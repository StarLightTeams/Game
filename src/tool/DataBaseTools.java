package tool;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSourceFactory;
import org.junit.Test;

import config.entity.Log;

public class DataBaseTools {
	
	public Connection getConectDataBase(String fileName) {
		Connection conn = null;
		try {
			Properties p = new Properties();
			p.load(this.getClass().getClassLoader().getResourceAsStream(fileName));
			DataSource data = BasicDataSourceFactory.createDataSource(p);
			conn = data.getConnection();
			Log.d("数据库连接成功");
			return conn;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Log.d("文件找不到");
		} catch (IOException e) {
			e.printStackTrace();
			Log.d("IO流出错");
		} catch (Exception e) {
			e.printStackTrace();
			Log.d("出现位置错误");
		}
		return null;
	}
	
	//关闭连接
	public static void closeCon(Connection con){
		if(con!=null){
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@Test
	public void test() {
		Connection con = getConectDataBase("db.properties");
		String sql ="SELECT * FROM playerinfo";
		PreparedStatement stmt;
		try {
			stmt = (PreparedStatement) con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				System.out.println(rs.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			closeCon(con);
		}
		
	}
}
