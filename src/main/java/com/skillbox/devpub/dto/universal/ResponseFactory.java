package com.skillbox.devpub.dto.universal;

import com.skillbox.devpub.dto.post.CalendarResponseDto;

import java.time.LocalDate;
import java.util.*;

public class ResponseFactory {
//    public static BaseResponse getBaseResponse(Dto dto){
//        return new BaseResponse(dto);
//    }

    public static DtoResponse getDtoResponse(Dto dto) {
        return new DtoResponse(dto);
    }

//    public static BaseDtoResponse getBaseDtoResponse(Boolean result, Dto dto) {
//        return new BaseDtoResponse(result, dto);
//    }

    public static UserBaseResponse getUserBaseResponse(Boolean result, Dto dto) {
        return new UserBaseResponse(result, dto);
    }

//    public static ErrorResponse getErrorResponse(/*String error,*/ String errorDescription) {
//        return new ErrorResponse();
//    }

    public static ErrorListResponse getErrorListResponse(HashMap<String, String> errors) {
        return new ErrorListResponse(errors);
    }

    public static BaseResponse responseOk() {
        return new BaseResponse(true);
    }

//    public static BaseResponse responseError() {
//        return new BaseResponse(false);
//    }

//    public static BaseResponseList getBaseResponseList(List<Dto> list, int total, int offset, int limit){
//        return new BaseResponseList(
//                total,
//                offset,
//                limit,
//                list
//        );
//    }
//
    public static Response getCalendar(HashSet<Integer> years, Map<LocalDate, Integer> result) {
        return new CalendarResponseDto(years, result);
    }

    public static Response getSettings(Boolean multiuserMode, Boolean postPremoderation, Boolean publicStatistics) {
        return new SettingsDto(multiuserMode, postPremoderation, publicStatistics);
    }

    public static BaseResponseList getBaseResponseListWithLimit(List<Dto> list, int offset, int limit){
        List<Dto> data = getElementsInRange(list, offset, limit);
//        System.out.println(getElementsInRange(list, offset, limit));
        return new BaseResponseList(
                list.size(),
//                offset,
//                limit,
                data //@TODO возврат ответа в нужном формате (поля поста)
        );
    }

    private static List<Dto> getElementsInRange(List<Dto> list, int offset, int limit) {
        int lastElementIndex = offset + limit;
        int lastPostIndex = list.size();
        if (lastPostIndex >= offset) {//если есть элементы входящие в нужный диапазон
            if (lastElementIndex <= lastPostIndex) {//если все элементы с нужными индексами есть в листе
//                System.out.println("1 " + list.subList(offset, lastElementIndex));
                return list.subList(offset, lastElementIndex);
            } else {//если не хватает элементов, то в посты записываем остаток, считая от offset
//                System.out.println("2 " + list.subList(offset, lastPostIndex));
                return list.subList(offset, lastPostIndex);
            }
        } else {
            return new ArrayList<>();
        }
    }

//    public static ErrorResponse getErrorResponse(String error, String errorDescription){
//        return new ErrorResponse(error, errorDescription);

//    }
}
