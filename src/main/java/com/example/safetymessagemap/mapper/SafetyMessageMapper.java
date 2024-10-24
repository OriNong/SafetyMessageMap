package com.example.safetymessagemap.mapper;

import com.example.safetymessagemap.vo.SafetyMessageVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SafetyMessageMapper {
    SafetyMessageVO findBySn(Long sn);

    void insertMessage(SafetyMessageVO message);

    void updateMessage(SafetyMessageVO message);
}
