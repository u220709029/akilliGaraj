package com.example.akilliGaraj.mapper;

import com.example.akilliGaraj.dtos.request.VehicleCreateRequest;
import com.example.akilliGaraj.dtos.response.VehicleResponse;
import com.example.akilliGaraj.entities.Vehicle;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-10-09T22:37:29+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class VehicleMapperImpl implements VehicleMapper {

    @Override
    public Vehicle toEntity(VehicleCreateRequest request) {
        if ( request == null ) {
            return null;
        }

        Vehicle.VehicleBuilder vehicle = Vehicle.builder();

        vehicle.brand( request.getBrand() );
        vehicle.model( request.getModel() );
        vehicle.productionYear( request.getProductionYear() );
        vehicle.currentOdometer( request.getCurrentOdometer() );
        vehicle.licensePlate( request.getLicensePlate() );

        return vehicle.build();
    }

    @Override
    public VehicleResponse toResponse(Vehicle entity) {
        if ( entity == null ) {
            return null;
        }

        VehicleResponse.VehicleResponseBuilder vehicleResponse = VehicleResponse.builder();

        vehicleResponse.id( entity.getId() );
        vehicleResponse.brand( entity.getBrand() );
        vehicleResponse.model( entity.getModel() );
        vehicleResponse.productionYear( entity.getProductionYear() );
        vehicleResponse.licensePlate( entity.getLicensePlate() );
        vehicleResponse.currentOdometer( entity.getCurrentOdometer() );

        return vehicleResponse.build();
    }
}
