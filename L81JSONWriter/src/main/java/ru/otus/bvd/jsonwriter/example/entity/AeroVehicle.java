package ru.otus.bvd.jsonwriter.example.entity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by vadim on 05.08.17.
 */
public class AeroVehicle {
    private String boardNumer;
    private ModelKind model;

    private List<Device> devices = new LinkedList<>();
    private List<Parameter> parameters = new LinkedList<>();

    public AeroVehicle(String boardNumer, ModelKind model) {
        this.boardNumer = boardNumer;
        this.model = model;
    }


    public List<Device> getDevices() {
        return devices;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public int[] intArray = {1,2,3};
    
    public String[][] strArray = { {"a","b","c"}, {"s","d","f"} };  
    
}
