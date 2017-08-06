package ru.otus.bvd.jsonwriter.example.entity;

/**
 * Created by vadim on 05.08.17.
 */
public class AeroVehicleUtils {
    private AeroVehicleUtils() {}

    public static AeroVehicle createCopter() {
        AeroVehicle copter = new AeroVehicle("C123", ModelKind.COPTER);
        copter.getDevices().add( new Device("Battery") );
        copter.getDevices().add( new Device("Compass") );
        copter.getParameters().add( new Parameter("maxSpeed", 20.0) );
        copter.getParameters().add( new Parameter("maxHeight", 10) );

        return copter;
    }

    public static AeroVehicle createPlane() {
        AeroVehicle plane = new AeroVehicle("C123", ModelKind.COPTER);
        plane.getDevices().add( new Device("Battery") );
        plane.getDevices().add( new Device("Compass") );
        plane.getDevices().add( new Device("Parachute") );
        plane.getParameters().add( new Parameter("maxSpeed", 60.0) );
        plane.getParameters().add( new Parameter("maxHeight", 100) );

        return plane;
    }

}
