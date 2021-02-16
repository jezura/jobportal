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
package jobportal.models.internal_models.user;

import jobportal.models.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@MappedSuperclass
public class Person extends BaseEntity {

    @Column(name = "first_name")
    @Pattern(regexp="^[a-zA-Z]{3,20}",message="Neplatné křestní jméno")
    private String firstName;

    @Column(name = "last_name")
    @Pattern(regexp="^[a-zA-Z]{3,20}",message="Neplatné příjmení")
    private String lastName;

    @Column(name = "email")
    @Pattern(regexp="^[a-zA-Z0-9]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,7}",message="Neplatný email/login")
    private String email;

    @Size(min = 5, message = "Password must be at least 5 characters long")
    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}