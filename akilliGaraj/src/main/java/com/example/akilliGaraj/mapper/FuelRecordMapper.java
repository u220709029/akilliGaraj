package com.example.akilliGaraj.mapper;





import com.example.akilliGaraj.dtos.request.FuelRecordCreateRequest;
import com.example.akilliGaraj.dtos.response.FuelRecordResponse;
import com.example.akilliGaraj.entities.FuelRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FuelRecordMapper {

    @Mapping(target = "vehicle", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "totalCost", ignore = true)
    FuelRecord toEntity(FuelRecordCreateRequest request);


    @Mapping(source = "vehicle.id", target = "vehicleId")
    FuelRecordResponse toResponse(FuelRecord entity);
}