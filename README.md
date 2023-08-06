# dream-gui (opis)

Small framework to creating inventories in a simple way.

## Register (inside onEnable in your plugin main class)

With MiniMessage:

```
Dream.register(yourPluginInstance, new MiniMessageTextFormatter());
```

With legacy:

```
Dream.register(yourPluginInstance, new LegacyTextFormatter());
```

#### Classic menu (building)

```
        DreamMenu dreamMenu = Dream.classic()
                .rows(6)
                .title("&cExample Menu")
                .disableInteractions()
                .build();

        dreamMenu.addItem(new DreamItem(new ItemStack(Material.DIRT)));

        dreamMenu.addItem(DreamItemBuilder.of(Material.DIRT)
                .buildAsDream(event-> player.sendMessage("dirt")));

        dreamMenu.setItem(1,DreamItemBuilder.of(new ItemStack(Material.COBBLESTONE))
                .buildAsDream(event-> player.sendMessage("Click")));

        dreamMenu.setItem(1,5,DreamItemBuilder.of(new ItemStack(Material.COBBLESTONE))
                .buildAsDream(event-> player.sendMessage("Click")));

        dreamMenu.open(player);
```

#### Paginated menu (building)

```
        DreamPaginatedMenu dreamPaginatedMenu = Dream.paginated()
                .template(Dream.classic()
                        .title("&cPage: &7{PAGE}&8/&7{MAX_PAGE}")
                        .rows(6)
                        .disableInteractions()
                        .build())
                .nextPageDoesNotExistMessage("&cNext page doesn't exist!")
                .previousPageDoesNotExistMessage("&cPrevious page doesn't exist!")
                .build();

        dreamPaginatedMenu.getTemplate()
                .getFiller()
                .fillBorder(DreamItemBuilder.of(Material.BLACK_STAINED_GLASS_PANE)
                        .buildAsDream());

        dreamPaginatedMenu.previousPage(47, new ItemStack(Material.STONE_BUTTON));
        dreamPaginatedMenu.nextPage(51, new ItemStack(Material.STONE_BUTTON));


        List<String> formattedLore = Dream.textBuilder()
                .text(
                        "&cAmazing text builder!",
                        "&aYour placeholder here: &b{EXAMPLE_PLACEHOLDER}"
                )
                .placeholder("{EXAMPLE_PLACEHOLDER}", "Nice placeholder!")
                .build();

        for (int i = 0; i < 40; i++) {
            dreamPaginatedMenu.addItem(DreamItemBuilder.of(Material.COBBLESTONE)
                    .lore(formattedLore)
                    .buildAsDream(event -> player.sendMessage(Dream.textFormatter().parse("&7Cobblestone"))));
        }

        dreamPaginatedMenu.open(player);
```
