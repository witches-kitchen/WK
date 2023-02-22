package cf.witcheskitchen.common.blockentity;

import cf.witcheskitchen.api.block.entity.WKBlockEntity;
import cf.witcheskitchen.api.block.entity.WKBlockEntityWithInventory;
import cf.witcheskitchen.api.ritual.Ritual;
import cf.witcheskitchen.api.ritual.RitualCircle;
import cf.witcheskitchen.common.recipe.RitualRecipe;
import cf.witcheskitchen.common.registry.WKBlockEntityTypes;
import cf.witcheskitchen.common.registry.WKBlocks;
import cf.witcheskitchen.common.registry.WKRecipeTypes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Set;

public class GlyphBlockEntity extends WKBlockEntityWithInventory {
    public int progress = 0;
    public RitualRecipe ritualRecipe = null;


    public GlyphBlockEntity(BlockPos pos, BlockState state) {
        super(WKBlockEntityTypes.GLYPH, pos, state, 9);
    }

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void tick(World world, BlockPos blockPos, BlockState blockState, WKBlockEntity blockEntity) {
        if(ritualRecipe != null){
            progress++;
            if(progress > 0){
                Ritual rite = ritualRecipe.rite;
                if(progress < ritualRecipe.duration){
                    rite.tick(world, blockPos, blockState, ritualRecipe);
                }else{
                    rite.onEnd(world, pos, ritualRecipe);
                    resetRitual();
                }
            }
        }
    }

    private void resetRitual() {
        ritualRecipe = null;
        progress = 0;
    }

    public void onUse(World world, BlockState state, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        ItemStack handStack = player.getMainHandStack();

        if(handStack.isEmpty()){
            RitualRecipe ritualRecipeNoCircleCheck = world.getRecipeManager().listAllOfType(WKRecipeTypes.RITUAL_RECIPE_TYPE).stream().filter(recipe -> recipe.matches(this.manager, world)).findFirst().orElse(null);
            if(ritualRecipeNoCircleCheck != null){
                Set<RitualCircle> circle = ritualRecipeNoCircleCheck.circleSet;
                if(checkValidCircle(world, pos, circle)){
                    if(checkValidSacrifices(ritualRecipeNoCircleCheck, world)){
                        this.manager.clear();
                        ritualRecipe = ritualRecipeNoCircleCheck;
                        Ritual rite = ritualRecipe.rite;
                        rite.onStart(world, pos, player, ritualRecipe);
                    }
                }
            }
        }
    }

    private boolean checkValidSacrifices(RitualRecipe ritual, World world) {
        if(ritual.sacrifices.isEmpty()){
            return true;
        }
        //TODO
        return false;
    }

    private boolean checkValidCircle(World world, BlockPos pos, Set<RitualCircle> circleSet) {
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for(RitualCircle circle : circleSet){
            if(circle.size == RitualCircle.Size.small){
                return checkValidCircle(world, circle, mutable, RitualCircle.small);
            }
            if(circle.size == RitualCircle.Size.medium){
                return checkValidCircle(world, circle, mutable, RitualCircle.medium);
            }
            if(circle.size == RitualCircle.Size.large){
                return checkValidCircle(world, circle, mutable, RitualCircle.large);
            }
        }
        return false;
    }

    private boolean checkValidCircle(World world, RitualCircle circle, BlockPos.Mutable mutable, byte[][] size){
        for (int x = 0; x < size.length; x++) {
            for (int z = 0; z < size.length; z++) {
                if (size[x][z] == 1 && !isValidGlyph(circle.type, world.getBlockState(mutable.set(pos.getX() + (x - size.length / 2), pos.getY(), pos.getZ() + (z - size.length / 2))).getBlock())) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isValidGlyph(RitualCircle.Type type, Block block) {
        if(type.equals(RitualCircle.Type.chalk) && block == WKBlocks.GLYPH){
            return true;
        }else if(type.equals(RitualCircle.Type.salt) && block == WKBlocks.SALT_BLOCK){
            return true;
        }else if(type.equals(RitualCircle.Type.candle) && block == Blocks.CANDLE){
            return true;
        }
        return false;
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        progress = nbt.getInt("Progress");
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putInt("Progress", progress);
    }


}
