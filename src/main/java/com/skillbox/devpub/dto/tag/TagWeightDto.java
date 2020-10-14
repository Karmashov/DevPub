package com.skillbox.devpub.dto.tag;

import com.skillbox.devpub.dto.universal.Dto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TagWeightDto implements Dto {

    private String name;
    private Double weight;
}
