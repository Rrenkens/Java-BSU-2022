package by.AlexAzyavchikov.quizer.tasks;

public abstract class AbstractTask implements Task {
    protected String text;

    public String getText() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof AbstractTask)) {
            return false;
        }
        return this.text.equals(((AbstractTask) o).text);
    }

    @Override
    public int hashCode() {
        return text.hashCode();
    }
}
