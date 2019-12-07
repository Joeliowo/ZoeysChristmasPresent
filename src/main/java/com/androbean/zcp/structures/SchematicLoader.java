package com.androbean.zcp.structures;

import mod.akrivus.kagic.init.KAGIC;
import mod.heimrarnadalr.kagic.world.structure.Schematic;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class SchematicLoader {

    public static StructureData loadSchematic(String schematic) {
        NBTTagCompound schematicData;
        try {
            schematicData = CompressedStreamTools.readCompressed(SchematicLoader.class.getResourceAsStream(schematic));
        } catch (Exception e) {
            KAGIC.instance.chatInfoMessage("Failed to load schematic " + schematic + "; trying uncompressed read");
            try {
                File file = new File(SchematicLoader.class.getResource(schematic).toExternalForm());
                schematicData = CompressedStreamTools.read(file);
            } catch (Exception e1) {
                KAGIC.instance.chatInfoMessage("Failed to load schematic " + schematic);
                return null;
            }
        }

        Map<BlockPos, IBlockState> structureBlocks = new HashMap<BlockPos, IBlockState>();

        boolean schematicaFormat = false;
        short length = schematicData.getShort("Length");
        short width = schematicData.getShort("Width");
        short height = schematicData.getShort("Height");
        byte[] blocks = schematicData.getByteArray("Blocks");
        byte[] additionalBlocks = null;
        HashMap<Short, String> additionalBlockNames = new HashMap<Short, String>();
        if (schematicData.hasKey("AddBlocks") && schematicData.hasKey("SchematicaMapping")) {
            schematicaFormat = true;
            byte[] addBlocks = schematicData.getByteArray("AddBlocks");
            additionalBlocks = new byte[addBlocks.length * 2];
            for (int i = 0; i < addBlocks.length; ++i) {
                additionalBlocks[i * 2] = (byte) ((addBlocks[i] >> 4) & 0xF);
                additionalBlocks[i * 2 + 1] = (byte) (addBlocks[i] & 0xF);
            }

            NBTTagCompound blockNames = (NBTTagCompound) schematicData.getTag("SchematicaMapping");
            for (String name : blockNames.getKeySet()) {
                additionalBlockNames.put(blockNames.getShort(name), name);
            }
        }
        byte[] data = schematicData.getByteArray("Data");
        NBTTagList tileEntities = schematicData.getTagList("TileEntities", 10);
        NBTTagList entities = schematicData.getTagList("Entities", 10);

        //i = (y * Length + z) * Width + x
        for (short y = 0; y < height; ++y) {
            for (short z = 0; z < length; ++z) {
                for (short x = 0; x < width; ++x) {
                    try {
                        int index = (y * length + z) * width + x;
                        BlockPos pos = new BlockPos(x, y, z);
                        IBlockState blockState = null;
                        if (schematicaFormat && additionalBlocks != null) {
                            short blockID = (short) Byte.toUnsignedInt(blocks[index]);
                            blockID |= ((additionalBlocks[index] & 0xFF) << 8);
                            blockState = Block.getBlockFromName(additionalBlockNames.get(blockID)).getStateFromMeta(Byte.toUnsignedInt(data[index]));
                        } else {
                            blockState = Block.getBlockById(Byte.toUnsignedInt(blocks[index])).getStateFromMeta(Byte.toUnsignedInt(data[index]));
                        }
                        structureBlocks.put(pos, blockState);
                    } catch (Exception e) {
                        KAGIC.instance.chatInfoMessage("Unable to create block at " + x + ", " + y + ", " + z);
                        structureBlocks.put(new BlockPos(x, y, z), Blocks.HAY_BLOCK.getDefaultState());
                    }
                }
            }
        }
        return new StructureData(width, height, length, structureBlocks, tileEntities, entities, schematicaFormat);
    }
}
