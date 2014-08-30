package ru.vyarus.guice.persist.orient.db.transaction.template;

import com.google.inject.Provider;
import ru.vyarus.guice.persist.orient.db.transaction.TxConfig;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Transaction template to be used with specific (single) connection type (reduce boilerplate, comparing to generic
 * {@code ru.vyarus.guice.persist.orient.db.transaction.template.TxTemplate}).
 * Internally use generic transaction template.
 *
 * @param <C> required connection type
 * @author Vyacheslav Rusakov
 * @since 25.07.2014
 */
@Singleton
public class SpecificTxTemplate<C> {

    private final TxTemplate template;
    private final Provider<C> provider;

    @Inject
    public SpecificTxTemplate(final TxTemplate template,
                              final Provider<C> provider) {
        this.template = template;
        this.provider = provider;
    }

    /**
     * @param action action to execute within transaction (new or ongoing)
     * @param <T>    return value type
     * @return value produced by action
     * @throws Throwable re-throws error thrown by action or after commit or rollback error
     */
    public <T> T doInTransaction(final SpecificTxAction<T, C> action) throws Throwable {
        return doInTransaction(null, action);
    }

    /**
     * @param config transaction config (ignored in case of ongoing transaction)
     * @param action action to execute within transaction (new or ongoing)
     * @param <T>    return value type
     * @return value produced by action
     * @throws Throwable re-throws error thrown by action or after commit or rollback error
     */
    public <T> T doInTransaction(final TxConfig config,
                                 final SpecificTxAction<T, C> action) throws Throwable {
        return template.doInTransaction(config, new TxAction<T>() {
            @Override
            public T execute() throws Throwable {
                return action.execute(provider.get());
            }
        });
    }
}
