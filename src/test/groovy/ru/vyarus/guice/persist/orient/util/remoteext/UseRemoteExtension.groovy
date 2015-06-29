package ru.vyarus.guice.persist.orient.util.remoteext

import org.spockframework.runtime.extension.AbstractAnnotationDrivenExtension
import org.spockframework.runtime.model.SpecInfo

/**
 * @author Vyacheslav Rusakov 
 * @since 03.05.2015
 */
class UseRemoteExtension extends AbstractAnnotationDrivenExtension<UseRemote> {

    @Override
    void visitSpecAnnotation(UseRemote annotation, SpecInfo spec) {
    }

    @Override
    void visitSpec(SpecInfo spec) {
        UseRemoteInterceptor interceptor = new UseRemoteInterceptor()
        // important to run before guice interceptor
        spec.sharedInitializerInterceptors.add(0, interceptor)
        spec.addSetupSpecInterceptor(interceptor)
        spec.addCleanupSpecInterceptor(interceptor)
        spec.addSetupInterceptor(interceptor)
    }
}
