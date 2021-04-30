package me.rockintuna.tpiratesdemo.store;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @NotNull
    public String name;
    @NotNull
    public String owner;
    public String description;
    public int level;
    public String address;
    public String phone;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    public List<BusinessTime> businessTimes = new ArrayList<>();

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    public List<HoliDay> holidays = new ArrayList<>();

    public Store(StoreDto.Info storeDto) {
        this.name = storeDto.getName();
        this.owner = storeDto.getOwner();
        this.description = storeDto.getDescription();
        this.level = storeDto.getLevel();
        this.address = storeDto.getAddress();
        this.phone = storeDto.getPhone();
        setBusinessTimes(storeDto.getBusinessTimes());
    }

    public void setBusinessTimes(List<BusinessTime> businessTimes) {
        for (BusinessTime businessTime : businessTimes) {
            businessTime.setStore(this);
        }
        this.businessTimes = businessTimes;
    }

    public void addHolidays(List<HoliDay> holidays) {
        this.holidays.addAll(holidays);
    }

    public String getBusinessStatusByDate(LocalDate date) {
        for (HoliDay holiday : this.holidays) {
            if ( holiday.isHoliday(date) ) {
                return "HOLIDAY";
            }
        }

        BusinessTime businessTime = getBusinessTimeWithDay(this.businessTimes,date.getDayOfWeek());

        if ( businessTime == null ) {
            return "CLOSE";
        }

        DateTimeFormatter myFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime open = LocalTime.parse(businessTime.getOpen(), myFormatter);
        LocalTime close = LocalTime.parse(businessTime.getClose(), myFormatter);

        if (date.equals(LocalDate.now())) {
            if ( open.isBefore(LocalTime.now())
                    && (close.isAfter(LocalTime.now())
                    || close.equals(LocalTime.parse("00:00"))) ) {
                return "OPEN";
            } else {
                return "CLOSE";
            }
        } else {
            return "CLOSE";
        }
    }

    private BusinessTime getBusinessTimeWithDay(List<BusinessTime> businessTimes, DayOfWeek day) {
        List<BusinessTime> businessTime = businessTimes.stream()
                .filter(time ->
                        time.getDay().toUpperCase().equals(day.toString()))
                .collect(Collectors.toList());
        if ( !businessTime.isEmpty() ) {
            return businessTime.get(0);
        } else {
            return null;
        }
    }

    public List<BusinessTime> getBusiness3Days() {
        List<BusinessTime> businessTimes = new ArrayList<>();
        BusinessTime businessTime;
        for (int i = 0; i < 3; i++) {
            businessTime = getBusinessTimeWithDay(this.businessTimes, LocalDate.now().getDayOfWeek().plus(i));
            if ( businessTime != null ) {
                businessTime.setStatus(getBusinessStatusByDate(LocalDate.now().plusDays(i)));
                businessTimes.add(businessTime);
            }
        }
        return businessTimes;
    }

}
