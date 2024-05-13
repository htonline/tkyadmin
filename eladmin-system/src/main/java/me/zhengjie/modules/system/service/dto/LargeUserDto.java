package me.zhengjie.modules.system.service.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Zuohaitao
 * @date 2024-05-13 15:07
 * @describe
 */
@Data
public class LargeUserDto implements Serializable {

    private Integer userId;

    private String username;

    private String password;
}
