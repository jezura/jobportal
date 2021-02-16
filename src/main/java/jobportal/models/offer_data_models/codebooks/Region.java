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
package jobportal.models.offer_data_models.codebooks;

import jobportal.models.base.CodedEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 */
@Entity
@Table(name = "regions")
public class Region extends CodedEntity {

    @Column(name = "kodNuts3")
    @Size(min = 2, max = 32, message = "kodNuts3 must be between 2 and 32 characters long")
    private String kodNuts3;

    public Region() {
    }

    public Region(@Size(min = 2, max = 32, message = "kodNuts3 must be between 2 and 32 characters long") String kodNuts3) {
        this.kodNuts3 = kodNuts3;
    }

    public String getKodNuts3() {
        return kodNuts3;
    }

    public void setKodNuts3(String kodNuts3) {
        this.kodNuts3 = kodNuts3;
    }
}