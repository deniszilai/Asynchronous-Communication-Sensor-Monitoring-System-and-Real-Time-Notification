package com.example.consumer.Data;

import java.time.LocalDateTime;
import java.util.Date;

public class SensorData {
    public String timestamp;
    public String deviceId;
    public String value;

    public SensorData(String timestamp, String deviceId, String value) {
        this.timestamp = timestamp;
        this.deviceId = deviceId;
        this.value = value;
    }

    public SensorData() {

    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "SensorData{" +
                "timestamp='" + timestamp + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
