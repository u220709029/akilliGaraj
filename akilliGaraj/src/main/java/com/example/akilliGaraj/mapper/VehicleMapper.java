package com.example.akilliGaraj.mapper;



import com.example.akilliGaraj.dtos.request.VehicleCreateRequest;
import com.example.akilliGaraj.dtos.response.VehicleResponse;
import com.example.akilliGaraj.entities.Vehicle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VehicleMapper {


    Vehicle toEntity(VehicleCreateRequest request);

    VehicleResponse toResponse(Vehicle entity);
}