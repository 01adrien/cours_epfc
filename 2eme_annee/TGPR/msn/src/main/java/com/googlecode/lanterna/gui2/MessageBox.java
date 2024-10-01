package com.googlecode.lanterna.gui2;

import com.googlecode.lanterna.gui2.dialogs.DialogWindow;

import java.util.List;

public class MessageBox extends DialogWindow {
    private String clickedButton = null;

    public String getClickedButton() {
        return clickedButton;
    }

    public MessageBox(String message, String title, String... buttons) {
        super(title);

        setHints(List.of(Hint.CENTERED));
        setCloseWindowWithEscape(true);

        Panel root = Panel.gridPanel(1, Margin.of(1, 1, 1, 0));
        setComponent(root);

        new Label(message).addTo(root);
        new EmptySpace().addTo(root);
        var buttonsPanel = Panel.horizontalPanel().addTo(root);
        int i = 0;
        for (var button : buttons) {
            var btn = new Button(button, () -> close(button)).addTo(buttonsPanel);
            if (i == 0)
                btn.takeFocus();
            ++i;
        }
    }

    private void close(String button) {
        clickedButton = button;
        close();
    }
}
