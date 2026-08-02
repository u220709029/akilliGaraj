package com.example.akilliGaraj.mapper;



import com.example.akilliGaraj.dtos.request.MaintenanceCreateRequest;
import com.example.akilliGaraj.dtos.response.MaintenanceResponse;
import com.example.akilliGaraj.entities.MaintenanceRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MaintenanceMapper {

    @Mapping(target = "vehicle", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    MaintenanceRecord toEntity(MaintenanceCreateRequest request);


    @Mapping(source = "vehicle.id", target = "vehicleId")
    MaintenanceResponse toResponse(MaintenanceRecord entity);
}