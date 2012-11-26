package Builders;

/**
 * Created with IntelliJ IDEA.
 * User: pedro
 * Date: 07/11/12
 * Time: 23:26
 * To change this template use File | Settings | File Templates.
 */
public class SuperClass {
    private final int superField;

    //@SuppressWarnings("rawtypes") // or generic parameter
    SuperClass(Builder<? extends Builder<?>> builder) {
        superField = builder.superField;
    }

    public static class Builder<B extends Builder<B>> extends AbstractBuilder<B> {

        private int superField;

        public B superField(int a) {
            this.superField = a;

            return self();
        }

        @Override
        public SuperClass build() {
            return new SuperClass(this);
        }
    }
}
