package ru.vyarus.guice.persist.orient.support.model4mapper

import ru.vyarus.guice.persist.orient.db.scheme.annotation.EdgeType
import ru.vyarus.guice.persist.orient.support.model.VertexModel

/**
 * NOT AN ERROR
 * Using to check V/E clash in hierarchy for mapper
 *
 * @author Vyacheslav Rusakov 
 * @since 04.08.2014
 */
@EdgeType
class BadComplexEdgeModel extends VertexModel{
}
