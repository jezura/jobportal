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
package jobportal.models.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Size;


/**
 * Simple JavaBean domain object adds a code property to <code>CodedEntity</code>. Used as a base class for objects
 * needing these properties.
 *
 * @author Jaroslav Ježek
 */
@MappedSuperclass
public class CodedEntity {

    @Id
    @Column(name = "id", length = 60)
    @Size(min = 1, max = 60, message = "ID must be between 1 and 60 characters long")
    private String id;

    @Column(name = "name")
    @Size(min = 2, max = 500, message = "Name must be between 2 and 500 characters long")
    private String name;

    @Column(name = "code")
    @Size(min = 1, max = 32, message = "Code must be between 1 and 32 characters long")
    private String code;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return this.getCode();
    }
}