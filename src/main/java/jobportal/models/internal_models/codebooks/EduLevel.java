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
package jobportal.models.internal_models.codebooks;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 */
@Entity
@Table(name = "edu_levels")
public class EduLevel {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @NotNull(message = "Choose ANN code")
    @Column(name = "ann_code")
    private int annCode;

    @Column(name = "name")
    @Size(min = 2, max = 500, message = "Name must be between 2 and 500 characters long")
    private String name;

    @Column(name = "pretty_name")
    @Size(min = 2, max = 500, message = "Pretty name must be between 2 and 500 characters long")
    private String prettyName;

    public EduLevel(){}

    public EduLevel(int id, @NotNull(message = "Choose ANN code") int annCode, @Size(min = 2, max = 500, message = "Name must be between 2 and 500 characters long") String name, @Size(min = 2, max = 500, message = "Pretty name must be between 2 and 500 characters long") String prettyName) {
        this.id = id;
        this.annCode = annCode;
        this.name = name;
        this.prettyName = prettyName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAnnCode() {
        return annCode;
    }

    public void setAnnCode(int annCode) {
        this.annCode = annCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrettyName() {
        return prettyName;
    }

    public void setPrettyName(String prettyName) {
        this.prettyName = prettyName;
    }
}