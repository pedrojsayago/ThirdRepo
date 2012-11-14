package Builders;

/**
 * Created with IntelliJ IDEA.
 * User: pedro
 * Date: 07/11/12
 * Time: 23:25
 * To change this template use File | Settings | File Templates.
 */
public class SubClass extends SuperClass{
    private final int subField;

    private SubClass(Builder builder) {
        super(builder);

        subField = builder.subField;
    }

    public static class Builder extends SuperClass.Builder<Builder> {

        private int subField;

        public Builder subField(int b) {
            subField = b;

            return this; // or self()
        }

        @Override
        public SubClass build() {
            return new SubClass(this);
        }
    }
}
