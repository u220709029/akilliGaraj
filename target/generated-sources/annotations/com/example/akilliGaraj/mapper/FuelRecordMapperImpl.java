package com.example.akilliGaraj.mapper;

import com.example.akilliGaraj.dtos.request.FuelRecordCreateRequest;
import com.example.akilliGaraj.dtos.response.FuelRecordResponse;
import com.example.akilliGaraj.entities.FuelRecord;
import com.example.akilliGaraj.entities.Vehicle;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-10-09T22:37:29+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class FuelRecordMapperImpl implements FuelRecordMapper {

    @Override
    public FuelRecord toEntity(FuelRecordCreateRequest request) {
        if ( request == null ) {
            return null;
        }

        FuelRecord.FuelRecordBuilder fuelRecord = FuelRecord.builder();

        fuelRecord.liters( request.getLiters() );
        fuelRecord.pricePerLiter( request.getPricePerLiter() );
        fuelRecord.odometerAtFill( request.getOdometerAtFill() );
        fuelRecord.isFullTank( request.getIsFullTank() );
        fuelRecord.fillDate( request.getFillDate() );

        return fuelRecord.build();
    }

    @Override
    public FuelRecordResponse toResponse(FuelRecord entity) {
        if ( entity == null ) {
            return null;
        }

        FuelRecordResponse.FuelRecordResponseBuilder fuelRecordResponse = FuelRecordResponse.builder();

        fuelRecordResponse.vehicleId( entityVehicleId( entity ) );
        fuelRecordResponse.id( entity.getId() );
        fuelRecordResponse.liters( entity.getLiters() );
        fuelRecordResponse.pricePerLiter( entity.getPricePerLiter() );
        fuelRecordResponse.totalCost( entity.getTotalCost() );
        fuelRecordResponse.odometerAtFill( entity.getOdometerAtFill() );
        fuelRecordResponse.isFullTank( entity.getIsFullTank() );
        fuelRecordResponse.fillDate( entity.getFillDate() );

        return fuelRecordResponse.build();
    }

    private Long entityVehicleId(FuelRecord fuelRecord) {
        if ( fuelRecord == null ) {
            return null;
        }
        Vehicle vehicle = fuelRecord.getVehicle();
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
