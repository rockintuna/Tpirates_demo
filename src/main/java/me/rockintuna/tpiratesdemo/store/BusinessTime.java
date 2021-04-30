package me.rockintuna.tpiratesdemo.store;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.rockintuna.tpiratesdemo.config.exceptions.BusinessTimeInvalidException;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class BusinessTime {

    public BusinessTime(String day, String open, String close) {
        this.day = day;
        if ( open.equals(close) ) {
            throw new BusinessTimeInvalidException();
        }
        this.open = open;
        this.close = close;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    public String day;
    public String open;
    public String close;
    public String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    @JsonIgnore
    public Store store;

}
