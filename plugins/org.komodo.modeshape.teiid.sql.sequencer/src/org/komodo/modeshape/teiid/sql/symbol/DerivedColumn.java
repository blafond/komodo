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

package org.komodo.modeshape.teiid.sql.symbol;

import org.komodo.modeshape.teiid.cnd.TeiidSqlLexicon;
import org.komodo.modeshape.teiid.parser.LanguageVisitor;
import org.komodo.modeshape.teiid.parser.ITeiidParser;
import org.komodo.modeshape.teiid.sql.lang.ASTNode;
import org.komodo.spi.query.sql.symbol.IDerivedColumn;

/**
 *
 */
public class DerivedColumn extends ASTNode implements IDerivedColumn<LanguageVisitor> {

    /**
     * @param p teiid parser
     * @param id node type id
     */
    public DerivedColumn(ITeiidParser p, int id) {
        super(p, id);
    }

    public String getAlias() {
        Object property = getProperty(TeiidSqlLexicon.DerivedColumn.ALIAS_PROP_NAME);
        return property == null ? null : property.toString();
    }

    public void setAlias(String alias) {
        setProperty(TeiidSqlLexicon.DerivedColumn.ALIAS_PROP_NAME, alias);
    }

    public Expression getExpression() {
        return getChildforIdentifierAndRefType(
                                               TeiidSqlLexicon.DerivedColumn.EXPRESSION_REF_NAME, Expression.class);
    }

    public void setExpression(Expression expression) {
        setChild(TeiidSqlLexicon.DerivedColumn.EXPRESSION_REF_NAME, expression);
    }

    public boolean isPropagateName() {
        Object property = getProperty(TeiidSqlLexicon.DerivedColumn.PROPAGATE_NAME_PROP_NAME);
        return property == null ? false : Boolean.parseBoolean(property.toString());
    }

    public void setPropagateName(boolean propogateName) {
        setProperty(TeiidSqlLexicon.DerivedColumn.PROPAGATE_NAME_PROP_NAME, propogateName);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((this.getAlias() == null) ? 0 : this.getAlias().hashCode());
        result = prime * result + ((this.getExpression() == null) ? 0 : this.getExpression().hashCode());
        result = prime * result + (this.isPropagateName() ? 1231 : 1237);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        DerivedColumn other = (DerivedColumn)obj;
        if (this.getAlias() == null) {
            if (other.getAlias() != null)
                return false;
        } else if (!this.getAlias().equals(other.getAlias()))
            return false;
        if (this.getExpression() == null) {
            if (other.getExpression() != null)
                return false;
        } else if (!this.getExpression().equals(other.getExpression()))
            return false;
        if (this.isPropagateName() != other.isPropagateName())
            return false;
        return true;
    }

    @Override
    public void acceptVisitor(LanguageVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public DerivedColumn clone() {
        DerivedColumn clone = new DerivedColumn(this.getTeiidParser(), this.getId());

        if (getExpression() != null)
            clone.setExpression(getExpression().clone());
        clone.setPropagateName(isPropagateName());
        if (getAlias() != null)
            clone.setAlias(getAlias());

        return clone;
    }

}
