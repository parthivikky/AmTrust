package mobello.amtrust.com.model;

import com.joanzapata.iconify.fonts.FontAwesomeIcons;

/**
 * Created by Vicky on 12-Aug-16.
 */
public class Test {
    private String name;
    private FontAwesomeIcons icon;
    private boolean isCompleted;

    public Test(String name, boolean isCompleted) {
        this.name = name;
        this.isCompleted = isCompleted;
    }

    public Test(String name, FontAwesomeIcons icon, boolean isCompleted) {
        this.name = name;
        this.icon = icon;
        this.isCompleted = isCompleted;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public FontAwesomeIcons getIcon() {
        return icon;
    }

    public void setIcon(FontAwesomeIcons icon) {
        this.icon = icon;
    }
}
