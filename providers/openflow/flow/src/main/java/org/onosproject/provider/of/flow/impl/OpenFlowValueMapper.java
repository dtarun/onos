/*
 * Copyright 2015 Open Networking Laboratory
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.onosproject.provider.of.flow.impl;

import com.google.common.collect.BiMap;
import com.google.common.collect.EnumHashBiMap;
import org.onosproject.net.ChannelSpacing;
import org.onosproject.net.GridType;
import org.onosproject.net.OchSignalType;

/**
 * Collection of helper methods to convert protocol agnostic models to values used in OpenFlow spec.
 */
final class OpenFlowValueMapper {

    // prohibit instantiation
    private OpenFlowValueMapper() {}

    private static final BiMap<GridType, Byte> GRID_TYPES = EnumHashBiMap.create(GridType.class);
    static {
        // See ONF "Optical Transport Protocol Extensions Version 1.0" for the following values
        GRID_TYPES.put(GridType.DWDM, (byte) 1); // OFPGRIDT_DWDM of enum ofp_grid_type
        GRID_TYPES.put(GridType.CWDM, (byte) 2); // OFPGRIDT_CWDM of enum ofp_grid_type
        GRID_TYPES.put(GridType.FLEX, (byte) 3); // OFPGRIDT_FLEX of enum ofp_grid_type
    }

    private static final BiMap<ChannelSpacing, Byte> CHANNEL_SPACING = EnumHashBiMap.create(ChannelSpacing.class);
    static {
        // See ONF "Optical Transport Protocol Extensions Version 1.0" for the following values
        CHANNEL_SPACING.put(ChannelSpacing.CHL_100GHZ, (byte) 1);   // OFPCS_100GHZ of enum ofp_chl_spacing
        CHANNEL_SPACING.put(ChannelSpacing.CHL_50GHZ, (byte) 2);    // OFPCS_50GHZ of enum ofp_chl_spacing
        CHANNEL_SPACING.put(ChannelSpacing.CHL_25GHZ, (byte) 3);    // OFPCS_25GHZ of enum ofp_chl_spacing
        CHANNEL_SPACING.put(ChannelSpacing.CHL_12P5GHZ, (byte) 4);  // OFPCS_12P5GHZ of enum ofp_chl_spacing
        CHANNEL_SPACING.put(ChannelSpacing.CHL_6P25GHZ, (byte) 5);  // OFPCS_6P25GHZ of enum ofp_chl_spacing
    }

    private static final BiMap<OchSignalType, Byte> OCH_SIGNAL_TYPES = EnumHashBiMap.create(OchSignalType.class);
    static {
        // See ONF "Optical Transport Protocol Extensions Version 1.0" for the following values
        OCH_SIGNAL_TYPES.put(OchSignalType.FIXED_GRID, (byte) 1); // OFPOCHT_FIX_GRID of enum ofp_och_signal_type
        OCH_SIGNAL_TYPES.put(OchSignalType.FLEX_GRID, (byte) 2);  // OFPOCHT_FLEX_GRID of enum ofp_och_signal_type
    }

    /**
     * Looks up the specified input value to the corresponding value with the specified map.
     *
     * @param map bidirectional mapping
     * @param input input value
     * @param cls class of output value
     * @param <I> type of input value
     * @param <O> type of output value
     * @return the corresponding value stored in the specified map
     * @throws NoMappingFoundException if no corresponding value is found
     */
    private static <I, O> O lookup(BiMap<I, O> map, I input, Class<O> cls) {
        if (!map.containsKey(input)) {
            throw new NoMappingFoundException(input, cls);
        }

        return map.get(input);
    }

    /**
     * Looks up the corresponding byte value defined in
     * ONF "Optical Transport Protocol Extensions Version 1.0"
     * from the specified {@link GridType} instance.
     *
     * @param type grid type
     * @return the byte value corresponding to the specified grid type
     * @throws NoMappingFoundException if the specified grid type is not found
     */
    static byte lookupGridType(GridType type) {
        return lookup(GRID_TYPES, type, Byte.class);
    }

    /**
     * Looks up the corresponding {@link GridType} instance
     * from the specified byte value for grid type
     * defined in ONF "Optical Transport Protocol Extensions Version 1.0".
     *
     * @param type byte value as grid type defined the spec
     * @return the corresponding GridType instance
     */
    static GridType lookupGridType(byte type) {
        return lookup(GRID_TYPES.inverse(), type, GridType.class);
    }

    /**
     * Looks up the corresponding byte value for channel spacing defined in
     * ONF "Optical Transport Protocol Extensions Version 1.0"
     * from the specified {@link ChannelSpacing} instance.
     *
     * @param spacing channel spacing
     * @return byte value corresponding to the specified channel spacing
     * @throws NoMappingFoundException if the specified channel spacing is not found
     */
    static byte lookupChannelSpacing(ChannelSpacing spacing) {
        return lookup(CHANNEL_SPACING, spacing, Byte.class);
    }

    /**
     * Looks up the corresponding {@link ChannelSpacing} instance
     * from the specified byte value for channel spacing
     * defined in ONF "Optical Transport Protocol Extensions Version 1.0".
     *
     * @param spacing byte value as channel spacing defined the spec
     * @return the corresponding ChannelSpacing instance
     * @throws NoMappingFoundException if the specified channel spacing is not found
     */
    static ChannelSpacing lookupChannelSpacing(byte spacing) {
        return lookup(CHANNEL_SPACING.inverse(), spacing, ChannelSpacing.class);
    }

    /**
     * Looks up the corresponding byte value for Och signal type defined in
     * ONF "Optical Transport Protocol Extensions Version 1.0"
     * from the specified {@link OchSignalType} instance.
     *
     * @param signalType optical signal type
     * @return byte value corresponding to the specified OCh signal type
     * @throws NoMappingFoundException if the specified Och signal type is not found
     */
    static byte lookupOchSignalType(OchSignalType signalType) {
        return lookup(OCH_SIGNAL_TYPES, signalType, Byte.class);
    }

    /**
     * Looks up the the corresponding {@link OchSignalType} instance
     * from the specified byte value for Och signal type defined in
     * ONF "Optical Transport Protocol Extensions Version 1.0".
     *
     * @param signalType byte value as Och singal type defined the spec
     * @return the corresponding OchSignalType instance
     * @throws NoMappingFoundException if the specified Och signal type is not found
     */
    static OchSignalType lookupOchSignalType(byte signalType) {
        return lookup(OCH_SIGNAL_TYPES.inverse(), signalType, OchSignalType.class);
    }
}
