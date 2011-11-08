/*
 * Copyright (c) 2011, Francis Galiegue <fgaliegue@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the Lesser GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.eel.kitchen.jsonschema.syntax;

import org.eel.kitchen.jsonschema.context.ValidationContext;
import org.eel.kitchen.util.NodeType;

/**
 * Abstract class for {@link SyntaxValidator} instances,
 * for which the only necessary validation is checking the type of its
 * argument. Most syntax validators inherit this.
 */
public abstract class SimpleSyntaxValidator
    extends SyntaxValidator
{
    /**
     * Constructor
     *
     * @param context the validation context
     * @param keyword the keyword to check
     * @param types the valid list of types for this keyword
     */
    protected SimpleSyntaxValidator(final ValidationContext context,
        final String keyword, final NodeType... types)
    {
        super(context, keyword, types);
    }

    @Override
    protected final void checkFurther()
    {
    }
}