package cf.witcheskitchen.api.fluid;


import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

/**
 * <h3>Represents a stack of fluids.</h3>.
 *
 * <h2 id="nbt-operations">NBT operations</h2>
 *
 * <h3>NBT serialization</h3>
 * <p>
 * An Item Stack can be serialized with {@link #writeToNbt(NbtCompound)}, and deserialized with {@link #fromNbt(NbtCompound)}.
 * <div class="fabric">
 * <table border=1>
 * <caption>Serialized NBT Structure</caption>
 * <tr>
 *   <th>Key</th><th>Type</th><th>Purpose</th>
 * </tr>
 * <tr>
 *   <td>{@code Fluid}</td><td>{@link net.minecraft.nbt.NbtString}</td><td>The identifier for the internal fluid.</td>
 * </tr>
 * <tr>
 *   <td>{@code Amount}</td><td>{@link net.minecraft.nbt.NbtInt}</td><td>The amount of fluids (in MilliBuckets) in the stack.</td>
 * </tr>
 * <tr>
 *   <td>{@code tag}</td><td>{@link NbtCompound}</td><td>The fluid stack internal nbt data.</td>
 * </tr>
 * </table>
 * </div>
 * <h3>Custom NBT</h3>
 * <p>
 * The fluid stack {@link NbtCompound} may be used to store extra information,
 * such as the type of fluid it contains, the amount, etc
 * <p>
 */
public final class FluidStack implements Comparable<FluidStack> {
    /**
     * Empty FluidStack instance (similar to a {@link net.minecraft.item.ItemStack#EMPTY)}
     */
    public static final FluidStack EMPTY = new FluidStack(Fluids.EMPTY, 0);

    // ---------- NBT DATA KEYS --------- //

    /**
     * The key of the amount of fluid in a fluid stack's custom NBT, whose value is {@value}.
     */
    private static final String AMOUNT_KEY = "Amount";
    /**
     * The key of the internal fluid NBT in a fluid stack's custom NBT, whose value is {@value}.
     */
    private static final String FLUID_KEY = "Fluid";
    /**
     * The key of the fluid stack's internal-sub nbt, whose value is {@value}.
     */
    private static final String INTERNAL_NBT_DATA = "Data";
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
    private NbtCompound data;

    // Default constructor
    public FluidStack(Fluid fluid, int amount) {
        this.fluid = fluid;
        this.amount = amount;
        updateEmptyState();
    }

    private FluidStack(Fluid fluid, int amount, NbtCompound nbt) {
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
    @Nonnull
    public static FluidStack fromNbt(NbtCompound nbt) {
        if (nbt == null) {
            return FluidStack.EMPTY;
        }
        final Fluid fluid = Registries.FLUID.get(new Identifier(nbt.getString(FLUID_KEY)));
        final int amount = nbt.getInt("Amount");
        final FluidStack stack = new FluidStack(fluid, amount);
        if (nbt.contains(INTERNAL_NBT_DATA)) {
            stack.data = nbt.getCompound("Tag");
        }
        return stack;
    }

    /**
     * Writes the serialized fluid stack into the given {@link NbtCompound}.
     *
     * @param nbt the NBT compound to write to
     * @return the written NBT compound
     * @see <a href="#nbt-operations">Fluid Stack NBT Operations</a>
     */
    public NbtCompound writeToNbt(NbtCompound nbt) {
        nbt.putString(FLUID_KEY, Registries.FLUID.getId(this.fluid).toString());
        nbt.putInt(AMOUNT_KEY, this.amount);
        if (this.data != null && !this.data.isEmpty()) {
            nbt.put(INTERNAL_NBT_DATA, this.data);
        }
        return nbt;
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
    public boolean isEqualIgnoreNbt(@Nonnull FluidStack other) {
        return this.hasFluid(other.getFluid());
    }

    /**
     * @return whether the given fluid stacks have equivalent custom {@link NbtCompound}
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
    public NbtCompound getNbt() {
        return this.data;
    }

    /**
     * Sets the custom NBT of this item stack.
     *
     * @param data the custom NBT compound, may be {@code null} to reset
     * @see <a href="#nbt-operations">Fluid Stack NBT Operations</a>
     */
    public void setNbt(NbtCompound data) {
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
