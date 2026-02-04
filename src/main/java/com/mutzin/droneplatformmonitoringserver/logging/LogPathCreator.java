package com.mutzin.droneplatformmonitoringserver.logging;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
/// LogPathCreator

@Component // Bean으로 등록
public class LogPathCreator {
    private static final DateTimeFormatter DATE = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Value("${log.path}")
    private String logPath; // static 제거

    public Path create() {
        String fileName = LocalDate.now().format(DATE) + ".log";
        // logPath가 null인지 체크하는 안전장치 추가
        if (logPath == null) {
            return Paths.get("logs", fileName); // 기본값 설정
        }
        return Paths.get(logPath, fileName);
    }
}