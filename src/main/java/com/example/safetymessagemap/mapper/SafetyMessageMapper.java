package com.example.safetymessagemap.mapper;

import com.example.safetymessagemap.vo.SafetyMessageVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface SafetyMessageMapper {
    void insertSafetyMessage(SafetyMessageVO safetyMessageVO);

    List<SafetyMessageVO> selectSafetyMessageByRegion(String rcptnRgnNm);

    Optional<SafetyMessageVO> selectMessageByRegionName(String regionName);

    SafetyMessageVO findBySn(@Param("sn") int sn);
}
