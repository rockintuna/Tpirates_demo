package me.rockintuna.tpiratesdemo.store;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class HoliDay {
    public HoliDay(LocalDate date, Store store) {
        this.date = date;
        this.store = store;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    public LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    @JsonIgnore
    public Store store;

    public boolean isHoliday(LocalDate date) {
        return this.getDate().equals(date);
    }
}
