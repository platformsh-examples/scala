package sh.platform.template

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

import scala.annotation.meta.field
import scala.beans.BeanProperty


@Document class User
(@(Id@field) @BeanProperty var id: String,
 @BeanProperty var name: String,
 @BeanProperty var country: String) {

  def this() = this(null, null, null)
}