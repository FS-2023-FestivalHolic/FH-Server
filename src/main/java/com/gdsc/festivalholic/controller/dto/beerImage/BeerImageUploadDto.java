package com.gdsc.festivalholic.controller.dto.beerImage;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@NoArgsConstructor
public class BeerImageUploadDto {

    private MultipartFile file;

    public BeerImageUploadDto(MultipartFile file){
        this.file = file;
    }
}
