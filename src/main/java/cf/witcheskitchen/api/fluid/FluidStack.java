package cf.witcheskitchen.api.fluid;


import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * <h3>Represents a stack of fluids.</h3>.
 *
 * <h2 id="nbt-operations">NBT operations</h2>
 *
 * <h3>NBT serialization</h3>
 * <p>
 * An Item Stack can be serialized with {@link #writeToNbt(CompoundTag)}, and deserialized with {@link #fromNbt(CompoundTag)}.
 * <div class="fabric">
 * <table border=1>
 * <caption>Serialized NBT Structure</caption>
 * <tr>
 *   <th>Key</th><th>Type</th><th>Purpose</th>
 * </tr>
 * <tr>
 *   <td>{@code Fluid}</td><td>{@link net.minecraft.nbt.StringTag}</td><td>The identifier for the internal fluid.</td>
 * </tr>
 * <tr>
 *   <td>{@code Amount}</td><td>{@link net.minecraft.nbt.IntTag}</td><td>The amount of fluids (in MilliBuckets) in the stack.</td>
 * </tr>
 * <tr>
 *   <td>{@code tag}</td><td>{@link CompoundTag}</td><td>The fluid stack internal nbt data.</td>
 * </tr>
 * </table>
 * </div>
 * <h3>Custom NBT</h3>
 * <p>
 * The fluid stack {@link CompoundTag} may be used to store extra information,
 * such as the type of fluid it contains, the amount, etc
 * <p>
 */
public final class FluidStack implements Comparable<FluidStack> {
    public static final Codec<FluidStack> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                            BuiltInRegistries.FLUID.byNameCodec()
                                    .fieldOf("fluid")
                                    .forGetter(FluidStack::getFluid),
                            Codec.INT
                                    .fieldOf("amount")
                                    .forGetter(FluidStack::getAmount),
                            CompoundTag.CODEC
                                    .optionalFieldOf("data", null)
                                    .forGetter(FluidStack::getNbt)
                    )
                    .apply(instance, FluidStack::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, FluidStack> PACKET_CODEC = StreamCodec.composite(
            ByteBufCodecs.registry(Registries.FLUID), FluidStack::getFluid,
            ByteBufCodecs.VAR_INT, FluidStack::getAmount,
            ByteBufCodecs.COMPOUND_TAG, FluidStack::getNbt,
            FluidStack::new
    );

    /**
     * Empty FluidStack instance (similar to a {@link net.minecraft.item.ItemStack#EMPTY)}
     */
    public static final FluidStack EMPTY = new FluidStack(Fluids.EMPTY, 0);

    /**
     * Internal Fluid type of the stack
     */
    private final Fluid fluid;
    /**
     * Represents the <b>amount</b> of fluid in <b>MilliBuckets</b>
     * this fluid stack has. <br>
     * For reference, look at {@link WKFluidAPI#BUCKET_VOLUME}
     */
    private int amount;
    /**
     * Determines whether this stack is empty or not
     */
    private boolean empty;
    /**
     * Represents the item stack's custom NBT.
     * <p>
     * Stored at the key {@code tag} in the serialized fluid stack NBT.
     */
    @Nullable
    private CompoundTag data;

    // Default constructor
    public FluidStack(Fluid fluid, int amount) {
        this.fluid = fluid;
        this.amount = amount;
        updateEmptyState();
    }

    private FluidStack(Fluid fluid, int amount, CompoundTag nbt) {
        this(fluid, amount);
        if (nbt != null) {
            this.data = nbt.copy();
        }
    }

    public static FluidStack fromStack(FluidStack old, int amount) {
        return new FluidStack(old.getFluid(), amount, old.data);
    }

    /**
     * Deserializes a fluid stack from NBT.
     *
     * @see <a href="#nbt-operations">Fluid Stack NBT Operations</a>
     */
    @NotNull
    public static FluidStack fromNbt(CompoundTag nbt) {
        if (nbt == null) {
            return FluidStack.EMPTY;
        }
        return CODEC.parse(NbtOps.INSTANCE, nbt).getOrThrow();
    }

    /**
     * Deserializes a fluid stack from a data view.
     *
     * @see <a href="#nbt-operations">Fluid Stack NBT Operations</a>
     */
    @NotNull
    public static FluidStack fromData(ValueInput data) {
        if (data == null) {
            return FluidStack.EMPTY;
        }
        // FIXME: this mismatches the NBT operations.
        return data.read("FluidStack", CODEC).orElse(FluidStack.EMPTY);
    }

    /**
     * Writes the serialized fluid stack into the given {@link CompoundTag}.
     *
     * @param nbt the NBT compound to write to
     * @return the written NBT compound
     * @see <a href="#nbt-operations">Fluid Stack NBT Operations</a>
     */
    public CompoundTag writeToNbt(CompoundTag nbt) {
        CODEC.encode(this, NbtOps.INSTANCE, nbt);
        return nbt;
    }

    /**
     * Writes the serialized fluid stack into the given {@link ValueOutput}.
     *
     * @param data the view to write to
     * @see <a href="#nbt-operations">Fluid Stack NBT Operations</a>
     */
    public void writeToData(ValueOutput data) {
        // FIXME: this mismatches the NBT operations.
        data.store("FluidStack", CODEC, this);
    }

    /**
     * Determines whether both stacks have equivalent fluid tyoes
     *
     * @param other {@link Fluid}
     * @return whether they are partially equivalent
     */
    public boolean hasFluid(Fluid other) {
        return this.fluid.equals(other);
    }

    /**
     * Determines whether both stacks are fully equal
     * checking the internal fluid and nbt but ignoring the amount of fluid.
     *
     * @param other {@link FluidStack} to compare
     * @return Whether this stack is equal to the other
     */
    public boolean isEqualIgnoreNbt(@NotNull FluidStack other) {
        return this.hasFluid(other.getFluid());
    }

    /**
     * @return whether the given fluid stacks have equivalent custom {@link CompoundTag}
     */
    public boolean isTagEqualTo(FluidStack other) {
        if (this.data == null && other.data == null) {
            return true;
        } else if (this.data != null && other.data != null) {
            return this.data.equals(other.data);
        } else {
            return false;
        }
    }

    /**
     * @return The internal {@link Fluid} of this stack
     */
    public Fluid getFluid() {
        return fluid;
    }

    /**
     * @return The amount of fluid in MilliBuckets of this fluid stack
     */
    public int getAmount() {
        if (this.empty) {
            return 0;
        } else {
            return amount;
        }
    }

    /**
     * Sets the amount of fluid in this fluid stack.
     *
     * @param amount the count of items
     */
    public void setAmount(int amount) {
        this.amount = amount;
        this.empty = isEmpty();
    }

    /**
     * Determines whether this fluid stack can be considered as "empty"
     *
     * @return Whether the stack is empty.
     */
    public boolean isEmpty() {
        if (this == EMPTY) {
            return true;
        }
        if (this.fluid == null || this.fluid == Fluids.EMPTY) {
            return true;
        }
        return this.amount <= 0;
    }

    /**
     * @return the custom NBT of this fluid stack, which may be <b>null</b>.
     * @see <a href="#nbt-operations">Item Stack NBT Operations</a>
     */
    public CompoundTag getNbt() {
        return this.data;
    }

    /**
     * Sets the custom NBT of this item stack.
     *
     * @param data the custom NBT compound, may be {@code null} to reset
     * @see <a href="#nbt-operations">Fluid Stack NBT Operations</a>
     */
    public void setNbt(CompoundTag data) {
        this.data = data;
    }

    // used for internal update
    private void updateEmptyState() {
        this.empty = false;
        this.empty = this.isEmpty();
    }

    // Fluid Stack equals implementation
    @Override
    public boolean equals(Object object) {
        if (object instanceof FluidStack stackObj) {
            return this.isEqualIgnoreNbt(stackObj) && this.isTagEqualTo(stackObj);
        } else {
            return false;
        }
    }

    // Fluid Stack hashCode implementation
    @Override
    public int hashCode() {
        int result = fluid.hashCode();
        result = 31 * result + amount;
        result = 31 * result + (empty ? 1 : 0);
        result = 31 * result + (data != null ? data.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FluidStack{" +
                "fluid=" + fluid +
                ", amount=" + amount +
                ", empty=" + empty +
                ", data=" + data +
                '}';
    }

    @Override
    public int compareTo(@NotNull FluidStack other) {
        if (!this.isEqualIgnoreNbt(other)) {
            return -1;
        } else {
            return Integer.compare(this.amount, other.amount);
        }
    }
}
