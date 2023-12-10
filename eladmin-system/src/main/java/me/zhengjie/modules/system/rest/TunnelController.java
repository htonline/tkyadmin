/*
*  Copyright 2019-2020 Zheng Jie
*
*  Licensed under the Apache License, Version 2.0 (the "License");
*  you may not use this file except in compliance with the License.
*  You may obtain a copy of the License at
*
*  http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing, software
*  distributed under the License is distributed on an "AS IS" BASIS,
*  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*  See the License for the specific language governing permissions and
*  limitations under the License.
*/
package me.zhengjie.modules.system.rest;

import me.zhengjie.annotation.Log;
import me.zhengjie.modules.system.domain.Tunnel;
import me.zhengjie.modules.system.service.TunnelService;
import me.zhengjie.modules.system.service.dto.TunnelQueryCriteria;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @website https://el-admin.vip
* @author zuohaitao
* @date 2023-11-01 Tunnel表的Controller
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "api/tunnel管理")
@RequestMapping("/api/tunnel")
public class TunnelController {

    private final TunnelService tunnelService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('tunnel:list')")
    public void exportTunnel(HttpServletResponse response, TunnelQueryCriteria criteria) throws IOException {
        tunnelService.download(tunnelService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询api/tunnel")
    @ApiOperation("查询api/tunnel")
    @PreAuthorize("@el.check('tunnel:list')")
    public ResponseEntity<Object> queryTunnel(TunnelQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(tunnelService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增api/tunnel")
    @ApiOperation("新增api/tunnel")
    @PreAuthorize("@el.check('tunnel:add')")
    public ResponseEntity<Object> createTunnel(@Validated @RequestBody Tunnel resources){
        return new ResponseEntity<>(tunnelService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改api/tunnel")
    @ApiOperation("修改api/tunnel")
    @PreAuthorize("@el.check('tunnel:edit')")
    public ResponseEntity<Object> updateTunnel(@Validated @RequestBody Tunnel resources){
        tunnelService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除api/tunnel")
    @ApiOperation("删除api/tunnel")
    @PreAuthorize("@el.check('tunnel:del')")
    public ResponseEntity<Object> deleteTunnel(@RequestBody Integer[] ids) {
        tunnelService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
