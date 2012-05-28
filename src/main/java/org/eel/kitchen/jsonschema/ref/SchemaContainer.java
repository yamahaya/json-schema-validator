/*
 * Copyright (c) 2012, Francis Galiegue <fgaliegue@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the Lesser GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * Lesser GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.eel.kitchen.jsonschema.ref;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.eel.kitchen.jsonschema.main.JsonSchemaException;
import org.eel.kitchen.util.JsonPointer;

public final class SchemaContainer
{
    private final JsonNode schema;

    private final JsonRef locator;

    public SchemaContainer(final JsonNode schema)
        throws JsonSchemaException
    {
        locator = JsonRef.fromNode(schema, "id");
        this.schema = cleanup(schema);

        if (!locator.isAbsolute() && !locator.isEmpty())
            throw new JsonSchemaException("a parent schema's id must be "
                + "absolute");

        if (!locator.isNormalized())
            throw new JsonSchemaException("a parent schema's id must be "
                + "normalized");
    }

    // For test purposes only...
    JsonNode getSchema()
    {
        return schema;
    }

    public JsonRef getLocator()
    {
        return locator;
    }

    public boolean contains(final JsonRef ref)
    {
        final JsonRef tmp = locator.resolve(ref);

        return locator.getLocator().equals(tmp.getLocator());
    }

    public JsonNode lookupFragment(final String fragment)
        throws JsonSchemaException
    {
        JsonNode ret;

        try {
            ret = new JsonPointer(fragment).getPath(schema);
        } catch (JsonSchemaException ignored) {
            ret = lookupById(schema, fragment);
        }

        if (ret.isMissingNode())
            throw new JsonSchemaException('"' + fragment + "\" does not match"
                + " any path/id in schema");

        return ret;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (o == null)
            return false;
        if (this == o)
            return true;
        if (getClass() != o.getClass())
            return false;

        final SchemaContainer that = (SchemaContainer) o;

        return locator.equals(that.locator)
            && schema.equals(that.schema);
    }

    @Override
    public int hashCode()
    {
        return 31 * locator.hashCode() + schema.hashCode();
    }

    private JsonNode cleanup(final JsonNode schema)
    {
        final ObjectNode ret = schema.deepCopy();

        ret.remove("id");
        return ret;
    }

    private JsonNode lookupById(final JsonNode schema, final String fragment)
    {
        if (!schema.isObject())
            return MissingNode.getInstance();

        try {
            final JsonRef ref = JsonRef.fromNode(schema, "id");
            if (ref.getFragment().equals(fragment))
                return schema;
        } catch (JsonSchemaException ignored) {
            // Do nothing, and go on with children
        }

        JsonNode ret;

        for (final JsonNode node: schema) {
            ret = lookupById(node, fragment);
            if (!ret.isMissingNode())
                return ret;
        }

        return MissingNode.getInstance();
    }
}