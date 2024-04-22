package com.booking.agency.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

import static com.booking.agency.constants.Constants.UNDERSCORE;

@Entity
@Table(name = "slot")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SlotEntity {
    @Id
    private String id;

    @Column(nullable = false)
    private int startHour;

    @Column(nullable = false)
    private int endHour;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    private LocalDate date;

    SlotEntity(SlotEntityBuilder builder) {
        this.id = builder.id;
        this.startHour = builder.startHour;
        this.endHour = builder.endHour;
        this.date = builder.date;
    }

    public static SlotEntityBuilder builder() {
        return new SlotEntityBuilder();
    }

    public static class SlotEntityBuilder {
        public SlotEntityBuilder id() {
            this.id = this.date + UNDERSCORE + this.startHour + UNDERSCORE + this.endHour;
            return this;
        }
        public SlotEntity build() {
            return new SlotEntity(this);
        }
    }
}
