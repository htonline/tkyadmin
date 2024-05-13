package me.zhengjie.modules.system.service.mapstruct;

import me.zhengjie.base.BaseMapper;
import me.zhengjie.modules.system.service.dto.LargeUserDto;
import me.zhengjie.modules.system.domain.LargeUser;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author Zuohaitao
 * @date 2024-05-13 15:09
 * @describe
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LargeUserMapper extends BaseMapper<LargeUserDto, LargeUser> {

}
