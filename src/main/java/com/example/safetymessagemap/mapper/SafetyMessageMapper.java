package com.example.safetymessagemap.mapper;

import com.example.safetymessagemap.vo.SafetyMessageVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface SafetyMessageMapper {
    SafetyMessageVO findBySn(Long sn);

    void insertMessage(SafetyMessageVO message);

    void updateMessage(SafetyMessageVO message);
}
