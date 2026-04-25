package com.nursinghome.dto;

import lombok.Data;

@Data
public class RoomBedQueryDTO {

    private Long current = 1L;
    private Long size = 10L;
    private String buildingNo;
    private String roomNo;
    private String bedNo;
    private String status;
}
