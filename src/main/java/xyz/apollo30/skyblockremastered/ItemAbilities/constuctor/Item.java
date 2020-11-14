package xyz.apollo30.skyblockremastered.ItemAbilities.constuctor;

import org.bukkit.inventory.ItemStack;

public abstract class Item extends ItemStack {

    protected ItemStack item;

    /*
    protected Inventory inv;
    protected MenuUtility menuUtility;
    protected ItemStack GLASS = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 15);
    public Menu(MenuUtility menuUtility) {
        this.menuUtility = menuUtility;
    }

    public abstract String getMenuName();

    public abstract int getSlots();

    public abstract void handleMenu(InventoryClickEvent e);

    public abstract void setMenuItems();

    public void open() {
        inv = Bukkit.createInventory(this, getSlots(), getMenuName());
        this.setMenuItems();
        menuUtility.getOwner().openInventory(inv);
    }

    public ItemStack makeItem(Material material, String displayName, String... lore) {

        ItemStack item = new ItemStack(material);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(Arrays.asList(lore));
        item.setItemMeta(itemMeta);

        return item;
    }


    @Override
    public Inventory getInventory() {
        return inv;
    }
     */

}
