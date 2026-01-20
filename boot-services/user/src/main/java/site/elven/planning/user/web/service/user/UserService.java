package site.elven.planning.user.web.service.user;

import site.elven.planning.api.web.model.base.response.page.PageRsp;
import site.elven.planning.api.web.model.user.request.UserReq;
import site.elven.planning.api.web.model.user.response.UserRsp;
import site.elven.planning.boot.base.web.service.base.IService;
import site.elven.planning.user.web.dal.user.UserRepository;
import site.elven.planning.user.web.model.entity.user.User;

public interface UserService extends IService<User, Long, UserRepository> {
    /**
     * 根据用户名查找用户
     * @param username 用户名
     * @return 用户
     */
    UserRsp findByUsername(String username);

    /**
     * 根据邮箱查找用户
     * @param email 邮箱
     * @return 用户
     */
    UserRsp findByEmail(String email);

    /**
     * 根据电话号码查找用户
     * @param phoneNumber 电话号码
     * @return 用户
     */
    UserRsp findByPhoneNumber(String phoneNumber);

    /**
     * 获取用户详情
     * @param id 用户ID
     * @return 用户详情
     */
    UserRsp detail(Long id);

    /**
     * 获取用户列表
     * @param searchReq 查询条件
     * @return 用户列表
     */
    PageRsp<UserRsp> list(UserReq searchReq);
}
