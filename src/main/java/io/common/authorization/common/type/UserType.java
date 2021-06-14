package io.common.authorization.common.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.*;

/**
 * 사용자 타입
 */
@Getter
@AllArgsConstructor
public enum UserType {
    NOMAL("Nomal","일반 사용자"),
    COMPANY("Company", "회사 사용자"),
    COMPANY_ADMIN("Company-Admin", "회사 관리자"),
    SUPER_ADMIN("Super-Admin","슈퍼 관리자");

    private String value;
    private String description;

    @JsonCreator
    public static UserType enumOf(String value) {
        return Arrays.stream(UserType.values())
                .filter(t -> t.getValue().equals(value))
                .findAny().orElse(null);
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    public static List<Map> getEnumToListMap() {
        List<Map> resultList = new ArrayList<>();
        for(UserType type : UserType.values()) {
            HashMap<String, String> map = new HashMap<>();
            map.put("value",type.getValue());
            map.put("description",type.getDescription());
            resultList.add(map);
        }

        return resultList;
    }
}
