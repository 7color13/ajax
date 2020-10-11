package controller;

import com.google.gson.Gson;
import dao.IUserDao;
import dbc.DatabaseConnection;
import factory.DAOFactory;
import util.MD5Util;
import vo.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = "/Register.do")
public class AjaxRegister extends HttpServlet {
    static Connection con;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> map = new HashMap<String, Object>();
        con = new DatabaseConnection().getConnection();
        IUserDao userDao = DAOFactory.getUserDAOInstance(con);
        User user = new User(req.getParameter("userName"), MD5Util.md5(req.getParameter("password")), req.getParameter("chrName"),
                req.getParameter("email"), req.getParameter("province"), req.getParameter("city"));
        try {
            userDao.insertOne(user);
            System.out.println("插入成功");
            map.put("code",0);
            String jsonStr = new Gson().toJson(map);
            resp.setContentType("text/html;charset=UTF-8");
            PrintWriter out = resp.getWriter();
            out.print(jsonStr);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
