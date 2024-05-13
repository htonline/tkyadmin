package me.zhengjie.modules.system.service;

import me.zhengjie.modules.system.domain.LargeUser;
import me.zhengjie.modules.system.service.dto.LargeUserDto;
import me.zhengjie.modules.system.service.dto.LargeUserQueryCriteria;
import me.zhengjie.modules.system.service.dto.UserDto;
import me.zhengjie.modules.system.service.dto.UserQueryCriteria;
import org.springframework.data.domain.Pageable;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.List;
import java.io.IOException;

/**
 * @author Zuohaitao
 * @date 2024-05-13 15:14
 * @describe
 */
public interface LargeUserServie {
    /**
     * 查询数据分页
     * @param criteria 条件
     * @param pageable 分页参数
     * @return Map<String,Object>
     */
    Map<String,Object> queryAll(LargeUserQueryCriteria criteria, Pageable pageable);

    /**
     * 查询所有数据不分页
     * @param criteria 条件参数
     * @return List<UserDto>
     */
    List<LargeUserDto> queryAll(LargeUserQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param userId ID
     * @return UserDto
     */
    LargeUserDto findById(Integer userId);

    /**
     * 根据用户名查询
     * @param username ID
     * @return UserDto
     */
    LargeUser findByUserName(String username);

    /**
     * 创建
     * @param resources /
     * @return UserDto
     */
    LargeUserDto create(LargeUser resources);

    /**
     * 编辑
     * @param resources /
     */
    void update(LargeUser resources);

    /**
     * 多选删除
     * @param ids /
     */
    void deleteAll(Integer[] ids);

    /**
     * 导出数据
     * @param all 待导出的数据
     * @param response /
     * @throws IOException /
     */
    void download(List<LargeUserDto> all, HttpServletResponse response) throws IOException;

}
