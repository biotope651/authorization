package io.common.authorization.common.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.*;

/**
 * Menu Depth Type
 */
@Getter
@AllArgsConstructor
public enum Depth {
    FIRST(1,"First"),
    SECOND(2, "Second");

    private Integer value;
    private String description;

    @JsonCreator
    public static Depth enumOf(Integer value) {
        return Arrays.stream(Depth.values())
                .filter(t -> t.getValue().equals(value))
                .findAny().orElse(null);
    }

    @JsonValue
    public Integer getValue() {
        return value;
    }

    public static List<Map> getEnumToListMap() {
        List<Map> resultList = new ArrayList<>();
        for(Depth type : Depth.values()) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("value",type.getValue());
            map.put("description",type.getDescription());
            resultList.add(map);
        }

        return resultList;
    }
}
