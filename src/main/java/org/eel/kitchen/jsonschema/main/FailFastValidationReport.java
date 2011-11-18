/*
 * Copyright (c) 2011, Francis Galiegue <fgaliegue@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the Lesser GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.eel.kitchen.jsonschema.main;

import java.util.Collections;
import java.util.List;

/**
 * A {@link ValidationReport} which will throw {@link
 * JsonValidationFailureException} instead of failing or reporting errors
 * (used if validation mode is set to {@link ValidationFeature#FAIL_FAST})
 */
public final class FailFastValidationReport
    extends ValidationReport
{
    @Override
    public List<String> getMessages()
    {
        return Collections.emptyList();
    }

    @Override
    public void message(final String message)
    {
    }

    @Override
    public void fail()
        throws JsonValidationFailureException
    {
        throw new JsonValidationFailureException();
    }

    @Override
    public void fail(final String message)
        throws JsonValidationFailureException
    {
        throw new JsonValidationFailureException(message);
    }

    @Override
    public void error(final String message)
        throws JsonValidationFailureException
    {
        throw new JsonValidationFailureException(message);
    }

    @Override
    public void mergeWith(final ValidationReport other)
    {
    }
}