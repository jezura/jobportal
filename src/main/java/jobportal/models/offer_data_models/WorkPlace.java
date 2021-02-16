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
package jobportal.models.offer_data_models;

import jobportal.models.offer_data_models.codebooks.District;
import jobportal.models.offer_data_models.codebooks.Village;
import jobportal.models.offer_data_models.codebooks.VillagePart;

import javax.persistence.*;
import java.util.Set;

/**
 *
 */
@Entity
@Table(name = "workplaces")
public class WorkPlace {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    @JoinColumn(name = "name")
    private String name;

    @JoinColumn(name = "house_number")
    private String houseNumber;

    @JoinColumn(name = "orientation_number")
    private String orientationNumber;

    @JoinColumn(name = "zip_code")
    private String zipCode;

    @JoinColumn(name = "street")
    private String street;

    @JoinColumn(name = "email")
    private String email;

    @JoinColumn(name = "phone")
    private String phone;

    @ManyToOne
    @JoinColumn(name = "village_id")
    private Village village;

    @ManyToOne
    @JoinColumn(name = "village_part_id")
    private VillagePart villagePart;

    @ManyToMany
    @JoinTable(name = "workplace_district",
            joinColumns = @JoinColumn(name = "workplace_id"),
            inverseJoinColumns = @JoinColumn(name = "district_id"))
    private Set<District> districts;

    public WorkPlace() {
    }

    public WorkPlace(int id, String name, String houseNumber, String orientationNumber, String zipCode, String street, String email, String phone, Village village, VillagePart villagePart, Set<District> districts) {
        this.id = id;
        this.name = name;
        this.houseNumber = houseNumber;
        this.orientationNumber = orientationNumber;
        this.zipCode = zipCode;
        this.street = street;
        this.email = email;
        this.phone = phone;
        this.village = village;
        this.villagePart = villagePart;
        this.districts = districts;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getOrientationNumber() {
        return orientationNumber;
    }

    public void setOrientationNumber(String orientationNumber) {
        this.orientationNumber = orientationNumber;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Village getVillage() {
        return village;
    }

    public void setVillage(Village village) {
        this.village = village;
    }

    public VillagePart getVillagePart() {
        return villagePart;
    }

    public void setVillagePart(VillagePart villagePart) {
        this.villagePart = villagePart;
    }

    public Set<District> getDistricts() {
        return districts;
    }

    public void setDistricts(Set<District> districts) {
        this.districts = districts;
    }
}