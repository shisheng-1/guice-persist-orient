package ru.vyarus.guice.persist.orient.repository.command.param

import ru.vyarus.guice.persist.orient.repository.command.core.el.ElDescriptor
import ru.vyarus.guice.persist.orient.repository.command.core.param.QueryParamsContext
import ru.vyarus.guice.persist.orient.repository.command.core.spi.CommandMethodDescriptor
import ru.vyarus.guice.persist.orient.repository.core.MethodDefinitionException
import ru.vyarus.guice.persist.orient.repository.core.spi.parameter.ParamInfo
import spock.lang.Specification


/**
 * @author Vyacheslav Rusakov 
 * @since 23.02.2015
 */
class ParamContextTest extends Specification {

    def "Check param descriptor"() {

        when: "assigning params"
        QueryParamsContext context = new QueryParamsContext(null)
        context.setOrdinals([])
        CommandMethodDescriptor desc = new CommandMethodDescriptor()
        context.addNamedParam("tst", new ParamInfo(1, Object))
        context.addNamedParam("tst2", new ParamInfo(2, Object))
        context.process(desc)
        then: "processed context contains values"
        desc.params.namedParametersIndex == ['tst': 1, 'tst2': 2]

        when: "assigning vars"
        context = new QueryParamsContext(null)
        context.setOrdinals([])
        desc = new CommandMethodDescriptor()
        desc.command = 'string ${tst} ${tst2}'
        desc.el = new ElDescriptor(['tst', 'tst2'])
        context.addDynamicElVarValue("tst")
        context.addStaticElVarValue("tst2", 'word')
        context.process(desc)
        then: "processed context contains vars"
        desc.el.directValues == ['tst2': 'word']
        desc.el.handledVars == ['tst']

        when: "assigning duplicate named"
        context = new QueryParamsContext(null)
        context.addNamedParam("tst", new ParamInfo(1, Object))
        context.addNamedParam("tst", new ParamInfo(3, Object))
        then: "error"
        thrown(MethodDefinitionException)

        when: "assigning duplicate static var"
        context = new QueryParamsContext(null)
        context.addStaticElVarValue("tst", 'word')
        context.addStaticElVarValue("tst", 'test')
        then: "error"
        thrown(MethodDefinitionException)

        when: "assigning duplicate dynamic var"
        context = new QueryParamsContext(null)
        context.addDynamicElVarValue("tst")
        context.addDynamicElVarValue("tst")
        then: "error"
        thrown(MethodDefinitionException)
    }
}