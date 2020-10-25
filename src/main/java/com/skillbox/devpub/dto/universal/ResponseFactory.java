package com.skillbox.devpub.dto.universal;

import com.skillbox.devpub.dto.post.CalendarResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.*;

public class ResponseFactory {

    public static UserBaseResponse getUserBaseResponse(Boolean result, Dto dto) {
        return new UserBaseResponse(result, dto);
    }

    public static ErrorListResponse getErrorListResponse(HashMap<String, String> errors) {
        return new ErrorListResponse(errors);
    }

    public static BaseResponse responseOk() {
        return new BaseResponse(true);
    }

    public static Response getCalendar(HashSet<Integer> years, Map<LocalDate, Integer> result) {
        return new CalendarResponseDto(years, result);
    }

    public static Response getSettings(Boolean multiuserMode, Boolean postPremoderation, Boolean publicStatistics) {
        return new SettingsDto(multiuserMode, postPremoderation, publicStatistics);
    }

    public static BaseResponseList getBaseResponseListWithLimit(List<Dto> list, int offset, int limit) {
        List<Dto> data = getElementsInRange(list, offset, limit);
        return new BaseResponseList(
                list.size(),
                data
        );
    }

    private static List<Dto> getElementsInRange(List<Dto> list, int offset, int limit) {
        int lastElementIndex = offset + limit;
        int lastPostIndex = list.size();
        if (lastPostIndex >= offset) {
            if (lastElementIndex <= lastPostIndex) {
                return list.subList(offset, lastElementIndex);
            } else {
                return list.subList(offset, lastPostIndex);
            }
        } else {
            return new ArrayList<>();
        }
    }
}
