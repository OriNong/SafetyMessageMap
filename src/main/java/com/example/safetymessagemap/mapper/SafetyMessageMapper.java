package com.example.safetymessagemap.mapper;

import com.example.safetymessagemap.vo.SafetyMessageVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface SafetyMessageMapper {
    Optional<SafetyMessageVO> selectMessageByRegionName(String regionName);

    void insertSafetyMessage(SafetyMessageVO safetyMessageVO);
}
