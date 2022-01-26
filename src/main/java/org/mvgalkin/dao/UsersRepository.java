package org.mvgalkin.dao;

import org.mvgalkin.models.User;
import org.springframework.data.repository.Repository;

@org.springframework.stereotype.Repository
public interface UsersRepository extends Repository<User, Long> {
    User findByName(String name);
}
