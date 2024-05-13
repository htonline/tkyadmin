package me.zhengjie.modules.system.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.zhengjie.annotation.Log;
import me.zhengjie.modules.system.domain.LargeUser;
import me.zhengjie.modules.system.service.LargeUserServie;
import me.zhengjie.modules.system.service.UserService;
import me.zhengjie.modules.system.service.dto.LargeUserQueryCriteria;
import me.zhengjie.modules.system.service.dto.UserQueryCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Zuohaitao
 * @date 2024-05-13 15:07
 * @describe
 */
@RestController
@RequiredArgsConstructor
@Api(tags = "api/user管理")
@RequestMapping("/api/largeUser")
public class LargeUserController {
    private final LargeUserServie largeUserServie;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('user:list')")
    public void exportUser(HttpServletResponse response, LargeUserQueryCriteria criteria) throws IOException {
        largeUserServie.download(largeUserServie.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询api/user")
    @ApiOperation("查询api/user")
    @PreAuthorize("@el.check('user:list')")
    public ResponseEntity<Object> queryUser(LargeUserQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(largeUserServie.queryAll(criteria,pageable), HttpStatus.OK);
    }

    @PostMapping
    @Log("新增api/user")
    @ApiOperation("新增api/user")
    @PreAuthorize("@el.check('user:add')")
    public ResponseEntity<Object> createUser(@Validated @RequestBody LargeUser resources){
        return new ResponseEntity<>(largeUserServie.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改api/user")
    @ApiOperation("修改api/user")
    @PreAuthorize("@el.check('user:edit')")
    public ResponseEntity<Object> updateUser(@Validated @RequestBody LargeUser resources){
        largeUserServie.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除api/user")
    @ApiOperation("删除api/user")
    @PreAuthorize("@el.check('user:del')")
    public ResponseEntity<Object> deleteUser(@RequestBody Integer[] ids) {
        largeUserServie.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
