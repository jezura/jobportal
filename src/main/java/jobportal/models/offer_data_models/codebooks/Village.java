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

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 *
 */
@Entity
@Table(name = "villages")
public class Village extends CodedEntity {

    @ManyToOne
    @NotNull(message = "Choose district")
    @JoinColumn(name = "district_id")
    private District district;

    public Village() {
    }

    public Village(@NotNull(message = "Choose district") District district) {
        this.district = district;
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }
}