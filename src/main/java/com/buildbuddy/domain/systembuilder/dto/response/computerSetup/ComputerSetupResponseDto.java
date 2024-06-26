package com.buildbuddy.domain.systembuilder.dto.response.computerSetup;

import com.buildbuddy.domain.systembuilder.entity.ComputerSetupEntity;
import com.buildbuddy.domain.systembuilder.entity.ComputerSetupModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Base64;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComputerSetupResponseDto {

    @JsonProperty
    private Integer computerSetupId;

    @JsonProperty
    private String username;

    @JsonProperty
    private Integer computerCaseId;

    @JsonProperty
    private String computerCaseName;

    @JsonProperty
    private String computerCaseImage;

    @JsonProperty
    private Integer coolerId;

    @JsonProperty
    private String coolerName;

    @JsonProperty
    private String coolerImage;

    @JsonProperty
    private Integer graphicsCardId;

    @JsonProperty
    private String graphicsCardName;

    @JsonProperty
    private Integer graphicsCardBenchmark;

    @JsonProperty
    private String graphicsCardImage;

    @JsonProperty
    private Integer monitorId;

    @JsonProperty
    private String monitorName;

    @JsonProperty
    private String monitorImage;

    @JsonProperty
    private Integer motherboardId;

    @JsonProperty
    private String motherboardName;

    @JsonProperty
    private String motherboardImage;

    @JsonProperty
    private Integer powersupplyId;

    @JsonProperty
    private String powersupplyName;

    @JsonProperty
    private String powersupplyImage;

    @JsonProperty
    private Integer processorId;

    @JsonProperty
    private String processorName;

    @JsonProperty
    private Integer processorBenchmark;

    @JsonProperty
    private String processorImage;

    @JsonProperty
    private Integer ramId;

    @JsonProperty
    private String ramName;

    @JsonProperty
    private String ramImage;

    @JsonProperty
    private Integer ramSize;

    @JsonProperty
    private Integer storageId;

    @JsonProperty
    private String storageName;

    @JsonProperty
    private Integer storageSize;

    @JsonProperty
    private String storageImage;

    @JsonProperty
    private BigDecimal totalPrice;

    @JsonProperty
    private Integer totalBenchmark;

    @JsonProperty
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdTime;

    @JsonProperty
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastUpdateTime;

    public static ComputerSetupResponseDto convertToDto(ComputerSetupEntity entity){
        return ComputerSetupResponseDto.builder()
                .computerSetupId(entity.getId())
                .computerCaseId(entity.getCaseId())
                .coolerId(entity.getCoolerId())
                .graphicsCardId(entity.getGraphicsCardId())
                .monitorId(entity.getMonitorId())
                .motherboardId(entity.getMotherboardId())
                .powersupplyId(entity.getPowersupplyId())
                .processorId(entity.getProcessorId())
                .ramId(entity.getRamId())
                .storageId(entity.getStorageId())
                .username(entity.getUser().getUsername())
                .createdTime(entity.getCreatedTime())
                .lastUpdateTime(entity.getLastUpdateTime())
                .build();
    }

    public static ComputerSetupResponseDto convertToDto(ComputerSetupModel entity){
        return ComputerSetupResponseDto.builder()
                .computerSetupId(entity.getComputerSetupId())
                .computerCaseId(entity.getCaseId())
                .computerCaseName(entity.getCaseName())
                .computerCaseImage(convertByteToImage(entity.getCaseImage()))
                .coolerId(entity.getCoolerId())
                .coolerName(entity.getCoolerName())
                .coolerImage(convertByteToImage(entity.getCoolerImage()))
                .graphicsCardId(entity.getGraphicsCardId())
                .graphicsCardName(entity.getGraphicsCardName())
                .graphicsCardBenchmark(entity.getGraphicsCardBenchmark())
                .graphicsCardImage(convertByteToImage(entity.getGraphicsCardImage()))
                .monitorId(entity.getMonitorId())
                .monitorName(entity.getMonitorName())
                .monitorImage(convertByteToImage(entity.getMonitorImage()))
                .motherboardId(entity.getMotherboardId())
                .motherboardName(entity.getMotherboardName())
                .motherboardImage(convertByteToImage(entity.getMotherboardImage()))
                .powersupplyId(entity.getPowersupplyId())
                .powersupplyName(entity.getPowersupplyName())
                .powersupplyImage(convertByteToImage(entity.getPowersupplyImage()))
                .processorId(entity.getProcessorId())
                .processorName(entity.getProcessorName())
                .processorBenchmark(entity.getProcessorBenchmark())
                .processorImage(convertByteToImage(entity.getProcessorImage()))
                .ramId(entity.getRamId())
                .ramName(entity.getRamName())
                .ramSize(entity.getRamSize())
                .ramImage(convertByteToImage(entity.getRamImage()))
                .storageId(entity.getStorageId())
                .storageName(entity.getStorageName())
                .storageSize(entity.getStorageSize())
                .storageImage(convertByteToImage(entity.getStorageImage()))
                .username(entity.getUsername())
                .totalPrice(entity.getTotalPrice())
                .totalBenchmark(entity.getTotalBenchmark())
                .createdTime(entity.getCreatedTime())
                .lastUpdateTime(entity.getLastUpdateTime())
                .build();
    }

    private static String convertByteToImage(byte[] image){
        return image != null ? Base64.getEncoder().encodeToString(image) : null;
    }


}
