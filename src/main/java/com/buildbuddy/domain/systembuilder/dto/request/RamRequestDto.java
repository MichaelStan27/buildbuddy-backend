package com.buildbuddy.domain.systembuilder.dto.request;

import com.buildbuddy.domain.systembuilder.entity.RamEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Base64;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RamRequestDto {

    private Integer id;
    private String name;
    private String manufacturer;
    private BigDecimal price;
    private String productLink;
    private Integer ramSize;
    private Integer ramQuantity;
    private String ramSpeed;
    private String ramType;
    private String casLatency;
    private String image;

    public static RamEntity convertToEntity(RamRequestDto dto){
        String image = dto.getImage();
        return RamEntity.builder()
                .name(dto.getName())
                .manufacturer(dto.getManufacturer())
                .price(dto.getPrice())
                .productLink(dto.getProductLink())
                .ramSize(dto.getRamSize())
                .ramQuantity(dto.getRamQuantity())
                .ramSpeed(dto.getRamSpeed())
                .ramType(dto.getRamType())
                .casLatency(dto.getCasLatency())
                .image(image != null ? Base64.getDecoder().decode(image) : null)
                .build();
    }

}
