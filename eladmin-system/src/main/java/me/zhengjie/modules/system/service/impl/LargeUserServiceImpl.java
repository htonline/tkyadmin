package me.zhengjie.modules.system.service.impl;

import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.system.domain.LargeUser;
import me.zhengjie.modules.system.repository.LargeUserRepository;
import me.zhengjie.modules.system.service.LargeUserServie;
import me.zhengjie.modules.system.service.dto.LargeUserDto;
import me.zhengjie.modules.system.service.dto.LargeUserQueryCriteria;
import me.zhengjie.modules.system.service.dto.UserDto;
import me.zhengjie.modules.system.service.mapstruct.LargeUserMapper;
import me.zhengjie.utils.FileUtil;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;
import me.zhengjie.utils.ValidationUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Zuohaitao
 * @date 2024-05-13 15:09
 * @describe
 */
@Service
@RequiredArgsConstructor
public class LargeUserServiceImpl implements LargeUserServie {

    private final LargeUserRepository largeUserRepository;
    private final LargeUserMapper largeUserMapper;


    @Override
    public Map<String, Object> queryAll(LargeUserQueryCriteria criteria, Pageable pageable) {
        Page<LargeUser> page = largeUserRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(largeUserMapper::toDto));
    }

    @Override
    public List<LargeUserDto> queryAll(LargeUserQueryCriteria criteria) {
        return largeUserMapper.toDto(largeUserRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));

    }

    @Override
    @Transactional
    public LargeUserDto findById(Integer userId) {
        LargeUser user = largeUserRepository.findById(userId).orElseGet(LargeUser::new);
        ValidationUtil.isNull(user.getUserId(),"User","userId",userId);
        return largeUserMapper.toDto(user);
    }

    @Override
    public LargeUser findByUserName(String username) {
        LargeUser user = largeUserRepository.findByUsername(username);
        return user;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public LargeUserDto create(LargeUser resources) {
        return largeUserMapper.toDto(largeUserRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(LargeUser resources) {
        LargeUser user = largeUserRepository.findById(resources.getUserId()).orElseGet(LargeUser::new);
        ValidationUtil.isNull( user.getUserId(),"User","id",resources.getUserId());
        user.copy(resources);
        largeUserRepository.save(user);
    }

    @Override
    public void deleteAll(Integer[] ids) {
        for (Integer userId : ids) {
            largeUserRepository.deleteById(userId);
        }

    }

    @Override
    public void download(List<LargeUserDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (LargeUserDto user : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put(" username",  user.getUsername());
            map.put(" password",  user.getPassword());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);

    }
}
