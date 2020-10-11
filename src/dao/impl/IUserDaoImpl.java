package dao.impl;


import dao.IUserDao;
import vo.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class IUserDaoImpl implements IUserDao {
    private Connection con;
    private PreparedStatement pst;
    public IUserDaoImpl(Connection con){
        this.con=con;
    }
    @Override
    public User selectOne(User user) throws Exception{
        User user1 =new User() ;
        String sql = "select * from t_users where 1=1 ";
        if (user.getUsername()!=null){
            sql+="and username = ?";
            this.pst = this.con.prepareStatement(sql);
            this.pst.setString(1,user.getUsername());
        }
        if (user.getEmail()!=null&&user.getUsername()==null){
            sql+="and email = ?";
            this.pst = this.con.prepareStatement(sql);
            this.pst.setString(1,user.getEmail());
        }

        ResultSet rs = pst.executeQuery();
        if (rs.next()){
            String password = rs.getString("password");
            if (password.equals(user.getPassword())) {
                user1 = new User(rs.getString("username"), rs.getString("password"),
                        rs.getString("chrName"), rs.getString("email"), rs.getString("province"), rs.getString("city"));
            }
            else {
                user1.setUsername(user.getUsername());
                user1.setEmail(user.getEmail());
            }
        }
       return user1;
    }

    @Override
    public void insertOne(User user) throws Exception {
        String sql = "insert into t_users(userName,password,chrName,email,province,city) values(?,?,?,?,?,?) ";
        this.pst = this.con.prepareStatement(sql);
        this.pst.setString(1,user.getUsername());
        this.pst.setString(2, user.getPassword());
        this.pst.setString(3, user.getChrName());
        this.pst.setString(4, user.getEmail());
        this.pst.setString(5, user.getProvince());
        this.pst.setString(6,user.getCity());
        this.pst.executeUpdate();

    }
}
