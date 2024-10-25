package com.example.safetymessagemap.mapper;

import com.example.safetymessagemap.vo.SafetyMessageVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface SafetyMessageMapper {
    // insert / update 판별 시 PK인 SN 값으로 테이블 데이터 select
    SafetyMessageVO findBySn(Long sn);

    // 반환된 지역명에 발령된 안전 재난 메시지 select
    List<SafetyMessageVO> findMessageByRegion(String region);

    // Api에서 신규 데이터 존재할 경우 새로운 데이터 insert 수행
    void insertMessage(SafetyMessageVO message);

    // Api에서 수정된 값 존재할 경우 기존 데이터에 update 수행
    void updateMessage(SafetyMessageVO message);

    // 24시간마다 DB 테이블 초기화 시 사용
    void resetMessage();
}
