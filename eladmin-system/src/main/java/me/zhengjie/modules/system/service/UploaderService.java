package me.zhengjie.modules.system.service;

import me.zhengjie.modules.system.service.dto.FileChunkDTO;
import me.zhengjie.modules.system.service.dto.FileChunkResultDTO;

import java.io.IOException;

/**
 * @author Zuohaitao
 * @date 2023-09-13 10:13
 * @describe
 */
public interface UploaderService {
    /**
     * 检查文件是否存在，如果存在则跳过该文件的上传，如果不存在，返回需要上传的分片集合
     * @param chunkDTO
     * @return
     */
    FileChunkResultDTO checkChunkExist(FileChunkDTO chunkDTO);


    /**
     * 上传文件分片
     * @param chunkDTO
     */
    void uploadChunk(FileChunkDTO chunkDTO) throws IOException;


    /**
     * 合并文件分片
     * @param identifier
     * @param fileName
     * @param totalChunks
     * @return
     * @throws IOException
     */
    boolean mergeChunk(String identifier,String fileName,Integer totalChunks)throws IOException;

}
