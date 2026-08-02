package com.example.akilliGaraj.mapper;

import com.example.akilliGaraj.dtos.request.MaintenanceCreateRequest;
import com.example.akilliGaraj.dtos.response.MaintenanceResponse;
import com.example.akilliGaraj.entities.MaintenanceRecord;
import com.example.akilliGaraj.entities.Vehicle;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-27T17:10:28+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class MaintenanceMapperImpl implements MaintenanceMapper {

    @Override
    public MaintenanceRecord toEntity(MaintenanceCreateRequest request) {
        if ( request == null ) {
            return null;
        }

        MaintenanceRecord.MaintenanceRecordBuilder maintenanceRecord = MaintenanceRecord.builder();

        maintenanceRecord.type( request.getType() );
        maintenanceRecord.description( request.getDescription() );
        maintenanceRecord.cost( request.getCost() );
        maintenanceRecord.recordDate( request.getRecordDate() );
        maintenanceRecord.nextDueDate( request.getNextDueDate() );
        maintenanceRecord.nextDueOdometer( request.getNextDueOdometer() );
        maintenanceRecord.maintenanceDate( request.getMaintenanceDate() );
        maintenanceRecord.odometerAtMaintenance( request.getOdometerAtMaintenance() );

        return maintenanceRecord.build();
    }

    @Override
    public MaintenanceResponse toResponse(MaintenanceRecord entity) {
        if ( entity == null ) {
            return null;
        }

        MaintenanceResponse.MaintenanceResponseBuilder maintenanceResponse = MaintenanceResponse.builder();

        maintenanceResponse.vehicleId( entityVehicleId( entity ) );
        maintenanceResponse.id( entity.getId() );
        maintenanceResponse.type( entity.getType() );
        maintenanceResponse.description( entity.getDescription() );
        maintenanceResponse.cost( entity.getCost() );
        maintenanceResponse.recordDate( entity.getRecordDate() );
        maintenanceResponse.nextDueDate( entity.getNextDueDate() );
        maintenanceResponse.nextDueOdometer( entity.getNextDueOdometer() );
        maintenanceResponse.odometerAtMaintenance( entity.getOdometerAtMaintenance() );

        return maintenanceResponse.build();
    }

    private Long entityVehicleId(MaintenanceRecord maintenanceRecord) {
        if ( maintenanceRecord == null ) {
            return null;
        }
        Vehicle vehicle = maintenanceRecord.getVehicle();
        if ( vehicle == null ) {
            return null;
        }
        Long id = vehicle.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
