package me.rockintuna.tpiratesdemo.store;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StoreDto {

    @Getter
    @Setter
    public static class Info {
        public Long id;
        public String name;
        public String owner;
        public String description;
        public int level;
        public String address;
        public String phone;
        public List<BusinessTime> businessTimes = new ArrayList<>();
    }

    @Getter
    @Setter
    public static class HolidayRequest {
        public HolidayRequest(Long id, List<String> holidays) {
            this.id = id;
            this.holidays = holidays;
        }

        public Long id;
        public List<String> holidays = new ArrayList<>();
    }

    @Getter
    @Setter
    public static class ListResponse {
        public String name;
        public String description;
        public int level;
        public String businessStatus;

        public ListResponse(Store store) {
            this.name = store.getName();
            this.description = store.getDescription();
            this.level = store.getLevel();
            this.businessStatus = store.getBusinessStatusByDate(LocalDate.now());
        }
    }

    @Getter
    @Setter
    public static class DetailResponse {
        public Long id;
        public String name;
        public String description;
        public int level;
        public String address;
        public String phone;
        public List<BusinessTime> businessDays;

        public DetailResponse(Store store) {
            this.id = store.getId();
            this.name = store.getName();
            this.description = store.getDescription();
            this.level = store.getLevel();
            this.address = store.getAddress();
            this.phone = store.getPhone();
            this.businessDays = store.getBusiness3Days();
        }
    }


}
