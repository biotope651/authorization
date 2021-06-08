package io.common.authorization.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /**
     * 표준 공통 코드 C10000번대 코드 사용
     */
    INVALID_FORMAT(400,"C10001","Invalid Data Format (입력 데이터를 확인하십시오.)"),
    UNAUTHORIZED_ERROR(401,"C10002","Unauthorized (권한이 없습니다.)"),
    INTERNAL_SERVER_ERROR(500,"C10003","Internal Server Error (서버에서 처리중 에러가 발생하였습니다.)"),
    JSON_PARSING_ERROR(500, "C10004","Json Data Parsing Error (데이터 파싱중 에러가 발생하였습니다.)"),
    CONNECTION_REFUSED_ERROR(500, "C10005","Connection Refused (서버와 연결을 확인하십시오.)"),
    RESOURCE_ACCESS_ERROR(500, "C10006", "Network I/O Error (서버와 연결 방식을 확인하십시오.)"),


    /**
     * Auth API 관련 코드는 A20000번대 코드 사용
     */
    MEMBER_NOT_FOUND(404, "A20001","Member not found (잘못된 유저 정보 입니다.)"),
    FORBIDDEN_ERROR(403, "A20002","API 조회 권한이 없습니다."),
    RESOURCE_ID_NOT_VALID(400,"A20003", "The ID of the resource is not valid (요청한 리소스 ID를 확인하십시오.)"),
    CANNOT_UPDATE_STATUS(400, "A20007", "Cannot update status (이 상태로 변경할 수 없습니다.)");

    private int status;
    private String code;
    private String message;

    public static ErrorCode enumOf(String code) {
        return Arrays.stream(ErrorCode.values())
                .filter(t -> t.getCode() == code)
                .findAny().orElse(null);
    }
}
