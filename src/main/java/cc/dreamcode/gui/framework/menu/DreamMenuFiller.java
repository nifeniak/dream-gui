package cc.dreamcode.gui.framework.menu;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class DreamMenuFiller {

    private final DreamMenu menu;

    public void fill(@NotNull DreamItem dreamItem) {
        int size = this.menu.getRows() * 9;
        for (int i = 0; i < size - 1; i++) {
            this.menu.setItem(i, dreamItem);
        }

    }

    public void fillBorder(@NotNull DreamItem dreamItem) {
        int rows = this.menu.getRows();
        if (rows <= 2) {
            return;
        }

        for (int i = 0; i < rows * 9; i++) {
            if ((i <= 8) || (i >= (rows * 9) - 8) && (i <= (rows * 9) - 2) || i % 9 == 0 || i % 9 == 8) {
                this.menu.setItem(i, dreamItem);
            }

        }

    }

}
