package io.common.authorization.common.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 활성 상태 Type
 */
@Getter
@AllArgsConstructor
public enum ActiveStatus {
    ACTIVE("Active","활성화"),
    INACTIVE("Inactive", "비활성화");

    private String value;
    private String description;

    @JsonCreator
    public static ActiveStatus enumOf(String value) {
        return Arrays.stream(ActiveStatus.values())
                .filter(t -> t.getValue().equals(value))
                .findAny().orElse(null);
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    public static List<Map> getEnumToListMap() {
        List<Map> resultList = new ArrayList<>();
        for(ActiveStatus type : ActiveStatus.values()) {
            HashMap<String, String> map = new HashMap<>();
            map.put("value",type.getValue());
            map.put("description",type.getDescription());
            resultList.add(map);
        }

        return resultList;
    }
}
