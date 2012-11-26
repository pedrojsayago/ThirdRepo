package Builders;

/**
 * Created with IntelliJ IDEA.
 * User: pedro
 * Date: 07/11/12
 * Time: 23:27
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractBuilder<B extends AbstractBuilder<B>> {

    @SuppressWarnings("unchecked")
    protected B self() {
        return (B) this;
    }

    public abstract Object build();
}
