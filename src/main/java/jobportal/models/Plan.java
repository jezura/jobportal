/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jobportal.models;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
  Working plan object - stores hours working plan info for 31 days of one month
 */
@Entity
@Table(name = "plans")
public class Plan extends BaseEntity {

    @ManyToOne
    @NotNull
    @JoinColumn(name = "contractor_id")
    private Contractor contractor;

    @Column(name = "month_number")
    @NotNull
    @Min(value = 1, message = "Minimum is 1")
    @Max(value = 12, message = "Maximum is 12")
    private int monthNumber;

    @Column(name = "day1")
    @NotNull
    @Min(value = 0, message = "Nemůžete pracovat méně než 0 hodin")
    @Max(value = 16, message = "Nelze pracovat více než 16 hodin denně")
private int day1;

    @Column(name = "day2")
    @NotNull
    @Min(value = 0, message = "Nemůžete pracovat méně než 0 hodin")
    @Max(value = 16, message = "Nelze pracovat více než 16 hodin denně")
private int day2;

    @Column(name = "day3")
    @NotNull
    @Min(value = 0, message = "Nemůžete pracovat méně než 0 hodin")
    @Max(value = 16, message = "Nelze pracovat více než 16 hodin denně")
private int day3;

    @Column(name = "day4")
    @NotNull
    @Min(value = 0, message = "Nemůžete pracovat méně než 0 hodin")
    @Max(value = 16, message = "Nelze pracovat více než 16 hodin denně")
private int day4;

    @Column(name = "day5")
    @NotNull
    @Min(value = 0, message = "Nemůžete pracovat méně než 0 hodin")
    @Max(value = 16, message = "Nelze pracovat více než 16 hodin denně")
private int day5;

    @Column(name = "day6")
    @NotNull
    @Min(value = 0, message = "Nemůžete pracovat méně než 0 hodin")
    @Max(value = 16, message = "Nelze pracovat více než 16 hodin denně")
private int day6;

    @Column(name = "day7")
    @NotNull
    @Min(value = 0, message = "Nemůžete pracovat méně než 0 hodin")
    @Max(value = 16, message = "Nelze pracovat více než 16 hodin denně")
private int day7;

    @Column(name = "day8")
    @NotNull
    @Min(value = 0, message = "Nemůžete pracovat méně než 0 hodin")
    @Max(value = 16, message = "Nelze pracovat více než 16 hodin denně")
private int day8;

    @Column(name = "day9")
    @NotNull
    @Min(value = 0, message = "Nemůžete pracovat méně než 0 hodin")
    @Max(value = 16, message = "Nelze pracovat více než 16 hodin denně")
private int day9;

    @Column(name = "day10")
    @NotNull
    @Min(value = 0, message = "Nemůžete pracovat méně než 0 hodin")
    @Max(value = 16, message = "Nelze pracovat více než 16 hodin denně")
private int day10;

    @Column(name = "day11")
    @NotNull
    @Min(value = 0, message = "Nemůžete pracovat méně než 0 hodin")
    @Max(value = 16, message = "Nelze pracovat více než 16 hodin denně")
private int day11;

    @Column(name = "day12")
    @NotNull
    @Min(value = 0, message = "Nemůžete pracovat méně než 0 hodin")
    @Max(value = 16, message = "Nelze pracovat více než 16 hodin denně")
private int day12;

    @Column(name = "day13")
    @NotNull
    @Min(value = 0, message = "Nemůžete pracovat méně než 0 hodin")
    @Max(value = 16, message = "Nelze pracovat více než 16 hodin denně")
private int day13;

    @Column(name = "day14")
    @NotNull
    @Min(value = 0, message = "Nemůžete pracovat méně než 0 hodin")
    @Max(value = 16, message = "Nelze pracovat více než 16 hodin denně")
private int day14;

    @Column(name = "day15")
    @NotNull
    @Min(value = 0, message = "Nemůžete pracovat méně než 0 hodin")
    @Max(value = 16, message = "Nelze pracovat více než 16 hodin denně")
private int day15;

    @Column(name = "day16")
    @NotNull
    @Min(value = 0, message = "Nemůžete pracovat méně než 0 hodin")
    @Max(value = 16, message = "Nelze pracovat více než 16 hodin denně")
private int day16;

    @Column(name = "day17")
    @NotNull
    @Min(value = 0, message = "Nemůžete pracovat méně než 0 hodin")
    @Max(value = 16, message = "Nelze pracovat více než 16 hodin denně")
private int day17;

    @Column(name = "day18")
    @NotNull
    @Min(value = 0, message = "Nemůžete pracovat méně než 0 hodin")
    @Max(value = 16, message = "Nelze pracovat více než 16 hodin denně")
private int day18;

    @Column(name = "day19")
    @NotNull
    @Min(value = 0, message = "Nemůžete pracovat méně než 0 hodin")
    @Max(value = 16, message = "Nelze pracovat více než 16 hodin denně")
private int day19;

    @Column(name = "day20")
    @NotNull
    @Min(value = 0, message = "Nemůžete pracovat méně než 0 hodin")
    @Max(value = 16, message = "Nelze pracovat více než 16 hodin denně")
private int day20;

    @Column(name = "day21")
    @NotNull
    @Min(value = 0, message = "Nemůžete pracovat méně než 0 hodin")
    @Max(value = 16, message = "Nelze pracovat více než 16 hodin denně")
private int day21;

    @Column(name = "day22")
    @NotNull
    @Min(value = 0, message = "Nemůžete pracovat méně než 0 hodin")
    @Max(value = 16, message = "Nelze pracovat více než 16 hodin denně")
private int day22;

    @Column(name = "day23")
    @NotNull
    @Min(value = 0, message = "Nemůžete pracovat méně než 0 hodin")
    @Max(value = 16, message = "Nelze pracovat více než 16 hodin denně")
private int day23;

    @Column(name = "day24")
    @NotNull
    @Min(value = 0, message = "Nemůžete pracovat méně než 0 hodin")
    @Max(value = 16, message = "Nelze pracovat více než 16 hodin denně")
private int day24;

    @Column(name = "day25")
    @NotNull
    @Min(value = 0, message = "Nemůžete pracovat méně než 0 hodin")
    @Max(value = 16, message = "Nelze pracovat více než 16 hodin denně")
private int day25;

    @Column(name = "day26")
    @NotNull
    @Min(value = 0, message = "Nemůžete pracovat méně než 0 hodin")
    @Max(value = 16, message = "Nelze pracovat více než 16 hodin denně")
private int day26;

    @Column(name = "day27")
    @NotNull
    @Min(value = 0, message = "Nemůžete pracovat méně než 0 hodin")
    @Max(value = 16, message = "Nelze pracovat více než 16 hodin denně")
private int day27;

    @Column(name = "day28")
    @NotNull
    @Min(value = 0, message = "Nemůžete pracovat méně než 0 hodin")
    @Max(value = 16, message = "Nelze pracovat více než 16 hodin denně")
private int day28;

    @Column(name = "day29")
    @NotNull
    @Min(value = 0, message = "Nemůžete pracovat méně než 0 hodin")
    @Max(value = 16, message = "Nelze pracovat více než 16 hodin denně")
private int day29;

    @Column(name = "day30")
    @NotNull
    @Min(value = 0, message = "Nemůžete pracovat méně než 0 hodin")
    @Max(value = 16, message = "Nelze pracovat více než 16 hodin denně")
private int day30;

    @Column(name = "day31")
    @NotNull
    @Min(value = 0, message = "Nemůžete pracovat méně než 0 hodin")
    @Max(value = 16, message = "Nelze pracovat více než 16 hodin denně")
private int day31;

    public Plan() {
    }

    public Plan(Contractor contractor, int monthNumber, int day1, int day2, int day3, int day4, int day5, int day6, int day7, int day8, int day9, int day10, int day11, int day12, int day13, int day14, int day15, int day16, int day17, int day18, int day19, int day20, int day21, int day22, int day23, int day24, int day25, int day26, int day27, int day28, int day29, int day30, int day31) {
        this.contractor = contractor;
        this.monthNumber = monthNumber;
        this.day1 = day1;
        this.day2 = day2;
        this.day3 = day3;
        this.day4 = day4;
        this.day5 = day5;
        this.day6 = day6;
        this.day7 = day7;
        this.day8 = day8;
        this.day9 = day9;
        this.day10 = day10;
        this.day11 = day11;
        this.day12 = day12;
        this.day13 = day13;
        this.day14 = day14;
        this.day15 = day15;
        this.day16 = day16;
        this.day17 = day17;
        this.day18 = day18;
        this.day19 = day19;
        this.day20 = day20;
        this.day21 = day21;
        this.day22 = day22;
        this.day23 = day23;
        this.day24 = day24;
        this.day25 = day25;
        this.day26 = day26;
        this.day27 = day27;
        this.day28 = day28;
        this.day29 = day29;
        this.day30 = day30;
        this.day31 = day31;
    }

    public Contractor getContractor() {
        return contractor;
    }

    public void setContractor(Contractor contractor) {
        this.contractor = contractor;
    }

    public int getMonthNumber() {
        return monthNumber;
    }

    public void setMonthNumber(int monthNumber) {
        this.monthNumber = monthNumber;
    }

    public int getDay1() {
        return day1;
    }

    public void setDay1(int day1) {
        this.day1 = day1;
    }

    public int getDay2() {
        return day2;
    }

    public void setDay2(int day2) {
        this.day2 = day2;
    }

    public int getDay3() {
        return day3;
    }

    public void setDay3(int day3) {
        this.day3 = day3;
    }

    public int getDay4() {
        return day4;
    }

    public void setDay4(int day4) {
        this.day4 = day4;
    }

    public int getDay5() {
        return day5;
    }

    public void setDay5(int day5) {
        this.day5 = day5;
    }

    public int getDay6() {
        return day6;
    }

    public void setDay6(int day6) {
        this.day6 = day6;
    }

    public int getDay7() {
        return day7;
    }

    public void setDay7(int day7) {
        this.day7 = day7;
    }

    public int getDay8() {
        return day8;
    }

    public void setDay8(int day8) {
        this.day8 = day8;
    }

    public int getDay9() {
        return day9;
    }

    public void setDay9(int day9) {
        this.day9 = day9;
    }

    public int getDay10() {
        return day10;
    }

    public void setDay10(int day10) {
        this.day10 = day10;
    }

    public int getDay11() {
        return day11;
    }

    public void setDay11(int day11) {
        this.day11 = day11;
    }

    public int getDay12() {
        return day12;
    }

    public void setDay12(int day12) {
        this.day12 = day12;
    }

    public int getDay13() {
        return day13;
    }

    public void setDay13(int day13) {
        this.day13 = day13;
    }

    public int getDay14() {
        return day14;
    }

    public void setDay14(int day14) {
        this.day14 = day14;
    }

    public int getDay15() {
        return day15;
    }

    public void setDay15(int day15) {
        this.day15 = day15;
    }

    public int getDay16() {
        return day16;
    }

    public void setDay16(int day16) {
        this.day16 = day16;
    }

    public int getDay17() {
        return day17;
    }

    public void setDay17(int day17) {
        this.day17 = day17;
    }

    public int getDay18() {
        return day18;
    }

    public void setDay18(int day18) {
        this.day18 = day18;
    }

    public int getDay19() {
        return day19;
    }

    public void setDay19(int day19) {
        this.day19 = day19;
    }

    public int getDay20() {
        return day20;
    }

    public void setDay20(int day20) {
        this.day20 = day20;
    }

    public int getDay21() {
        return day21;
    }

    public void setDay21(int day21) {
        this.day21 = day21;
    }

    public int getDay22() {
        return day22;
    }

    public void setDay22(int day22) {
        this.day22 = day22;
    }

    public int getDay23() {
        return day23;
    }

    public void setDay23(int day23) {
        this.day23 = day23;
    }

    public int getDay24() {
        return day24;
    }

    public void setDay24(int day24) {
        this.day24 = day24;
    }

    public int getDay25() {
        return day25;
    }

    public void setDay25(int day25) {
        this.day25 = day25;
    }

    public int getDay26() {
        return day26;
    }

    public void setDay26(int day26) {
        this.day26 = day26;
    }

    public int getDay27() {
        return day27;
    }

    public void setDay27(int day27) {
        this.day27 = day27;
    }

    public int getDay28() {
        return day28;
    }

    public void setDay28(int day28) {
        this.day28 = day28;
    }

    public int getDay29() {
        return day29;
    }

    public void setDay29(int day29) {
        this.day29 = day29;
    }

    public int getDay30() {
        return day30;
    }

    public void setDay30(int day30) {
        this.day30 = day30;
    }

    public int getDay31() {
        return day31;
    }

    public void setDay31(int day31) {
        this.day31 = day31;
    }
}