package site.elven.planning.user.web.dal.user;

import org.springframework.stereotype.Repository;
import site.elven.planning.boot.base.web.dal.base.BaseRepository;
import site.elven.planning.user.web.model.entity.user.User;

@Repository
public interface UserRepository extends BaseRepository<User, Long> {
    User findByUsername(String username);
    User findByEmail(String email);
    User findByPhoneNumber(String phoneNumber);
}
