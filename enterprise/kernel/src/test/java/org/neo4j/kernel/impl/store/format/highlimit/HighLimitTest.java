/*
 * Copyright (c) 2002-2016 "Neo Technology,"
 * Network Engine for Objects in Lund AB [http://neotechnology.com]
 *
 * This file is part of Neo4j.
 *
 * Neo4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.neo4j.kernel.impl.store.format.highlimit;

import org.junit.Ignore;
import org.junit.Test;

import org.neo4j.helpers.collection.MapUtil;
import org.neo4j.kernel.configuration.Config;
import org.neo4j.kernel.impl.logging.NullLogService;
import org.neo4j.kernel.impl.store.format.InternalRecordFormatSelector;
import org.neo4j.kernel.impl.store.format.RecordFormats;
import org.neo4j.kernel.impl.store.format.lowlimit.LowLimit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class HighLimitTest
{
    @Ignore("Waiting on store migration work")
    @Test
    public void shouldResolveHighLimitsRecordFormat() throws Exception
    {
        Config config = new Config( MapUtil.stringMap( "record_format", "highlimit" ) );
        RecordFormats formatSelector = InternalRecordFormatSelector.select( config, NullLogService.getInstance() );
        assertEquals( HighLimit.RECORD_FORMATS.storeVersion(), formatSelector.storeVersion() );
    }

    @Test
    public void shouldResolveCommunityRecordFormat() throws Exception
    {
        Config config = new Config( MapUtil.stringMap( "record_format", "community" ) );
        RecordFormats formatSelector = InternalRecordFormatSelector.select( config, NullLogService.getInstance() );
        assertEquals( LowLimit.RECORD_FORMATS.storeVersion(), formatSelector.storeVersion() );
    }

    @Ignore("Waiting on store migration work")
    @Test
    public void shouldResolveNoRecordFormatToHighLimitDefault() throws Exception
    {
        Config config = Config.empty();
        RecordFormats formatSelector = InternalRecordFormatSelector.select( config, NullLogService.getInstance() );
        assertEquals( HighLimit.RECORD_FORMATS.storeVersion(), formatSelector.storeVersion() );
    }

    @Test
    public void shouldNotResolveNoneExistingRecordFormat() throws Exception
    {
        Config config = new Config( MapUtil.stringMap( "record_format", "notAValidRecordFormat" ) );
        try
        {
            RecordFormats formatSelector = InternalRecordFormatSelector.select( config, NullLogService.getInstance() );
            fail( "Should not be possible to specify non-existing format" );
        }
        catch ( IllegalArgumentException ignored )
        {
            // Success
        }
    }
}