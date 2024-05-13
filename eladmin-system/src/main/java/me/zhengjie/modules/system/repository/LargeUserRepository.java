package me.zhengjie.modules.system.repository;

import me.zhengjie.modules.system.domain.LargeUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author Zuohaitao
 * @date 2024-05-13 15:05
 * @describe
 */
public interface LargeUserRepository extends JpaRepository<LargeUser, Integer>, JpaSpecificationExecutor<LargeUser> {
    /**
     * 根据用户名查询
     * @param username 用户名
     * @return /
     */
    LargeUser findByUsername(String username);

}
