package module;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

import config.entity.Log;
import entity.IO.MainIO;
import entity.info.Info;
import rule.agreement.GeneralInformationCommand;
import tool.DataBaseTools;
import tool.JsonTools;

public class DbOperator {
	public DataBaseTools tools;
	
	public DbOperator() {
		
	}
	public DbOperator(DataBaseTools tools) {
		this.tools = tools;
	}
	
	/**
	 * 判断用户登录是否成功
	 * @param name
	 * @param password
	 * @param state
	 * @return
	 */
	public boolean judgePeopleLogin(String name,String password,int state) {
		Connection con = tools.getConectDataBase("db.properties");
		String sql = "Select * From playerinfo p where p.name = ? and p.loginState = ? ";
		try {
			con.setAutoCommit(false);
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, name);
			ps.setInt(2,state);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				String dbName = rs.getString("name");
				String dbPassword = rs.getString("password");
				if(dbName.equals(name) && dbPassword.equals(password)) {
					con.commit();
					return true;
				}else {
					Log.d("用户名密码错误");
					return false;
				}
			}else {
				Log.d("没有此用户");
				con.commit();
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			tools.rollBack(con);
			Log.d("判断用户登录出现问题");
			return false;
		}finally {
			tools.closeCon(con);
		}
	}
	
	/**
	 * 判断用户名是否存在
	 * @param name
	 * @return
	 */
	public boolean judgePeopleNameExist(String name) {
		Connection con = tools.getConectDataBase("db.properties");
		String sql = "Select * From playerinfo p where p.name = ? ";
		try {
			con.setAutoCommit(false);
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			boolean isFlag = rs.next();
			con.commit();
			if(isFlag) {
				Log.d("用户存在");
				return true;
			}else {
				Log.d("用户不存在存在");
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			tools.rollBack(con);
			Log.d("判断用户是否存在出现问题");
			return false;
		}finally {
			tools.closeCon(con);
		}
	}
	
	/**
	 * 增加一个新的用户
	 * @param name
	 * @param password
	 * @return
	 */
	public boolean insertNewPlayer(String name,String password,int state,MainIO mainIO) {
		String boxId = name+"_b"+state;
		String friendId = name+"_f"+state;
		String gradeId = name+"_g"+state;
		int pFlag = 0;
		Connection con = tools.getConectDataBase("db.properties");
		String sql = "INSERT INTO boxinfo (boxid) VALUES (?)";
		PreparedStatement ps;
		try {
			con.setAutoCommit(false);
			ps = con.prepareStatement(sql);
			ps.setString(1, boxId);
			int bFlag = ps.executeUpdate();
			if(bFlag==1) {
				Log.d("创建个人道具箱成功");
				mainIO.sendMessage(new GeneralInformationCommand(), JsonTools.getString(new Info("注册中","创建个人道具箱成功")));
				sql = "INSERT INTO gradeinfo "
						+"(gradeid) "
						+ "VALUES(?) ";
				ps = con.prepareStatement(sql);
				ps.setString(1,gradeId);
				int gFlag = ps.executeUpdate();
				if(gFlag==1) {
					Log.d("创建个人分数表成功");
					mainIO.sendMessage(new GeneralInformationCommand(), JsonTools.getString(new Info("注册中","创建个人分数表成功")));
					sql = "INSERT INTO friendinfo "
							+"(friendid) "
							+"VALUES "
							+ "(?) ";
					ps = con.prepareStatement(sql);
					ps.setString(1, friendId);
					int fFlag = ps.executeUpdate();
					if(fFlag == 1) {
						Log.d("创建个人好友表成功");
						mainIO.sendMessage(new GeneralInformationCommand(), JsonTools.getString(new Info("注册中","创建个人好友表成功")));
						sql = "INSERT INTO playerinfo "
								+ "(name,password,friendId,boxId,gradeId,loginState) "
								+ "VALUES "
								+ "(?,?,?,?,?,?) ";
						ps = con.prepareStatement(sql);
						ps.setString(1, name);
						ps.setString(2, password);
						ps.setString(3, friendId);
						ps.setString(4, boxId);
						ps.setString(5, gradeId);
						ps.setInt(6, state);
						pFlag = ps.executeUpdate();
					}else {
						Log.d("创建个人好友表失败");
						mainIO.sendMessage(new GeneralInformationCommand(), JsonTools.getString(new Info("注册中","创建个人好友表失败")));
					}
				}else {
					Log.d("创建个人分数表失败");
					mainIO.sendMessage(new GeneralInformationCommand(), JsonTools.getString(new Info("注册中","创建个人分数表失败")));
				}
			}else {
				Log.d("创建个人道具箱失败");
				mainIO.sendMessage(new GeneralInformationCommand(), JsonTools.getString(new Info("注册中","创建个人道具箱失败")));
			}
			if(pFlag==1) {
				Log.d("创建个人信息成功");
				mainIO.sendMessage(new GeneralInformationCommand(), JsonTools.getString(new Info("注册中","创建个人信息成功")));
				con.commit();
				return true;
			}else {
				Log.d("创建个人信息失败");
				mainIO.sendMessage(new GeneralInformationCommand(), JsonTools.getString(new Info("注册中","创建个人信息失败")));
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			tools.rollBack(con);
			return false;
		}finally {
			tools.closeCon(con);
		}
	}
	
	@Test
	public void t() {
		DbOperator db = new DbOperator();
		db.tools = new DataBaseTools();
//		db.insertNewPlayer("test", "123456");
//		System.out.println(db.judgePeopleNameExist("test"));
		System.out.println(db.judgePeopleLogin("test","123456",4));
	}
}
