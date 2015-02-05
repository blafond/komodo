/*
 * JBoss, Home of Professional Open Source.
 * See the COPYRIGHT.txt file distributed with this work for information
 * regarding copyright ownership.  Some portions may be licensed
 * to Red Hat, Inc. under one or more contributor license agreements.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301 USA.
 */
package org.komodo.repository.search;

import org.komodo.spi.constants.StringConstants;
import org.komodo.utils.ArgCheck;

/**
 * A from type with its requisite alias
 */
class FromType {

    private final String alias;

    private final String type;

    /**
     * Constructor
     * @param type the type
     * @param alias the alias
     */
    public FromType(String type, String alias) {
        ArgCheck.isNotEmpty(type);
        if (alias == null)
            alias = StringConstants.EMPTY_STRING;

        this.type = type;
        this.alias = alias;
    }

    /**
     * @return the alias
     */
    public String getAlias() {
        return this.alias;
    }

    /**
     * @return the type
     */
    public String getType() {
        return this.type;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.alias == null) ? 0 : this.alias.hashCode());
        result = prime * result + ((this.type == null) ? 0 : this.type.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        FromType other = (FromType)obj;
        if (this.alias == null) {
            if (other.alias != null)
                return false;
        } else if (!this.alias.equals(other.alias))
            return false;
        if (this.type == null) {
            if (other.type != null)
                return false;
        } else if (!this.type.equals(other.type))
            return false;
        return true;
    }
}