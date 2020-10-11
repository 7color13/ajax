package dao;

import vo.User;

public interface IUserDao {
    User selectOne(User user) throws Exception;
    void insertOne(User user) throws Exception;
}
