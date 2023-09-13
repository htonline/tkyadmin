package me.zhengjie.modules.system.rest;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.system.service.UploaderService;
import me.zhengjie.modules.system.service.dto.FileChunkDTO;
import me.zhengjie.modules.system.service.dto.FileChunkResultDTO;
import me.zhengjie.modules.system.verify.RestApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Zuohaitao
 * @date 2023-09-12 16:32
 * @describe 大文件分块上传
 */
@RestController
@RequiredArgsConstructor
@Api(tags = "api/big_radar_acquisition_upload管理")
@RequestMapping("/api/big_radar_acquisition_upload")
public class UploaderController {
    @Autowired
    private UploaderService uploadService;

    /**
     * 检查分片是否存在
     *
     * @return
     */
    @GetMapping("/chunk")
    public RestApiResponse<Object> checkChunkExist(FileChunkDTO chunkDTO) {
        FileChunkResultDTO fileChunkCheckDTO;
        try {
            fileChunkCheckDTO = uploadService.checkChunkExist(chunkDTO);
            return RestApiResponse.success(fileChunkCheckDTO);
        } catch (Exception e) {
            return RestApiResponse.error(e.getMessage());
        }
    }


    /**
     * 上传文件分片
     *
     * @param chunkDTO
     * @return
     */
    @PostMapping("/chunk")
    public RestApiResponse<Object> uploadChunk(FileChunkDTO chunkDTO) {
        try {
            uploadService.uploadChunk(chunkDTO);
            return RestApiResponse.success(chunkDTO.getIdentifier());
        } catch (Exception e) {
            return RestApiResponse.error(e.getMessage());
        }
    }

    /**
     * 请求合并文件分片
     *
     * @param chunkDTO
     * @return
     */
    @PostMapping("/merge")
    public RestApiResponse<Object> mergeChunks(@RequestBody FileChunkDTO chunkDTO) {
        try {
            boolean success = uploadService.mergeChunk(chunkDTO.getIdentifier(), chunkDTO.getFilename(), chunkDTO.getTotalChunks());
            return RestApiResponse.flag(success);
        } catch (Exception e) {
            return RestApiResponse.error(e.getMessage());
        }
    }
}
