package ru.otus.istyazhkina.constructor.domain.rest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.istyazhkina.constructor.domain.entity.Power;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PowerDTO {
    private String id;
    private String name;
    private String value;

    public static Power toEntity(PowerDTO dto) {
        return Power.builder()
                .id(dto.getId())
                .name(dto.getName())
                .value(dto.getValue())
                .build();
    }

    public static PowerDTO toDto(Power power) {
        return PowerDTO.builder()
                .id(power.getId())
                .name(power.getName())
                .value(power.getValue())
                .build();
    }
}
